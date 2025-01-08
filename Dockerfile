# Build stage
FROM gradle:8.5-jdk21 AS build

WORKDIR /app

# Copy gradle config files first to leverage Docker cache
COPY gradle.properties settings.gradle.kts build.gradle.kts ./
COPY gradle ./gradle

# Copy source code
COPY src ./src
COPY libs ./libs
COPY resources ./resources

# Build the application
RUN gradle build --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy the built artifact from build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]