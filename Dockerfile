FROM openjdk:17-oracle

WORKDIR /app

COPY target/recipes-saver-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "recipes-saver-0.0.1-SNAPSHOT.jar"]
