FROM maven:3.6.3-jdk-11 AS MAVEN_TOOL_CHAIN

WORKDIR /build

COPY pom.xml /build

RUN mvn org.apache.maven.plugins:maven-dependency-plugin:3.1.1:resolve
RUN mvn org.apache.maven.plugins:maven-dependency-plugin:3.1.1:resolve-plugins

COPY src /build/src

RUN mvn package


FROM openjdk:11.0.7-slim-buster

WORKDIR /app

COPY --from=MAVEN_TOOL_CHAIN /build/target/dependency-jars ./dependency-jars
COPY --from=MAVEN_TOOL_CHAIN /build/target/servicebus-1.0.jar .

EXPOSE 8080

CMD java -jar servicebus-1.0.jar
