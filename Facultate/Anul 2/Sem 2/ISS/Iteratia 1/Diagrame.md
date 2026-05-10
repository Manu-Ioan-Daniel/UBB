
## Diagrame de Secventa

### UC-1: Register Account

```mermaid
sequenceDiagram
    participant U as «actor» Visitor
    participant B as «boundary» RegistrationForm
    participant C as «control» RegistrationCtrl
    participant S as «entity» SGBD

    U->>B: 1: clickRegister()
    B->>C: 2: <<create>>
    C-->>B: 3: displayForm()
    B-->>U: 4: showRegistrationForm()
    U->>B: 5: submitData(name, email, pwd)
    B->>C: 6: validateAndRegister(data)
    C->>S: 7: checkEmailExists(email)
    S-->>C: 8: emailUnique
    C->>S: 9: <<create>> saveNewUser(details)
    S-->>C: 10: accountCreated
    C-->>B: 11: registrationSuccess
    C-->>C: 12: <<destroy>>
    B-->>U: 13: redirectToLogin()
```



### UC-2: Login

```mermaid
sequenceDiagram
    participant A as «actor» User/Dealer/Admin
    participant B as «boundary» LoginForm
    participant C as «control» AuthController
    participant S as «entity» SGBD

    A->>B: 1: openLoginPage()
    B->>C: 2: <<create>>
    C-->>B: 3: displayLoginForm()
    B-->>A: 4: showLoginForm()
    A->>B: 5: enterCredentials(email, password)
    B->>C: 6: login(email, password)
    C->>S: 7: verifyCredentials(email, password)
    S-->>C: 8: credentialsValid
    C->>C: 9: createSession()
    C-->>B: 10: authenticationSuccess(role)
    destroy C
    C-->>C: 11: <<destroy>>
    B-->>A: 12: redirectToDashboard(role)
```

---

### UC-3: Browse and Search Motorcycles

```mermaid
sequenceDiagram
    participant U as «actor» User
    participant B as «boundary» CatalogPage
    participant C as «control» SearchController
    participant S as «entity» SGBD

    U->>B: 1: openCatalog()
    B->>C: 2: <<create>>
    C->>S: 3: fetchAllMotorcycles()
    S-->>C: 4: motorcycleList
    C-->>B: 5: displayResults(data)
    B-->>U: 6: showMotorcycles()
    U->>B: 7: applyFilters(criteria)
    B->>C: 8: filterAndSort(data, criteria)
    C->>C: 9: applyFilters(data, criteria)
    C->>C: 10: applySort(filteredData)
    C-->>B: 11: updateResults(filteredData)
    B-->>U: 12: showFilteredMotorcycles()
    destroy C
    C-->>C: 13: <<destroy>>
```

---

## Diagrame de Comunicare

### UC-1: Register Account

```mermaid
graph TD
    U["«actor» Visitor"]
    B["«boundary» RegistrationForm"]
    C["«control» RegistrationCtrl"]
    S["«entity» SGBD"]

    U -->|"1: clickRegister()"| B
    B -->|"2: «create»"| C
    C -->|"3: displayForm()"| B
    B -->|"4: showRegistrationForm()"| U
    U -->|"5: submitData(name, email, pwd)"| B
    B -->|"6: validateAndRegister(data)"| C
    C -->|"7: checkEmailExists(email)"| S
    S -->|"8: emailUnique"| C
    C -->|"9: saveNewUser(details)"| S
    S -->|"10: accountCreated"| C
    C -->|"11: registrationSuccess"| B
    B -->|"12: redirectToLogin()"| U
    C -->|"13: «destroy»"| C
```

---

### UC-2: Login

```mermaid
graph TD
    A["«actor» User/Dealer/Admin"]
    B["«boundary» LoginForm"]
    C["«control» AuthController"]
    S["«entity» SGBD"]

    A -->|"1: openLoginPage()"| B
    B -->|"2: «create»"| C
    C -->|"3: displayLoginForm()"| B
    B -->|"4: showLoginForm()"| A
    A -->|"5: enterCredentials(email, password)"| B
    B -->|"6: login(email, password)"| C
    C -->|"7: verifyCredentials(email, password)"| S
    S -->|"8: credentialsValid"| C
    C -->|"9: createSession()"| C
    C -->|"10: authenticationSuccess(role)"| B
    B -->|"11: redirectToDashboard(role)"| A
    C -->|"12: «destroy»"| C
```

---

### UC-3: Browse and Search Motorcycles

```mermaid
graph TD
    U["«actor» User"]
    B["«boundary» CatalogPage"]
    C["«control» SearchController"]
    S["«entity» SGBD"]

    U -->|"1: openCatalog()"| B
    B -->|"2: «create»"| C
    C -->|"3: fetchAllMotorcycles()"| S
    S -->|"4: motorcycleList"| C
    C -->|"5: displayResults(data)"| B
    B -->|"6: showMotorcycles()"| U
    U -->|"7: applyFilters(criteria)"| B
    B -->|"8: filterAndSort(data, criteria)"| C
    C -->|"9: applyFilters(data, criteria)"| C
    C -->|"10: applySort(filteredData)"| C
    C -->|"11: updateResults(filteredData)"| B
    B -->|"12: showFilteredMotorcycles()"| U
    C -->|"13: «destroy»"| C
```
