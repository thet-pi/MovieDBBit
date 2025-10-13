# Docker Build Instructions for Apple Silicon

## ARM64 Native Build

This Docker configuration is optimized exclusively for **Apple Silicon (M1/M2/M3/M4) Macs**.

## Solution
The Dockerfile is configured to use:
- **linux/arm64 platform** - enforces ARM64 architecture
- **ARM64 Android SDK tools** (version 11076708) - native ARM64 binaries
- **No emulation** - runs natively on Apple Silicon for maximum performance

⚠️ **Note**: This will NOT work on Intel Macs. If you need Intel compatibility, remove the `--platform=linux/arm64` flags.

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
docker build --platform linux/arm64 -t android-builder .
docker run --platform linux/arm64 -v $(pwd)/app:/workspace android-builder ./gradlew clean assembleDebug
```

## Notes
- This configuration uses **ARM64 exclusively** - optimized for Apple Silicon
- Gradle cache is persisted in a Docker volume for faster subsequent builds
- Will fail on Intel Macs (by design)

## Advantages of ARM64-Only Configuration
- **Maximum performance**: Native ARM64 execution, no compatibility overhead
- **Faster builds**: Typically 2-3x faster than x86_64 emulation
- **Lower resource usage**: Better CPU efficiency and battery life
- **Simpler configuration**: No architecture detection needed
- **Official support**: Google provides full ARM64 Android SDK

## For Intel Mac Users
If you need to build on an Intel Mac, you'll need to:
1. Change `--platform=linux/arm64` to `--platform=linux/amd64` in both files
2. Change the SDK URL to: `commandlinetools-linux-9477386_latest.zip`
