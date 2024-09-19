FROM openjdk:22-jdk

# Copy the JAR file to the container
COPY target/shopapp_backend.jar shopapp_backend.jar

# Expose the port the application runs on
EXPOSE 8088

# Specify the entrypoint command to run the JAR
ENTRYPOINT ["java", "-jar", "shopapp_backend.jar"]
