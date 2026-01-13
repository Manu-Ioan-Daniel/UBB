CREATE NONCLUSTERED INDEX IX_CharacterRaids_Status
ON CharacterRaids (CompletionStatus);
GO

CREATE NONCLUSTERED INDEX IX_CharacterRaids_Character
ON CharacterRaids (CharacterID);
GO

SELECT * FROM dbo.pendingRaids
SELECT * FROM dbo.completedRaids
