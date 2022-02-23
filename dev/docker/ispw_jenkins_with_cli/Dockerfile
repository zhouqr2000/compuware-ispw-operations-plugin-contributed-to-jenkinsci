FROM jenkins/jenkins
LABEL author="Compuware - A BMC Company"

USER root

RUN apt-get --no-install-recommends update \
    && apt-get install -y --no-install-recommends unzip git-core wget apt-utils vim nano mc \
    && rm -rf /var/lib/apt/lists/*

COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/plugins.txt

RUN mkdir /TopazCliInstall
COPY ./dist/TopazCLI-linux.gtk.x86_64.zip /TopazCliInstall/TopazCLI-linux.gtk.x86_64.zip

WORKDIR "/TopazCliInstall"
RUN unzip ./TopazCLI-linux.gtk.x86_64.zip
RUN chmod 777 ./*.sh
RUN rm -rf ./TopazCLI-linux.gtk.x86_64.zip