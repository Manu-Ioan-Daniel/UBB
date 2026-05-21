README - API scurt (pentru frontend)

Scop: acest fișier conține instrucțiuni concise (în română) pentru integrarea frontend-ului cu backend-ul JavaTemaRestAPI.

Bază URL
- URL local de test: http://localhost:8080/api/rides

Autentificare / API keys
- Nu există mecanism de autentificare implementat în proiect (nu se folosește API Key, Bearer token sau Basic auth).
- Pentru dezvoltare frontend, nu este nevoie de header special pentru autentificare.
- Daca vei adăuga în viitor autentificare, documentează header-ul Authorization (ex: Authorization: Bearer <token>). 

Endpoint-uri disponibile
- GET /api/rides
  - Descriere: returnează toate cursele.
  - Parametri query: optional: destination (ex: /api/rides?destination=Bucuresti) — filtrează după destinație.
  - Răspuns: JSON array de RideResponse.

- GET /api/rides/{id}
  - Descriere: returnează detaliile unei curse după id.
  - Răspuns: RideResponse (JSON) sau 200 cu body null dacă nu există.

- POST /api/rides
  - Descriere: creează o cursă nouă.
  - Content-Type: application/json
  - Request body: RideRequest (vezi exemplu mai jos)
  - Răspuns: 201 Created cu body: { "rideId": <id creat> }

- PUT /api/rides/{id}
  - Descriere: modifică cursa cu id-ul specificat.
  - Content-Type: application/json
  - Request body: RideRequest (vezi exemplu mai jos)
  - Răspuns: 204 No Content

- DELETE /api/rides/{id}
  - Descriere: șterge cursa cu id-ul specificat.
  - Răspuns: 204 No Content

Structura JSON
- RideRequest (folosit la POST și PUT)
  - destination: string (obligatoriu)
  - rideDate: string (obligatoriu) — format ISO date: YYYY-MM-DD
  - departureTime: string (obligatoriu) — format ISO time: HH:MM:SS sau HH:MM
  - availableSeats: integer (obligatoriu)

  Exemplu:
  {
    "destination": "Bucuresti",
    "rideDate": "2026-05-22",
    "departureTime": "12:00:00",
    "availableSeats": 3
  }

- RideResponse (returnat de GET)
  - id: long
  - rideDate: string (YYYY-MM-DD)
  - departureTime: string (HH:MM:SS)
  - destination: string
  - availableSeats: integer

  Exemplu:
  {
    "id": 1,
    "rideDate": "2026-05-22",
    "departureTime": "12:00:00",
    "destination": "Bucuresti",
    "availableSeats": 3
  }

Erori / coduri de stare
- 201 Created la POST reușit
- 204 No Content la PUT/DELETE reușite
- 200 OK la GET
- În cazul erorilor interne/validări, serverul ar putea returna coduri 4xx/5xx și un body de eroare — vezi `GlobalExceptionHandler` pentru detalii (dacă e implementat).

Sfat frontend
- Folosește Content-Type: application/json la POST/PUT.
- Trimite datele date în formate ISO (yyyy-MM-dd, HH:mm[:ss]).
- Pentru filtrare după destinație folosește query param `destination` la GET /api/rides.

Dacă dorești, pot adăuga un exemplu de cod fetch (JavaScript) sau axios cu toate request-urile.

