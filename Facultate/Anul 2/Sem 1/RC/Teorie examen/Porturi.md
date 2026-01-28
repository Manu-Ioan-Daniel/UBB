Un **port** este o destinație logică (un număr) la nivelul sistemului de operare, folosită pentru a identifica un proces sau un serviciu specific dintr-un calculator conectat la rețea.

### Roluri principale

- **Identificarea aplicației:** În timp ce adresa IP identifică un dispozitiv, portul identifică **aplicația** din interiorul acelui dispozitiv (ex: browser, client de mail, joc).
    
- **Multiplexarea:** Permite unui singur calculator să ruleze zeci de conexiuni simultan prin același cablu de internet, fără ca datele să se amestece între ele.
    
- **Punct de control (Firewall):** Permite blocarea sau deschiderea accesului către anumite servicii (dacă închizi portul, aplicația devine inaccesibilă din exterior).
    

### Clasificare (Grupuri de porturi)

Există în total **65.535** de porturi, împărțite în trei categorii:

|**Categorie**|**Interval**|**Descriere**|
|---|---|---|
|**Well-Known**|0 – 1023|Rezervate pentru servicii standard (HTTP: 80, HTTPS: 443, SSH: 22).|
|**Registered**|1024 – 49151|Folosite de companii pentru aplicații specifice (ex: baze de date, jocuri).|
|**Dynamic/Private**|49152 – 65535|Porturi temporare folosite de calculatorul tău când inițiază o cerere.|

### Caracteristici tehnice

- **Nivel:** Operează la **Nivelul 3 (Transport)**.
- **Protocoale:** Porturile sunt specifice protocolului de transport. Există portul 80 pe **TCP** și, separat, portul 80 pe **UDP**.
    
- **Socket:** Combinația dintre o adresă IP și un număr de port se numește **Socket** (ex: `192.168.1.5:80`).