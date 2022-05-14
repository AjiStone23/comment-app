
sleep 1
gnome-terminal --tab  -t "EUREKA - SERVER" -- bash -c "java -jar eureka-server/target/eureka-server-1.0-SNAPSHOT.jar ; "
sleep 5
gnome-terminal --tab  -t "APIGW" -- bash -c "java -jar apigw/target/apigw-1.0-SNAPSHOT.jar ; "
sleep 5
gnome-terminal --tab  -t "COMMENT - SERVICE" -- bash -c "java -jar comment-service/target/comment-service-1.0-SNAPSHOT.jar ; "
sleep 7
gnome-terminal --tab  -t "DASHBOARD - SERVICE" -- bash -c "java -jar dashboard-service/target/dashboard-service-1.0-SNAPSHOT.jar ; "
sleep 7
gnome-terminal --tab  -t "COMMENT - SERVICE 2" -- bash -c "java -Dserver.port=8085 -jar comment-service/target/comment-service-1.0-SNAPSHOT.jar ; "
sleep 7
gnome-terminal --tab  -t "DASHBOARD - SERVICE 2" -- bash -c "java -jar -Dserver.port=8086 -Drabbitmq.queues.dashboard=comment.queue2  dashboard-service/target/dashboard-service-1.0-SNAPSHOT.jar ; "