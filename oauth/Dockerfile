FROM openjdk:8-jre-alpine
VOLUME /tmp
COPY /target/oauth-0.0.1-SNAPSHOT.jar oauth-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","oauth-0.0.1-SNAPSHOT.jar"]