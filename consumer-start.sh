#!/bin/bash

while ! nc -z broker 29092; do
    echo "Waiting for kafka to launch on ${KAFKA_HOST}:${KAFKA_PORT}..."
    sleep 1
done

while ! nc -z minio 9000; do
    echo "Waiting for minio to launch on minio:9000 ..."
    sleep 1
done

while ! nc -z connect 8083; do
    echo "Waiting for connect to launch on connect:8083 ..."
    sleep 1
done

sleep 15

curl -X POST -H "Content-Type: application/json" --data @/app/source_connector.json http://connect:8083/connectors

java -jar /app/target/scala-2.13/kafkaclients.jar ${BOOTSTRAP_SERVERS} ${USERNAME} ${PASSWORD} ${TOPIC} ${MINIO_HOST} ${MINIO_USERNAME} ${MINIO_PASSWORD} ${MINIO_BUCKET}