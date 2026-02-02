
INSERT INTO Tipuri (descriere) VALUES ('Regio'), ('InterCity'), ('Softrans');


INSERT INTO Trenuri (tip, nume) VALUES 
(1, 'R-2021'), 
(2, 'IC-551'), 
(3, 'Hyperion');


INSERT INTO Statii (nume) VALUES 
('Bucuresti Nord'), 
('Ploiesti Sud'), 
('Brasov'), 
('Predeal');


INSERT INTO Ruta (tren_id, nume) VALUES 
(1, 'Bucuresti-Brasov Regio'),
(2, 'Bucuresti-Ploiesti Scurt');
