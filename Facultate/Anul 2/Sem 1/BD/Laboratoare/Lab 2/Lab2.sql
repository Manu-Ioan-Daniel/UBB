--1/10 ,group by,mai mult de 2 tabele,tabele m-n,having

SELECT P.PlayerID,P.PlayerUsername,PC.Amount AS Gold,COUNT(C.CharacterID) AS CharNr
FROM Players P
LEFT JOIN PlayerCurrencies PC ON P.PlayerID=PC.PlayerID AND PC.currencyID=1 --gold
LEFT JOIN Characters C ON P.PlayerID=C.PlayerID
GROUP BY P.PlayerID,P.PlayerUsername,PC.Amount
HAVING PC.Amount>10

--2/10,group by,where,mai mult de 2 tabele,

SELECT P.PlayerID,C.Class,G.GuildName, COUNT(C.Class) AS CharacterCount FROM Players P 
INNER JOIN Characters C ON C.PlayerID=P.PlayerID  
INNER JOIN Guilds G ON G.GuildID=C.GuildID 
WHERE P.PlayerCreationDate < '2026-01-01'  
GROUP BY P.PlayerID,C.Class,G.GuildName

--3/10,group by,where,having,mai mult de 2 tabele

SELECT P.PlayerUsername,C.CharacterName,C.Class,G.GuildName FROM Players P 
INNER JOIN Characters C ON P.PlayerID=C.PlayerID INNER JOIN Guilds G ON G.GuildID=C.GuildID
INNER JOIN CharacterRaids CR ON CR.CharacterID=C.CharacterID 
WHERE CR.CompletionStatus=1 
GROUP BY P.PlayerUsername,C.CharacterName,C.Class,G.GuildName 
HAVING COUNT(CR.RaidID)>=1

--4/10,distinct,where,mai mult de 2 tabele,tabele m-n

SELECT DISTINCT P.PlayerUsername,C.CharacterName,C.Class,G.GuildName FROM Players P 
INNER JOIN Characters C ON C.PlayerID=P.PlayerID 
INNER JOIN Guilds G ON C.GuildID=G.GuildID 
INNER JOIN CharacterRaids CR ON CR.CharacterID=C.CharacterID 
WHERE CR.CompletionStatus=1 
ORDER BY P.PlayerUsername,C.CharacterName;

--5/10,distinct,where,mai mult de 2 tabele,tabele m-n,

SELECT DISTINCT P.PlayerUsername,C.CharacterName,C.Class,E.EquipmentName FROM Players P 
INNER JOIN Characters C ON C.PlayerID=P.PlayerID 
INNER JOIN CharacterEquipments CE ON CE.CharacterID=C.CharacterID 
INNER JOIN Equipments E ON E.EquipmentID=CE.EquipmentID 
WHERE E.EquipmentRarity='Legendary'

--6/10 where,mai mult de 2 tabele

SELECT P.PlayerUsername,C.CharacterName,E.EquipmentName FROM Players P 
INNER JOIN Characters C ON C.PlayerID=P.PlayerID 
INNER JOIN CharacterEquipments CE ON CE.CharacterID=C.CharacterID 
INNER JOIN Equipments E ON E.EquipmentID=CE.EquipmentID WHERE E.EquipmentRarity='Epic';

--7/10 mai mult de 2 tabele

SELECT P.PlayerUsername,C.CharacterName, G.GuildName 
FROM Players P 
INNER JOIN Characters C ON C.PlayerID=P.PlayerID 
INNER JOIN Guilds G ON G.GuildID=C.GuildID 
ORDER BY P.PlayerUsername

--8/10

SELECT C.CharacterName,E.EquipmentName 
FROM Characters C
INNER JOIN CharacterEquipments CE ON CE.CharacterID=C.CharacterID 
INNER JOIN Equipments E ON E.EquipmentID=CE.EquipmentID
ORDER BY CharacterName

--9/10

SELECT C.CharacterName,R.RaidName,R.RaidDifficulty FROM Characters C
INNER JOIN CharacterRaids CR ON CR.CharacterID=C.CharacterID
INNER JOIN Raids R ON CR.RaidID=R.RaidID
ORDER BY CharacterName

--10/10

SELECT P.PlayerID,P.PlayerUserName,C.CurrencyName,PC.Amount FROM Players P
INNER JOIN PlayerCurrencies PC ON PC.PlayerID=P.PlayerID
INNER JOIN Currencies C ON C.CurrencyID=PC.CurrencyID
ORDER BY PlayerID,PlayerUserName