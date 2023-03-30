/*

INSERT INTO TmpCustomerInvalid (
	[firstName],
	[lastName],
	[city],
	[state],
	[zip],
	[phone],
	[email],
	[ip]
) VALUES(
	'rafique',
	'islam',
	'rajshahi',
	'bd',
	'6000',
	'01970111211',
	'rafique@hello.com',
	'192.168.111.111'
)


IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CustomerRaw]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[CustomerRaw](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[fisrtName] [nvarchar](255) NULL,
	[lastName] [nvarchar](255) NULL,
	[city] [nvarchar](255) NULL,
	[state] [nvarchar](255) NULL,
	[zip] [nvarchar](255) NULL,
	[phone] [nvarchar](255) NULL,
	[email] [nvarchar](255) NULL,
	[ip] [nvarchar](255) NULL,
 CONSTRAINT [PK_CustomerRaw] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
END

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CustomerNew]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[CustomerNew](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[fisrtName] [nvarchar](255) NULL,
	[lastName] [nvarchar](255) NULL,
	[city] [nvarchar](255) NULL,
	[state] [nvarchar](255) NULL,
	[zip] [nvarchar](255) NULL,
	[phone] [nvarchar](255) NULL,
	[email] [nvarchar](255) NULL,
	[ip] [nvarchar](255) NULL,
	CONSTRAINT [PK_CustomerNew] PRIMARY KEY CLUSTERED ([id] ASC)
)
END

SELECT DISTINCT 
	   [firstName]
      ,[lastName]
      ,[city]
      ,[state]
      ,[zip]
      ,[phone]
      ,[email]
      ,[ip]
 FROM [DataProc].[dbo].[CustomerAll]

SELECT id FROM CustomerValid 
WHERE [firstName]='Chong' 
AND [lastName]='Park' 
AND [city]='Woodside' 
AND [state]='NY'
AND [zip]='11377'
AND [phone]='7187553983' 
AND  [email]='chong.park@comcast.net' 
AND [ip]='207.226.191.87'

SELECT [id] FROM CustomerValid 
WHERE [firstName]='Duane' 
AND [lastName]='Jones' 
AND [city]='Kansas city' 
AND [state]='MO'
AND [zip]='64131'
AND [phone]='8166452368' 
AND  [email]='kelseyfrechin@yahoo.com' 
AND [ip]='192.231.171.102'

INSERT INTO TmpCustomerInvalid ([firstName],[lastName],[city],[state],[zip],[phone],[email],[ip]) 
VALUES('rafique','islam','rajshahi','bd','6000','01970111211','rafique@hello.com','192.168.111.111')



select * from TmpCustomerInvalid
delete from TmpCustomerInvalid
DBCC CHECKIDENT (TmpCustomerInvalid, RESEED, -1)





select * from [dbo].[CustomerInvalid]

select * from [dbo].[CustomerValid]





SELECT id FROM CustomerValid 
WHERE [firstName]='Chong' 
AND [lastName]='Park' 
AND [city]='Woodside' 
AND [state]='NY'
AND [zip]='11377'
AND [phone]='7187553983' 
AND  [email]='chong.park@comcast.net' 
AND [ip]='207.226.191.87'

delete CustomerValid where id=4


SELECT * 
FROM CustomerValid 
ORDER BY id 
OFFSET 1000 ROWS FETCH NEXT 100 ROWS ONLY;


SELECT COUNT(id) Idx FROM [CustomerAll]

SELECT * 
FROM CustomerAll 
ORDER BY id 
OFFSET 0 ROWS FETCH NEXT 100 ROWS ONLY;

SELECT [firstName],[lastName],[city],[state],[zip],[phone],[email],[ip] 
FROM [CustomerValid]
ORDER BY id 
OFFSET 0 ROWS FETCH NEXT 100 ROWS ONLY
*/

SELECT COUNT([Id]) AS Idx FROM [CustomerValid_01]

SELECT COUNT([Id]) AS Idx FROM CustomerValid_01

SELECT * FROM CustomerValid_02




