

docker command for run keycloak

docker run -p 8082:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin 
-v .\config\keycloak\import:\opt\keycloak\data\import quay.io/keycloak/keycloak:23.0.4 start-dev --import-realm