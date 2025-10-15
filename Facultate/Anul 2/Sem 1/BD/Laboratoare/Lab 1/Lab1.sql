CREATE DATABASE LostArkDB
GO
USE LostArkDB
CREATE TABLE Players(
	PlayerID INT PRIMARY KEY IDENTITY(1,1),
	PlayerUsername NVARCHAR(50) NOT NULL,
	PlayerPassword NVARCHAR(50) NOT NULL,
	PlayerCreationDate DATE NOT NULL

	);
CREATE TABLE Guilds(
    GuildID INT PRIMARY KEY IDENTITY(1,1),
    GuildName NVARCHAR(100) NOT NULL,
    GuildCreationDate DATE NOT NULL
);

CREATE TABLE Characters(
	CharacterID INT PRIMARY KEY IDENTITY(1,1),
	PlayerID INT NOT NULL,
	CharacterName NVARCHAR(50) NOT NULL,
	GuildID INT NOT NULL,
	Class NVARCHAR(50) NOT NULL,
	GuildRole NVARCHAR(100),
	GuildJoinDate DATE,
	FOREIGN KEY (PlayerID) REFERENCES Players(PlayerID),
	FOREIGN KEY (GuildID) REFERENCES Guilds(GuildID)
);
CREATE TABLE EquipmentCategories(
	EquipmentCategoryID INT PRIMARY KEY IDENTITY(1,1),
	CategoryName NVARCHAR(100) NOT NULL
);
CREATE TABLE Currencies(
	CurrencyID INT PRIMARY KEY IDENTITY(1,1),
	CurrencyName NVARCHAR(50) NOT NULL
	);
CREATE TABLE PlayerCurrencies(
	CurrencyID INT NOT NULL,
	PlayerID INT NOT NULL,
	Amount INT NOT NULL DEFAULT 0,
	PRIMARY KEY (PlayerID,CurrencyID),
	FOREIGN KEY (CurrencyID) REFERENCES Currencies(CurrencyID),
	FOREIGN KEY (PlayerID) REFERENCES Players(PlayerID)
	);
CREATE TABLE Equipments(
	EquipmentID INT PRIMARY KEY IDENTITY(1,1),
	EquipmentRarity NVARCHAR(50) NOT NULL,
	EquipmentName NVARCHAR(50) NOT NULL,
	EquipmentSlot NVARCHAR(50) NOT NULL,
	EquipmentCategoryID INT FOREIGN KEY REFERENCES EquipmentCategories(EquipmentCategoryID)
	);
CREATE TABLE CharacterEquipments(
	EquipmentID INT NOT NULL,
	CharacterID INT NOT NULL,
	PRIMARY KEY(EquipmentID,CharacterID),
	FOREIGN KEY (EquipmentID) REFERENCES Equipments(EquipmentID),
	FOREIGN KEY (CharacterID) REFERENCES Characters(CharacterID)
	);
CREATE TABLE Raids(
	RaidID INT PRIMARY KEY IDENTITY(1,1),
	RaidName NVARCHAR(50) NOT NULL,
	RaidDifficulty NVARCHAR(50) NOT NULL,
	RaidPlayerRequirment INT NOT NULL
	);
CREATE TABLE CharacterRaids(
    CharacterID INT NOT NULL,
    RaidID INT NOT NULL,
    CompletionStatus BIT NOT NULL DEFAULT 0,
    CompletionDate DATE NULL,
    PRIMARY KEY (CharacterID, RaidID),
    FOREIGN KEY (CharacterID) REFERENCES Characters(CharacterID),
    FOREIGN KEY (RaidID) REFERENCES Raids(RaidID)
);


	
