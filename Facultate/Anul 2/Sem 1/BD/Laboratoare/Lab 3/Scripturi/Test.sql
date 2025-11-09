USE LostArkDB
GO
EXEC P_SetDBVersion @Version = 1.5; 
SELECT * FROM DBVersion 