#!/bin/sh

SERVICE_HOME=/opt/jaicf
SERVICE_CONF_DIR=$SERVICE_HOME/conf
SERVICE_OVERRIDING_DIR=/etc/jaicf
SERVICE_ENV_CFG=/etc/jaicf/env.cfg
SERVICE_LOG_DIR=/var/log/jaicf
SERVICE_TMP_DIR=/tmp

JAR_FILE=$SERVICE_HOME/app.jar

#set default service parameters
SERVICE_JAVA_XMX=512m
SERVICE_JAVA_MAX_PERM_SIZE=256m

# ------ customServiceInitScript starts

# ------ customServiceInitScript ends

# if you want to access jmx interface outside the host then put next line into .cfg file
#PUBLIC_IP=`wget http://ipinfo.io/ip -qO -`
PUBLIC_IP=localhost

PORT=8080
JMX_PORT=10011
RMI_PORT=10012
JDWP_PORT=10013

LOG_CONFIG=
LOGGING_CFG=$SERVICE_CONF_DIR/logback.xml
if [ -f $SERVICE_OVERRIDING_DIR/logback.xml ]; then
    LOGGING_CFG=$SERVICE_OVERRIDING_DIR/logback.xml
fi

if [ -f $SERVICE_CONF_DIR/logback.xml ]; then
    LOG_CONFIG="-Dlogging.config=$LOGGING_CFG"
fi

# import overridden definitions from SERVICE_ENV_CFG
if [ -f $SERVICE_ENV_CFG ]; then
    . $SERVICE_ENV_CFG
fi

JMX_OPTS="-Dcom.sun.management.jmxremote=true \
          -Djava.rmi.server.hostname=$PUBLIC_IP \
          -Dcom.sun.management.jmxremote.port=$JMX_PORT \
          -Dcom.sun.management.jmxremote.rmi.port=$RMI_PORT \
          -Dcom.sun.management.jmxremote.authenticate=false \
          -Dcom.sun.management.jmxremote.ssl=false"

JDWP_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=$JDWP_PORT"

MEM_OPTS="-Xms$SERVICE_JAVA_XMX -Xmx$SERVICE_JAVA_XMX -XX:MaxMetaspaceSize=$SERVICE_JAVA_MAX_PERM_SIZE"

ADDITIONAL_JVM_OPTS=""

DEFAULT_JVM_OPTS="-XX:+UseCompressedOops\
                  -XX:-OmitStackTraceInFastThrow\
                  -XX:+ExitOnOutOfMemoryError\
                  -XX:+DoEscapeAnalysis\
                  -XX:AutoBoxCacheMax=10000\
                  -XX:+AlwaysPreTouch\
                  -XX:+UseG1GC\
                  -XX:+DisableExplicitGC\
                  -XX:+HeapDumpOnOutOfMemoryError"

JVM_OPTS="$ADDITIONAL_JVM_OPTS $DEFAULT_JVM_OPTS $JMX_OPTS $JDWP_OPTS $MEM_OPTS"

JAVA_ENV_OPTS="-Djava.io.tmpdir=$SERVICE_TMP_DIR\
          -Dfile.encoding=UTF-8\
          -Duser.timezone=UTC"

APP_OPTS="-Dlog.dir=$SERVICE_LOG_DIR\
          $LOG_CONFIG\
          -Dspring.config.location=classpath:/application.yml,optional:$SERVICE_CONF_DIR/application.yml,optional:$SERVICE_OVERRIDING_DIR/zone.application.yml,optional:$SERVICE_OVERRIDING_DIR/application.yml\
          -Djustai.config.location=$SERVICE_CONF_DIR\
          -DPORT=$PORT"

ADDITIONAL_APP_OPTS=""

JAVA_ARGS="$JVM_OPTS $JAVA_ENV_OPTS $APP_OPTS $ADDITIONAL_APP_OPTS"

java -jar $JAVA_ARGS $JAR_FILE
