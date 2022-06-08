# Secure Remote Password
**SRP | Safe authentication without password exchange**

This is a Java implementation of Secure Remote Password as documented at [RFC5054](https://datatracker.ietf.org/doc/html/rfc5054).

## Installation

### Gradle
```gradle
dependencies {
    //...
    // Kindly download and build artifact
}
```

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

### Credentials
The user's credentials that will be used for registration and authentication.
```java
final String username = "JexSrs";
final String password = "pass123";
```

### Registration
This flow will register a new user to the server.

```java
// Client
IVerifierAndSalt verifierAndSalt = Client.register(options, username, password);
sendToServer(username, verifierAndSalt.salt, verifierAndSalt.verifier);

// Server
storeToDatabase(username, salt, verifier);
```

### Login
This flow will verify a user that has registered using the above flow.
__Caution!__ If the options used during registration are different from the ones used during authentication,
the authentication will always fail.

#### Step 1
In this step we will initialize client with user's credentials and request from server a generated
public value (B) and the salt that was given during registration.

```java
// Client
Client client = new Client(options);
client.step1(username, password);
password = ""; // No longer needed.
sendToServer(username);

// Server
Server server = new Server(options);
Document document = getFromDatabase(username);
if(document == null) {
    // Send random data to avoid if user exists
    respondToClient(randomB, randomSalt);
    return;
}

String salt = document.salt;
String B = server.step1(username, salt, document.verifier); // Generate server's public key
saveToCache(server.toState()); // Mayve a redis or database
respondToClient(B, salt);
```
### Step 2
In this step the client has received the public value (B) and salt from the server.
The client will now generate a public value (A) and the evidence message (M1) and send it to server
to authenticate itself.

```java
// Client
M1AndA m1AndA = client.step2(salt, B);
sendToServer(m1AndA.A, m1AndA.M1);

// Server
Document document = getFromCache(username);
if(document == null)
    return sendToClient('Authentication failed');

options.srvState = document; // add server state to options
Server server = new Server(options);

String M2;
try {
    M2 = server.step2(A, M1);
} catch(BadClientCredentials e) {
    return sendToClient('Authentication failed');
}
 
respondToClient(M2);
```

#### Step 3
In step 3 the client has received the server's evidence message (M2) and will verify that the server is
the same as the one that started the authentication.

```java
// Client
try {
    client.step3(M2);
} catch (BadServerCredentials e) {
    // Server is not the one we started.
}
```

## Options
### PrimeGroup
Default value: `2048`

Available values: `256`, `512`, `768`, `1024`, `1536`, `2048`, `3072`, `4096`, `6144`, `8192`

### HashFunction
Default value: `SHA512`

Available values: `SHA1`, `SHA256`, `SHA384`, `SHA512`

### Routines
Tou can always implement different routines by extending the routines class.

### Server State
Initialize server using an older state. This can be when authenticating with HTTP protocol.

### Client State
Initialize client using an older state.


