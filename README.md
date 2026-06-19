## Tech Stack

## Tech Stack

<!-- ![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)
&nbsp;&nbsp;
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=springboot&logoColor=white)
&nbsp;&nbsp;
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.x-6DB33F?logo=springsecurity&logoColor=white)
&nbsp;&nbsp;
![OAuth2](https://img.shields.io/badge/OAuth2.0-Google-4285F4?logo=google&logoColor=white)
&nbsp;&nbsp;
![JWT](https://img.shields.io/badge/JWT-Authentication-000000?logo=jsonwebtokens&logoColor=white)
&nbsp;&nbsp;
![React](https://img.shields.io/badge/React-19-61DAFB?logo=react&logoColor=black)
&nbsp;&nbsp;
![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?logo=mysql&logoColor=white)-->

### Backend

![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.x-6DB33F?logo=springsecurity&logoColor=white)

### Authentication

![Google OAuth](https://img.shields.io/badge/Google-OAuth2-4285F4?logo=google&logoColor=white)
![GitHub OAuth](https://img.shields.io/badge/GitHub-OAuth2-181717?logo=github&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Authentication-000000?logo=jsonwebtokens&logoColor=white)

### Frontend & Database

![React](https://img.shields.io/badge/React-19-61DAFB?logo=react&logoColor=black)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=white)

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