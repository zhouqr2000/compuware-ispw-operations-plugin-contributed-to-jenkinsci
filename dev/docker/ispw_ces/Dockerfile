FROM opensuse:latest
LABEL author="Sam Zhou <sam.zhou@compuware.com>"

RUN zypper up -y &&\
	zypper -q --non-interactive install \
	bats \
	curl \
	figlet \
	findutils \
	git-core \
	make \
	mc \
	siege \
	wget \
	vim \
	unzip \
	expect \
	java-1_8_0-openjdk

COPY Linux /tmp/Linux/cpwr/Linux

WORKDIR "/tmp/Linux/cpwr/Linux/Disk1/InstData/NoVM"
RUN chmod +x ./install.bin
COPY install-auto.sh .
RUN chmod +x ./install-auto.sh
RUN ./install-auto.sh

WORKDIR "/tmp"
RUN rm -rf Linux

WORKDIR "/opt/Compuware/CES"
COPY execces.sh .
RUN chmod +x ./execces.sh

RUN ln -sf /dev/stdout /opt/Compuware/CES/data/ces/logs/ces.log

VOLUME /opt/Compuware
EXPOSE 48080
EXPOSE 1545

CMD ./execces.sh start
