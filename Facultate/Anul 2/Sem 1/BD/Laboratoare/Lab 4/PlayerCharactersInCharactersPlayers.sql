USE LostArkDB2
GO
INSERT INTO Legaturi_Eliminate (NumeTabelSt, IdSt, NumeTabelDr, IdDr)
SELECT 'Players', PlayerID, 'Characters', CharacterID
FROM Characters
WHERE CharacterID NOT IN (
    SELECT MAX(CharacterID)
    FROM Characters
    GROUP BY PlayerID
);

DELETE FROM Characters
WHERE CharacterID NOT IN (
    SELECT MAX(CharacterID)
    FROM Characters
    GROUP BY PlayerID
);

ALTER TABLE Players
ADD CharacterID INT
FOREIGN KEY (CharacterID) REFERENCES Characters(CharacterID)
GO

UPDATE P
SET P.CharacterID = C.CharacterID
FROM Players P
JOIN Characters C ON P.PlayerID=C.PlayerID

ALTER TABLE Characters
DROP CONSTRAINT FK__Character__Playe__3B75D760
ALTER TABLE Characters
DROP COLUMN PlayerID











