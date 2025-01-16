

docker command for run keycloak

docker run -p 8082:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin 
-v .\config\keycloak\import:\opt\keycloak\data\import quay.io/keycloak/keycloak:23.0.4 start-dev --import-realm



если что руками отправить и зарегатьть

curl -X POST http://localhost:8888/instances \
-H "Content-Type: application/json" \
-d '{
"name": "customer-service",
"managementUrl": "http://192.168.0.104:8084/actuator",
"healthUrl": "http://19[SecurityConfig.java](customer-app/src/main/java/com/salas/customerapp/config/SecurityConfig.java)2.168.0.104:8084/actuator/health",
"serviceUrl": "http://192.168.0.104:8084/"
}'




docker run --name selmag-metrics -p 8428:8428 -v C:\Users\salas\IdeaProjects\selmag-project\config\victoria-metrics\promscrape.yaml:/promscrape.yaml victoriametrics/victoria-metrics:v1.93.12 --promscrape.config=/promscrape.yaml
