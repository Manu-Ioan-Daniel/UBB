CREATE DATABASE ParcDistractiiSeminar2
USE ParcDistractiiSeminar2
CREATE TABLE Atractii
(atractieID INT PRIMARY KEY IDENTITY(1,1),
numeAtractie VARCHAR(100) NOT NULL,
descriereAtractie VARCHAR(100) NOT NULL,
varstaMin INT NOT NULL,
sectiuneID INT FOREIGN KEY REFERENCES Sectiuni(sectiuneID) ON UPDATE CASCADE ON DELETE CASCADE)
CREATE TABLE Sectiuni
(sectiuneID INT PRIMARY KEY IDENTITY(1,1),
numeSectiune VARCHAR(100) NOT NULL,
descriereSectiune VARCHAR(100) NOT NULL)
                       
CREATE TABLE Categorii
(categorieID INT PRIMARY KEY IDENTITY(1,1),
nume VARCHAR(100) NOT NULL UNIQUE)
CREATE TABLE Note
(atractieID INT FOREIGN KEY REFERENCES Atractii(atractieID) ON UPDATE CASCADE ON DELETE CASCADE,
vizitatorID INT FOREIGN KEY REFERENCES Vizitatori(vizitatorID) ON UPDATE CASCADE ON DELETE CASCADE,
notaID INT PRIMARY KEY(atractieID,vizitatorID),
nota INT NOT NULL)
CREATE TABLE Vizitatori
(vizitatorID INT PRIMARY KEY IDENTITY(1,1),
numeVizitator VARCHAR(100) NOT NULL,
email VARCHAR(100) NOT NULL,
categorieID INT FOREIGN KEY REFERENCES Categorii(categorieID) ON UPDATE CASCADE ON DELETE CASCADE)
ALTER TABLE Note
ADD CONSTRAINT ck_nota CHECK(nota >=1 AND nota <= 10)
INSERT INTO Sectiuni(numeSectiune,descriereSectiune) VALUES
('sectiunea1','cea mai mare sectiune'),
('sectiunea2','la mijloc'),
('sectiunea3','la sud'),
('sectiunea4','la nord'),
('sectiunea5','la est'),
('sectiunea6','la vest'),
('sectiunea7','cea mai mica sectiune');
INSERT INTO Atractii(numeAtractie,descriereAtractie,varstaMin,sectiuneID) VALUES
('Atractie1','Descriere1',5,1),
('Atractie2','Descriere2',6,1),
('Atractie3','Descriere3',7,2),
('Atractie4','Descriere4',8,4),
('Atractie5','Descriere5',9,5),
('Atractie6','Descriere6',10,3),
('Atractie7','Descriere7',11,3);
INSERT INTO Categorii(nume) VALUES
('n1'),
('n2'),
('n3'),
('n4'),
('n5'),
('n6'),
('n7');
INSERT INTO Note(atractieID,vizitatorID,nota) VALUES
(1,1,1),
(2,2,2),
(3,3,3),
(4,4,4),
(1,2,1),
(1,3,1),
(2,1,3);
INSERT INTO Vizitatori(numeVizitator,email,categorieID) VALUES
('n1','e1',1),
('n2','e2',2),
('n3','e3',3),
('n4','e4',4),
('n5','e5',5),
('n6','e6',6),
('n7','e7',7);
UPDATE Sectiuni SET descriereSectiune='cea mai proasta sectiune' WHERE numeSectiune='sectiunea1'
UPDATE Atractii SET descriereAtractie='cea mai proasta atractie' WHERE numeAtractie='Atractie1'
UPDATE Vizitatori SET numeVizitator='utilizator suspect' WHERE email='e1'
UPDATE Note SET nota=5 WHERE notaID=1
UPDATE Categorii SET nume='what' WHERE categorieID=2
DELETE FROM Note WHERE atractieID=1 AND vizitatorID=1 
DELETE FROM Atractii WHERE numeAtractie='Atractie1'
SELECT * FROM Categorii WHERE nume IN ('pensionari','copii')
SELECT * FROM Sectiuni WHERE numeSectiune LIKE 'C%'
SELECT * FROM Sectiuni WHERE numeSectiune LIKE '__%[Nn]'
SELECT COUNT(*) FROM Categorii



