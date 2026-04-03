# CSharpTema2

Acesta este un proiect de laborator in C# pentru gestionarea unor curse si rezervari.

## Ce face proiectul
- gestioneaza angajati (save, update, delete, find)
- gestioneaza curse (save, update, delete, find)
- gestioneaza rezervari pentru curse
- testeaza operatii CRUD direct din `Program.cs`

## Tehnologii folosite
- .NET (`net10.0`)
- C#
- MySQL (`MySql.Data`)
- NLog pentru loguri

## Structura pe scurt
- `CSharpLab3/main/Program.cs` - punctul de intrare si exemple de operatii
- `CSharpLab3/models` - entitati (`Employee`, `Ride`, `Reservation`)
- `CSharpLab3/repos` - repository-uri pentru acces la DB
- `CSharpLab3/utils/DbUtils.cs` - utilitar pentru conexiunea la baza de date
- `CSharpLab3/db.properties` - configurare DB

## Configurare rapida
1. Creeaza baza de date in MySQL (ex: `mpp`).
2. Completeaza datele de conectare in `CSharpLab3/db.properties`.
3. Ruleaza proiectul din folderul `CSharpLab3`.

## Rulare
```powershell
cd CSharpLab3
dotnet restore
dotnet run
```

## Observatii
Proiectul este orientat pe partea de persistenta si operatii CRUD.
Codul din `Program.cs` ruleaza operatii demo pentru a verifica repository-urile.

