CREATE NONCLUSTERED INDEX IX_CharacterEquipments_Character ON CharacterEquipments(CharacterID,EquipmentID);
GO

CREATE NONCLUSTERED INDEX IX_Characters_Player ON Characters(PlayerID);
GO

CREATE NONCLUSTERED INDEX IX_Characters_CharacterID ON Characters(CharacterID);
GO

CREATE NONCLUSTERED INDEX IX_Equipments_Name ON Equipments(EquipmentName);
GO


SELECT * FROM dbo.characterCount;
SELECT * FROM equipmentNames;
SELECT * FROM dbo.characterEquipmentCount;
GO