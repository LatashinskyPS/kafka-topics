mvn spring-boot:build-image -Dspring-boot.build-image.imageName=spring-k8s/kafka-producer

docker tag spring-k8s/kafka-producer spring-k8s/kafka-producer:snapshot

kind load docker-image spring-k8s/kafka-producer:snapshot

