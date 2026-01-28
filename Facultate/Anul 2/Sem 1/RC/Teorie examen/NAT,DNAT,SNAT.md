
|**Caracteristică**|**SNAT (Source NAT)**|**DNAT (Destination NAT)**|
|---|---|---|
|**Ce înseamnă**|Source Network Address Translation|Destination Network Address Translation|
|**Ce modifică**|**Adresa IP a Sursei** (expeditorului)|**Adresa IP a Destinației** (receptorului)|
|**Direcția traficului**|Din LAN (interior) → WAN (internet)|Din WAN (internet) → LAN (interior)|
|**Cine inițiază**|Dispozitivul din rețeaua internă (ex: PC-ul tău)|Un client din exterior (ex: cineva de pe internet)|
|**Scop principal**|Permite accesul la internet pentru mai multe dispozitive folosind un singur IP public.|Permite accesarea unui server intern din exterior (**Port Forwarding**).|
|**Conversie**|**IP Privat (Fals)** → **IP Public (Real)**|**IP Public (Real)** → **IP Privat (Fals)**|
|**Exemplu concret**|Tu accesezi Google de pe laptop.|Găzduiești un server de Minecraft sau un site web acasă.|
|**Momentul aplicării**|**Post-routing** (la ieșirea din router)|**Pre-routing** (la intrarea în router)|
