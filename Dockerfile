FROM openjdk:21

EXPOSE 8080

ADD backend/target/groupset-hero.jar  app.jar

CMD ["sh", "-c", "java -jar /app-jar"]