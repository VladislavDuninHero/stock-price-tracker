FROM openjdk:17-jdk-slim

COPY build/libs/stock-price-tracker-0.0.1.jar stock-price-tracker.jar

EXPOSE 4040

ENTRYPOINT ["java", "-jar", "stock-price-tracker.jar"]