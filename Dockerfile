FROM openjdk:22-jdk-slim

# Install wget to download the JAR file
RUN apt-get update && apt-get install -y wget

# Download the JAR file from MediaFire
RUN wget -O app.jar https://download856.mediafire.com/4jedwdgk60ng/tpqftusq1thobh2/elgharabwy-22.0.0.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
