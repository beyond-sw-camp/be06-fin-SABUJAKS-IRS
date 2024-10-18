FROM openjdk:17-buster
EXPOSE 9090
COPY backend-msa/service-gateway/build/libs/service-gateway.jar /service-gateway.jar
CMD ["java", "-jar", "/service-gateway.jar"]