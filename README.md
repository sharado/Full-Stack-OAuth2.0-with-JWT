<h2 align="center">Tech Stack</h2>
<hr/>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white" />
  &nbsp;
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=springboot&logoColor=white" />
  &nbsp;
  <img src="https://img.shields.io/badge/Spring%20Security-6.x-6DB33F?logo=springsecurity&logoColor=white" />
</p>

<p align="center">
  <img src="https://img.shields.io/badge/JWT-Access%20%26%20Refresh%20Tokens-000000?logo=jsonwebtokens&logoColor=white" />
  &nbsp;
  <img src="https://img.shields.io/badge/Google-OAuth2-4285F4?logo=google&logoColor=white" />
  &nbsp;
  <img src="https://img.shields.io/badge/GitHub-OAuth2-181717?logo=github&logoColor=white" />
</p>

<p align="center">
  <img src="https://img.shields.io/badge/React-19-61DAFB?logo=react&logoColor=black" />
  &nbsp;
  <img src="https://img.shields.io/badge/React_Bootstrap-7952B3?logo=bootstrap&logoColor=white" />
  &nbsp;
  <img src="https://img.shields.io/badge/MySQL-Database-4479A1?logo=mysql&logoColor=white" />
</p>
<hr/>

### Backend
- Java 21
- Spring Boot
- Spring Security
- OAuth 2.0
- JWT Authentication

### Frontend
- React.js
- React Bootstrap
- SCSS

### Database
- MySQL

### Authentication
- Google OAuth2
- GitHub OAuth2
- JWT Access & Refresh Tokens

```mermaid
flowchart TD
    A[Browser]
    B[React SPA]
    C[OAuth Server]
    D[Spring Security OAuth2]
    E[JWT Token Service]
    F[(MySQL)]

    A <--> B
    B <--> C
    C <--> D
    D <--> E
    E <--> F
```