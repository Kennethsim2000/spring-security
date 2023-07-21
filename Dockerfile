FROM openjdk:20
EXPOSE 8080
ADD target/DemoKenneth.jar DemoKenneth.jar
ENTRYPOINT ["java", "-jar", "/DemoKenneth.jar"]