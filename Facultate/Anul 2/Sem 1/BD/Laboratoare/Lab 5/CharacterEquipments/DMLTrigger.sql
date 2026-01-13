CREATE TRIGGER dbo.characterEquipmentsTrigger
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