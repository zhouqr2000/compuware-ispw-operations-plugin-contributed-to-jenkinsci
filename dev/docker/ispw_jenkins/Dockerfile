FROM jenkins/jenkins:lts
LABEL author="Sam Zhou <sam.zhou@compuware.com>"

USER root

RUN apt-get update && apt-get install -y apt-utils vim nano mc wget unzip && rm -rf /var/lib/apt/lists/*

COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/plugins.txt
