FROM hirokimatsumoto/alpine-openjdk-11
ADD ./demo-service-exec.jar app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 26000
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar