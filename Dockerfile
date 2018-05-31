FROM openjdk:8-jre

RUN mkdir /usr/src/kodi-api

COPY target/api-kodi-0.0.3.jar /usr/src/kodi-api

WORKDIR /usr/src/kodi-api

CMD ["java", "-jar", "api-kodi-0.0.3.jar"]