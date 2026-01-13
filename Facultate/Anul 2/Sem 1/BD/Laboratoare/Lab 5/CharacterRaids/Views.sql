CREATE VIEW dbo.completedRaids
AS
SELECT c.CharacterName, r.RaidName, cr.CompletionDate
FROM CharacterRaids cr
JOIN Characters c ON cr.CharacterID = c.CharacterID
JOIN Raids r ON cr.RaidID = r.RaidID
WHERE cr.CompletionStatus = 1;
GO

CREATE VIEW dbo.pendingRaids
AS
SELECT c.CharacterName, r.RaidName
FROM CharacterRaids cr
JOIN Characters c ON cr.CharacterID = c.CharacterID
JOIN Raids r ON cr.RaidID = r.RaidID
WHERE cr.CompletionStatus = 0;
GO