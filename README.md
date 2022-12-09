## Тестовый проект с использованием Kafka и Kubernetes

### Requirements:

#### 1. Java + Java tools

- Java 11 ([install](https://www.oracle.com/java/technologies/downloads/))
- Maven ([install](https://maven.apache.org/install.html))

#### 2. Kind + Kind Requirements

- Docker ([install](https://docs.docker.com/engine/install/ubuntu/))
- Kubernetes ([install](https://kubernetes.io/docs/tasks/tools/))
- Kind ([install](https://kind.sigs.k8s.io/docs/user/quick-start/#installation))

#### 3. Git

- Git ([install](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git))

### Start with base settings:

    $ git clone 
    $ kind create cluster
    $ kubectl cluster-info
    $ cd 
    $ /bin/bash k8s-load.sh 
    $ kubectl apply -f ./k8s
    $ kubectl get all -A
    - Select kafka-broker pod
    $ kubectl exec -n <name space here> <pod-name>  -it -- /bin/sh
    $ cd bin
    $ kafka-topics.sh --bootstrap-server kafka-service:9092 --alter --topic kafka-test-topic --partitions 40
    $ kafka-topics.sh --bootstrap-server kafka-service:9092 --alter --topic kafka-test-topic-retry --partitions 40
    $ kafka-topics.sh --create --bootstrap-server kafka-service:9092 --replication-factor 1 --partitions 10  --topic kafka-test-topic-dlq
    $ kafka-topics.sh --bootstrap-server kafka-service:9092 --alter  --topic kafka-test-topic-back  --partitions 40
    $ exit
    $ kubectl port-forward svc/kafka-producer 9090:80

#### Open browser with [link](http://localhost:8080/swagger-ui/index.html) and see Swagger UI panel