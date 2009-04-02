/****** Objeto:  ForeignKey [FK_Ciudad_Pais]    Fecha de la secuencia de comandos: 04/02/2009 16:23:55 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Ciudad_Pais]') AND parent_object_id = OBJECT_ID(N'[dbo].[Ciudad]'))
ALTER TABLE [dbo].[Ciudad] DROP CONSTRAINT [FK_Ciudad_Pais]
GO
/****** Objeto:  ForeignKey [FK_Proyecto_Usuario]    Fecha de la secuencia de comandos: 04/02/2009 16:23:55 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Proyecto_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
ALTER TABLE [dbo].[Proyecto] DROP CONSTRAINT [FK_Proyecto_Usuario]
GO
/****** Objeto:  ForeignKey [FK_Usuario_Ciudad]    Fecha de la secuencia de comandos: 04/02/2009 16:23:55 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Usuario_Ciudad]') AND parent_object_id = OBJECT_ID(N'[dbo].[Usuario]'))
ALTER TABLE [dbo].[Usuario] DROP CONSTRAINT [FK_Usuario_Ciudad]
GO
/****** Objeto:  Table [dbo].[Proyecto]    Fecha de la secuencia de comandos: 04/02/2009 16:23:55 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Proyecto]') AND type in (N'U'))
DROP TABLE [dbo].[Proyecto]
GO
/****** Objeto:  Table [dbo].[Usuario]    Fecha de la secuencia de comandos: 04/02/2009 16:23:55 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Usuario]') AND type in (N'U'))
DROP TABLE [dbo].[Usuario]
GO
/****** Objeto:  Table [dbo].[Ciudad]    Fecha de la secuencia de comandos: 04/02/2009 16:23:55 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Ciudad]') AND type in (N'U'))
DROP TABLE [dbo].[Ciudad]
GO
/****** Objeto:  Table [dbo].[Pais]    Fecha de la secuencia de comandos: 04/02/2009 16:23:55 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Pais]') AND type in (N'U'))
DROP TABLE [dbo].[Pais]
GO
/****** Objeto:  Table [dbo].[Pais]    Fecha de la secuencia de comandos: 04/02/2009 16:23:55 ******/
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
)WITH (IGNORE_DUP_KEY = OFF)
)
END
GO
SET IDENTITY_INSERT [dbo].[Pais] ON
INSERT [dbo].[Pais] ([Id], [Version], [Nombre]) VALUES (1, 1, N'Argentina')
SET IDENTITY_INSERT [dbo].[Pais] OFF
/****** Objeto:  Table [dbo].[Ciudad]    Fecha de la secuencia de comandos: 04/02/2009 16:23:55 ******/
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
)WITH (IGNORE_DUP_KEY = OFF)
)
END
GO
SET IDENTITY_INSERT [dbo].[Ciudad] ON
INSERT [dbo].[Ciudad] ([Id], [Version], [Nombre], [Pais]) VALUES (2, 1, N'Buenos Aires', 1)
SET IDENTITY_INSERT [dbo].[Ciudad] OFF
/****** Objeto:  Table [dbo].[Usuario]    Fecha de la secuencia de comandos: 04/02/2009 16:23:55 ******/
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
	[Ciudad] [int] NOT NULL,
	[CodPostal] [varchar](50) COLLATE Traditional_Spanish_CI_AS NOT NULL,
	[DescripPerfil] [varchar](max) COLLATE Traditional_Spanish_CI_AS NULL,
	[PathLogo] [varchar](255) COLLATE Traditional_Spanish_CI_AS NULL,
 CONSTRAINT [PK_Usuario] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (IGNORE_DUP_KEY = OFF)
)
END
GO
SET IDENTITY_INSERT [dbo].[Usuario] ON
INSERT [dbo].[Usuario] ([Id], [Version], [Nombre], [Apellido], [Email], [Login], [PasswordHash], [Ciudad], [CodPostal], [DescripPerfil], [PathLogo]) VALUES (3, 7, N'Cecilia', N'Hagge', N'cecilia_hagge@hotmail.com', N'chechu', N'10623b16a98bd56f97f5b89cee62cfba', 2, N'10000', N'YOOOOOOO', NULL)
SET IDENTITY_INSERT [dbo].[Usuario] OFF
/****** Objeto:  Table [dbo].[Proyecto]    Fecha de la secuencia de comandos: 04/02/2009 16:23:55 ******/
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
	[PresupuestoMax] [int] NOT NULL,
	[PresupuestoMin] [int] NOT NULL,
	[Nivel] [varchar](50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	[Dificultad] [varchar](50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	[Tamanio] [varchar](50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	[Descripcion] [varchar](50) COLLATE Modern_Spanish_CI_AS NULL,
	[PathArchivo] [varchar](255) COLLATE Modern_Spanish_CI_AS NULL,
	[Usuario] [int] NOT NULL,
	[Fecha] [datetime] NULL,
 CONSTRAINT [PK_Proyecto] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (IGNORE_DUP_KEY = OFF)
)
END
GO
SET IDENTITY_INSERT [dbo].[Proyecto] ON
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha]) VALUES (1, 0, N'lala', 100, 500, N'Normal', N'Simple', N'Chico', N'lalala', NULL, 3, NULL)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha]) VALUES (2, 0, N'ceeee', 500, 1000, N'Normal', N'Simple', N'Mediano', N'aaaa', N'', 3, NULL)
SET IDENTITY_INSERT [dbo].[Proyecto] OFF
/****** Objeto:  ForeignKey [FK_Ciudad_Pais]    Fecha de la secuencia de comandos: 04/02/2009 16:23:55 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Ciudad_Pais]') AND parent_object_id = OBJECT_ID(N'[dbo].[Ciudad]'))
ALTER TABLE [dbo].[Ciudad]  WITH CHECK ADD  CONSTRAINT [FK_Ciudad_Pais] FOREIGN KEY([Pais])
REFERENCES [dbo].[Pais] ([Id])
GO
/****** Objeto:  ForeignKey [FK_Proyecto_Usuario]    Fecha de la secuencia de comandos: 04/02/2009 16:23:55 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Proyecto_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
ALTER TABLE [dbo].[Proyecto]  WITH CHECK ADD  CONSTRAINT [FK_Proyecto_Usuario] FOREIGN KEY([Usuario])
REFERENCES [dbo].[Usuario] ([Id])
GO
/****** Objeto:  ForeignKey [FK_Usuario_Ciudad]    Fecha de la secuencia de comandos: 04/02/2009 16:23:55 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Usuario_Ciudad]') AND parent_object_id = OBJECT_ID(N'[dbo].[Usuario]'))
ALTER TABLE [dbo].[Usuario]  WITH CHECK ADD  CONSTRAINT [FK_Usuario_Ciudad] FOREIGN KEY([Ciudad])
REFERENCES [dbo].[Ciudad] ([Id])
GO
