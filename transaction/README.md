[![oddy-bassey](https://circleci.com/gh/oddy-bassey/transaction.svg?style=svg)](https://circleci.com/gh/oddy-bassey/transaction)

# Transaction
This application is a simple Spring REST app which provide CRUD APIs for bank account transactions in the zubank application.
The application runs on port: **8087** but is routed to, from port: **8080** by the **Gateway** application.

Technologies
-
below are the technologies used in developing the application
* Spring Web
* JPA
* H2 Database (in memory)
* Junit5

Accessing Transaction APIs
-
The bank transaction APIs can be accessed using the OpenAPI doc. This documentation is located on the route: **http://localhost:8087/swagger-ui/index.html** <br>
![alt text](https://github.com/oddy-bassey/transaction/blob/main/src/main/resources/screen_shots/transc_doc.PNG?raw=true)

Accessing Transaction database (H2)
-
This service makes use of H2 in memory database for storing the bank transaction data. The database can be accessed at **http://localhost:8087/h2-console/** <br>
**Credentials**
* url = jdbc:h2:mem:appdb
* username = sa
* password =
  <br>
  ![alt text](https://github.com/oddy-bassey/transaction/blob/main/src/main/resources/screen_shots/transc_db.PNG?raw=true)

Architecture
-
Transaction uses a simple REST service architecture. Here, a Rest controller provide (CRUD based) transaction API definitions which allow
the account service make transaction requests. Requests made to the REST controller are being delegated to the service layer
which then make appropriate calls to the repository from which the data is persisted or retrieved from the database. This level of
abstraction between layers completely decouples the application, provides security and makes the application database agnostic.<br>
![alt text](https://github.com/oddy-bassey/transaction/blob/main/src/main/resources/screen_shots/transc_arch.PNG?raw=true)

Testing
-
Testing is achieved using Junit5 & Mockito library. The application features simple test classes for: <br>
* Service unit test
  ![alt text](https://github.com/oddy-bassey/transaction/blob/main/src/main/resources/screen_shots/transc_service_test.PNG?raw=true)
* Database integration test
  ![alt text](https://github.com/oddy-bassey/transaction/blob/main/src/main/resources/screen_shots/transc_db_Itest.PNG?raw=true)
* Controller test
* ![alt text](https://github.com/oddy-bassey/transaction/blob/main/src/main/resources/screen_shots/transc_controller_test.PNG?raw=true)