FROM gitpod/workspace-full-vnc

SHELL ["/bin/bash", "-c"]

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
