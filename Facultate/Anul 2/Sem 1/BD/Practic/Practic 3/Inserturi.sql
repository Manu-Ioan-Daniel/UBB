-- 1. Inserăm Domenii
INSERT INTO Domenii (descriere) VALUES ('Literatura'), ('Stiintific'), ('SF');

-- 2. Inserăm Librării
INSERT INTO Librarii (nume, adresa) VALUES 
('Carturesti', 'Str. Lipscani 55'), 
('Humanitas', 'Bd. Elisabeta 38'),
('Libris', 'Online Brasov');

-- 3. Inserăm Autori
INSERT INTO Autori (nume, prenume) VALUES 
('Rebreanu', 'Liviu'), 
('Asimov', 'Isaac'), 
('Popescu', 'Ion');

-- 4. Inserăm Cărți (trebuie să existe domeniul mai întâi)
INSERT INTO CARTI (titlu, id_domeniu) VALUES 
('Ion', 1),             -- id_carte 1
('Fundatia', 3),        -- id_carte 2
('Manual Fizica', 2),   -- id_carte 3
('ok',1),
('ok2',2);

SELECT * FROM CARTI

-- 5. Inserăm în Carti_Librarii (Asociem cărțile cu librăriile și data achiziției)
INSERT INTO Carti_Librarii (id_carte, id_librarie, data_achizitie) VALUES 
(4, 1, '2021-05-10'), -- Ion la Carturesti
(5, 1, '2024-02-15'), -- Fundatia la Carturesti
(4, 3, '2025-01-01'), -- Ion și la Libris
(6, 1, '2009-11-20'),
(7, 1, '2009-11-20'),
(8, 1, '2009-11-20'),
(5, 2, '2021-05-10'),
(6, 2, '2021-05-10'),
(7, 2, '2021-05-10'),
(8, 2, '2021-05-10'),
(4, 2, '2021-05-10')



-- 6. Inserăm în Autori_Carti (Asociem autorii cu cărțile)
INSERT INTO Autori_Carti (id_autor, id_carte) VALUES 
(3,4),
(2,5),
(1,6),
(2,7)