## Money Transfer Application

This application is a pair-programmed mini capstone assignment, which was created during my time as a Java development bootcamp student. It is based off of our Client-Server Database Programming module, focused on relational databases, SQL, Java data access layers & DAO design pattern, HTTP request/response life cycle, and RESTful APIs.

This application enables a customer to transfer money to another registered user of the application.

### Key Features

* A new user can register on the application.
* Authenticated users can view their account balance.
* Authenticated users can chose another registered user to which they wish to transfer money, and select an amount to send.
* If account has sufficient funds to transfer, both accounts are updated. (Otherwise, user will be notified of insufficient funds.)
* Authenticated users can view their transaction history, and details of a specific transfer.

## Functionality
* Java-based program that uses CLI client to interact with user
* Application built using Spring framework
* RESTful API interface is used for client/server communication
* Server code organized using DAO design pattern
* PostgreSQL is used as the backend RDBMS

## Future Goals
* Switching the functionality of 'whether an account has sufficient funds' to the server side, allowing the client side to solely handle the exception
* Additional features: *request* a transfer; view *pending* transfers (status associated with a request-a-transfer feature); *approve* or *decline* a transfer
* Modernizing the CLI to a web-based client application

## Set Up & How to Run
* Run SQL script
* Start server
* Run client CLI application