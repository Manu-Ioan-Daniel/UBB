INSERT INTO Tip_Restaurante (nume, descriere) VALUES ('Italian', 'Paste si pizza'), ('Fast Food', 'Burger si cartofi'), ('Fine Dining', 'Experienta premium');

INSERT INTO Orase (nume) VALUES ('Bucuresti'), ('Cluj-Napoca'), ('Timisoara');

INSERT INTO Restaurante (nume, adresa, nr_telefon, oras_id, tip_restaurant_id) VALUES 
('Trattoria Roma', 'Str. Victoriei 10', '0721111222', 1, 1),
('Burger King', 'Mall Vitan', '0722333444', 1, 2),
('Da Pino', 'Str. Universitatii 5', '0733444555', 2, 1);

INSERT INTO Utilizatori (nume, email, parola) VALUES 
('Ion Popescu', 'ion@gmail.com', 'parola123'),
('Maria Enache', 'maria@yahoo.com', 'secure456');