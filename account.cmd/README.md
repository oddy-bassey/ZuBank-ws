[![oddy-bassey](https://circleci.com/gh/oddy-bassey/account.cmd.svg?style=svg)](https://circleci.com/gh/oddy-bassey/account.cmd)

# Account Command (account.cmd)
This application is a simple Spring REST app which provide CRUD APIs for bank account command requests in the zubank application.
Examples of these commands implemented are **create account, deposit funds, withdraw funds and delete account**.
The application runs on port: **8085** but is routed to, from port: **8080** by the **Gateway** application.

Technologies
-
below are the technologies used in developing the application
* Spring Web
* JPA
* MongoDB (de.flapdoodle.embed.mongo in-memory database)
* Kafka
* Junit5 & Mockito

Accessing Account Command APIs
-
The bank account.cmd APIs can be accessed using the OpenAPI doc. This documentation is located on the route: **http://localhost:8085/swagger-ui/index.html** <br>
![alt text](https://github.com/oddy-bassey/account.cmd/blob/main/src/main/resources/screen_shots/acc_cmd_doc.PNG?raw=true)

Accessing Account Command database (de.flapdoodle.embed.mongo in-memory MongoDB implementation)
-
This service makes use of mongoDB in memory database for storing account command events (this is commonly referred to as the events-tore). 
The event-store can be accessed at **http://localhost:27017** <br> using any mongoDB client example: MongoDBCompass
**Credentials**
* connection string: mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false
  <br>
![alt text](https://github.com/oddy-bassey/account.cmd/blob/main/src/main/resources/screen_shots/mongodb.PNG?raw=true)

Architecture
-
The account service as a whole is delivered through both the account.cmd and account.query service. This is because Domain 
Driven Design pattern is used to define the problem within a bounded context (domain) as in this case. The account.cmd service therefore
defining the write/command domain layer utilizes CQRS & event-sourcing which DDD facilitates to implement a core part of the bank account service.
Not to delve too much into the entire architecture for this, I'll be focusing on the command domain alone. The command layer features
key important implementations which functions together to deliver an event driven process for the command module. These are:
* Command dispatcher
* Command Handler
* Event sourcing handler
* Event store
* Event producer <br>

When a request (open new account, deposit funds, withdraw funds, delete account) is being made to the REST controller, 
it creates an associating command object which is then dispatched by the command dispatcher to the command handler. The handler's job is
to therefore handle this request by calling on the aggregate (a sequence events whose state is managed by the aggregate root) to
establish a change within the account aggregate. This aggregate is then sent to the event-sourcing handler which then commits the 
uncommitted change in the aggregate in the form of an event to the event-store. Once this event has been stored, the event is then published
into a kafka queue by the event producer from which the query service will complete execution of the remaining processes.<br>
![alt text](https://github.com/oddy-bassey/account.cmd/blob/main/src/main/resources/screen_shots/acc_cmd_arch.PNG?raw=true)

Testing
-
Testing is achieved using Junit5 & Mockito library. The application features simple test classes for: <br>
* Database integration test
  ![alt text](https://github.com/oddy-bassey/account.cmd/blob/main/src/main/resources/screen_shots/acc_db_Itest.PNG?raw=true)
...