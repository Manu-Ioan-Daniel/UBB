
USE LostArkDB2
GO

CREATE TABLE Continents(
    ContinentID INT PRIMARY KEY IDENTITY(1,1),
    ContinentName NVARCHAR(50)
);
CREATE TABLE Zones(
    ZoneID INT PRIMARY KEY IDENTITY(1,1),
    ZoneName NVARCHAR(50),
    ContinentID INT FOREIGN KEY REFERENCES Continents(ContinentID)
);

-- M:N
CREATE TABLE Mounts(
    MountID INT PRIMARY KEY IDENTITY(1,1),
    MountName NVARCHAR(50)
);

CREATE TABLE Cosmetics(
    CosmeticID INT PRIMARY KEY IDENTITY(1,1),
    CosmeticName NVARCHAR(50)
);

CREATE TABLE MountCosmetics(
    MountID INT,
    CosmeticID INT,
    PRIMARY KEY (MountID, CosmeticID),
    FOREIGN KEY (MountID) REFERENCES Mounts(MountID),
    FOREIGN KEY (CosmeticID) REFERENCES Cosmetics(CosmeticID)
);
CREATE TABLE Legaturi_Eliminate (
    NumeTabelSt NVARCHAR(100),
    IdSt INT,
    NumeTabelDr NVARCHAR(100),
    IdDr INT
);


