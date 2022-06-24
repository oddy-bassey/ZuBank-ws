# CQRS.core
This project is a simple Java Library for my ZuBank Application. It provides definition for classes
which implement CQRS and Event Sourcing pattern amongst the bank modules (account.cmd, account.query) <br>
These core abstract implementations are:
1) command
2) event
3) query
4) domain
5) infrastructure
    * dispatcher
    * handler
    * producer
    * service
6) other non(CQRS and Event Sourcing pattern) modules: exception, message
    