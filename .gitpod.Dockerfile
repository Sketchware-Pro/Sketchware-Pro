FROM gitpod/workspace-full-vnc

SHELL ["/bin/bash", "-c"]
ENV ANDROID_HOME=$HOME/Android/Sdk
ENV PATH="$HOME/Android/Sdk/emulator:$HOME/Android/Sdk/tools:$HOME/Android/Sdk/cmdline-tools/latest/bin:$HOME/Android/Sdk/platform-tools:$PATH"

USER root

# Add the Tailscale installation command
RUN curl -fsSL https://pkgs.tailscale.com/stable/ubuntu/focal.gpg | sudo apt-key add - \
     && curl -fsSL https://pkgs.tailscale.com/stable/ubuntu/focal.list | sudo tee /etc/apt/sources.list.d/tailscale.list \
     && apt-get update \
     && apt-get install -y tailscale
RUN update-alternatives --set ip6tables /usr/sbin/ip6tables-nft

RUN sed -i 's|resize=scale|resize=remote|g' /opt/novnc/index.html

RUN add-apt-repository ppa:maarten-fonville/android-studio && \
    apt-get update && \
    apt-get install android-sdk \
        lib32stdc++6 \
        android-studio \
        android-sdk-platform-23 --no-install-recommends --yes \
        && apt-get clean \
        && rm -rf /var/lib/apt/lists/*

# Install Android SDK
RUN mkdir -p /home/gitpod/Android/Sdk  
RUN wget "https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip" 
RUN unzip "commandlinetools-linux-11076708_latest.zip" -d $ANDROID_HOME 
RUN rm -f "commandlinetools-linux-11076708_latest.zip" 
RUN mkdir -p $ANDROID_HOME/cmdline-tools/latest 
RUN mv $ANDROID_HOME/cmdline-tools/{bin,lib} $ANDROID_HOME/cmdline-tools/latest 

#RUN  \
#      yes  | sdkmanager --licenses
#RUN  \
#      yes  | sdkmanager "platform-tools" "build-tools;34.0.0" "platforms;android-34" 
