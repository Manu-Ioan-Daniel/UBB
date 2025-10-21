
INSERT INTO Players (PlayerUsername, PlayerPassword, PlayerCreationDate)
VALUES 
('player1', 'pwd123', '2025-01-15'),
('player2', 'pwd456', '2025-03-22'),
('player3', 'pwd789', '2025-05-10');


INSERT INTO Guilds (GuildName, GuildCreationDate)
VALUES 
('AlphaGuild', '2025-01-20'),
('BetaGuild', '2025-02-05'),
('GammaGuild', '2025-03-01');


INSERT INTO Characters (PlayerID, CharacterName, GuildID, Class, GuildRole, GuildJoinDate)
VALUES
(1, 'CharacterA', 1, 'Warrior', 'Leader', '2025-01-21'),
(2, 'CharacterB', 2, 'Archer', 'Member', '2025-02-06'),
(3, 'CharacterC', 3, 'Mage', 'Officer', '2025-03-02');



INSERT INTO EquipmentCategories (CategoryName)
VALUES 
('Weapon'),
('Armor'),
('Accessory');


INSERT INTO Currencies (CurrencyName)
VALUES
('Gold'),
('Silver'),
('Crystal');

INSERT INTO PlayerCurrencies (CurrencyID, PlayerID, Amount)
VALUES
(1, 1, 500),
(2, 1, 1000),
(3, 1, 50),
(1, 2, 300),
(2, 2, 1500),
(3, 2, 70),
(1, 3, 800),
(2, 3, 500),
(3, 3, 100);

INSERT INTO Equipments (EquipmentRarity, EquipmentName, EquipmentSlot, EquipmentCategoryID)
VALUES
('Legendary', 'Sword of Valor', 'Hand', 1),
('Epic', 'Longbow', 'Hand', 1),
('Rare', 'Steel Helmet', 'Head', 2);

INSERT INTO CharacterEquipments (EquipmentID, CharacterID)
VALUES
(1, 1),
(2, 2),
(3, 3);


INSERT INTO Raids (RaidName, RaidDifficulty, RaidPlayerRequirment)
VALUES
('Dragon Cave', 'Hard', 3),
('Forest Patrol', 'Medium', 2),
('Mountain Pass', 'Easy', 1);


INSERT INTO CharacterRaids (CharacterID, RaidID, CompletionStatus, CompletionDate)
VALUES
(1, 1, 1, '2025-06-01'),
(2, 2, 0, NULL),
(3, 3, 1, '2025-07-15');

INSERT INTO Characters (PlayerID, CharacterName, GuildID, Class, GuildRole, GuildJoinDate)
VALUES
(1, 'CharacterD', 3, 'Mage' , 'Member', '2025-02-23');