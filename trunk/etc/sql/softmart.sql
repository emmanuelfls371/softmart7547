/****** Object:  Table [dbo].[Usuario]    Script Date: 03/22/2009 11:57:20 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Usuario]') AND type in (N'U'))
DROP TABLE [dbo].[Usuario]
GO
/****** Object:  Table [dbo].[Proyecto]    Script Date: 03/22/2009 11:57:20 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Proyecto]') AND type in (N'U'))
DROP TABLE [dbo].[Proyecto]
GO
/****** Object:  Table [dbo].[Proyecto]    Script Date: 03/22/2009 11:57:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Proyecto]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Proyecto](
	[Id] [bigint] IDENTITY(1,1) NOT NULL,
	[Version] [bigint] NOT NULL,
	[Nombre] [varchar](50) COLLATE Traditional_Spanish_CI_AS NOT NULL,
 CONSTRAINT [PK_Proyecto] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON)
)
END
GO
/****** Object:  Table [dbo].[Usuario]    Script Date: 03/22/2009 11:57:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Usuario]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Usuario](
	[Id] [bigint] IDENTITY(1,1) NOT NULL,
	[Version] [bigint] NOT NULL,
	[Nombre] [varchar](50) COLLATE Traditional_Spanish_CI_AS NOT NULL,
	[PasswordHash] [varchar](32) COLLATE Traditional_Spanish_CI_AS NOT NULL,
 CONSTRAINT [PK_Usuario] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON)
)
END
GO
SET IDENTITY_INSERT [dbo].[Usuario] ON
INSERT [dbo].[Usuario] ([Id], [Version], [Nombre], [PasswordHash]) VALUES (1, 1, N'Moncho', N'd09066cfeef4736a5b6f15f8e09205a0')
SET IDENTITY_INSERT [dbo].[Usuario] OFF
