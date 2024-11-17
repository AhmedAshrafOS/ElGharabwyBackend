FROM openjdk:22-jdk-slim

# Add repository and install megatools
RUN echo 'deb http://debian.mega.co.nz/ jessie main' >> /etc/apt/sources.list.d/mega.list
RUN apt-key adv --keyserver keyserver.ubuntu.com --recv-keys E6F5C7B7
RUN apt-get update && apt-get install -y wget megatools

# Download the JAR file using megadl
RUN megadl 'https://mega.nz/file/omEHjZoY#duk-ddNshbqSObL0A0VHw-YEURDVQ7X_1z-Ua28TEJA' -o app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
