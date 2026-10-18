# Descrierea problemei

## Descriere plastica

Cand codul unui proiect se schimba, diagramele actualizate manual pot ramane in urma. Aplicatia transforma descrierea unui sistem in diagrame C4 definite prin text. Un dezvoltator poate descrie interfata web, API-ul, baza de date si serviciile externe, apoi obtine vederi coerente ale aceleiasi arhitecturi.

Diagramele ajuta echipa sa inteleaga sistemul, sa explice arhitectura colegilor noi si sa discute modificarile propuse. Utilizatorii sunt dezvoltatorii, arhitectii software, echipele DevOps si studentii care documenteaza proiecte.


## Formulare formala

Intrarea x este un sir de caract4ere care descrie o arhitectura. Iesirea intermediara este un graf  G = (V, E, p, t, a), unde V sunt entitatile, E sunt relatiile directionate, p este parintele, t atribuie tipul C4, iar a include nume, responsabilitati si tehnologii.

Sistemul urmărește patru niveluri de detaliu de la utilizator și aplicație, până la bucățile mari și piesele lor interne organizate strict ierarhic, în care piesele aparțin containerelor, iar containerele aparțin sistemului. Din aceeași bază de date unică se extrag automat atât diagramele pentru fiecare nivel în parte, cât și codul Structurizr DSL. Pentru ca diagramele să fie clare, conexiunile dintre elementele mici sunt ridicate automat la nivelul sistemelor mari, iar eventualele bucle ilogice rezultate din această urcare sunt eliminate.

Obiectivul viitorului model este aproximarea transformarii x -> G de referinta. Serializerul determinist produce apoi DSL(G). Modelul AI identifica arhitectura, iar o functie separata genereaza DSL-ul.

In prototip, predict(description) returneaza o definitie Structurizr DSL fixa, salvata in examples/workspace.dsl, indiferent de textul introdus, inclusiv daca este gol. Inputul nu este analizat sau validat. DSL-ul contine un singur model si cele trei vederi C4. Modelul AI va inlocui aceasta functie intr-o etapa ulterioara. Datele nu sunt trimise in retea. Nu exista set de antrenare si nu se raporteaza performanta AI pentru acest mock.

## Evaluarea viitoarei solutii AI

Se va construi un set de exemple (descriere, graf C4 de referinta) adnotat de membrii echipei, verificat de ambii membri. Impartirea train/validation/test se va face la nivel de proiect, astfel incat variantele aceleiasi arhitecturi sa nu apara in partitii diferite. Testul ramane separat de alegerea modelului si a prompturilor.

- Precision, recall si F1 pentru entitati: potrivirea foloseste identitatea semantica, tipul C4 si parintele, nu identitatea textuala a numelui.
- Precision, recall si F1 pentru relatii: se verifica extremitatile aliniate si directia; tehnologia si responsabilitatea pot fi evaluate separat.
- Rata de validitate DSL: proportia exporturilor acceptate de un parser Structurizr.
- Consistenta: proportia modelelor fara parinti/referinte invalide sau contradictii intre niveluri; rata entitatilor si relatiilor nesustinute de intrare.
- Evaluare umana a abstractiei si utilitatii pe o scala 1-5, cu rubrica comuna si doi evaluatori.
- Latenta per exemplu si cost per predictie, pe acelasi set de test si in conditii comparabile.

Precision = TP / (TP + FP), recall = TP / (TP + FN), F1 = 2PR / (P + R). Conventiile pentru multimi vide trebuie fixate inainte de evaluare. Rezultatele vor fi raportate pe fiecare nivel C4 si agregat. Nu exista inca rezultate experimentale sau valori masurate ale acestor metrici.
