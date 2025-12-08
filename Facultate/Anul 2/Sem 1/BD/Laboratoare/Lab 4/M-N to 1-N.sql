USE LostArkDB2
GO

ALTER TABLE Cosmetics
ADD MountID INT;
ALTER TABLE Cosmetics
ADD CONSTRAINT FK_Cosmetics_Mounts
FOREIGN KEY (MountID) REFERENCES Mounts(MountID);

UPDATE Cosmetics
SET MountID = (
    SELECT MAX(MountID)
    FROM MountCosmetics
    WHERE MountCosmetics.CosmeticID = Cosmetics.CosmeticID
);

INSERT INTO Legaturi_Eliminate (NumeTabelSt,IdSt,NumeTabelDr,IdDr)
SELECT 'Mount', MC.MountID, 'Cosmetics', MC.CosmeticID
FROM MountCosmetics MC
WHERE MC.MountID <> (
    SELECT MAX(MountID)
    FROM MountCosmetics
    WHERE CosmeticID=MC.CosmeticID
);

DROP TABLE MountCosmetics



