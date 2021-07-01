FROM openjdk:16

MAINTAINER Yasmin Waldeck <yasmin.waldeck@posteo.de>

ADD backend/target/potatodays.jar app.jar

CMD [ "sh", "-c", "java -Dserver.port=$PORT -Dspring.data.mongodb.uri=$MONGO_DB_URI  -jar /app.jar" ]