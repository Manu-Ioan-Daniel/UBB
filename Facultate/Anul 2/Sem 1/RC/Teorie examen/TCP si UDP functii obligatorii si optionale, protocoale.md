| **Apel Sistem**               | **Rol**             | **Protocol TCP**                      | **Protocol UDP**                                  |
| ----------------------------- | ------------------- | ------------------------------------- | ------------------------------------------------- |
| **`socket()`**                | Crearea socket-ului | **Obligatoriu** (ambele părți)        | **Obligatoriu** (ambele părți)                    |
| **`bind()`**                  | Server              | **Obligatoriu** (pentru port fix)     | **Obligatoriu** (pentru port fix)                 |
| **`bind()`**                  | Client              | **Opțional** (OS-ul alege portul)     | **Opțional** (OS-ul alege portul)                 |
| **`listen()`**                | Ambele              | **Obligatoriu** (așteaptă conexiuni)  | **NU se folosește**                               |
| **`accept()`**                | Ambele              | **Obligatoriu** (confirmă conexiunea) | **NU se folosește**                               |
| **`connect()`**               | Ambele              | **Obligatoriu** (face handshake-ul)   | **Opțional** (doar fixare destinație)             |
| **`send()` / `recv()`**       | Ambele              | **Standard** (după conexiune)         | **Opțional** (doar dacă s-a dat `connect`)        |
| **`sendto()` / `recvfrom()`** | Ambele              | Rar folosit (nu e necesar)            | **Standard** (specifică adresa la fiecare pachet) |

| **Protocol**    | **Bazat pe...** | **Ce înseamnă / Rolul lui pe scurt**                         |
| --------------- | --------------- | ------------------------------------------------------------ |
| **HTTP**        | **TCP**         | Protocolul pentru navigare web (transfer de pagini).         |
| **HTTPS**       | **TCP**         | Versiunea securizată (criptată) a HTTP-ului.                 |
| **SSH**         | **TCP**         | Conectare securizată la distanță la alte calculatoare.       |
| **SMTP**        | **TCP**         | Protocolul folosit pentru **trimiterea** de e-mailuri.       |
| **FTP**         | **TCP**         | Protocol pentru transferul de fișiere între calculatoare.    |
| **POP3 / IMAP** | **TCP**         | Protocoale folosite pentru **recepția** e-mailurilor.        |
| **Telnet**      | **TCP**         | Conectare la distanță (necriptată, varianta veche a SSH).    |
| **NTP**         | **UDP**         | Sincronizarea ceasului calculatorului prin rețea.            |
| **DNS**         | **UDP / TCP**   | Transformă numele de site-uri (google.ro) în adrese IP.      |
| **DHCP**        | **UDP**         | Alocă automat adrese IP calculatoarelor din rețea.           |
| **SNMP**        | **UDP**         | Monitorizarea și managementul echipamentelor de rețea.       |
| **TFTP**        | **UDP**         | Versiunea simplificată și rapidă a FTP-ului.                 |
| **VoIP / RTP**  | **UDP**         | Transmisia de voce și video în timp real (apeluri WhatsApp). |