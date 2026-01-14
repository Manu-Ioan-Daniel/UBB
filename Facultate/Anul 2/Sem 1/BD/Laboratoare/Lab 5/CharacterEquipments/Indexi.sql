CREATE NONCLUSTERED INDEX IX_CharacterEquipments_Character ON CharacterEquipments(CharacterID,EquipmentID);
GO
CREATE NONCLUSTERED INDEX IX_Characters_CharacterName
ON Characters(CharacterName);
GO

CREATE NONCLUSTERED INDEX IX_Characters_PlayerID
ON Characters(PlayerID);
GO

CREATE NONCLUSTERED INDEX IX_Equipments_EquipmentName
ON Equipments(EquipmentName);
GO

CREATE NONCLUSTERED INDEX IX_Equipments_EquipmentCategoryID
ON Equipments(EquipmentCategoryID);
GO

SELECT * FROM dbo.characterList;
SELECT * FROM dbo.characterCount;
SELECT * FROM dbo.equipmentList;
SELECT * FROM dbo.equipmentCount;
SELECT * FROM dbo.characterEquipmentCount;
SELECT * FROM dbo.characterEquipmentCount;
GO