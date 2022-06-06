# Secure Remote Password
**SRP | Safe authentication without password exchange**

This is a Java implementation of Secure Remote Password as documented at [RFC5054](https://datatracker.ietf.org/doc/html/rfc5054).

## How to use?

### Options
The options that will be created here, will be used from both server and client.
Take caution that different options will generate different values, so there will be no compatibility.

```java
Options options = new Options();
options.routines = new Routines(); // This is the default and can be ommited. You can write your own routines by inheriting the routines clss
options.primeGroup = Routines.PrimeGroup.get(2048);
options.hashFunction = Routines.Hash.get("SHA512");
```

### Registration

```java
// Client
final String username = "projectChristopher";
final String password = "password";

IVerifierAndSalt verifierAndSalt = Client.register(options, username, password);
String salt = verifierAndSalt.salt;
String verifier = verifierAndSalt.verifier;
/* sendToServer(username, salt, verifier) */

// Server
/* storeToDatabase(username, salt, verifier) */
```

### Login
```java
final String username = "projectChristopher";
String password = "password";

// Client
Client client = new Client(options);
client.step1(username, password);
password = ""; // No longer needed.
/* sendToServer(username) */

// Server
Server server = new Server(options);
/* Document document = getFromDatabase(username); */
if(document == null) {
// Send random data to avoid if user exists
/* respondToClient(randomB, randomSalt); */
return;
}

String salt = document.salt;
String B = server.step1(username, salt, document.verifier); // Generate server's public key
/* saveToDatabase(server.toState()); */
/* respondToClient(B, salt); */

// Client
M1AndA m1AndA = client.step2(salt, B);
String A = m1AndA.A;
String M1 = m1AndA.M1;
/* sendToServer(A, M1) */

// Server
/* Document document = getFromDatabase(username) */
options.srvState = document;
Server server2 = new Server(options);
String M2 = server.step2(A, M1);
/* respondToClient(M2) */

// Client
client.step3(M2);
```

For the full documentation visit the [docs](https://project-christopher.com/docs/srp/installation).
