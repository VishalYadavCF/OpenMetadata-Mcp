# Use a lightweight JRE to run the application
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/openmetadata-mcp-server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8070
ENTRYPOINT ["java", "-jar", "app.jar"]

