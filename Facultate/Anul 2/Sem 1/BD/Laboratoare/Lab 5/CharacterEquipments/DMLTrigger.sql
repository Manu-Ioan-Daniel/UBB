CREATE OR ALTER TRIGGER dbo.characterEquipmentsTrigger
ON CharacterEquipments
AFTER UPDATE, DELETE
AS
BEGIN
    INSERT INTO CharacterEquipments_Log
    SELECT
        d.CharacterID,
        d.EquipmentID,
        CASE
            WHEN i.CharacterID IS NULL THEN 'DELETE'
            ELSE 'UPDATE'
        END,
        GETDATE(),
        SUSER_SNAME()
    FROM deleted d
    LEFT JOIN inserted i
        ON d.CharacterID = i.CharacterID
       AND d.EquipmentID = i.EquipmentID;
END;
GO

CREATE OR ALTER TRIGGER dbo.charactersTrigger
ON Characters
AFTER UPDATE, DELETE
AS
BEGIN
    INSERT INTO Characters_Log
    SELECT
        d.CharacterID,
        d.PlayerID,
        d.CharacterName,
        d.GuildID,
        d.Class,
        d.GuildRole,
        d.GuildJoinDate,
        CASE
            WHEN i.CharacterID IS NULL THEN 'DELETE'
            ELSE 'UPDATE'
        END,
        GETDATE(),
        SUSER_SNAME()
    FROM deleted d
    LEFT JOIN inserted i
        ON d.CharacterID = i.CharacterID;
END;
GO


CREATE OR ALTER TRIGGER dbo.equipmentsTrigger
ON Equipments
AFTER UPDATE, DELETE
AS
BEGIN
    INSERT INTO Equipments_Log
    SELECT
        d.EquipmentID,
        d.EquipmentRarity,
        d.EquipmentName,
        d.EquipmentSlot,
        d.EquipmentCategoryID,
        CASE
            WHEN i.EquipmentID IS NULL THEN 'DELETE'
            ELSE 'UPDATE'
        END,
        GETDATE(),
        SUSER_SNAME()
    FROM deleted d
    LEFT JOIN inserted i
        ON d.EquipmentID = i.EquipmentID;
END;
GO
