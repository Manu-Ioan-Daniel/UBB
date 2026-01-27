TTL = Time To Live

|**Valoare TTL**|**Avantaj**|**Dezavantaj**|
|---|---|---|
|**Mare** (ex: 86400s / 24h)|Performanță mai bună, încarcă site-ul mai repede, scade sarcina serverului.|Schimbările de IP se propagă foarte greu (lumea vede site-ul "vechi").|
|**Mic** (ex: 300s / 5 min)|Schimbările se aplică aproape instantaneu.|Serverul DNS este bombardat cu cereri, ceea ce poate încetini navigarea.|
