# gRPC-Store

## Project Status 🚧
**On-Hold**.  
This project is intended to implement gRPC endpoints instead of HTTP. The current state is a microservice using Spring Boot with various dependencies.

## Objective 🎯
The primary goal of this project is to automate the process after a delivery creation. Regardless of who creates the delivery, we have an API that triggers a Camunda BPMN process. The main objective is to manage deliveries from the warehouse to stores and to automatically select available drivers for each delivery. 

## Architecture 🔧
The `gRPC-Store` is a Spring Boot-based Java application that integrates several powerful tools and frameworks:
- **Camunda/Zeebe** for BPMN-based workflow automation.
- **Elasticsearch** for search and analytics.
- **PostgreSQL** for relational database management.
- **Kafka & RabbitMQ** for messaging and event-driven architecture.
- **ActiveMQ** for message queuing.
- **gRPC** for high-performance RPC communication.

### Components in the Project:
- **Zeebe** for process orchestration.
- **Tasklist** for managing task lists.
- **Operate** for real-time monitoring of processes.
- **Optimize** for business process analytics.
- **Connectors** for integrating with external systems.

## Dependencies 📦
This project includes the following key dependencies:

- **Spring Boot 3.x** (Java 17)
- **Camunda Zeebe Starter** for BPMN process automation.
- **Spring Kafka** for event-driven messaging.
- **Spring AMQP** (RabbitMQ) for message brokers.
- **Spring ActiveMQ** for message queuing.
- **Spring Data Elasticsearch & PostgreSQL** for persistence and data management.
- **Lombok** for reducing boilerplate code.
- **MapStruct** for object mapping.
- **Jackson DataFormat Protobuf** for Protobuf serialization.

## Installation 🛠️
This project is a microservice and can be run using Docker. Follow the steps below to get started.

### Prerequisites:
1. **Docker**: Ensure you have Docker installed to run the services as containers.
2. **gRPC Configuration**: Before running the service, ensure you have the connection configurations set up for the external services like PostgreSQL, Elasticsearch, Kafka, RabbitMQ, ActiveMQ, and Camunda.

### Steps to Run:
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/gRPC-Store.git
   cd gRPC-Store
