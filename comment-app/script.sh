
echo -e "MAVEN"
mvn clean install

echo -e "EUREKA - SERVER"
java -jar eureka-server/target/eureka-server-1.0-SNAPSHOT.jar

echo -e "APIGW"
java -jar apigw/target/apigw-1.0-SNAPSHOT.jar

echo -e "COMMENT - SERVICE"
java -jar comment-service/target/comment-service-1.0-SNAPSHOT.jar
echo -e "DASHBOARD - SERVICE"
java -jar dashboard-service/target/dashboard-service-1.0-SNAPSHOT.jar
echo -e "COMMENT - SERVICE 2"
java -Dserver.port=8085 -jar comment-service/target/comment-service-1.0-SNAPSHOT.jar
echo -e "DASHBOARD - SERVICE 2"
java -jar -Dserver.port=8086 -Drabbitmq.queues.dashboard=comment.queue2  dashboard-service/target/dashboard-service-1.0-SNAPSHOT.jar
