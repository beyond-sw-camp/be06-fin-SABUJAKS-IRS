FROM openjdk:17-buster
EXPOSE 9001
COPY backend-msa/service-discovery/build/libs/service-discovery.jar /service-discovery.jar
CMD ["java", "-jar", "/service-discovery.jar"]