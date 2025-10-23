--1/10 ,group by,mai mult de 2 tabele,tabele m-n,having,distinct
--Selectam toti playerii care au un average de toate currency urile mai mare ca 200,si afisam si cate caractere au
SELECT P.PlayerID,P.PlayerUsername,COUNT(DISTINCT C.CharacterID) AS CharacterNo, AVG(PC.Amount) AS Average
FROM Players P
INNER JOIN PlayerCurrencies PC ON P.PlayerID=PC.PlayerID
INNER JOIN Characters C ON C.PlayerID=P.PlayerID
GROUP BY P.PlayerID,P.PlayerUsername
HAVING AVG(PC.Amount)>200

--2/10,group by,where,mai mult de 2 tabele,tabele m-n
--selectam toti playerii care au contul creat inainte de 2026,si au completat cel putin un raid,numarul de raiduri facute
-- in total pe o anumita clasa,si cate caractere de acea clasa detine player ul
SELECT P.PlayerID,C.Class,COUNT(C.Class) AS CharacterCount,COUNT(R.RaidID) AS RaidCompletions FROM Players P 
INNER JOIN Characters C ON C.PlayerID=P.PlayerID  
INNER JOIN CharacterRaids CR ON CR.CharacterID=C.CharacterID
INNER JOIN Raids R ON R.RaidID=CR.RaidID
WHERE P.PlayerCreationDate < '2026-01-01' AND CR.CompletionStatus=1  
GROUP BY P.PlayerID,C.Class

--3/10,group by,where,having,mai mult de 2 tabele,tabele m-n
--selectam toate username urile playerilor,numele caracterelor,clasa,guild ul din care provin,care au mai mult de un raid completat
SELECT P.PlayerUsername,C.CharacterName,C.Class,G.GuildName FROM Players P 
INNER JOIN Characters C ON P.PlayerID=C.PlayerID 
INNER JOIN Guilds G ON G.GuildID=C.GuildID
INNER JOIN CharacterRaids CR ON CR.CharacterID=C.CharacterID 
WHERE CR.CompletionStatus=1 
GROUP BY P.PlayerUsername,C.CharacterName,C.Class,G.GuildName 
HAVING COUNT(CR.RaidID)>1

--4/10,distinct,where,mai mult de 2 tabele,tabele m-n
--la fel ca mai sus,dar cel putin 1 raid si distincte
SELECT DISTINCT P.PlayerUsername,C.CharacterName,C.Class,G.GuildName FROM Players P 
INNER JOIN Characters C ON C.PlayerID=P.PlayerID 
INNER JOIN Guilds G ON C.GuildID=G.GuildID 
INNER JOIN CharacterRaids CR ON CR.CharacterID=C.CharacterID 
WHERE CR.CompletionStatus=1 
ORDER BY P.PlayerUsername,C.CharacterName;

--5/10,distinct,where,mai mult de 2 tabele,tabele m-n,
--selectam toate characterele care au cel putin un equipment de raritate legendara,si aratam ce clasa este,numele,si al carui player apartine
SELECT DISTINCT P.PlayerUsername,C.CharacterName,C.Class,E.EquipmentName FROM Players P 
INNER JOIN Characters C ON C.PlayerID=P.PlayerID 
INNER JOIN CharacterEquipments CE ON CE.CharacterID=C.CharacterID 
INNER JOIN Equipments E ON E.EquipmentID=CE.EquipmentID 
WHERE E.EquipmentRarity='Legendary'

--6/10 where,mai mult de 2 tabele
--afisam toate piecuri le de equipment de raritate epica al fiecarui caracter
SELECT P.PlayerUsername,C.CharacterName,E.EquipmentName FROM Players P 
INNER JOIN Characters C ON C.PlayerID=P.PlayerID 
INNER JOIN CharacterEquipments CE ON CE.CharacterID=C.CharacterID 
INNER JOIN Equipments E ON E.EquipmentID=CE.EquipmentID WHERE E.EquipmentRarity='Epic';

--7/10 mai mult de 2 tabele
--afisam fiecare caracter al fiecarui player in ce guild este

SELECT P.PlayerUsername,C.CharacterName, G.GuildName 
FROM Players P 
INNER JOIN Characters C ON C.PlayerID=P.PlayerID 
INNER JOIN Guilds G ON G.GuildID=C.GuildID 
ORDER BY P.PlayerUsername

--8/10
--selectam toate equipment urile al tuturor caracterelor
SELECT C.CharacterName,E.EquipmentName 
FROM Characters C
INNER JOIN CharacterEquipments CE ON CE.CharacterID=C.CharacterID 
INNER JOIN Equipments E ON E.EquipmentID=CE.EquipmentID
ORDER BY CharacterName

--9/10
--afisam toate raid urile completate cu dificultatea respectiva al tuturor caracterelor
SELECT C.CharacterName,R.RaidName,R.RaidDifficulty FROM Characters C
INNER JOIN CharacterRaids CR ON CR.CharacterID=C.CharacterID
INNER JOIN Raids R ON CR.RaidID=R.RaidID
WHERE CR.CompletionStatus=1
ORDER BY CharacterName

--10/10
--afisam toate currency urile pe care le au toti playerii
SELECT P.PlayerID,P.PlayerUserName,C.CurrencyName,PC.Amount FROM Players P
INNER JOIN PlayerCurrencies PC ON PC.PlayerID=P.PlayerID
INNER JOIN Currencies C ON C.CurrencyID=PC.CurrencyID
ORDER BY PlayerID,PlayerUserName