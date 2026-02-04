


## Nivelele de abstractizare

- Mai multe _structuri externe (views)_, câte o singură _structură conceptuală (logică)_ și o _structură fizică (internă)_.
    
    - _Views_ – cum văd utilizatorii datele.
        
    - _Conceptual_ - modelul logic compus din relații, atribute, etc
        
    - _Fizic_ - fișierele de date și indecși
        


## Structura fizică a fișierelor BD

- SGBD-urile stochează informația pe disc magnetic
    
- Acest lucru are implicații majore în proiectarea unui SGBD!
    
    - READ: transfer date de pe disc în memoria internă
        
    - WRITE: transfer date din memoria internă pe disc
        

Ambele operații sunt costisitoare, comparativ cu operațiile _in-memory_, deci trebuie planificate corespunzător!



## Componentele unui disc dur (_hard disk_)

- Rotația platanelor (90rps)
    
- Ansamblu de brațe ce se deplasează pentru poziționarea capului magnetic pe pista dorită. Pistele aflate la aceeași distanță de centrul platanelor formează un **cilindru** (imaginar!).
    
- Un singur cap citește/scrie la un moment dat.
    
- Un **bloc** e un multiplu de **sectoare** (care e fix).
    



## Accesarea unei pagini (bloc)

- Timp de acces (citire/scriere) a unui bloc: _seek time_ (mutare braț pentru poziționarea capului de citire/scriere pe pistă) _rotational delay_ (timp poziționare bloc sub cap) _transfer time_ (transfer date de pe/pe disc)
    
- _Seek time_ și _rotational delay_ domină.
    
    - _Seek time_ variază între 1 și 20msec
        
    - _Rotational delay_ variază între 0 și 10msec
        
    - _Transfer rate_ e de aproximativ 1msec pe 4KB (pagină)
        
- Reducerea costului I/O: **reducere _seek/rotational delays_!**
    
- Soluții hardware sau software?
    



## RAID

- _Disk Array_: configurație de discuri magnetice ce abstractizează un singur disc.
    
    - mult mai puțin costisitor; se utilizează mai multe discuri de capacitate mică și ieftine în locul unui disc de capacitate ridicată
        
- Scop: Creșterea performanței și fiabilității.
    
- Tehnici: _Data striping_: distribuirea datelor pe mai multe discuri (în partiții prestabilite - _striping unit_) _Mirroring_: stocarea automată a unei copii a datelor pe alte discuri → redundanță. Permite reconstruirea datelor în cazul unor defecte ale discurilor.
    



## Nivele RAID

- Nivel 0: Fără redundanță
    
- Nivel 1: Discuri oglindite (_mirrored_)
    
    - Fiecare disc are o “oglindă” (_check disk_)
        
    - Citiri paralele, o scriere implică două discuri.
        
    - Rata maximă de transfer = rata de transfer a unui disc
        



## Nivele RAID

- Nivel 0+1: Întrețesut și oglindit
    
- Nivel 3: Bit de paritate intercalat
    
    - _Striping Unit_: un bit. un singur disc de verificare
        
    - Fiecare citire și scriere implică toate discurile
        



## Nivele RAID

- Nivel 4: Block de paritate
    
    - _Striping unit_: un bloc. un singur disc de verificare.
        
    - Citiri în paralel pentru cereri de dimensiune mică
        
    - Scrierile implică blocul modificat și discul de verificare
        
- Nivel 5: Bloc de paritate distribuit
    
    - Similar cu RAID 4, dar blocurile de paritate sunt distribuite pe toate discurile
        



## Gestionare _buffer_ de către SGBD

- Programul care a cerut blocul de date trebuie să îl elibereze și să indice dacă blocul a fost modificat:
    
    - folosește un **dirty bit**.
        
- Același bloc de date din _buffer_ poate fi folosit de mai multe programe:
    
    - folosește un **pin count**. Un _bloc-buffer_ e un candidat pentru a fi înlocuit ddacă **pin count** = 0.
        
- Modulele _Concurrency Control & Crash Recovery_ pot implica acțiuni I/O adiționale la înlocuirea unui bloc-_buffer_.
    



## Politici de înlocuire a blocurilor în _buffer_

- _Least Recently Used_ (LRU): utilizează șablonul de utilizare a blocurilor ca predictor al utilizării viitoare. Interogările au șabloane de acces bine definite (ex, scanările secvențiale), iar un SGBD poate utiliza informațiile din interogare pentru a prezice accesările ulterioare ale blocurilor.
    
- _Toss-immediate_: eliberează spațiul ocupat de un bloc atunci când a fost procesat ultima înregistrare stocată în blocul respectiv
    
- _Most recently used_ (MRU): după procesarea ultimei înregistrări dintr-un bloc, blocul este eliberat (_pin count_ e decrementat) și devine blocul utilizat cel mai recent.