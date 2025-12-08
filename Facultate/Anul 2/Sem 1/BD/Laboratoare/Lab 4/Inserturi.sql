
USE LostArkDB2;
GO

INSERT INTO Guilds (GuildName, GuildCreationDate) VALUES
('GuildA','2023-01-01'),
('GuildB','2023-01-02'),
('GuildC','2023-01-03'),
('GuildD','2023-01-04'),
('GuildE','2023-01-05'),
('GuildF','2023-01-06'),
('GuildG','2023-01-07'),
('GuildH','2023-01-08'),
('GuildI','2023-01-09'),
('GuildJ','2023-01-10');

INSERT INTO Players (PlayerUsername, PlayerPassword, PlayerCreationDate) VALUES
('PlayerA','PassA','2023-01-01'),
('PlayerB','PassB','2023-01-02'),
('PlayerC','PassC','2023-01-03'),
('PlayerD','PassD','2023-01-04'),
('PlayerE','PassE','2023-01-05'),
('PlayerF','PassF','2023-01-06'),
('PlayerG','PassG','2023-01-07'),
('PlayerH','PassH','2023-01-08'),
('PlayerI','PassI','2023-01-09'),
('PlayerJ','PassJ','2023-01-10');


INSERT INTO Characters (PlayerID, CharacterName, GuildID, Class, GuildRole, GuildJoinDate) VALUES
(1,'CharA1',1,'ClassA','RoleA','2023-02-01'),

(2,'CharB1',2,'ClassA','RoleA','2023-02-02'),
(2,'CharB2',2,'ClassB','RoleB','2023-02-03'),

(3,'CharC1',3,'ClassA','RoleA','2023-02-04'),
(3,'CharC2',3,'ClassB','RoleA','2023-02-05'),
(3,'CharC3',3,'ClassC','RoleC','2023-02-06'),

(4,'CharD1',4,'ClassA','RoleA','2023-02-07'),

(5,'CharE1',5,'ClassA','RoleA','2023-02-08'),
(5,'CharE2',5,'ClassB','RoleB','2023-02-09'),
(5,'CharE3',5,'ClassC','RoleA','2023-02-10'),
(5,'CharE4',5,'ClassD','RoleA','2023-02-11'),

(7,'CharG1',7,'ClassA','RoleA','2023-02-12'),

(8,'CharH1',8,'ClassA','RoleA','2023-02-13'),
(8,'CharH2',8,'ClassB','RoleB','2023-02-14'),

(9,'CharI1',9,'ClassA','RoleA','2023-02-15'),
(9,'CharI2',9,'ClassB','RoleA','2023-02-16'),
(9,'CharI3',9,'ClassC','RoleA','2023-02-17'),

(10,'CharJ1',10,'ClassA','RoleA','2023-02-18');


INSERT INTO EquipmentCategories (CategoryName) VALUES
('CategoryA'),
('CategoryB'),
('CategoryC'),
('CategoryD'),
('CategoryE'),
('CategoryF'),
('CategoryG'),
('CategoryH'),
('CategoryI'),
('CategoryJ');

INSERT INTO Equipments (EquipmentRarity, EquipmentName, EquipmentSlot, EquipmentCategoryID) VALUES
('RarityA','Equipment_A1','Slot_A1',1),
('RarityB','Equipment_A2','Slot_A1',1),
('RarityC','Equipment_A3','Slot_A1',1),

('RarityA','Equipment_B1','Slot_B1',2),
('RarityB','Equipment_B2','Slot_B1',2),

('RarityA','Equipment_C1','Slot_C1',3),
('RarityB','Equipment_C2','Slot_C1',3),
('RarityC','Equipment_C3','Slot_C1',3),

('RarityA','Equipment_D1','Slot_D1',5),
('RarityB','Equipment_D2','Slot_D1',5),
('RarityC','Equipment_D3','Slot_D1',5),
('RarityA','Equipment_D4','Slot_D1',5),

('RarityA','Equipment_E1','Slot_E1',6),

('RarityA','Equipment_F1','Slot_F1',7),
('RarityB','Equipment_F2','Slot_F1',7),
('RarityC','Equipment_F3','Slot_F1',7),

('RarityA','Equipment_G1','Slot_G1',8),
('RarityB','Equipment_G2','Slot_G1',8),

('RarityA','Equipment_H1','Slot_H1',10);


INSERT INTO Continents (ContinentName) VALUES
('ContinentA'),
('ContinentB'),
('ContinentC'),
('ContinentD'),
('ContinentE'),
('ContinentF'),
('ContinentG'),
('ContinentH'),
('ContinentI'),
('ContinentJ');


INSERT INTO Zones (ZoneName, ContinentID) VALUES
('ZoneA1',1),('ZoneA2',1),
('ZoneB1',2),
('ZoneC1',3),('ZoneC2',3),
('ZoneD1',4),
('ZoneE1',5),('ZoneE2',5),('ZoneE3',5),
('ZoneF1',6),
('ZoneG1',7),
('ZoneH1',8),('ZoneH2',8),
('ZoneI1',9),
('ZoneJ1',10);


INSERT INTO Mounts (MountName) VALUES
('MountA'),
('MountB'),
('MountC'),
('MountD'),
('MountE'),
('MountF'),
('MountG'),
('MountH'),
('MountI'),
('MountJ');


INSERT INTO Cosmetics (CosmeticName) VALUES
('CosmeticA'),
('CosmeticB'),
('CosmeticC'),
('CosmeticD'),
('CosmeticE'),
('CosmeticF'),
('CosmeticG'),
('CosmeticH'),
('CosmeticI'),
('CosmeticJ');


INSERT INTO MountCosmetics (MountID, CosmeticID) VALUES
(1,1),
(2,1),(2,2),
(3,3),(3,4),
(4,5),
(5,6),
(6,7),(6,8),(6,9),
(7,10),
(8,2),(8,3),
(9,4),
(10,1),(10,5);


