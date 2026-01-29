#FROM eclipse-temurin:21-jdk-alpine
FROM eclipse-temurin:21-jdk
COPY target/*.jar app.jar
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
#ENTRYPOINT ["java","-jar","/app.jar"]
ENTRYPOINT ["./wait-for-it.sh", "mysql:3306", "--", "./wait-for-it.sh", "kafka:9092", "--", "java", "-jar", "/app.jar"]