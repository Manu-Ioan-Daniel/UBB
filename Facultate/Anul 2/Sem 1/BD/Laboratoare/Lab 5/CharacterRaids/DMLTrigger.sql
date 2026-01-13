CREATE TRIGGER dbo.characterRaidsTrigger
ON CharacterRaids
AFTER UPDATE, DELETE
AS
BEGIN
    INSERT INTO CharacterRaids_Log
    SELECT
        d.CharacterID,
        d.RaidID,
        d.CompletionStatus,
        d.CompletionDate,
        CASE
            WHEN i.CharacterID IS NULL THEN 'DELETE'
            ELSE 'UPDATE'
        END,
        GETDATE(),
        SUSER_SNAME()
    FROM deleted d
    LEFT JOIN inserted i
        ON d.CharacterID = i.CharacterID
       AND d.RaidID = i.RaidID;
END;
GO