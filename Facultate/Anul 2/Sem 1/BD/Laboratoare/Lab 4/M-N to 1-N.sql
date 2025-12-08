USE LostArkDB2
GO

SELECT * FROM MountCosmetics
SELECT * FROM Legaturi_Eliminate


ALTER TABLE Cosmetics
ADD MountID INT;

WITH CTE AS (
    SELECT CosmeticID, MAX(MountID) AS MountID
    FROM MountCosmetics
    GROUP BY CosmeticID
)
UPDATE C
SET C.MountID = CTE.MountID
FROM Cosmetics C
JOIN CTE ON C.CosmeticID = CTE.CosmeticID;

ALTER TABLE Cosmetics
ADD CONSTRAINT FK_Cosmetics_Mounts
FOREIGN KEY (MountID) REFERENCES Mounts(MountID);

DELETE MC
FROM MountCosmetics MC
WHERE (MC.MountID, MC.CosmeticID) NOT IN (
    SELECT MAX(MountID) OVER (PARTITION BY CosmeticID), CosmeticID
    FROM MountCosmetics
);
