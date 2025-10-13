# Native architecture - manually install build-tools to avoid architecture issues
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

# Create Android SDK directory structure
RUN mkdir -p ${ANDROID_HOME}/cmdline-tools ${ANDROID_HOME}/build-tools ${ANDROID_HOME}/platforms

# Download and install Android command line tools (ARM64/x86_64 auto-detect)
RUN ARCH=$(uname -m) && \
    CMDLINE_TOOLS_URL="https://dl.google.com/android/repository/commandlinetools-linux-13114758_latest.zip"; \
    wget -q ${CMDLINE_TOOLS_URL} -O /tmp/cmdline-tools.zip && \
    unzip -q /tmp/cmdline-tools.zip -d /tmp && \
    mv /tmp/cmdline-tools ${ANDROID_HOME}/cmdline-tools/latest && \
    rm /tmp/cmdline-tools.zip

# Manually download and install build-tools (workaround for architecture compatibility)
# This avoids sdkmanager issues with build-tools on different architectures
RUN wget -q https://dl.google.com/android/repository/build-tools_r34-linux.zip -O /tmp/build-tools.zip && \
    unzip -q /tmp/build-tools.zip -d ${ANDROID_HOME}/build-tools && \
    mv ${ANDROID_HOME}/build-tools/android-14 ${ANDROID_HOME}/build-tools/34.0.0 && \
    rm /tmp/build-tools.zip

# Accept Android SDK licenses
RUN yes | sdkmanager --licenses || true

# Install remaining Android SDK components via sdkmanager
RUN sdkmanager --update && \
    sdkmanager \
    "platform-tools" \
    "platforms;android-34" \
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
