## Tech Stack

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
    C[Google OAuth]
    D[Spring Security OAuth2]
    E[JWT Token Service]
    F[(MySQL)]

    A <--> B
    B <--> C
    C <--> D
    D <--> E
    E <--> F
```