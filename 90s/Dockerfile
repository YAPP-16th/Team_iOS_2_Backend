FROM openjdk:11

VOLUME ["/tmp", "/logs"]

EXPOSE 8080

ARG JAR_FILE=build/libs/90s-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} 90s-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/90s-0.0.1-SNAPSHOT.jar"]
