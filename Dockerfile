FROM openjdk:11-jdk-slim

ENTRYPOINT ["sh", "/opt/jaicf/run.sh"]

ADD build/libs/app.jar /opt/jaicf/app.jar
ADD scripts/run.sh /opt/jaicf
