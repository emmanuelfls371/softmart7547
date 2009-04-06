/****** Objeto:  ForeignKey [FK_Ciudad_Pais]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Ciudad_Pais]') AND parent_object_id = OBJECT_ID(N'[dbo].[Ciudad]'))
ALTER TABLE [dbo].[Ciudad] DROP CONSTRAINT [FK_Ciudad_Pais]
GO
/****** Objeto:  ForeignKey [FK_Contrato_Calificacion]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Calificacion]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] DROP CONSTRAINT [FK_Contrato_Calificacion]
GO
/****** Objeto:  ForeignKey [FK_Contrato_Calificacion1]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Calificacion1]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] DROP CONSTRAINT [FK_Contrato_Calificacion1]
GO
/****** Objeto:  ForeignKey [FK_Contrato_Oferta]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Oferta]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] DROP CONSTRAINT [FK_Contrato_Oferta]
GO
/****** Objeto:  ForeignKey [FK_Contrato_Proyecto]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] DROP CONSTRAINT [FK_Contrato_Proyecto]
GO
/****** Objeto:  ForeignKey [FK_Oferta_Proyecto]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta] DROP CONSTRAINT [FK_Oferta_Proyecto]
GO
/****** Objeto:  ForeignKey [FK_Oferta_Usuario]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta] DROP CONSTRAINT [FK_Oferta_Usuario]
GO
/****** Objeto:  ForeignKey [FK_Proyecto_Usuario]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Proyecto_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
ALTER TABLE [dbo].[Proyecto] DROP CONSTRAINT [FK_Proyecto_Usuario]
GO
/****** Objeto:  ForeignKey [FK_Usuario_Ciudad]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Usuario_Ciudad]') AND parent_object_id = OBJECT_ID(N'[dbo].[Usuario]'))
ALTER TABLE [dbo].[Usuario] DROP CONSTRAINT [FK_Usuario_Ciudad]
GO
/****** Objeto:  Table [dbo].[Contrato]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Contrato]') AND type in (N'U'))
DROP TABLE [dbo].[Contrato]
GO
/****** Objeto:  Table [dbo].[Oferta]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Oferta]') AND type in (N'U'))
DROP TABLE [dbo].[Oferta]
GO
/****** Objeto:  Table [dbo].[Proyecto]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Proyecto]') AND type in (N'U'))
DROP TABLE [dbo].[Proyecto]
GO
/****** Objeto:  Table [dbo].[Usuario]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Usuario]') AND type in (N'U'))
DROP TABLE [dbo].[Usuario]
GO
/****** Objeto:  Table [dbo].[Ciudad]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Ciudad]') AND type in (N'U'))
DROP TABLE [dbo].[Ciudad]
GO
/****** Objeto:  Table [dbo].[Pais]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Pais]') AND type in (N'U'))
DROP TABLE [dbo].[Pais]
GO
/****** Objeto:  Table [dbo].[Calificacion]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Calificacion]') AND type in (N'U'))
DROP TABLE [dbo].[Calificacion]
GO
/****** Objeto:  Table [dbo].[Calificacion]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Calificacion]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Calificacion](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Version] [int] NOT NULL,
	[Calificacion] [int] NOT NULL,
	[Comentario] [varchar](50) COLLATE Modern_Spanish_CI_AS NULL,
 CONSTRAINT [PK_Calificacion] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (IGNORE_DUP_KEY = OFF)
)
END
GO
SET IDENTITY_INSERT [dbo].[Calificacion] ON
INSERT [dbo].[Calificacion] ([Id], [Version], [Calificacion], [Comentario]) VALUES (2, 0, 3, NULL)
INSERT [dbo].[Calificacion] ([Id], [Version], [Calificacion], [Comentario]) VALUES (3, 0, 10, N'Excelente!')
INSERT [dbo].[Calificacion] ([Id], [Version], [Calificacion], [Comentario]) VALUES (4, 0, 8, N'Muy bueno')
INSERT [dbo].[Calificacion] ([Id], [Version], [Calificacion], [Comentario]) VALUES (5, 0, 8, N'Muy bueno')
SET IDENTITY_INSERT [dbo].[Calificacion] OFF
/****** Objeto:  Table [dbo].[Pais]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
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
/****** Objeto:  Table [dbo].[Ciudad]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
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
/****** Objeto:  Table [dbo].[Usuario]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
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
INSERT [dbo].[Usuario] ([Id], [Version], [Nombre], [Apellido], [Email], [Login], [PasswordHash], [Ciudad], [CodPostal], [DescripPerfil], [PathLogo]) VALUES (4, 1, N'Ariel', N'Piechotka', N'arielpie@hotmail.com', N'ariel', N'4900d0a19b6894a4a514e9ff3afcc2c0', 2, N'2222', N'Ing. Informatico y Electronico', NULL)
INSERT [dbo].[Usuario] ([Id], [Version], [Nombre], [Apellido], [Email], [Login], [PasswordHash], [Ciudad], [CodPostal], [DescripPerfil], [PathLogo]) VALUES (5, 0, N'Cecilia', N'Hagge', N'chechuhagge@hotmail.com', N'chechu', N'd9e9516de4a32264146ecb0ed0b5dde2', 2, N'1111', N'Ing Informatica', NULL)
SET IDENTITY_INSERT [dbo].[Usuario] OFF
/****** Objeto:  Table [dbo].[Proyecto]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
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
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha]) VALUES (1, 2, N'CivilizAlgo', 5000, 2000, N'Normal', N'Medio', N'Chico', N'', N'', 4, CAST(0x00009BFA00000000 AS DateTime))
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha]) VALUES (2, 1, N'Hoteles Maximo', 2000, 1000, N'Normal', N'Complejo', N'Chico', N'Administrar hotel', N'', 5, CAST(0x00009BF300000000 AS DateTime))
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha]) VALUES (3, 1, N'Webalia', 10000, 5000, N'Premium', N'Medio', N'Chico', N'', N'', 5, CAST(0x00009BF000000000 AS DateTime))
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha]) VALUES (4, 0, N'HyperMarket', -1, 100000, N'Normal', N'Simple', N'Mediano', N'Administrar supermercado', N'', 5, CAST(0x00009BFB00000000 AS DateTime))
SET IDENTITY_INSERT [dbo].[Proyecto] OFF
/****** Objeto:  Table [dbo].[Oferta]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Oferta]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Oferta](
	[Monto] [int] NOT NULL,
	[CantDias] [int] NOT NULL,
	[Descripcion] [varchar](50) COLLATE Modern_Spanish_CI_AS NULL,
	[Notificacion] [varchar](2) COLLATE Modern_Spanish_CI_AS NOT NULL,
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Proyecto] [int] NOT NULL,
	[Version] [int] NULL,
	[Usuario] [int] NOT NULL,
 CONSTRAINT [PK_Oferta] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (IGNORE_DUP_KEY = OFF)
)
END
GO
SET IDENTITY_INSERT [dbo].[Oferta] ON
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario]) VALUES (200, 10, N'', N'Si', 2, 1, 0, 5)
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario]) VALUES (800, 15, N'Oferta de prueba', N'Si', 3, 2, 0, 4)
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario]) VALUES (3000, 20, N'Soy experto', N'No', 4, 3, 0, 4)
SET IDENTITY_INSERT [dbo].[Oferta] OFF
/****** Objeto:  Table [dbo].[Contrato]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Contrato]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Contrato](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Version] [int] NOT NULL,
	[Proyecto] [int] NOT NULL,
	[Oferta] [int] NOT NULL,
	[CalifVendedor] [int] NULL,
	[CalifComprador] [int] NULL,
 CONSTRAINT [PK_Contrato] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (IGNORE_DUP_KEY = OFF)
)
END
GO
SET IDENTITY_INSERT [dbo].[Contrato] ON
INSERT [dbo].[Contrato] ([Id], [Version], [Proyecto], [Oferta], [CalifVendedor], [CalifComprador]) VALUES (2, 1, 1, 2, 3, NULL)
INSERT [dbo].[Contrato] ([Id], [Version], [Proyecto], [Oferta], [CalifVendedor], [CalifComprador]) VALUES (3, 0, 2, 3, NULL, NULL)
INSERT [dbo].[Contrato] ([Id], [Version], [Proyecto], [Oferta], [CalifVendedor], [CalifComprador]) VALUES (4, 2, 3, 4, 4, 5)
SET IDENTITY_INSERT [dbo].[Contrato] OFF
/****** Objeto:  ForeignKey [FK_Ciudad_Pais]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Ciudad_Pais]') AND parent_object_id = OBJECT_ID(N'[dbo].[Ciudad]'))
ALTER TABLE [dbo].[Ciudad]  WITH CHECK ADD  CONSTRAINT [FK_Ciudad_Pais] FOREIGN KEY([Pais])
REFERENCES [dbo].[Pais] ([Id])
GO
/****** Objeto:  ForeignKey [FK_Contrato_Calificacion]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Calificacion]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato]  WITH CHECK ADD  CONSTRAINT [FK_Contrato_Calificacion] FOREIGN KEY([CalifComprador])
REFERENCES [dbo].[Calificacion] ([Id])
GO
/****** Objeto:  ForeignKey [FK_Contrato_Calificacion1]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Calificacion1]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato]  WITH CHECK ADD  CONSTRAINT [FK_Contrato_Calificacion1] FOREIGN KEY([CalifVendedor])
REFERENCES [dbo].[Calificacion] ([Id])
GO
/****** Objeto:  ForeignKey [FK_Contrato_Oferta]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Oferta]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato]  WITH CHECK ADD  CONSTRAINT [FK_Contrato_Oferta] FOREIGN KEY([Oferta])
REFERENCES [dbo].[Oferta] ([Id])
GO
/****** Objeto:  ForeignKey [FK_Contrato_Proyecto]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato]  WITH CHECK ADD  CONSTRAINT [FK_Contrato_Proyecto] FOREIGN KEY([Proyecto])
REFERENCES [dbo].[Proyecto] ([Id])
GO
/****** Objeto:  ForeignKey [FK_Oferta_Proyecto]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta]  WITH CHECK ADD  CONSTRAINT [FK_Oferta_Proyecto] FOREIGN KEY([Proyecto])
REFERENCES [dbo].[Proyecto] ([Id])
GO
/****** Objeto:  ForeignKey [FK_Oferta_Usuario]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta]  WITH CHECK ADD  CONSTRAINT [FK_Oferta_Usuario] FOREIGN KEY([Usuario])
REFERENCES [dbo].[Usuario] ([Id])
GO
/****** Objeto:  ForeignKey [FK_Proyecto_Usuario]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Proyecto_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
ALTER TABLE [dbo].[Proyecto]  WITH CHECK ADD  CONSTRAINT [FK_Proyecto_Usuario] FOREIGN KEY([Usuario])
REFERENCES [dbo].[Usuario] ([Id])
GO
/****** Objeto:  ForeignKey [FK_Usuario_Ciudad]    Fecha de la secuencia de comandos: 04/06/2009 18:05:51 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Usuario_Ciudad]') AND parent_object_id = OBJECT_ID(N'[dbo].[Usuario]'))
ALTER TABLE [dbo].[Usuario]  WITH CHECK ADD  CONSTRAINT [FK_Usuario_Ciudad] FOREIGN KEY([Ciudad])
REFERENCES [dbo].[Ciudad] ([Id])
GO
