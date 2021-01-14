#
# Host backend
#

FROM adoptopenjdk/openjdk11:jre-11.0.9_11-alpine
COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]

