#mvn clean install
nohup java -jar api/target/api-0.1.jar > api.out &
nohup java -jar webapp/target/webapp-0.1.jar > web.out &