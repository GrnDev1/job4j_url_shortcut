# URL Shortcut

## Technical task

To ensure the safety of users, all links on the site are replaced with links to our service.
The service works on REST API.

Required functionality:

### 1. Site registration.

Different sites can use the service. Each site is given a pair of password and login.
To register a site in the system you need to send a request.

***URL***

``` POST /registration ```

With the body of JSON object.

```{site : "job4j.ru"} ```

Response from the server.

```
{registration : true/false, login : UNICAL_CODE, password : UNICAL_CODE}
```

The **registration** flag indicates that registration is done or not, that is, the site is already in the system.

### 2. Authorization.

Authorization should be done through JWT. The user sends a POST request with login and password and receives a key.

This key is sent in the request in the HEAD block.

```
Authorization: Bearer e25d31c5-db66-4cf2-85d4-8faaa8c544ad6
```

### 3. URL Registration.

Once the user has registered his site he can send links to the site and receive converted links.

**Example**

Sending URL

```
https://job4j.ru/profile/exercise/106/task-view/532
```

Receiving

```
ZRUfdD2
```

The ZRUfD2 key is associated with a URL.

Let's describe the calls to it.

```
POST /convert
```

With the body of a JSON object.

```
{url: "https://job4j.ru/profile/exercise/106/task-view/532"}
```

Response from the server

```
{code: UNICAL_CODE} 
```

### 4. Redirection. It is performed without authorization.

When a site sends a link with a code in response, the associated address and 302 status must be returned.
Let's describe the calls.

```
GET /redirect/UNICAL_CODE
```

The response from the server in the header.

```
HTTP CODE - 302 REDIRECT URL5.
```

### 5. Statistics.

The service counts the number of calls to each address.

Increasing the counter should be done in the database, not in Java. In the answer you need to explain why.

On the site you can get statistics of all addresses and the number of calls of this address.

Let's describe the calls.

```
GET /statistic
```

Response from the JSON server.

```
{
{url : URL, total : 0},
{url : "https://job4j.ru/profile/exercise/106/task-view/532", total : 103}
}
```

### Used technologies:

* Java 17
* Spring Boot 2.7.0
* Spring Data Jpa 2.7.0
* Spring Security 2.7.0
* Java-JWT 3.4.0
* Maven 3.9.5
* PostgreSQL 16
* Mapstruct 1.5.5
* Lombok 1.18.24
* Junit 5.8.2
* AssertJ 3.22.0
* Mockito 4.5.1
* Liquibase 4.9.1
* H2database 2.1.220
* Checkstyle 10.12.5

### Environment Requirements:

* JDK 17
* Maven 3.9.5
* PostgreSQL 16

### How to run the project:

- To run the project, you need to clone the project from this repository;
- Then you need to create a local database "url_shortcut";
- Specify the login and password for the database you created in the db/liquibase.properties file;
- Launch the application through the Main class, located in the folder
  src\main\java\ru\job4jJob4jUrlShortcutApplication;
- Write API test scripts in client (Postman);