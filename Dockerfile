# Use an official maven image as a parent image
FROM maven:3.9.5-eclipse-temurin-17-alpine

# Set the working directory in the image to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Package the application
RUN mvn package

# Use an official openjdk image as a parent image
FROM eclipse-temurin:17-alpine

# Set the working directory in the image to /app
WORKDIR /app

# Copy the jar file from the maven image into the current container
COPY --from=0 /app/target/your-app.jar /app

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","/app/your-app.jar"]