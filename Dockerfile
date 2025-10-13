# Use native ARM64 for Apple Silicon - much faster!
FROM ubuntu:22.04

# Prevent interactive prompts during package installation
ENV DEBIAN_FRONTEND=noninteractive

# Set environment variables
ENV ANDROID_HOME=/opt/android-sdk
ENV ANDROID_SDK_ROOT=${ANDROID_HOME}
ENV PATH=${PATH}:${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/platform-tools:${ANDROID_HOME}/build-tools/34.0.0

# Install required packages
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    git \
    openjdk-17-jdk \
    build-essential \
    curl \
    file \
    && rm -rf /var/lib/apt/lists/*

# Create Android SDK directory
RUN mkdir -p ${ANDROID_HOME}/cmdline-tools

# Detect architecture and download appropriate Android command line tools
# ARM64 version is available and works natively on Apple Silicon
RUN ARCH=$(uname -m) && \
    if [ "$ARCH" = "aarch64" ] || [ "$ARCH" = "arm64" ]; then \
        echo "Downloading ARM64 Android SDK tools..."; \
        wget -q https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip -O /tmp/cmdline-tools.zip; \
    else \
        echo "Downloading x86_64 Android SDK tools..."; \
        wget -q https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip -O /tmp/cmdline-tools.zip; \
    fi && \
    unzip -q /tmp/cmdline-tools.zip -d /tmp && \
    mv /tmp/cmdline-tools ${ANDROID_HOME}/cmdline-tools/latest && \
    rm /tmp/cmdline-tools.zip

# Accept Android SDK licenses
RUN yes | sdkmanager --licenses

# Install Android SDK components
RUN sdkmanager --update && \
    sdkmanager \
    "platform-tools" \
    "platforms;android-34" \
    "build-tools;34.0.0" \
    "ndk;25.2.9519653" \
    "cmake;3.22.1"

# Set working directory
WORKDIR /workspace

# Create a non-root user for builds (optional but recommended)
RUN useradd -m -u 1000 -s /bin/bash builder && \
    chown -R builder:builder /workspace && \
    chown -R builder:builder ${ANDROID_HOME}

USER builder

CMD ["/bin/bash"]
