# README Template

Below is a template provided for use when building your README file for students.

# Critter Chronologer Project Starter

Critter Chronologer a Software as a Service application that provides a scheduling interface for a small business that takes care of animals. This Spring Boot project will allow users to create pets, owners, and employees, and then schedule events for employees to provide services for pets.


## Getting Started

### Dependencies

* [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download) (or Ultimate) recommended 
* [Java SE Development Kit 8+](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Maven](https://maven.apache.org/download.cgi)
* [MySQL Server 8](https://dev.mysql.com/downloads/mysql/) (or another standalone SQL instance)

Part of this project involves configuring a Spring application to connect to an external data source. Before beginning this project, you must install a database to connect to. Here are [instructions for installing MySQL 8](https://dev.mysql.com/doc/refman/8.0/en/installing.html).

You should install the Server and Connector/J, but it is also convenient to install the Documentation and Workbench.

Alternately, you may wish to run MySQL in a docker container, using [these instructions](https://hub.docker.com/_/mysql/).

After installing the Server, you will need to create a user that your application will use to perform operations on the server. You should create a user that has all permissions on localhost using the sql command found [here](https://dev.mysql.com/doc/refman/8.0/en/creating-accounts.html).

Another SQL database may be used if desired, but do not use the H2 in-memory database as your primary datasource.

### Installation

1. Clone or download this repository
2. Open IntelliJ IDEA
3. In IDEA, select `File` -> `Open` and navigate to the `critter` directory within this repository. Select that directory to open.
4. The project should open in IDEA. In the project structure, navigate to `src/main/java/com.udacity.jdnd.course3.critter`. Within that directory, click on CritterApplication.java and select `Run` -> `Debug CritterApplication`. 
5. Open a browser and navigate to the url: [http://localhost:8082/test](http://localhost:8082/test)

You should see the message "Critter Starter installed successfully" in your browser.


```
Give an example here
```

## Testing

Explain the steps needed to run any automated tests

### Break Down Tests

Explain what each test does and why

```
Examples here
```

## Built With

* [Item1](www.item1.com) - Description of item
* [Item2](www.item2.com) - Description of item
* [Item3](www.item3.com) - Description of item

Include all items used to build project.

## License

This project is licensed under the MIT License - see the [LICENSE.md]()
