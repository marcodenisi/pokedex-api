# Pokedex API

Simple service exposing two REST APIs:

`GET /pokemon/{pokemon_name}`

`GET /pokemon/translated/{pokemon_name}`

### Tools and Frameworks

To quickly create this service I decided to use the tech stack I'm most comfortable with, that is:

- Java 11
- Spring Boot

To containerize this app, a simple Dockerfile is included. Check below to see instructions on how to use it.

### How to run

#### Method 1 - Using Maven Wrapper

This is the preferred method if you want to just check this out without installing anything else. Just open your terminal,
navigate to the root of this project and run:

`./mvnw spring-boot:run`

The server will start at port 5000.

#### Method 2 - Using Java

First, create the `jar` file by typing:

`./mvnw clean install`

This will clean up already present artifacts, run tests and eventually create the `jar` file. 

`java -jar target/pokedex-api-0.0.1-SNAPSHOT.jar`

The server will start at port 5000.

#### Method 3 - Using Docker

Create the `jar` file:

`./mvnw clean install`

Build the docker image:

`docker build -t md/pokedex-api .`

Run it:

`docker run -p 5000:5000 md/pokedex-api`

The server will start at port 5000.

### Improvements

#### Exception handling
* better exception handling: right now for each and every integration exception I just return 422 instead of
  differentiating different scenarios

#### Logging
* better logging (JSON formatted) redirected to an external service to be queried later for debug reasons
  (e.g., Splunk, Datadog)

#### Tests
* unit tests: while I'm not a huge fan of having 100% coverage, I think having a good coverage (75%+) adds great value and confidence 
while developing. In this case I just put together the most meaningful unit tests, so this is something to improve.
* add integration tests, end-to-end tests, performance tests.

#### External API calls
* set timeouts, circuit breakers, fallbacks, rate limiters...
* put a cache between Pokedex service and the translation APIs to limit paid calls

#### CI/CD, Branching Strategy & Semantic Versioning
* decide on a branching strategy (`git-flow`?)
* create CI/CD pipelines triggered from git pushes
  * run tests, build artifact, version it, deploy

#### Api versioning
Can be useful to version APIs (by prepending `/v1`, `/v2`, ... to the API endpoint) to support all clients while the service
evolves.

#### Different envs
* test - for dev, maybe with mocked external services
* staging - as pre-prod, also for test integrations with external services
* performance - for perf tests
* prod

#### Metrics
* export our API response times, response status codes
* export external services response times, response status codes
* Micrometer & Prometheus
