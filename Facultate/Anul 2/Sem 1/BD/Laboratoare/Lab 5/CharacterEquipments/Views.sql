CREATE VIEW dbo.characterEquipmentList
AS
SELECT c.CharacterName, e.EquipmentName
FROM CharacterEquipments ce
JOIN Characters c ON ce.CharacterID = c.CharacterID
JOIN Equipments e ON ce.EquipmentID = e.EquipmentID;
GO

CREATE VIEW dbo.characterEquipmentCount
AS
SELECT c.CharacterName, COUNT(ce.EquipmentID) AS EquipCount
FROM CharacterEquipments ce
JOIN Characters c ON ce.CharacterID = c.CharacterID
GROUP BY c.CharacterName;
GO