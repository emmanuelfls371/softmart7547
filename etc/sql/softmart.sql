/****** Object:  ForeignKey [FK_Ciudad_Pais]    Script Date: 03/24/2009 22:37:52 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Ciudad_Pais]') AND parent_object_id = OBJECT_ID(N'[dbo].[Ciudad]'))
ALTER TABLE [dbo].[Ciudad] DROP CONSTRAINT [FK_Ciudad_Pais]
GO
/****** Object:  ForeignKey [FK_Usuario_Ciudad]    Script Date: 03/24/2009 22:37:52 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Usuario_Ciudad]') AND parent_object_id = OBJECT_ID(N'[dbo].[Usuario]'))
ALTER TABLE [dbo].[Usuario] DROP CONSTRAINT [FK_Usuario_Ciudad]
GO
/****** Object:  Table [dbo].[Usuario]    Script Date: 03/24/2009 22:37:52 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Usuario]') AND type in (N'U'))
DROP TABLE [dbo].[Usuario]
GO
/****** Object:  Table [dbo].[Ciudad]    Script Date: 03/24/2009 22:37:52 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Ciudad]') AND type in (N'U'))
DROP TABLE [dbo].[Ciudad]
GO
/****** Object:  Table [dbo].[Proyecto]    Script Date: 03/24/2009 22:37:52 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Proyecto]') AND type in (N'U'))
DROP TABLE [dbo].[Proyecto]
GO
/****** Object:  Table [dbo].[Pais]    Script Date: 03/24/2009 22:37:52 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Pais]') AND type in (N'U'))
DROP TABLE [dbo].[Pais]
GO
/****** Object:  Table [dbo].[Pais]    Script Date: 03/24/2009 22:37:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Pais]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Pais](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Version] [int] NULL,
	[Nombre] [varchar](50) COLLATE Traditional_Spanish_CI_AS NOT NULL,
 CONSTRAINT [PK_Pais] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON)
)
END
GO
/****** Object:  Table [dbo].[Proyecto]    Script Date: 03/24/2009 22:37:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Proyecto]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Proyecto](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Version] [int] NOT NULL,
	[Nombre] [varchar](50) COLLATE Traditional_Spanish_CI_AS NOT NULL,
 CONSTRAINT [PK_Proyecto] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON)
)
END
GO
/****** Object:  Table [dbo].[Ciudad]    Script Date: 03/24/2009 22:37:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Ciudad]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Ciudad](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Version] [int] NOT NULL,
	[Nombre] [varchar](70) COLLATE Traditional_Spanish_CI_AS NOT NULL,
	[Pais] [int] NOT NULL,
 CONSTRAINT [PK_Ciudad] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON),
 CONSTRAINT [IX_Ciudad] UNIQUE NONCLUSTERED 
(
	[Pais] ASC,
	[Nombre] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON)
)
END
GO
/****** Object:  Table [dbo].[Usuario]    Script Date: 03/24/2009 22:37:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Usuario]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Usuario](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Version] [int] NOT NULL,
	[Nombre] [varchar](50) COLLATE Traditional_Spanish_CI_AS NOT NULL,
	[Apellido] [varchar](50) COLLATE Traditional_Spanish_CI_AS NOT NULL,
	[Email] [varchar](255) COLLATE Traditional_Spanish_CI_AS NOT NULL,
	[Login] [varchar](50) COLLATE Traditional_Spanish_CI_AS NOT NULL,
	[PasswordHash] [varchar](32) COLLATE Traditional_Spanish_CI_AS NOT NULL,
	[Ciudad] [int] NULL,
	[CodPostal] [varchar](50) COLLATE Traditional_Spanish_CI_AS NOT NULL,
	[DescripPerfil] [varchar](max) COLLATE Traditional_Spanish_CI_AS NULL,
	[PathLogo] [varchar](255) COLLATE Traditional_Spanish_CI_AS NULL,
 CONSTRAINT [PK_Usuario] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON),
 CONSTRAINT [IX_Usuario] UNIQUE NONCLUSTERED 
(
	[Login] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON)
)
END
GO
/****** Object:  ForeignKey [FK_Ciudad_Pais]    Script Date: 03/24/2009 22:37:52 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Ciudad_Pais]') AND parent_object_id = OBJECT_ID(N'[dbo].[Ciudad]'))
ALTER TABLE [dbo].[Ciudad]  WITH CHECK ADD  CONSTRAINT [FK_Ciudad_Pais] FOREIGN KEY([Pais])
REFERENCES [dbo].[Pais] ([Id])
GO
ALTER TABLE [dbo].[Ciudad] CHECK CONSTRAINT [FK_Ciudad_Pais]
GO
/****** Object:  ForeignKey [FK_Usuario_Ciudad]    Script Date: 03/24/2009 22:37:52 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Usuario_Ciudad]') AND parent_object_id = OBJECT_ID(N'[dbo].[Usuario]'))
ALTER TABLE [dbo].[Usuario]  WITH CHECK ADD  CONSTRAINT [FK_Usuario_Ciudad] FOREIGN KEY([Ciudad])
REFERENCES [dbo].[Ciudad] ([Id])
GO
ALTER TABLE [dbo].[Usuario] CHECK CONSTRAINT [FK_Usuario_Ciudad]
GO
