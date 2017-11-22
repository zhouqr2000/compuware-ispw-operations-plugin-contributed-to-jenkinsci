#!/bin/sh
# chkconfig: 3 80 80
# description: Starts and stops the Compuware Enterprise Server under Linux \
#              These comments are documented for the RedHat chkconfig implementation. 
# This script handles the startup and shutdown of the Compuware Enterprise Server
#
# >Set the Path to the install directory
#
JAVA_HOME=/usr/lib64/jvm/java-1.8.0-openjdk-1.8.0/jre
export JAVA_HOME

CES_HOME=/opt/Compuware/CES
export CES_HOME

CESDATA_HOME=/opt/Compuware/CES/data
export CESDATA_HOME

CES_USER=root
export CES_USER

OS_TYPE=`uname -s`
export OS_TYPE

ARGS="-Xms256m -Xmx1536m -XX:MaxPermSize=256m"
export ARGS  

PATH=$JAVA_HOME/bin:$PATH

#
#  Print out the usage
#
usage()
{
   echo 'Usage: execces.sh [start | stop]                              '
   echo '                                                              '
   echo '  stop             stops the Compuware Enterprise Server      '
   echo '  start            starts the Compuware Enterprise Server     '
}


#
#  Performs a look up of the PID based on the name and kills the PID
#
killProcess()
{
#Returns the process Id that is currently running the manager
PROCESS=`ps -ef | grep $CES_HOME | grep -v grep | awk '{print $2}' `
kill -9 $PROCESS
}

#
#  Performs a look up of the PID based on the name and kills the PID
#
execCommand()
{
#---------------------------------#
# dynamically build the classpath #
#---------------------------------#
LOCALCLASSPATH=

  case $CMD in
	start)
		ARGS="$ARGS -Djava.awt.headless=true"
	    ARGS="$ARGS -Dosgi.instance.area=$CESDATA_HOME/workspace"
	    ARGS="$ARGS -DAppDataDir=$CESDATA_HOME"
	    ARGS="$ARGS -Djetty.home=$CESDATA_HOME/jetty"
	    ARGS="$ARGS -Dlog.data.path=$CESDATA_HOME/workspace/logs"
	    ARGS="$ARGS -Dosgi.parentClassLoader=boot"
	    ARGS="$ARGS -Declipse.log.level=INFO"
	    export ARGS	    
su $CES_USER -c 'java $ARGS -jar $CES_HOME/plugins/org.eclipse.equinox.launcher_1.3.0.v20140415-2008.jar -noRegistryCache -clean -application com.compuware.webapps.application &'
		;;
	stop)
		ARGS="$ARGS -DAppDataDir=$CESDATA_HOME"
		export ARGS
su $CES_USER -c '  		java $ARGS -cp "$CES_HOME/plugins/*" ${ARGS} com.compuware.Compuware.util.ShutCES $PORTNUM'
		;;
  esac
}

execCES()
{
	ARGS="$ARGS -Djava.awt.headless=true"
	ARGS="$ARGS -Dosgi.instance.area=$CESDATA_HOME/workspace"
	ARGS="$ARGS -DAppDataDir=$CESDATA_HOME"
	ARGS="$ARGS -Djetty.home=$CESDATA_HOME/jetty"
	ARGS="$ARGS -Dlog.data.path=$CESDATA_HOME/workspace/logs"
	ARGS="$ARGS -Dosgi.parentClassLoader=boot"
	ARGS="$ARGS -Declipse.log.level=INFO"
	export ARGS	    
	su $CES_USER -c 'java $ARGS -jar $CES_HOME/plugins/org.eclipse.equinox.launcher_1.3.0.v20140415-2008.jar -noRegistryCache -clean -application com.compuware.webapps.application 2>&1 &' 
}

CMD=$1
export CMD
CMDFILE=$2
export CMDFILE

umask 027

if [ $# -lt 1 ]; then
       usage 
       exit 15
else
	#    echo "Command invocation on $OS_TYPE"
	#    echo "Invoking command $CMD"
	if [ -e $CES_HOME/logs/$CMD.log ]; then
		rm -f $CES_HOME/logs/$CMD.log >/dev/null 
	fi
	date > $CESDATA_HOME/ces/logs/$CMD.log 2>&1
    echo "$USER requested $1 for CES Daemon under user $CES_USER" >>$CESDATA_HOME/ces/logs/$CMD.log  2>&1
    chown $CES_USER:$(id -g $CES_USER) $CESDATA_HOME/ces/logs/$CMD.log  2>&1

	case $OS_TYPE in
      "OS/390")
        ARGS="$ARGS -Dfile.encoding=ISO-8859-1 -Xnoargsconversion"
		export ARGS  
         ;;
      *)
         ;;
    esac

	case $1
     in
     stop)
			CMD=stop
			export CMD
			PORTNUM=$2
            export PORTNUM
            execCommand
            ;;
     start)
 			CMD=start
			export CMD
            execCommand
            ;;
     kill)
            killProcess
            ;;
     *)
            echo "The value <$CMD> was not a valid command option." 
            usage
            exit 127
            ;;
    esac
fi

sleep infinity
