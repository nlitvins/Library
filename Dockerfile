FROM azul/zulu-openjdk-alpine:21-latest
LABEL authors="Nikita"

COPY build/libs/Library_task-0.0.1-SNAPSHOT.jar app.jar
USER 1001
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]

