FROM openjdk:11
VOLUME /tmp
EXPOSE 8085
ADD ./target/ms-exchange-rate-0.0.1-SNAPSHOT.jar ms-exchange-rate.jar
ENTRYPOINT ["java","-jar","/ms-exchange-rate.jar"]