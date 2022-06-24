# ZuBank Workspace

This work-space project features a collections of services and libraries which form the architecture of
my ZuBank application

Contained Modules
-
* CRM (customer relationship management service) [![oddy-bassey](https://circleci.com/gh/oddy-bassey/crm.svg?style=svg)](https://circleci.com/gh/oddy-bassey/crm) https://github.com/oddy-bassey/crm
* Account
  * cmd (accounts command service) [![oddy-bassey](https://circleci.com/gh/oddy-bassey/account.cmd.svg?style=svg)](https://circleci.com/gh/oddy-bassey/account.cmd) https://github.com/oddy-bassey/account.cmd
  * query (accounts query service) [![oddy-bassey](https://circleci.com/gh/oddy-bassey/account.query.svg?style=svg)](https://circleci.com/gh/oddy-bassey/account.query) https://github.com/oddy-bassey/account.query
  * common (accounts core library) [![oddy-bassey](https://circleci.com/gh/oddy-bassey/account.common.svg?style=svg)](https://circleci.com/gh/oddy-bassey/account.common) https://github.com/oddy-bassey/account.common
* CQRS.core (CQRS core library) https://github.com/oddy-bassey/cqrs.core
* Transaction (accounts transaction service) [![oddy-bassey](https://circleci.com/gh/oddy-bassey/transaction.svg?style=svg)](https://circleci.com/gh/oddy-bassey/transaction) https://github.com/oddy-bassey/transaction
* Zubank_discovery_server (eureka discovery service) [![oddy-bassey](https://circleci.com/gh/oddy-bassey/zubank_discovery_server.svg?style=svg)](https://circleci.com/gh/oddy-bassey/zubank_discovery_server) https://github.com/oddy-bassey/zubank_discovery_server
* Zubank_gateway (cloud gateway service) [![oddy-bassey](https://circleci.com/gh/oddy-bassey/zubank_gateway.svg?style=svg)](https://circleci.com/gh/oddy-bassey/zubank_gateway) https://github.com/oddy-bassey/zubank_gateway
* Zubank_client (Client application) [![oddy-bassey](https://circleci.com/gh/oddy-bassey/zubank_client.svg?style=svg)](https://circleci.com/gh/oddy-bassey/zubank_client) https://github.com/oddy-bassey/zubank_client
* Zubank_config_server (configuration sever)

System Architecture
-
The entire application is built using the Microservice architecture. This architecture facilitates the building of large scale systems
in a rapid and reliable style by breaking down the system into smaller, independent modules which work together as a single integral 
system to deliver the desired application. While using the "Microservice architecture", the Gateway design pattern is being employed
to serve as a single entry point between the client application and a collection of microservices which deliver certain functionalities 
in a defined problem domain. The gateway pattern provides and facilitates certain functional structures that address cross-cutting concerns 
across microservices like, inter-service communication, security, fault tolerance, request routing, load balancing etc.. <br>
Below are the components/tools/technologies fundamental to this defined structure:
1)Gateway
  * Loadbalancer
  * Security (Keycloak)
2) Discovery server (Eureka discovery server)
3) Circuit breaker (fault tolerance)
4) OpenFeign (for http client communication #inter-service communication)
5) microservices (SpringBoot apps etc)
6) Databases (H2 and Mongodb)
7) Event queue (Kafka)
8) Containerization (Docker)
9) CI/CD (CircleCI)
10) Testing (JUnit5 & Mockito)
<br> the below image shows an illustration of the system architecture.
![alt text](https://github.com/oddy-bassey/ZuBank-ws/blob/main/resources/img/sys_arch.PNG?raw=true)

# Database Structure
<br> The database structure of this system is presented using an ERD (Entity Relationship Diagram). This diagram provides
a diagrammatic representation which defines all tables as entities with defined properties (in the context of columns) and 
expresses entity relationships to define how the entities(tables) relate with one another using links. 
The below image shows an illustration of the database structure.
<br>

![alt text](https://github.com/oddy-bassey/ZuBank-ws/blob/main/resources/img/erd.PNG?raw=true)

# Bank account service
This module (service) being the key point of this application is being implemented using CQRS (Command and Query Responsibility Segregation) 
and Event-Sourcing. CQRS entails the separation of the read and query parts of an application as separate concerns. This is important because 
CQRS allows us to scale up the command and query sides independently... Example: In applications where the reads out-numbers the writes or vice-versa,
this is a great advantage since each can independently be scaled. Event-Sourcing on the other-hand defines an approach where all changes made to an object
or entity are stored as a sequence of immutable events to an event store, rather than storing just the current state. This pattern is often used with the 
CQRS pattern to perform the data management tasks in response to events. **In a nutshell**, once a command request is made to the command api and the command handled,
it typically alters the state of the domain model (aggregate) which then raises an event which is appended (persisted) to the list of events in the event-store.
This event is then produced/published into an event bus(Kafka) from where the query api consumes the event and handles the event by updating the associated record
in the read database. Please note that the query api has data sovereignty over the read database (Accounts). <br>

**NOTE** : for more detailed information as to how both domains (Command & Read service) implement this pattern, please have a look at each individual repositories.

![alt text](https://github.com/oddy-bassey/ZuBank-ws/blob/main/resources/img/acc_arch.PNG?raw=true)

# How to run the application ?
...
