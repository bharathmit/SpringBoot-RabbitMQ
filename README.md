
## Introduction:

 cableCard is an open-source CableTV Management framework written entirely in Java on top of the Spring framework.

## Key Features and Technologies: 

### Maven Build Tool

Maven is a project management and comprehension tool. Maven provides developers a complete build lifecycle framework. Development team can automate the project's build infrastructure in almost no time as Maven uses a standard directory layout and a default build lifecycle. 

### Java Server Faces(JSF) / PrimeFaces.

JavaServer Faces (JSF) is a Java-based web application framework intended to simplify development integration of web-based user interfaces. UI component implement to  PrimeFaces. PrimeFaces is a component suite open source User Interface (UI) component library for JavaServer Faces (JSF) based applications.

### Spring Framework

Spring is the enterprise Java platform. It provides numerous features, including dependency injection and transaction control.

### Security

Spring Security provides a robust security framework for controlling authentication and authorization at both the code and page level and is utilized by Application for access control.

### Persistence

JPA and Hibernate represent ORM infrastructure for controlling persistence of our rich domain model.

### MySQL 

 MySql is an open-source relational database management system (RDBMS). 
 
### Email

Email support is provided throughout the framework in either synchronous or asynchronous (Spring Batch) modes. Email presentation customization is achieved via velocity templates.

### Spring Batch 

Spring Batch provides reusable functions that are essential in processing large volumes of records, including job processing statistics, job restart, skip, and resource management. Simple as well as complex, high-volume batch jobs can leverage the framework in a highly scalable manner to process significant volumes of information.

### Paypal 

Cablecard currently offers integration with the PayPal RestRull API. This module allows users to complete their online payment experience using their PayPal account.

### RabbitMQ 

RabbitMQ is an open source message broker software (sometimes called message-oriented middleware) that implements the Advanced Message Queuing Protocol (AMQP). JMS can be used as a mechanism to allow asynchronous HTTP request processing.


## Local Framework Development


## Prerequisites

1.Download and install Java 1.7 Development Kit.

2.Download and install the latest version of Maven (at least version 3.3.1).

3.Download and install the Git Client.

4.Download and install the MySQL Server and GUI.

5.Recommended to user Eclipse IDE.


Now that all of the pre-requisites are installed, let's set up the Cable-Card.

## Install Cable-Card.

1.The easiest way to get the Cable-Card framework locally set up with the Eclipse. Clone this repository using Git.

2.At the command line for your operating system, change your directory to where the Cable-Card Cloned and execute a clean install via Maven:


```sh
mvn clean install
```


> Note: This might take a while as all of the Cable-Card dependencies are downloaded.


## Developers who contributed to this work

Currently none

*contributions are welcome!* 

*If you have any improvement suggestions or bug fix, just fork to your GitHub and issue pull requests*

## Licensing

Copyright 2016 Bharath Mannaperumal

This work is licensed under <a rel="license" href="http://www.apache.org/licenses/LICENSE-2.0">Apache License Version 2.0</a>.








