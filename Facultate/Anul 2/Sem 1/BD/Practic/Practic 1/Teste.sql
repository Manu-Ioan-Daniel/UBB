
EXEC adaugaStatie @RutaID = 1, @StatieID = 1, @Ora_Sosire = '08:00', @Ora_Plecare = '08:10';
SELECT * FROM Rute_Statii;
EXEC adaugaStatie @RutaID = 1, @StatieID = 1, @Ora_Sosire = '07:50', @Ora_Plecare = '08:00';
SELECT * FROM Rute_Statii;
EXEC adaugaStatie @RutaID = 2, @StatieID = 1, @Ora_Sosire = '14:00', @Ora_Plecare = '14:10';
EXEC adaugaStatie @RutaID = 2, @StatieID = 2, @Ora_Sosire = '15:00', @Ora_Plecare = '15:05';
EXEC adaugaStatie @RutaID = 1, @StatieID = 4, @Ora_Sosire = '10:30', @Ora_Plecare = '10:35';
EXEC adaugaStatie @RutaID = 1, @StatieID = 3, @Ora_Sosire = '10:31', @Ora_Plecare = '10:36';
EXEC adaugaStatie @RutaID = 1, @StatieID = 2, @Ora_Sosire = '10:32', @Ora_Plecare = '10:37';
SELECT * FROM Rute_Statii;
SELECT * FROM numeRute;