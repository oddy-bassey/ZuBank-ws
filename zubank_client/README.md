[![oddy-bassey](https://circleci.com/gh/oddy-bassey/zubank_client.svg?style=svg)](https://circleci.com/gh/oddy-bassey/zubank_client)

# ZuBank Client App
This application is a simple Spring MVC application which runs on port : 1500
<br>The route (http://localhost:1500/) loads up the index page which is a simple dashboard showing aggregates of all entities in the application (customer, accounts and transaction).
The rest of the application can therefore be accessed from here. 

Technologies
-
below are the technologies used in developing the application
* Java
* thymeleaf, html & css
* JavaSrcipt

Architecture
-
Zubank client app uses a simple Model View Controller architecture <br>
![alt text](https://github.com/oddy-bassey/zubank_client/blob/main/src/main/resources/screen_shots/client_arch.PNG?raw=true)

Testing
-
Testing is achieved using Junit5 & Mockito library. The application features a simple test class for testing the controllers <br>
![alt text](https://github.com/oddy-bassey/zubank_client/blob/main/src/main/resources/screen_shots/client_test.PNG?raw=true)

Application Features
- 
1) Dashboard <br>
The application dashboard simply displays information regarding the aggregate of all entities (customer, accounts and transaction)
in the application.

![alt text](https://github.com/oddy-bassey/zubank_client/blob/main/src/main/resources/screen_shots/dashboard.PNG?raw=true)

2) Customer <br>
The customer page displays all the customers currently stored in the database. Here we can carry out certain actions relating 
to each customer. These are:
    * Edit (edit customers information)
    * View details
    * create account (create bank account for customer)
    * delete (delete customer)

![alt text](https://github.com/oddy-bassey/zubank_client/blob/main/src/main/resources/screen_shots/customer.PNG?raw=true)

3) Account <br>
The account page displays all accounts owned by any customer in the database. We can carry out certain operations related 
to any account. These are:
    * view details (displays all the details of the bank account: customer info, bank account info and transactions)
    * deposit
    * withdraw
    * transfer (not implemented yet)
    * delete 
   
![alt text](https://github.com/oddy-bassey/zubank_client/blob/main/src/main/resources/screen_shots/account.PNG?raw=true)

4) Transactions
The transactions page simply displays all transactions carried out on all accounts

![alt text](https://github.com/oddy-bassey/zubank_client/blob/main/src/main/resources/screen_shots/transaction.PNG?raw=true)