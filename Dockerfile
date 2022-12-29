FROM openjdk:17-jdk-alpine

ENTRYPOINT ["java", "-jar", "usr/share/app/app.jar"]

ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/app/app.jar