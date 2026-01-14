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

CREATE VIEW dbo.characterList
AS
SELECT
    CharacterID,
    CharacterName,
    Class,
    GuildID,
    PlayerID,
    GuildRole,
    GuildJoinDate
FROM Characters;
GO

CREATE VIEW dbo.characterCount
AS
SELECT
    PlayerID,
    COUNT(CharacterID) AS CharacterCount
FROM Characters
GROUP BY PlayerID;
GO

CREATE VIEW dbo.equipmentList
AS
SELECT
    EquipmentID,
    EquipmentName,
    EquipmentRarity,
    EquipmentSlot,
    EquipmentCategoryID
FROM Equipments;
GO

CREATE VIEW dbo.equipmentCount
AS
SELECT
    EquipmentCategoryID,
    COUNT(EquipmentID) AS EquipmentCount
FROM Equipments
GROUP BY EquipmentCategoryID;
GO