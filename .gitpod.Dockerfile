FROM gitpod/workspace-full-vnc

USER root

RUN add-apt-repository ppa:maarten-fonville/android-studio && \
    apt-get update && \
    apt-get install android-sdk \
        lib32stdc++6 \
        android-studio \
        && apt-get clean \
        && rm -rf /var/lib/apt/lists/*

RUN mkdir /usr/lib/android-sdk/licenses \
    && echo "8933bad161af4178b1185d1a37fbf41ea5269c55" > /usr/lib/android-sdk/licenses/android-sdk-license \
    && echo "d56f5187479451eabf01fb78af6dfcb131a6481e" > /usr/lib/android-sdk/licenses/android-sdk-preview-license \
    && sdkmanager --update --sdk_root=/usr/lib/android-sdk \
    && sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0" --sdk_root=/usr/lib/android-sdk \
    && yes | sdkmanager --licenses --sdk_root=/usr/lib/android-sdk
		
# Add the Tailscale installation command
RUN curl -fsSL https://tailscale.com/install.sh | sh