# Use a base image with JDK 21
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy Gradle files
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle gradle

# Copy source code
COPY src src

# Grant execution permissions for gradlew
RUN chmod +x gradlew

# Build the application
RUN ./gradlew build

# Use JRE 21 for runtime
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built artifact from the build stage
COPY --from=0 /app/build/libs/*.jar app.jar

# Expose port (adjust if needed)
EXPOSE 8080

# Run the application with Java 21 features enabled
CMD ["java", "--enable-preview", "-jar", "app.jar"]