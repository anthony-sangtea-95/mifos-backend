# SG-CBS Fineract (training mini backend)

Sample Spring Boot + JAX-RS users API (Java 25 toolchain).

## Run

```bat
docker compose up -d
set JAVA_HOME=C:\Program Files\Zulu\zulu-21
set GRADLE_OPTS=-Djavax.net.ssl.trustStoreType=Windows-ROOT
run-gradle.cmd bootRun
```

- Health: http://localhost:8088/mini-fineract-provider/api/v1/health
- Users: http://localhost:8088/mini-fineract-provider/api/v1/users

DB: localhost:5433 / mini_fineract / postgres / postgres

## Security (same idea as Apache Fineract / web-app)

| Mode | Default | How |
|------|---------|-----|
| **HTTP Basic** | **Yes** | Login `POST /v1/authentication`, then `Authorization: Basic …` |
| **OAuth2 / JWT** | Optional | Keycloak on :9000 + flags — see `c:\mifos\DOCS\04-oauth2-jwt-setup.md` |

```properties
training.security.basicauth.enabled=true
training.security.oauth2.enabled=false
```

Frontend: `environment.oauth.enabled = false` (Basic). Set `true` only when using Keycloak.
