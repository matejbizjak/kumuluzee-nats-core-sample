# KumuluzEE NATS Core sample

> Develop REST services that produces and consumes messages using the NATS Core connective technology  

The objective of this sample is to show how to produce and consume NATS Core messages with a simple REST service using KumuluzEE NATS Core extension.
The tutorial will guide you through all the necessary steps.
You will add KumuluzEE dependencies into pom.xml.
You will develop two simple REST services, which use KumuluzEE NATS Core extension for producing messages
and also few simple annotated methods, which use the KumuluzEE NATS Core extension for consuming messages.

Required knowledge: basic familiarity with JAX-RS and REST; basic familiarity with NATS Core.

## Requirements

In order to run this example you will need the following:

1. Java 8 (or newer), you can use any implementation:
   * If you have installed Java, you can check the version by typing the following in a command line:

       ```
       java -version
       ```

2. Maven 3.2.1 (or newer):
   * If you have installed Maven, you can check the version by typing the following in a command line:

       ```
       mvn -version
       ```
3. Git:
   * If you have installed Git, you can check the version by typing the following in a command line:

       ```
       git --version
       ```

## Prerequisites

To run this sample you will need to start a few instances of NATS servers.
You can do this easily with Docker Compose using the configuration file we provided in the `util/run-nats-server/` folder.

Simply run: 
```
docker-compose -f util/run-nats-server/run-nats-servers.yaml up
```

## Usage

This example uses Docker Compose to set up NATS servers and maven to build and run the microservice.

1. Start the NATS server instances: 
```
docker-compose -f util/run-nats-server/run-nats-servers.yaml up 
```
2. Build the sample using maven:
```
mvn clean package
```
3. Run the sample:
* Uber-jar:

    ```bash
    $ java -jar target/${project.build.finalName}.jar
    ```

  in Windows environemnt use the command
    ```batch
    java -jar target/${project.build.finalName}.jar
    ```

* Exploded:

    ```bash
    $ java -cp target/classes:target/dependency/* com.kumuluz.ee.EeApplication
    ```

  in Windows environment use the command
    ```batch
    java -cp target/classes;target/dependency/* com.kumuluz.ee.EeApplication
    ```
4. Producing messages:

    There are seven REST endpoints available that produce messages.
    You can import the Postman collection located in the `util/postman` folder.
    Each of them produces a message that is sent to the NATS server and can be retrieved by consumers (next step).
    Some producers also expects a response from the consumer. We can see the response in the HTTP response body.

5. Consuming messages:

   The consumed messages will be printed in the terminal.

To shut down the example simply stop the processes in the foreground and all Docker containers started for this example.

## Tutorial

This tutorial will guide you through the steps required to create a NATS Core producers and consumers with the help of the KumuluzEE NATS Core extension.
We will develop two simple REST services for producing NATS Core messages and a few simple annotated methods which will be invoked when the message is consumed.

We will follow these steps:
* Create a Maven project in the IDE of your choice (Eclipse, IntelliJ, etc.)
* Add Maven dependencies to KumuluzEE and include KumuluzEE components with the microProfile-3.3 dependency
* Add Maven dependency to KumuluzEE NATS Core extension
* Configure NATS connections
* Implement the annotated methods and REST services
* Build the microservice
* Run it

Add the KumuluzEE BOM module dependency to your `pom.xml` file:
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.kumuluz.ee</groupId>
            <artifactId>kumuluzee-bom</artifactId>
            <version>${kumuluzee.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

Add the `kumuluzee-microProfile-3.3` and `kumuluzee-nats-core` dependencies:
```xml
<dependencies>
    <dependency>
        <groupId>com.kumuluz.ee</groupId>
        <artifactId>kumuluzee-microProfile-3.3</artifactId>
    </dependency>
    <dependency>
        <groupId>com.kumuluz.ee.nats</groupId>
        <artifactId>kumuluzee-nats-core</artifactId>
        <version>${kumuluzee-nats.version}</version>
    </dependency>
</dependencies>
```
We will use `kumuluzee-logs` for logging in this sample, so you need to include kumuluzee logs implementation dependency:
```xml
<dependency>
    <artifactId>kumuluzee-logs-log4j2</artifactId>
    <groupId>com.kumuluz.ee.logs</groupId>
    <version>${kumuluzee-logs.version}</version>
</dependency>
```
For more information about the KumuluzEE Logs visit the [KumuluzEE Logs Github page](https://github.com/kumuluz/kumuluzee-logs).
Currently, Log4j2 is supported implementation of `kumuluzee-logs`, so you need to include a sample Log4j2 configuration,
which should be in a file named `log4j2.xml` and located in `src/main/resources`:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration name="config-name">
    <Appenders>
        <Console name="console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d %p %marker %m %X %ex %n"/>
        </Console>
    </Appenders>
    <Loggers>
        <Root level="info">
            <AppenderRef ref="console"/>
        </Root>
    </Loggers>
</Configuration>
```
If you would like to collect NATS related logs through the KumuluzEE Logs, you have to include the following `slf4j` implementation as dependency:
```xml
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j-impl</artifactId>
    <version>${log4j-slf4j.version}</version>
</dependency>
```

Add the `jackson-datatype-jsr310` dependency for our custom ObjectMapper provider, so it can work with Java 8 Date & Time API.

```xml
<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jsr310</artifactId>
</dependency>
```

Add the `kumuluzee-maven-plugin` build plugin to package microservice as uber-jar:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.kumuluz.ee</groupId>
            <artifactId>kumuluzee-maven-plugin</artifactId>
            <version>${kumuluzee.version}</version>
            <executions>
                <execution>
                    <id>package</id>
                    <goals>
                        <goal>repackage</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

or exploded:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.kumuluz.ee</groupId>
            <artifactId>kumuluzee-maven-plugin</artifactId>
            <version>${kumuluzee.version}</version>
            <executions>
                <execution>
                    <id>package</id>
                    <goals>
                        <goal>copy-dependencies</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

### Implement the servlet

Register your module as JAX-RS service and define the application path. You could do that in web.xml or
for example with `@ApplicationPath` annotation:

```java
@ApplicationPath("v1")
public class ProducerApplication extends Application {
}
```

#### Custom ObjectMapper

First create a custom ObjectMapper for de/serialization by implementing `NatsObjectMapperProvider`.
Here we register `JavaTimeModule` which enables the usage of Java 8 Date & Time API.

```java
public class NatsMapperProvider implements NatsObjectMapperProvider {

    @Override
    public ObjectMapper provideObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
```

#### Producer

Create the interface TextClient, annotate it with `@RegisterRestClient` and specify the name of the NATS connection that the client will use.

Annotate each producer method with `@Subject`.
If you want to specify the subject dynamically, use parameter annotation (as you can see at the method `sendTextDynamicSubjectResponse()`).

```java
@RegisterNatsClient(connection = "default")
public interface TextClient {

    @Subject(value = "text1")
    void sendText(String value);

    @Subject(value = "text2")
    String sendTextResponse(String value);

    @Subject(connection = "default")
    String sendTextDynamicSubjectResponse(@Subject String subject, String value);
}
```

Implement the first JAX-RS resource with GET methods that initiate producing of the messages.
Inject the Nats Client `TextClient` we described before with the `@NatsClient` and call the corresponding methods.

```java
@Path("/text/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class TextResource {

    @Inject
    @NatsClient
    private TextClient textClient;

    @GET
    public Response getText() {
        textClient.sendText("simple string");
        return Response.ok("A simple message was sent.").build();
    }

    @GET
    @Path("/withResponse")
    public Response getTextResponse() {
        String msgResponse = textClient.sendTextResponse("another simple string");
        return Response.ok(String.format("A simple message was sent. Even more, I also received a response: '%s'"
                , msgResponse)).build();
    }

    @GET
    @Path("/withResponseDynamicSubject")
    public Response getTextDynamicSubject() {
        String msgResponse = textClient.sendTextDynamicSubjectResponse("dynamic", "simple string with dynamic subject");
        return Response.ok(String
                .format("A simple message was sent to a dynamic subject. Even more, I also received a response: '%s'"
                        , msgResponse)).build();
    }
}
```

#### Consumer

To consume messages, create a class and annotate it with `@NatsListener`.
Specify the name of the connection the consumer will use.

Then create consumer methods by annotating them with `@Subject` and specify the subject name. If you add more consumers to the same group, only one of them will get the message. 

```java
@NatsListener(connection = "default")
public class TextListener {

    @Subject(value = "text1")
    public void receive(String value) {
        System.out.println(value);
    }

    @Subject(value = "text2", queue = "group1")
    public String receiveAndReturn1(String value) {
        System.out.println(value);
        return value.toUpperCase();
    }

    @Subject(value = "text2", queue = "group1")
    public String receiveAndReturn2(String value) {
        System.out.println(value);
        return value.toLowerCase();
    }

    @Subject(value = "dynamic")
    public String receiveDynamicSubject(String value) {
        System.out.println(value);
        return value.toUpperCase();
    }
}
```

---

You can continue developing another REST endpoint for producer and consumer: ProductClient, ProductResource and ProductListener.
The main difference is that the type of the messages is not String anymore, but a more complex class `Product`.
The extension automatically de/serializes the data, just make sure to include the default constructor 
```java
public Product() {
}
```
in your class. And that the consumer's message type matches the producer's.

The product variant is also using a different NATS connection that is secured with the TLS.
In the next section we learn how to configure the connections and how to use the TLS.

### Configuration

Let's configure 2 NATS connections we need for this application:
* default: unsecured connection on the default address
* secure: secured connection by Mutual TLS on localhost:4224

```yaml
kumuluzee:
  nats:
    response-timeout: PT5S
    servers:
      - name: default
        addresses:
          - nats://localhost:4222
      - name: secure
        addresses:
          - tls://localhost:4224
        tls:
#          trust-store-path: C:\Users\Matej\IdeaProjects\kumuluzee-nats-core-sample\src\main\resources\certs\truststore.jks
#          trust-store-password: password2
          certificate-path: C:\Users\Matej\IdeaProjects\kumuluzee-nats-core-sample\src\main\resources\certs\server-cert.pem
          key-store-path: C:\Users\Matej\IdeaProjects\kumuluzee-nats-core-sample\src\main\resources\certs\keystore.jks
          key-store-password: password
```

See the next section to learn how to set up the TLS.

### Securing with TLS:
1. Generate self-singed CA, client certificates:

    [Easy way (using mkcert)](https://docs.nats.io/running-a-nats-service/configuration/securing_nats/tls#creating-self-signed-certificates-for-testing):
    ```
    mkcert -install
    mkcert -cert-file server-cert.pem -key-file server-key.pem localhost ::1
    mkcert -client -cert-file client-cert.pem -key-file client-key.pem localhost ::1 email@localhost
    ``` 
2. [Create a truststore and keystore](https://docs.nats.io/using-nats/developer/connecting/tls):

    ```
   (winpty) openssl pkcs12 -export -out keystore.p12 -inkey client-key.pem -in client-cert.pem -password pass:password
   keytool -importkeystore -srcstoretype PKCS12 -srckeystore keystore.p12 -srcstorepass password -destkeystore keystore.jks -deststorepass password
   
   keytool -importcert -trustcacerts -file rootCA.pem -storepass password2 -noprompt -keystore truststore.jks
    ```
   You can find the location of your rootCA with `mkcert -CAROOT`.

3. Add server's certificates to the configuration (examples are in *util/run-nats-server*)
   
4. Properly set the client's configuration file:

   ```yaml
   tls:
       ###
       trust-store-path: ...\resources\certs\truststore.jks
       trust-store-password: password2
       # or
       certificate-path: ...\resources\certs\server-cert.pem
       ###
       key-store-path: ...\resources\certs\keystore.jks
       key-store-password: password
   ```
   - You can either use a truststore or server's certificate for server verification. 
   - Keystore is only needed at Mutual TLS (when also verifying clients). You enable this feature with `verify: true` in the TLS settings in the NATS server configuration file.

#### Examples

##### Default server connection with a custom response timeout
```yaml
kumuluzee:
  nats:
    response-timeout: PT5S
```

##### TLS with a single address

```yaml
kumuluzee:
  nats:
    response-timeout: PT5S
    servers:
      - name: secure-unverified-client
        addresses:
          - tls://localhost:4223
        tls:
#          trust-store-path: C:\Users\Matej\IdeaProjects\Nats Core Sample\src\main\resources\certs\truststore.jks
#          trust-store-password: password2
          certificate-path: C:\Users\Matej\IdeaProjects\Nats Core Sample\src\main\resources\certs\server-cert.pem
```

You can either specify the trust store and password, or the server's certificate path.

##### Mutual TLS with a single address

```yaml
kumuluzee:
  nats:
    servers:
      - name: secure
        addresses:
          - tls://localhost:4224
        tls:
          trust-store-path: C:\Users\Matej\IdeaProjects\Nats Core Sample\src\main\resources\certs\truststore.jks
        trust-store-password: password2
#        certificate-path: C:\Users\Matej\IdeaProjects\Nats Core Sample\src\main\resources\certs\server-cert.pem
        key-store-path: C:\Users\Matej\IdeaProjects\Nats Core Sample\src\main\resources\certs\keystore.jks
        key-store-password: password
```

For Mutual TLS you also need to specify a key store.

### Build the microservice and run it

To build the microservice and run the example, use the commands as described in previous sections.
