USE LostArkDB
GO
EXEC P_SetDBVersion @Version = 0
SELECT * FROM DBVersion