
- În anumite situații valorile particulare ale unor attribute (câmpuri) pot fi _necunoscute_ sau _inaplicabile_ temporar.
    
    - SQL permite utilizarea unei valori speciale _<u>null</u>_ pentru astfel de situații.
        
- Prezența valorii _null_ implică unele probleme suplimentare:
    
    - E necesară implementarea unei logici cu 3 valori: _true_, _false_ și _null_ (de exemplu o condiție de tipul _rating>8_ va fi intotdeauna evaluată cu _false_ daca valoarea câmpului rating este _null_)
        
    - E necesară adăugarea unui operator special IS NULL / IS NOT NULL.