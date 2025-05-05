
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/envios-0.0.1-SNAPSHOT.jar /app/app.jar
COPY Wallet_fs1 /app/wallet
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]