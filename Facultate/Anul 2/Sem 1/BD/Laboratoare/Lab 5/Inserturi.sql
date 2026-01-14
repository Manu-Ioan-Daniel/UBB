DELETE FROM CharacterEquipments;
DELETE FROM PlayerCurrencies;
DELETE FROM CharacterRaids;
DELETE FROM Characters;
DELETE FROM Equipments;
DELETE FROM EquipmentCategories;
DELETE FROM Players;
DELETE FROM Currencies;
DELETE FROM Raids;
DELETE FROM Guilds;

DBCC CHECKIDENT ('Players', RESEED, 0);
DBCC CHECKIDENT ('Characters', RESEED, 0);
DBCC CHECKIDENT ('Currencies', RESEED, 0);
DBCC CHECKIDENT ('Raids', RESEED, 0);
DBCC CHECKIDENT ('EquipmentCategories', RESEED, 0);
DBCC CHECKIDENT ('Equipments', RESEED, 0);
DBCC CHECKIDENT ('Guilds', RESEED, 0);

INSERT INTO Players (PlayerUsername, PlayerPassword, PlayerCreationDate)
SELECT 
    CONCAT('Player', n),
    'Password123',
    GETDATE()
FROM (SELECT TOP (30) ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) AS n FROM sys.objects) AS numbers;

INSERT INTO Currencies (CurrencyName)
VALUES
    ('Gold'),
    ('Silver'),
    ('Bronze'),
    ('Gems'),
    ('Tokens');

INSERT INTO Raids (RaidName, RaidDifficulty, RaidPlayerRequirment)
VALUES
    ('Raid1','Easy',5),
    ('Raid2','Easy',5),
    ('Raid3','Medium',10),
    ('Raid4','Medium',10),
    ('Raid5','Hard',20),
    ('Raid6','Hard',20),
    ('Raid7','Legendary',50),
    ('Raid8','Legendary',50),
    ('Raid9','Mythic',100),
    ('Raid10','Mythic',100);

INSERT INTO Guilds (GuildName, GuildCreationDate)
VALUES
    ('Guild Alpha', GETDATE()),
    ('Guild Beta', GETDATE()),
    ('Guild Gamma', GETDATE()),
    ('Guild Delta', GETDATE()),
    ('Guild Omega', GETDATE());

INSERT INTO Characters (PlayerID, CharacterName, GuildID, Class, GuildRole, GuildJoinDate)
SELECT 
    CAST(1 + FLOOR(RAND(CHECKSUM(NEWID())) * 30) AS INT),
    CONCAT('Char', n),
    1,
    'Warrior',
    'Member',
    GETDATE()
FROM (SELECT TOP (100) ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) AS n FROM sys.objects) AS numbers;

INSERT INTO EquipmentCategories (CategoryName)
VALUES
    ('Weapon'),
    ('Armor'),
    ('Accessory'),
    ('Consumable'),
    ('Misc');

INSERT INTO Equipments (EquipmentRarity, EquipmentName, EquipmentSlot, EquipmentCategoryID)
SELECT 
    CONCAT('Rarity', n),
    CONCAT('Equip', n),
    CONCAT('Slot', n),
    CAST(1 + FLOOR(RAND(CHECKSUM(NEWID())) * 5) AS INT)
FROM (SELECT TOP (100) ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) AS n FROM sys.objects) AS numbers;

INSERT INTO PlayerCurrencies (PlayerID, CurrencyID, Amount)
SELECT TOP (50)
    p.PlayerID,
    c.CurrencyID,
    CAST(100 + FLOOR(RAND(CHECKSUM(NEWID())) * 900) AS INT)
FROM Players p
CROSS JOIN Currencies c
ORDER BY NEWID(); -- shuffle random


INSERT INTO CharacterRaids (CharacterID, RaidID, CompletionStatus, CompletionDate)
SELECT TOP (25)
    ch.CharacterID,
    r.RaidID,
    1,
    GETDATE()
FROM Characters ch
CROSS JOIN Raids r
ORDER BY NEWID();

INSERT INTO CharacterRaids (CharacterID, RaidID, CompletionStatus, CompletionDate)
SELECT TOP (25)
    ch.CharacterID,
    r.RaidID,
    0,
    NULL
FROM Characters ch
CROSS JOIN Raids r
ORDER BY NEWID();


INSERT INTO CharacterEquipments (EquipmentID, CharacterID)
SELECT TOP (10)
    e.EquipmentID,
    ch.CharacterID
FROM Equipments e
CROSS JOIN Characters ch
ORDER BY NEWID();
