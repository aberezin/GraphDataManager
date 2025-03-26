#!/bin/bash

echo "Building Neo4j Server..."
cd "$(dirname "$0")" || exit
mvn clean package -DskipTests

echo "Starting Neo4j Server..."
java -jar target/neo4j-server-1.0.0-SNAPSHOT.jar