[![oddy-bassey](https://circleci.com/gh/oddy-bassey/crm.svg?style=svg)](https://circleci.com/gh/oddy-bassey/crm)

# CRM (Customer Relationship Management)
This application is a simple Spring REST app which provide CRUD APIs for customers in the zubank application.
The application runs on port: **8083** but is routed to, from port: **8080** by the **Gateway** application.

Technologies
-
below are the technologies used in developing the application
* Spring Web
* JPA
* H2 Database (in memory)
* Junit5

Accessing customer APIs
-
The customer APIs can be accessed using the OpenAPI doc. The documentation can be accessed on the route: **http://localhost:8083/swagger-ui/index.html** <br>
![alt text](https://github.com/oddy-bassey/crm/blob/main/src/main/resources/screen_shots/crm_doc.PNG?raw=true)

Accessing customer database (H2)
-
This service makes use of H2 in memory database for storing the customer data. The database can be accessed at **http://localhost:8083/h2-console/** <br>
**Credentials**
* url = jdbc:h2:mem:appdb
* username = sa
* password = 
<br>
![alt text](https://github.com/oddy-bassey/crm/blob/main/src/main/resources/screen_shots/crm_db.PNG?raw=true)

Architecture
-
CRM uses a simple REST service architecture. Here, a Rest controller provide (CRUD based) customer API definitions which allow
other client application make requests to the CRM. Requests made to the REST controller are being delegated to the service layer
which then make appropriate calls to the repository from which the data is persisted or retrieved from the database. This level of 
abstraction between layers completely decouples the application, provides security and makes the application database agnostic.<br>
![alt text](https://github.com/oddy-bassey/crm/blob/main/src/main/resources/screen_shots/crm_arch.PNG?raw=true)

Testing
-
Testing is achieved using Junit5 & Mockito library. The application features simple test classes for: <br>
* Service unit test
  ![alt text](https://github.com/oddy-bassey/crm/blob/main/src/main/resources/screen_shots/crm_service_test.PNG?raw=true)
* Database integration test
  ![alt text](https://github.com/oddy-bassey/crm/blob/main/src/main/resources/screen_shots/crm_db_Itest.PNG?raw=true)
* Controller test
* ![alt text](https://github.com/oddy-bassey/crm/blob/main/src/main/resources/screen_shots/crm_controller_test.PNG?raw=true)