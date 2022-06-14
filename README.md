
# Kafka Admin Client 

### Assumptions:
1. The security mechanism in kafka is `SASL_PLAINTEXT`

### RUN
```aidl
cd kafka-bootstrap
sbt assembly
java -jar target/scala-2.13/kafkaclients.jar "<host>:<port>" <username> <password>
```

```
docker-compose up -d --build 
```