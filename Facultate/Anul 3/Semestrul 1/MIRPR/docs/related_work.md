# Related work si tehnologii utile

Cele 4 surse de mai jos acopera generarea diagramelor din text si instrumentele C4. Informatiile provin din documentatiile oficiale, consultate la 18 octombrie 2026.

## 1. Structurizr DSL

- Sursa: https://docs.structurizr.com/dsl ; codul proiectului este accesibil prin organizatia https://github.com/structurizr si legaturile oficiale din documentatie.
- Date: definitii textuale ale sistemelor, persoanelor, containerelor, componentelor si relatiilor, organizate intr-un workspace.
- Algoritmi AI: DSL-ul propriu-zis este un mecanism determinist de modelare si parsare, nu un model antrenat. Functiile AI conexe nu sunt necesare pentru folosirea DSL-ului.
- Rezultate: permite generarea mai multor vederi pornind de la un singur model. Nu sunt raportate rezultate de acuratete pentru extragerea arhitecturii din text.
- Tehnologii: ecosistem Structurizr, DSL, parser si instrumente pentru randare/export.
- Relevanta: formatul de iesire al prototipului si principiul modelului comun, care pastreaza coerenta dintre nivelurile C4.

## 2. C4-PlantUML

- Sursa si Git: https://github.com/plantuml-stdlib/C4-PlantUML
- Date: fisiere text PlantUML cu macrocomenzi C4 pentru persoane, sisteme, containere, componente si relatii. Repository-ul include exemple.
- Algoritmi AI: nu utilizeaza AI pentru deducerea arhitecturii; utilizatorul declara elementele si relatiile.
- Rezultate: suporta vederi Context, Container, Component si vederi suplimentare. README-ul nu raporteaza precision/recall sau un benchmark comparativ de extragere AI.
- Tehnologii: PlantUML, macrocomenzi si mecanisme de layout; biblioteca poate fi inclusa local sau prin biblioteca standard PlantUML.
- Relevanta: alternativa de export pentru o etapa viitoare si exemple de exprimare explicita a abstractiilor C4.

## 3. Mermaid

- Sursa si Git: https://github.com/mermaid-js/mermaid
- Date: definitii textuale de diagrame, integrate frecvent in documentatie Markdown.
- Algoritmi AI: biblioteca de baza parseaza si randeaza definitii; nu este un model AI pentru interpretarea descrierilor de arhitectura.
- Rezultate: genereaza diagrame din text, utile pentru documentatie versionata. Repository-ul consultat nu ofera un scor de acuratete pentru transformarea limbajului natural in C4.
- Tehnologii: JavaScript/TypeScript si randare in ecosistemul web.
- Relevanta: posibil export suplimentar pentru integrarea diagramelor in documentatie. Consistenta nivelurilor trebuie garantata de modelul aplicatiei noastre.

## 4. DiagramGPT - Eraser

- Sursa: https://www.eraser.io/diagramgpt
- Date: prompturi text de la utilizatori; diagramele rezultate pot fi editate folosind sintaxa diagram-as-code Eraser. Setul de date de antrenare nu este descris pe pagina consultata.
- Algoritmi AI: pagina oficiala mentioneaza modele OpenAI. Versiunea exacta, antrenarea si configuratia pipeline-ului nu sunt publicate acolo.
- Rezultate: instrumentul genereaza diagrame din prompturi. Pagina nu publica masuratori de acuratete sau un set de test.
- Tehnologii: aplicatia Eraser si sintaxa sa diagram-as-code. Pagina produsului nu indica un repository public cu implementarea completa DiagramGPT.
- Relevanta: exemplu direct al interactiunii text -> diagrama editabila. Proiectul nostru urmareste explicit un model C4 comun si export Structurizr DSL.

