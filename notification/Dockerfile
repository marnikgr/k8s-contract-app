#
# Maven build stage
#
FROM arm64v8/maven:3.9.1-eclipse-temurin-17 AS build

COPY src /opt/app/src
COPY pom.xml /opt/app

RUN mvn -f /opt/app/pom.xml clean package

#
# JRE run stage
#
FROM arm64v8/eclipse-temurin:17-jre
EXPOSE 8080

ENV TZ=Europe/Athens
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY --from=build /opt/app/target/*.jar /opt/app.jar
ENTRYPOINT ["java", "-jar", "/opt/app.jar"]

