# spring-kotlin-auth

Authorization Service

This service currently attempts to the minimum requirements to satisfy an OIDC client using an Implicit auth flow
## Prerequisites
* JDK 8 ([Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or [OpenJDK](http://openjdk.java.net/install/))


## Building
```bash
./gradlew build
```

## Testing
```bash
./gradlew test
```

## Running Locally
```bash
./gradlew bootRun
```

### Endpoints
* `/oauth/authorize` for client authorization (authentication provider redirect)
* `/userinfo` for clients to retrieve additional identity data
* `/logout` for invalidating the session and redirecting to to the provided `post_logout_redirect_uri`

OAuth2/OIDC Clients can request an access token using the `app.auth` properties defined in *application.yml*.
For example,
* Grant Type: Implicit
* Callback URL: http://localhost:3000/implicit/callback
* Auth URL: http://localhost:9000/oauth/authorize
* Client ID:foo
* Scope: openid

With those properties, the `UserManager` in [oidc-client-js](https://github.com/IdentityModel/oidc-client-js/wiki#usermanager) can be configured as follows:
```javascript
let userManager = new UserManager({
    client_id: 'foo',
    redirect_uri: `http://localhost:3000/implicit/callback`,
    post_logout_redirect_uri: `http://localhost:3000/`,
    response_type: 'token',
    scope: 'openid',
    authority: 'http://localhost:9000/oauth/authorize',
    loadUserInfo: true,
    metadata: {
      issuer: 'localhost',
      authorization_endpoint: 'http://localhost:9000/oauth/authorize',
      end_session_endpoint: 'http://localhost:9000/logout',
      userinfo_endpoint: 'http://localhost:9000/userinfo'
    }
});
```

## IntelliJ IDEA
To use [Intellij IDEA](https://www.jetbrains.com/idea/) for development:
1. Import this project via File → New → Project From Existing Sources... → (Select project folder)
1. Select *Gradle* and use the Gradle Wrapper if not set

To Debug, you first need to create a *Debug Configuration*. The simplest way is to do the following:
1. Expand the *Gradle* pane on the right
1. Expand *Tasks* → *application*
1. Right-click *bootRun*, and select *Debug authorization-service [bootRun]*

You should now have a Debug Configuration available to reuse later.

## Public key
The keystore used by the token converter can be found in `src/main/resources/`. 

Using 'password' as the keystore password, you can get the public key via:
```bash
keytool -list -rfc --keystore src/main/resources/keystore.jks | openssl x509 -inform pem -pubkey -noout
```
