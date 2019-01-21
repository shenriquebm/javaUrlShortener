FROM openjdk:8-jdk-alpine

VOLUME /tmp

EXPOSE 1992

ADD build/libs/urlShortener-0.1.0.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]