FROM openjdk:22-jdk-slim

# Install wget to download the JAR file
RUN apt-get update && apt-get install -y wget

# Download the JAR file from Mega using wget
RUN wget --no-check-certificate 'https://mega.nz/file/omEHjZoY#duk-ddNshbqSObL0A0VHw-YEURDVQ7X_1z-Ua28TEJA' -O app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
