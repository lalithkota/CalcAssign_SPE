# Random
FROM openjdk:15-alpine
ADD myJar.jar /my-app/
ENTRYPOINT java -jar /my-app/myJar.jar
