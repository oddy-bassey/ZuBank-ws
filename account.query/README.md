[![oddy-bassey](https://circleci.com/gh/oddy-bassey/account.query.svg?style=svg)](https://circleci.com/gh/oddy-bassey/account.query)

# Account Query (account.query)
This application is a simple Spring REST app which provide CRUD APIs for bank account query requests in the zubank application.
Examples of these queries implemented are **find all accounts, find account by id, find account by customerId, find account by customer id
and account type, get account details and get count of accounts**.
The application runs on port: **8086** but is routed to, from port: **8080** by the **Gateway** application.

Technologies
-
below are the technologies used in developing the application
* Spring Web
* JPA
* H2 (in-memory database)
* Kafka
* Junit5 & Mockito

Accessing Account Query APIs
-
The bank account.query APIs can be accessed using the OpenAPI doc. This documentation is located on the route: **http://localhost:8086/swagger-ui/index.html** <br>
![alt text](https://github.com/oddy-bassey/account.query/blob/main/src/main/resources/screen_shots/acc_qry_doc.PNG?raw=true)

Accessing Account database (H2)
-
This service makes use of H2 in memory database for storing the bank account data. The database can be accessed at **http://localhost:8086/h2-console/** <br>
**Credentials**
* url = jdbc:h2:mem:appdb
* username = sa
* password =
  <br>
![alt text](https://github.com/oddy-bassey/account.query/blob/main/src/main/resources/screen_shots/qry_db.PNG?raw=true)

Architecture
-
The account service as a whole is delivered through both the account.cmd and account.query service. This is because Domain
Driven Design pattern is used to define the problem within a bounded context (domain) as in this case. The account.query service therefore
defining the read/query domain layer utilizes CQRS & event-sourcing which DDD facilitates to implement a core part of the bank account service.
Not to delve too much into the entire architecture for this, I'll be focusing on the query domain alone. The query layer features
key important implementations which functions together to deliver an event driven process for the query module. These are:
* Query dispatcher
* Query Handler
* Event Consumer
* Event Handler
* Entity & Repository <br>

Unlike the command module whose mode of operation is simply atomic, the query module operates within 2 contexts. These contexts are: when a command event is 
raised, and when query requests are raised. We'll take a look at each individually: When a command event is raised (let's say an account is to be created or a deposit
/withdrawal/transfer/account deletion is required, these events are being published/produced into the event bus(Kafka) by the command module(producer). 
The query module here being the consumer of the event, listens on these events and once any is consumed it delegates the proper handler which handles
this event for which an account is created or updated by the repository. In the case where query requests are being received from the REST controller in order
to access bank account information, such queries are dispatched by the query dispatcher to the appropriate handler (query handler) which handles these queries and then
delegates the call to the repository to return the requested data from the read database. This setup ensures that the query module has total sovereignty over the read database
(Bank Accounts)<br>

![alt text](https://github.com/oddy-bassey/account.query/blob/main/src/main/resources/screen_shots/acc_qry_arch.PNG?raw=true)

Testing
-
...