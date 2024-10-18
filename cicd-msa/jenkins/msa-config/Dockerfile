FROM openjdk:17-buster
EXPOSE 9002
COPY backend-msa/service-config/build/libs/service-config.jar /service-config.jar
CMD ["java", "-jar", "/service-config.jar"]