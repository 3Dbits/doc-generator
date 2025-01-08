# Build stage
FROM gradle:8.5-jdk21 AS build

WORKDIR /app

# Copy gradle config files first to leverage Docker cache
COPY gradle.properties settings.gradle.kts build.gradle.kts ./
COPY gradle ./gradle

# Copy source code
COPY src ./src
COPY libs ./libs

# Build the application
RUN gradle build --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy the built artifact from build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]