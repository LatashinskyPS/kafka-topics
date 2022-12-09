mvn spring-boot:build-image -Dspring-boot.build-image.imageName=spring-k8s/kafka-consumer

docker tag spring-k8s/kafka-consumer spring-k8s/kafka-consumer:snapshot

kind load docker-image spring-k8s/kafka-consumer:snapshot

