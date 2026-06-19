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