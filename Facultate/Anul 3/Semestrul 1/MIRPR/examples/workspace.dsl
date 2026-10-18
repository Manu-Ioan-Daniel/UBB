workspace "Magazin online" "Predictie demonstrativa - laborator 1" {
    model {
        client = person "Client" "Plaseaza comenzi"
        payments = softwareSystem "Procesator plati" "Autorizeaza platile"
        shop = softwareSystem "Magazin online" "Gestioneaza comenzile" {
            web = container "Aplicatie web" "Interfata pentru client" "React"
            api = container "API" "Proceseaza comenzile" "Python" {
                controller = component "Controller comenzi" "Expune endpoint-uri REST" "Python"
                service = component "Serviciu comenzi" "Aplica regulile comenzilor" "Python"
            }
            db = container "Baza de date" "Persista comenzile" "PostgreSQL"
        }
        client -> web "Plaseaza comenzi" "HTTPS"
        web -> controller "Trimite comenzi" "HTTPS / JSON"
        controller -> service "Valideaza si proceseaza" "Apel intern"
        service -> db "Citeste si scrie" "SQL / TCP"
        service -> payments "Solicita plata" "HTTPS / JSON"
    }
    views {
        systemContext shop "Context" {
            include *
            autoLayout lr
        }
        container shop "Containers" {
            include *
            autoLayout lr
        }
        component api "Components" {
            include *
            autoLayout lr
        }
    }
}
