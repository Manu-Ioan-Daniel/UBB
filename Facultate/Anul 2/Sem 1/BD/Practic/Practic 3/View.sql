CREATE VIEW nr_carti
AS
SELECT L.nume as NumeLibrarie, COUNT(id_carte) as NumarCarti
FROM Carti_Librarii
JOIN Librarii L ON L.id = id_librarie
WHERE data_achizitie > '2010-01-01'
GROUP BY L.nume
HAVING  COUNT(id_carte) >= 5;

GO

SELECT * FROM nr_carti
ORDER BY NumeLibrarie DESC;

