# Camunda Spring Boot Book Loan
---

This repo is another one of my novice attempt at implementing a Spring boot Java Application created from 
[Camunda quick start guide](https://docs.camunda.org/get-started/). The difference between 
this and the original attempt is that this uses camunda embedded into Spring Boot, which 
removes the need to deploy the process and decision models via the API. 


Another difference is that because this is running embedded inside Spring Boot, it can use 
[Java Delegates](https://docs.camunda.org/get-started/java-process-app/service-task/#add-a-javadelegate-implementation) 
rather than an external service task running separately.

### Overview

---

The examples of `.bpmn` files is located in `src/main/resources` directory of this repo. **Camunda Modeller** 
is required to view and edit files with this extension.

The Java code in this repo is for a basic receiver that runs as a two part external service tasks in the business process.
They basically give a simple example how custom code can be plugged into a business logic when required. In this instance,
the [Java Delegates](https://docs.camunda.org/get-started/java-process-app/service-task/#add-a-javadelegate-implementation) 
do some logic and logging to show if a book has been loaned, out of stock or rejected when receiving an empty request.

The process in this case is simple, it will look at the `title` value coming from an external web request and:
* If the title is not empty, it will perform a search in the in-memory database for a requested book.
  * If the requested book is found and is in stock, a message is displayed.
  * If the requested book is found and is no longer in stock, a message is displayed.
* If the title is empty, it will reject the book loan.
* If the requested book cannot be found in the library, it will simulate the process or ordering the book into to library. 

Java Delegates are used for some business logic rather than a decision table.

This repository exposes a URL endpoint for receiving a request using an API application like [**Postman**](https://www.postman.com/)

### Sending a request

---

Send a http `GET` request to `http://localhost:8080/v1/loanBook` with a `JSON` request body as shown below:
```json
{
  "title": "Alice In Wonderland"
}
```

Send a http `POST` request to `http://localhost:8080/v1/returnBook` with a `JSON` request body as shown below:
```json
{
  "title": "Alice In Wonderland"
}
```


### Running locally

---
Locate the Java class `BookLoanCamundaSpringBootApplication` in the `src/main/java/` directory, inside the `com.example.camunda.book` package.

Run as a spring boot application.
