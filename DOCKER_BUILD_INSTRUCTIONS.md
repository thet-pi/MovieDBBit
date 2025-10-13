# Docker Build Instructions for Apple Silicon

## Problem
When building Android apps on Apple Silicon (M1/M2/M3) Macs using Docker, you may encounter compatibility issues with Android build tools.

## Solution
The Dockerfile has been configured to automatically detect the architecture and download the appropriate Android SDK command-line tools:
- **ARM64 version** for Apple Silicon Macs (native, much faster!)
- **x86_64 version** for Intel Macs

This provides the best performance by avoiding emulation layers entirely.

## Build and Run Commands

### Clean rebuild (recommended after platform changes)
```bash
docker-compose down -v
docker-compose build --no-cache
docker-compose up -d
```

### Standard build
```bash
docker-compose build
docker-compose up -d
```

### Run Gradle commands inside the container
```bash
docker-compose exec android-builder bash
# Then inside the container:
./gradlew clean assembleDebug
```

### Or run directly
```bash
docker-compose exec android-builder ./gradlew clean assembleDebug
```

## Alternative: Build without docker-compose
```bash
docker build -t android-builder .
docker run -v $(pwd)/app:/workspace android-builder ./gradlew clean assembleDebug
```

## Notes
- The Dockerfile automatically detects your architecture and downloads the correct SDK tools
- On Apple Silicon, this uses native ARM64 - no emulation overhead!
- On Intel Macs, this uses x86_64 natively
- Gradle cache is persisted in a Docker volume for faster subsequent builds
- Build performance should be significantly better than using x86_64 emulation

## Advantages of ARM64 Native Build
- **Much faster**: No Rosetta/QEMU translation layer
- **Lower CPU usage**: Native ARM64 execution
- **Better battery life**: More efficient on Apple Silicon
- **Same compatibility**: Android SDK officially supports ARM64
