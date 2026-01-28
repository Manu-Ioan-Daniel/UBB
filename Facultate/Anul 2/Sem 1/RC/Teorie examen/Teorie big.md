
### Definiția domeniu de coliziune

Un domeniu de coliziune este un segment de rețea în care, dacă două dispozitive transmit date (pachete) exact în același moment, semnalele electrice se vor ciocni pe fir și datele vor fi distruse.

#### 3. Cum influențează echipamentele domeniul de coliziune?

Aceasta este partea cea mai importantă pentru examene și înțelegerea rețelelor:

#### A. HUB-ul (Extinde domeniul de coliziune)

- Un Hub este ca o prelungire a cablului. El conectează electric toate porturile între ele.
    
- **Regula:** Oricâte calculatoare ai conecta la un Hub (sau la mai multe Hub-uri legate între ele), **toate fac parte dintr-un SINGUR domeniu de coliziune**.
    
- **Rezultat:** Dacă PC1 trimite ceva, PC5 trebuie să aștepte. Dacă nu așteaptă -> Coliziune -> Rețeaua merge greu.
    

#### B. SWITCH-ul (Sparge domeniul de coliziune)

- Un Switch este inteligent. El creează o conexiune privată, directă, între expeditor și destinatar.
    
- **Regula:** Fiecare **port** al unui Switch este propriul său domeniu de coliziune separat.
    
- **Exemplu:** Dacă ai un Switch cu 8 porturi și 8 calculatoare, ai **8 domenii de coliziune**.
    
- **Rezultat:** PC1 poate vorbi cu PC2, în timp ce PC3 vorbește cu PC4, simultan, fără nicio coliziune.
    

#### 4. Ce se întâmplă când are loc o coliziune?

În rețelele Ethernet vechi (cu Hub-uri), există un protocol numit **CSMA/CD** (Carrier Sense Multiple Access with Collision Detection) care gestionează situația:

1. Calculatoarele detectează că voltajul pe cablu a crescut brusc (semn de coliziune).
    
2. Toată lumea se oprește din vorbit (trimite un semnal de _jam_).
    
3. Fiecare calculator așteaptă un timp aleatoriu (milisecunde).
    
4. Cine are timpul cel mai scurt, începe să transmită din nou.
    

#### Rezumat:

- **Domeniu de coliziune =** Zona unde datele se pot ciocni.
    
- **Hub =** 1 singur domeniu mare (Rău pentru viteză).
    
- **Switch =** Multe domenii mici (Excelent pentru viteză).

### Domenii de broadcast

Pe scurt, un **Domeniu de Broadcast** este „zona” dintr-o rețea în care un mesaj de tip broadcast (un strigăt general) este recepționat de toate dispozitivele.

Iată esențialul:

#### 1. Ce este un Broadcast?

Este un pachet de date trimis de un dispozitiv către **toate** celelalte dispozitive din aceeași rețea. Este echivalentul unui anunț la megafon într-o instituție: toată lumea din clădire îl aude, chiar dacă mesajul nu îi privește pe toți.

#### 2. Cum se comportă echipamentele?

- **HUB-ul:** Trimite broadcast-ul peste tot (nu are nicio putere de filtrare).
    
- **SWITCH-ul:** Deși oprește coliziunile, Switch-ul **REPETĂ** orice mesaj de broadcast pe toate porturile sale. Deci, toate dispozitivele conectate la unul sau mai multe switch-uri legate între ele fac parte din **același domeniu de broadcast**.
    
- **ROUTER-ul:** Este singurul care **OPREȘTE** (blochează) broadcast-ul. Un router nu va trimite niciodată un mesaj de broadcast din Rețeaua A în Rețeaua B.
    

#### 3. De ce contează?

Dacă un domeniu de broadcast este prea mare (mii de calculatoare legate doar prin switch-uri):

1. **Zgomot:** Fiecare calculator „strigă” din când în când (ex: pentru a afla o adresă IP prin protocolul ARP).
    
2. **Performanță scăzută:** Toate celelalte mii de calculatoare trebuie să proceseze acel „strigăt” pentru a vedea dacă le este destinat, ceea ce le consumă resursele.
    
3. **Soluția:** Folosirea unui **Router** (sau a VLAN-urilor pe switch-uri deștepte) pentru a sparge rețeaua mare în domenii de broadcast mai mici.
    


**Recapitulare rapidă:**

- **Switch-ul** = Sparge domenii de **coliziune**.
    
- **Router-ul** = Sparge domenii de **broadcast**.
### I. Dispozitive de Rețea: Hub vs. Switch vs. Router

Diferența principală dintre ele este **inteligența** (la ce nivel OSI lucrează) și modul în care gestionează traficul.

#### 1. HUB (Concentrator)

- **Nivel OSI:** 1 (Fizic).
    
- **Inteligență:** Nulă ("Dispozitiv prost").
    
- **Funcționare:** Când primește biți pe un port, îi copiază electric și îi trimite pe **toate** celelalte porturi (Broadcast). Nu știe cine este destinatarul.
    
- **Problemă majoră:**
    
    - **Coliziuni:** Toate porturile sunt într-un singur _Domeniu de Coliziune_. Dacă două PC-uri vorbesc deodată, datele se strică.
        
    - **Securitate:** Orice PC conectat poate "asculta" traficul celorlalte.
        
    - **Duplex:** Funcționează doar **Half-Duplex** (nu poate primi și trimite în același timp).
        

#### 2. SWITCH (Comutator)

- **Nivel OSI:** 2 (Legătură de date - Data Link).
    
- **Inteligență:** Medie. Folosește adrese **MAC** (adrese fizice).
    
- **Funcționare:** Are o tabelă (MAC Table) în memorie. Când primește date, citește adresa MAC a destinatarului și trimite pachetul **DOAR** pe portul unde se află acel calculator.
    
- **Avantaj:**
    
    - Fiecare port este propriul său _Domeniu de Coliziune_. Coliziunile sunt eliminate aproape total.
        
    - Permite **Full-Duplex** (trimite și primește simultan).
        
    - Viteza rețelei este mult mai mare decât la Hub.
        

#### 3. ROUTER (Rutator)

- **Nivel OSI:** 3 (Rețea).
    
- **Inteligență:** Ridicată. Folosește adrese **IP** (adrese logice).
    
- **Funcționare:** Este "creierul" care conectează rețele **diferite** (ex: rețeaua ta de acasă cu Internetul). Decide cea mai bună rută pentru ca un pachet să ajungă la destinație.
    
- **Rol cheie:**
    
    - Separă _Domeniile de Broadcast_ (oprește traficul inutil să inunde alte rețele).
        
    - Fără router, nu ar exista Internet, doar rețele locale izolate.


        



### II. Topologii de Rețea (Arhitectura fizică)

Topologia se referă la modul în care sunt trase cablurile și conectate dispozitivele.

#### 1. Topologia BUS (Magistrală)

- **Descriere:** Un singur cablu central (backbone) la care sunt conectate toate calculatoarele. La capete are "terminatori" pentru a absorbi semnalul.
    
- **Avantaje:** Ieftină, ușor de instalat pentru 2-3 calculatoare.
    
- **Dezavantaje:**
    
    - Dacă se rupe cablul principal oriunde, **toată rețeaua cade**.
        
    - Multe coliziuni.
        
- **Statut:** Învechită (nu se mai folosește).
    

#### 2. Topologia STAR (Stea)

- **Descriere:** Toate cablurile pleacă dintr-un punct central (Switch sau Hub) către calculatoare.
    
- **Avantaje:**
    
    - Dacă se rupe cablul unui PC, doar acel PC este afectat; restul rețelei merge.
        
    - Ușor de depanat.
        
- **Dezavantaje:** Dacă se strică echipamentul central (Switch-ul), toată rețeaua cade.
    
- **Statut:** Cea mai folosită astăzi (în LAN-uri).
    

#### 3. Topologia EXTENDED STAR (Stea Extinsă)

- **Descriere:** Mai multe topologii "Stea" conectate între ele. Un Switch central se conectează la alte Switch-uri secundare, care la rândul lor au PC-uri.
    
- **Utilizare:** Clădiri mari, campusuri. Permite extinderea ușoară a rețelei fără a trage cabluri de la fiecare PC până la un singur punct central îndepărtat.
    

#### 4. Topologia RING (Inel)

- **Descriere:** Calculatoarele sunt legate în cerc. Datele circulă într-o singură direcție, trecând pe la fiecare nod (ca un jeton/token).
    
- **Avantaje:** Nu există coliziuni (doar cine are jetonul vorbește).
    
- **Dezavantaje:** Dacă un PC se strică sau cablul se rupe, inelul se întrerupe și rețeaua cade (există variante cu dublu inel pentru protecție).
    
- **Statut:** Rar folosită azi (ex: FDDI, Token Ring).
    

#### 5. Topologia MESH (Plasă)

- **Full Mesh:** Fiecare nod este conectat direct la _toate_ celelalte noduri.
    
- **Avantaje:** Cea mai sigură (fiabilitate maximă). Dacă pica 3 cabluri, datele tot găsesc o cale.
    
- **Dezavantaje:** Extrem de scumpă (foarte multe cabluri).
    
- **Statut:** Folosită în infrastructura critică a Internetului (între routerele mari) sau la servere, nu la PC-urile de birou.
    

![Imagine cu network topologies diagram bus star ring mesh](https://encrypted-tbn3.gstatic.com/licensed-image?q=tbn:ANd9GcR269uU1X4StsJqCXnvwD_7lX-Dhwl5cusc0xHD5MX45SPhhB62z5_rcyB3N9LIG8MTYBsRqvRDdSeHPvvs4ncA5W7oTREnjV1fD4N9DdsyIKtbY0I)


### III. Moduri de conectare și clasificare a rețelelor (Colectarea rețelelor)

Când ne referim la "colectarea" sau gruparea rețelelor, le clasificăm în funcție de aria geografică pe care o acoperă:

1. **LAN (Local Area Network):**
    
    - O rețea într-o singură locație fizică (o cameră, o clădire).
        
    - Echipamente: Switch-uri, Cabluri UTP, WiFi.
        
    - Viteză mare, cost mic.
        
2. **MAN (Metropolitan Area Network):**
    
    - Acoperă un oraș. Conectează mai multe LAN-uri (ex: sediile unei bănci din tot Bucureștiul).
        
    - Folosește adesea fibră optică.
        
3. **WAN (Wide Area Network):**
    
    - Acoperă arii geografice mari (țări, continente).
        
    - **Internetul** este cel mai mare WAN.
        
    - Folosește Routere, sateliți, cabluri submarine.
        
4. **PAN (Personal Area Network):**
    
    - Distanță foarte mică (câțiva metri). Ex: Bluetooth între telefon și căști.
        
5. **WLAN (Wireless LAN):**
    
    - Același lucru ca LAN, dar fără fire, folosind unde radio.


## IV.Adrese IP


### 1. IP Privat vs. IP Public

|**Caracteristică**|**IP Privat (Local)**|**IP Public (Real)**|
|---|---|---|
|**Unde se folosește?**|În interiorul rețelei tale (Acasă, Birou).|Pe tot Internetul.|
|**Cine îl alocă?**|Router-ul tău (prin protocolul DHCP).|Furnizorul de Internet (ISP - Digi, Orange, etc.).|
|**Vizibilitate**|Invizibil din exterior.|Unic în toată lumea.|
|**Cost**|Gratuit, poți avea oricâte.|Plătit (inclus în abonament).|
|**Exemplu tipic**|`192.168.1.5`|`82.76.123.45`|

#### Cum comunică ele între ele? (**NAT**)

Deoarece IP-urile private nu pot naviga pe internetul real, router-ul tău folosește o tehnologie numită **NAT (Network Address Translation)**. Router-ul ia cererea de la PC-ul tău (IP privat), îi pune "ștampila" IP-ului său public și o trimite în lume. Când vine răspunsul, router-ul știe cui să i-l dea înapoi în casă.

### 2. Clase de IP-uri Private

Există intervale speciale de adrese rezervate **doar** pentru rețele private. Acestea nu vor fi niciodată găsite pe site-uri de pe internet:

- **Clasa A:** `10.0.0.0` – `10.255.255.255` (Folosită de corporații mari).
    
- **Clasa B:** `172.16.0.0` – `172.31.255.255` (Folosită în campusuri).
    
- **Clasa C:** `192.168.0.0` – `192.168.255.255` (Cea mai comună pentru acasă).
    

---

### 3. IP Static vs. IP Dinamic

- **IP Dinamic:** Se schimbă periodic (sau de fiecare dată când restartezi router-ul). Majoritatea utilizatorilor casnici au IP dinamic pentru că e mai eficient pentru furnizor.
    
- **IP Static:** Rămâne mereu același. Este necesar dacă vrei să găzduiești un server, un site sau să te conectezi de la distanță la camerele de supraveghere prin IP.
    

---

### 4. IPv4 vs. IPv6 (Evoluția)

- **IPv4:** Format din 4 grupuri de numere (ex: `192.168.1.1`).
    
    - S-au epuizat! Există doar aproximativ **4,3 miliarde** de adrese, prea puține pentru câte telefoane și frigidere inteligente avem azi.
        
- **IPv6:** Format din cifre și litere (ex: `2001:0db8:85a3:0000:0000:8a2e:0370:7334`).
    
    - Sunt aproape infinite ($3.4 \times 10^{38}$ adrese). Fiecare grăunte de nisip de pe pământ ar putea avea propriul IP.
    - Are o lungime de 128 de biti
        

---

### Detalii extra importante:

1. **Loopback Address (localhost):** Adresa `127.0.0.1`. Este adresa prin care un calculator vorbește cu el însuși. Se folosește pentru teste.
    
2. **Default Gateway:** Este adresa IP a router-ului tău (poarta de ieșire). De obicei este prima din rețea, ex: `192.168.1.1`.
    
3. **Subnet Mask (Mască de rețea):** Îi spune calculatorului care parte din IP reprezintă "Numele Rețelei" și care parte este "Numele Calculatorului". (Ex: `255.255.255.0`).



## V.Determinarea netmask ului maximal pentru o adresa de broadcast

### Scriem adresa de broadcast în binar

Adresa: **10.27.25.255**

Ultimele două octeți:

- 25 = `00011001`
    
- 255 = `11111111`
    

Observăm câți **biți de 1 consecutivi sunt la final**, de la dreapta spre stânga:

- ultimul octet: **8 biți de 1**
    
- din octetul 25: se termină în `...0001` → **1 bit de 1**
    

➡️ Total **9 biți de host**

---

### 2️⃣ Calculăm prefixul

IPv4 are 32 biți:

`32 − 9 = 23`

➡️ **Netmask maximal: `/23`**

## VI. Servere DHCP SI DNS
### 1. DHCP (Dynamic Host Configuration Protocol) – "Administratorul de Adrese"

DHCP se ocupă de partea **internă** și **automată** a conectării. Fără el, fiecare dispozitiv ar trebui configurat manual.

#### Componentele procesului DHCP:

- **IP Pool:** Un interval de adrese (ex: `192.168.1.50` - `192.168.1.150`) pe care serverul are voie să le împartă.
    
- **Lease Time (Timp de închiriere):** Adresa IP nu este a ta pe vecie; serverul ți-o „închiriază”. Când timpul expiră (ex: după 24h), dispozitivul cere reînnoirea.
    
- **DHCP Reservation:** Poți seta serverul să dea mereu același IP unei imprimante, recunoscând-o după adresa MAC.
    

#### Procesul DORA (Cum primești un IP):

1. **D**iscover: Clientul trimite un mesaj de **broadcast** în rețea: „Salut, am nevoie de un IP!”
    
2. **O**ffer: Serverul DHCP răspunde cu o ofertă de IP disponibil.
    
3. **R**equest: Clientul acceptă oferta și cere oficial adresa.
    
4. **A**cknowledge: Serverul confirmă și trimite și restul setărilor (Mască, Gateway, DNS).
    

---

### 2. DNS (Domain Name System) – "Agenda Telefonică a Internetului"

DNS se ocupă de **traducerea** numelor pe care le scriem noi în browser (https://www.google.com/search?q=google.com) în adresele IP pe care le înțeleg serverele (`142.250.180.142`).

#### Ierarhia DNS:

Sistemul DNS este structurat ca un arbore inversat:

1. **Root Servers (.)**: Știu unde se află serverele pentru terminatii (TLDs).
    
2. **TLD Servers (.com, .ro, .org)**: Știu cine gestionează domeniile specifice.
    
3. **Authoritative Name Servers**: Sunt serverele finale care dețin „actul de proprietate” al site-ului și știu exact IP-ul acestuia.

Orice server asteapta cereri pe portul 53 UDP

|**Serviciu**|**Port**|**Protocol**|
|---|---|---|
|**DNS**|**53**|UDP/TCP|
|**HTTP** (Web)|**80**|TCP|
|**HTTPS** (Web securizat)|**443**|TCP|
|**SSH** (Acces la distanță)|**22**|TCP|
    

## VII. Adresa de retea

O **adresă de rețea** este prima adresă IP dintr-un segment (subrețea) și servește drept "nume" sau identificator oficial pentru întregul grup de dispozitive din acea rețea.

Iată detaliile esențiale:

### 1. Rolul și Funcționalitatea

- **Identificator:** Ea definește "strada" pe care se află dispozitivele (host-urile).
    
- **Rutare:** Routerele folosesc adresa de rețea pentru a decide unde să trimită pachetele de date; ele nu caută fiecare calculator individual, ci caută rețeaua din care acesta face parte.
    
- **Nefolosibilă pentru host:** Această adresă este rezervată strict pentru protocol; nu o poți atribui unui PC, telefon sau server.
    

### 2. Cum se calculează?

Adresa de rețea se obține printr-o operație matematică numită **AND logic** între o adresă IP și Masca de rețea.

- Toți biții de "gazdă" (host) sunt setați pe **0**.
    
- **Exemplu:** Pentru IP-ul `192.168.1.50` cu masca `/24` (255.255.255.0), adresa de rețea este `192.168.1.0`.


## VIII. Pachete IP,TCP,UDP,Frame uri

### 1. Segmentul TCP și UDP (Nivelul 4 - Transport)

Acestea sunt primele „păpuși”. Ele iau datele tale (un mesaj, o poză) și le pun într-un format de transport.

- **TCP (Transmission Control Protocol):** Este protocolul „politicos” și sigur. Verifică dacă datele au ajuns la destinație, le pune în ordinea corectă și cere retrimiterea lor dacă s-au pierdut pe drum. Se folosește la site-uri web (HTTP), e-mail.
    
- **UDP (User Datagram Protocol):** Este protocolul „rapid” și nesigur. Trimite datele fără să verifice dacă au ajuns. E perfect pentru streaming video sau jocuri online, unde viteza e mai importantă decât dacă se pierde un mic pixel pe drum.
    

---

### 2. Pachetul IP (Nivelul 3 - Rețea)

Segmentul (TCP sau UDP) este băgat în interiorul unui **Pachet IP**.

- **Ce conține:** Cel mai important lucru aici sunt **Adresele IP** (Sursă și Destinație).
    
- **Rol:** Pachetul IP este cel care știe să circule prin **Routere** pentru a ajunge de la un calculator la altul, chiar dacă sunt în orașe diferite.
    

---

### 3. Frame-ul / Cadrul (Nivelul 2 - Data Link)

Pachetul IP este băgat la rândul lui în interiorul unui **Frame**.

- **Ce conține:** Aici găsim **Adresele MAC** (Sursă și Destinație).
    
- **Rol:** Frame-ul este unitatea pe care o înțelege **Switch-ul**. Acesta transportă datele doar în interiorul rețelei tale locale (de la PC la Router sau de la PC la Switch).
    
- **Excepție:** Există și frame-uri care nu conțin pachete IP, ci alte tipuri de date de control ale rețelei.
    

---

### Rezumatul Încapsulării (De la mic la mare):

1. **Datele tale** (ex: "Salut") sunt puse în:
    
2. **Segment (TCP/UDP):** Se adaugă porturile (ex: 80, 443).
    
3. **Pachet (IP):** Se adaugă adresele IP.
    
4. **Frame (Ethernet):** Se adaugă adresele MAC și se trimit fizic prin cablu/Wi-Fi.
    

**Sfat de reținut:** Switch-ul transportă **Frame-uri**, Router-ul transportă **Pachete IP**, iar aplicațiile tale (browser, jocuri) folosesc **TCP sau UDP**.

## VIV . NAT

**NAT (Network Address Translation)** este „vama” sau „traducătorul” de la marginea rețelei tale, care face posibil ca mai multe dispozitive să folosească o singură conexiune la internet.

Iată punctele esențiale:

- **Economisirea adreselor:** NAT a fost creat pentru că nu există destule adrese IPv4 publice pentru toate dispozitivele din lume.
    
- **Mascare:** NAT ia adresele tale **private** (cele „false”  ex: `10.x.x.x` sau `192.168.x.x`) și le transformă în **adresa publică** unică a routerului tău atunci când ieși pe internet.
    
- **Securitate:** Deoarece NAT ascunde IP-urile reale ale calculatoarelor tale, este mult mai greu pentru cineva de pe internet să atace direct un dispozitiv din casa ta; el vede doar routerul.
    
- **Tabelul de translatare:** Routerul ține minte într-un tabel cine a cerut ce (ex: „Telefonul a cerut Google, Laptopul a cerut YouTube”), astfel încât, atunci când vin datele înapoi, să știe cui să le dea în interiorul rețelei.


## X. Algoritmi de dirijare dinamica

### 1. Distance Vector (Vector de Distanță)

Routerele care folosesc acest algoritm funcționează pe principiul "zvonurilor". Un router îi spune vecinului său: _"Eu pot ajunge la Rețeaua X în 3 pași"_. Vecinul crede ce i se spune și adaugă informația în tabelul său.

- **Cum calculează:** Folosește un număr de "sărituri" (hop count). Drumul cu cele mai puține routere intermediare este cel ales.
    
- **Exemplu:** **RIP** (Routing Information Protocol).
    
- **Dezavantaj:** Poate dura mult până când toată rețeaua află că un drum s-a stricat (problema "count to infinity").
    

### 2. Link-State (Starea Legăturii)

Aici, fiecare router încearcă să își construiască propria **hartă completă** a întregii rețele. Nu se bazează doar pe ce spun vecinii, ci colectează informații despre starea fiecărei conexiuni (link) din rețea.

- **Cum calculează:** Folosește algoritmul lui **Dijkstra** (Shortest Path First) pentru a calcula drumul cel mai scurt matematic, luând în calcul viteza benzii (bandwidth), nu doar numărul de routere.
    
- **Exemplu:** **OSPF** (Open Shortest Path First).
    
- **Avantaj:** Mult mai rapid și eficient în rețele mari.
    

---

### Comparație Rapidă

|**Caracteristică**|**Distance Vector (ex: RIP)**|**Link-State (ex: OSPF)**|
|---|---|---|
|**Cunoașterea rețelei**|Știe doar ce îi spun vecinii.|Are harta completă a rețelei.|
|**Viteza de reacție**|Lentă (convergență scăzută).|Foarte rapidă.|
|**Resurse consumate**|Puține (procesor/RAM slab).|Multe (necesită routere mai puternice).|
|**Criteriu alegere**|Număr de sărituri (hop count).|Cost (bazat pe viteza conexiunii).|

---


## Stiva TCP/IP
| **Nivel (Layer)**     | **Rolul principal**                             | **Protocoale și Concepte (Extins)**                                                          |
| --------------------- | ----------------------------------------------- | -------------------------------------------------------------------------------------------- |
| **4. Aplicație**      | Interfața cu utilizatorul și programele.        | **HTTP**, **URL**, **DNS** (Port 53), server proxy, proces server, **SMTP** (Email),browser. |
| **--- Interfață ---** | _Puntea între program și rețea_                 | **Socket** (ușa prin care aplicația trimite date către transport).                           |
| **3. Transport**      | Se ocupă cu livrarea datelor (sigur sau rapid). | **Port**, **TCP** (cu confirmare), **UDP** (fără confirmare).                                |
| **2. Internet**       | Găsește drumul și pune adresa IP pe pachete.    | **IP** (IPv4, IPv6), **Netmask**, **Tabela de dirijare**, **Clasa de adrese**, ICMP,router.  |
| **1. Acces la Rețea** | Transformă datele în biți pe fir/aer.           | **Switch**, **Ethernet**, Wi-Fi, Adresa MAC, Modem, Cablu optic,ARP.                         |

## Bridge vs Switch

|**Caracteristică**|**Switch**|**Bridge**|
|---|---|---|
|**Funcție Principală**|Responsabil pentru direcționarea datelor de la porturile de intrare către un port de ieșire specific către destinație.|Responsabil pentru divizarea unei singure rețele în mai multe segmente de rețea.|
|**Număr de Porturi**|Un switch poate avea un număr mare de porturi.|Un bridge are, de regulă, doar 2 sau 4 porturi.|
|**Implementare**|Realizează redirecționarea pachetelor folosind hardware specializat (ASIC), fiind bazat pe hardware.|Realizează redirecționarea pachetelor prin software, fiind bazat pe software.|
|**Metodă de Transmisie**|Poate folosi metodele: _store and forward_, _fragment free_ sau _cut through_.|Folosește exclusiv metoda _store and forward_.|
|**Verificarea Erorilor**|Sarcina de verificare a erorilor este realizată de către switch.|Un bridge nu poate efectua verificarea erorilor.|
|**Memorie Buffer**|Un switch dispune de buffere (memorii temporare).|Un bridge s-ar putea să nu aibă memorie buffer.|


## Mod de scriere al unei adrese MAC

![[Pasted image 20260128155051.png]]
