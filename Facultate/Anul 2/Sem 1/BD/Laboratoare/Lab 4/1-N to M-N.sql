USE LostArkDB2
GO

CREATE TABLE EquipmentCategoryEquipments (
    EquipmentCategoryID INT NOT NULL,
    EquipmentID INT NOT NULL,
    PRIMARY KEY (EquipmentCategoryID, EquipmentID),
    FOREIGN KEY (EquipmentCategoryID) REFERENCES EquipmentCategories(EquipmentCategoryID),
    FOREIGN KEY (EquipmentID) REFERENCES Equipments(EquipmentID)
);
GO

INSERT INTO EquipmentCategoryEquipments (EquipmentCategoryID, EquipmentID)
SELECT EquipmentCategoryID,EquipmentID
FROM Equipments

ALTER TABLE Equipments
DROP CONSTRAINT FK__Equipment__Equip__47DBAE45
ALTER TABLE Equipments
DROP COLUMN EquipmentCategoryID;
