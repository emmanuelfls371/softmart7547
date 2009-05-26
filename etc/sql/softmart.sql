/****** Objeto:  ForeignKey [FK_Contrato_Calificacion]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Calificacion]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] DROP CONSTRAINT [FK_Contrato_Calificacion]
GO
/****** Objeto:  ForeignKey [FK_Contrato_Calificacion1]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Calificacion1]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] DROP CONSTRAINT [FK_Contrato_Calificacion1]
GO
/****** Objeto:  ForeignKey [FK_Contrato_Oferta]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Oferta]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] DROP CONSTRAINT [FK_Contrato_Oferta]
GO
/****** Objeto:  ForeignKey [FK_Contrato_Proyecto]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] DROP CONSTRAINT [FK_Contrato_Proyecto]
GO
/****** Objeto:  ForeignKey [FK_Oferta_Moneda]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Moneda]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta] DROP CONSTRAINT [FK_Oferta_Moneda]
GO
/****** Objeto:  ForeignKey [FK_Oferta_Proyecto]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta] DROP CONSTRAINT [FK_Oferta_Proyecto]
GO
/****** Objeto:  ForeignKey [FK_Oferta_Usuario]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta] DROP CONSTRAINT [FK_Oferta_Usuario]
GO
/****** Objeto:  ForeignKey [FK_Proyecto_Moneda]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Proyecto_Moneda]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
ALTER TABLE [dbo].[Proyecto] DROP CONSTRAINT [FK_Proyecto_Moneda]
GO
/****** Objeto:  ForeignKey [FK_Proyecto_Usuario]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Proyecto_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
ALTER TABLE [dbo].[Proyecto] DROP CONSTRAINT [FK_Proyecto_Usuario]
GO
/****** Objeto:  ForeignKey [FK_Usuario_Pais]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Usuario_Pais]') AND parent_object_id = OBJECT_ID(N'[dbo].[Usuario]'))
ALTER TABLE [dbo].[Usuario] DROP CONSTRAINT [FK_Usuario_Pais]
GO
/****** Objeto:  Default [DF_Proyecto_Cancelado]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF  EXISTS (SELECT * FROM sys.default_constraints WHERE object_id = OBJECT_ID(N'[dbo].[DF_Proyecto_Cancelado]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
Begin
ALTER TABLE [dbo].[Proyecto] DROP CONSTRAINT [DF_Proyecto_Cancelado]

End
GO
/****** Objeto:  Default [DF_Proyecto_Revisado]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF  EXISTS (SELECT * FROM sys.default_constraints WHERE object_id = OBJECT_ID(N'[dbo].[DF_Proyecto_Revisado]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
Begin
ALTER TABLE [dbo].[Proyecto] DROP CONSTRAINT [DF_Proyecto_Revisado]

End
GO
/****** Objeto:  Default [DF_Proyecto_CanceladoAdmin]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF  EXISTS (SELECT * FROM sys.default_constraints WHERE object_id = OBJECT_ID(N'[dbo].[DF_Proyecto_CanceladoAdmin]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
Begin
ALTER TABLE [dbo].[Proyecto] DROP CONSTRAINT [DF_Proyecto_CanceladoAdmin]

End
GO
/****** Objeto:  Default [DF_Usuario_Nivel]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF  EXISTS (SELECT * FROM sys.default_constraints WHERE object_id = OBJECT_ID(N'[dbo].[DF_Usuario_Nivel]') AND parent_object_id = OBJECT_ID(N'[dbo].[Usuario]'))
Begin
ALTER TABLE [dbo].[Usuario] DROP CONSTRAINT [DF_Usuario_Nivel]

End
GO
/****** Objeto:  Default [DF_Usuario_Bloqueado]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF  EXISTS (SELECT * FROM sys.default_constraints WHERE object_id = OBJECT_ID(N'[dbo].[DF_Usuario_Bloqueado]') AND parent_object_id = OBJECT_ID(N'[dbo].[Usuario]'))
Begin
ALTER TABLE [dbo].[Usuario] DROP CONSTRAINT [DF_Usuario_Bloqueado]

End
GO
/****** Objeto:  Table [dbo].[Contrato]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Contrato]') AND type in (N'U'))
DROP TABLE [dbo].[Contrato]
GO
/****** Objeto:  Table [dbo].[Oferta]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Oferta]') AND type in (N'U'))
DROP TABLE [dbo].[Oferta]
GO
/****** Objeto:  Table [dbo].[Proyecto]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Proyecto]') AND type in (N'U'))
DROP TABLE [dbo].[Proyecto]
GO
/****** Objeto:  Table [dbo].[Usuario]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Usuario]') AND type in (N'U'))
DROP TABLE [dbo].[Usuario]
GO
/****** Objeto:  Table [dbo].[Calificacion]    Fecha de la secuencia de comandos: 05/25/2009 21:09:33 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Calificacion]') AND type in (N'U'))
DROP TABLE [dbo].[Calificacion]
GO
/****** Objeto:  Table [dbo].[Pais]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Pais]') AND type in (N'U'))
DROP TABLE [dbo].[Pais]
GO
/****** Objeto:  Table [dbo].[Moneda]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Moneda]') AND type in (N'U'))
DROP TABLE [dbo].[Moneda]
GO
/****** Objeto:  Table [dbo].[Moneda]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Moneda]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Moneda](
	[Nombre] [varchar](50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	[Conversion] [float] NOT NULL,
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Version] [int] NOT NULL,
 CONSTRAINT [PK_Moneda] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (IGNORE_DUP_KEY = OFF)
)
END
GO
SET IDENTITY_INSERT [dbo].[Moneda] ON
INSERT [dbo].[Moneda] ([Nombre], [Conversion], [Id], [Version]) VALUES (N'Peso', 1, 4, 1)
INSERT [dbo].[Moneda] ([Nombre], [Conversion], [Id], [Version]) VALUES (N'Dolar', 0.27, 5, 1)
INSERT [dbo].[Moneda] ([Nombre], [Conversion], [Id], [Version]) VALUES (N'Euro', 0.2, 6, 1)
INSERT [dbo].[Moneda] ([Nombre], [Conversion], [Id], [Version]) VALUES (N'Yen', 32, 7, 1)
INSERT [dbo].[Moneda] ([Nombre], [Conversion], [Id], [Version]) VALUES (N'Yuan', 2.25, 8, 1)
SET IDENTITY_INSERT [dbo].[Moneda] OFF
/****** Objeto:  Table [dbo].[Pais]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Pais]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Pais](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Version] [int] NULL,
	[Nombre] [varchar](50) COLLATE Modern_Spanish_CI_AS NOT NULL,
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
/****** Objeto:  Table [dbo].[Calificacion]    Fecha de la secuencia de comandos: 05/25/2009 21:09:33 ******/
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
INSERT [dbo].[Calificacion] ([Id], [Version], [Calificacion], [Comentario]) VALUES (12, 0, 8, N'')
INSERT [dbo].[Calificacion] ([Id], [Version], [Calificacion], [Comentario]) VALUES (13, 0, 9, N'')
INSERT [dbo].[Calificacion] ([Id], [Version], [Calificacion], [Comentario]) VALUES (14, 0, 10, N'')
INSERT [dbo].[Calificacion] ([Id], [Version], [Calificacion], [Comentario]) VALUES (15, 0, 9, N'')
INSERT [dbo].[Calificacion] ([Id], [Version], [Calificacion], [Comentario]) VALUES (16, 0, 8, N'')
INSERT [dbo].[Calificacion] ([Id], [Version], [Calificacion], [Comentario]) VALUES (17, 0, 10, N'')
INSERT [dbo].[Calificacion] ([Id], [Version], [Calificacion], [Comentario]) VALUES (18, 0, 9, N'')
INSERT [dbo].[Calificacion] ([Id], [Version], [Calificacion], [Comentario]) VALUES (19, 0, 8, N'')
INSERT [dbo].[Calificacion] ([Id], [Version], [Calificacion], [Comentario]) VALUES (20, 0, 7, N'')
INSERT [dbo].[Calificacion] ([Id], [Version], [Calificacion], [Comentario]) VALUES (21, 0, 2, N'')
SET IDENTITY_INSERT [dbo].[Calificacion] OFF
/****** Objeto:  Table [dbo].[Usuario]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Usuario]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Usuario](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Version] [int] NOT NULL,
	[Nombre] [varchar](50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	[Apellido] [varchar](50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	[Email] [varchar](255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	[Login] [varchar](50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	[PasswordHash] [varchar](32) COLLATE Modern_Spanish_CI_AS NOT NULL,
	[Pais] [int] NOT NULL,
	[Ciudad] [varchar](50) COLLATE Traditional_Spanish_CI_AS NOT NULL,
	[CodPostal] [varchar](50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	[DescripPerfil] [varchar](max) COLLATE Modern_Spanish_CI_AS NULL,
	[PathLogo] [varchar](255) COLLATE Modern_Spanish_CI_AS NULL,
	[Nivel] [varchar](50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	[Bloqueado] [bit] NOT NULL,
 CONSTRAINT [PK_Usuario] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (IGNORE_DUP_KEY = OFF)
)
END
GO
SET IDENTITY_INSERT [dbo].[Usuario] ON
INSERT [dbo].[Usuario] ([Id], [Version], [Nombre], [Apellido], [Email], [Login], [PasswordHash], [Pais], [Ciudad], [CodPostal], [DescripPerfil], [PathLogo], [Nivel], [Bloqueado]) VALUES (4, 19, N'Ariel', N'Piechotka', N'arielpie@hotmail.com', N'ariel', N'4900d0a19b6894a4a514e9ff3afcc2c0', 1, N'Lujan', N'2222', N'Ing. Informatico y Electronico', NULL, N'Premium', 0)
INSERT [dbo].[Usuario] ([Id], [Version], [Nombre], [Apellido], [Email], [Login], [PasswordHash], [Pais], [Ciudad], [CodPostal], [DescripPerfil], [PathLogo], [Nivel], [Bloqueado]) VALUES (5, 46, N'Cecilia', N'Hagge', N'chechuhagge@hotmail.com', N'chechu', N'd9e9516de4a32264146ecb0ed0b5dde2', 1, N'Avellaneda', N'1111', N'Ing Informatica', NULL, N'Normal', 0)
INSERT [dbo].[Usuario] ([Id], [Version], [Nombre], [Apellido], [Email], [Login], [PasswordHash], [Pais], [Ciudad], [CodPostal], [DescripPerfil], [PathLogo], [Nivel], [Bloqueado]) VALUES (6, 0, N'asd', N'das', N'asd@as.com', N'asd', N'cc7a1b7d45671ade36f4998e1a485571', 1, N'Capital Federal', N'sio', N'', NULL, N'Normal', 0)
INSERT [dbo].[Usuario] ([Id], [Version], [Nombre], [Apellido], [Email], [Login], [PasswordHash], [Pais], [Ciudad], [CodPostal], [DescripPerfil], [PathLogo], [Nivel], [Bloqueado]) VALUES (8, 0, N'Marcio', N'Degiovannini', N'marcio@marcio.com.ar', N'marcio', N'1b150854805cbe12194c8dbc55c900cd', 1, N'Castelar', N'1712', N'', NULL, N'Normal', 0)
INSERT [dbo].[Usuario] ([Id], [Version], [Nombre], [Apellido], [Email], [Login], [PasswordHash], [Pais], [Ciudad], [CodPostal], [DescripPerfil], [PathLogo], [Nivel], [Bloqueado]) VALUES (12, 0, N'dsa', N'dasdsa', N'asd@d.com', N'osdfi', N'869806bf9fdf7663bb4760df1f7648e7', 1, N'fsiuoh', N'mmm', N'', NULL, N'Normal', 0)
SET IDENTITY_INSERT [dbo].[Usuario] OFF
/****** Objeto:  Table [dbo].[Proyecto]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Proyecto]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Proyecto](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Version] [int] NOT NULL,
	[Nombre] [varchar](50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	[PresupuestoMax] [int] NOT NULL,
	[PresupuestoMin] [int] NOT NULL,
	[Nivel] [varchar](50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	[Dificultad] [varchar](50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	[Tamanio] [varchar](50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	[Descripcion] [varchar](50) COLLATE Modern_Spanish_CI_AS NULL,
	[PathArchivo] [varchar](255) COLLATE Modern_Spanish_CI_AS NULL,
	[Usuario] [int] NOT NULL,
	[Fecha] [datetime] NULL,
	[Moneda] [int] NOT NULL,
	[Cancelado] [bit] NOT NULL,
	[Revisado] [bit] NOT NULL,
	[CanceladoAdmin] [bit] NOT NULL,
	[Destacado] [bit] NOT NULL,
 CONSTRAINT [PK_Proyecto] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (IGNORE_DUP_KEY = OFF)
)
END
GO
SET IDENTITY_INSERT [dbo].[Proyecto] ON
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (1, 3, N'CivilizAlgo', 5000, 2000, N'Normal', N'Medio', N'Chico', N'', N'', 4, CAST(0x00009C1A00000000 AS DateTime), 4, 0, 1, 0, 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (2, 9, N'Hoteles Maximo', 2000, 1000, N'Normal', N'Complejo', N'Chico', N'Administrar hotel', N'', 5, CAST(0x00009C1A00000000 AS DateTime), 4, 0, 1, 0, 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (3, 2, N'Webalia', 10000, 5000, N'Premium', N'Medio', N'Chico', N'', N'', 5, CAST(0x00009C1A00000000 AS DateTime), 4, 0, 1, 0, 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (4, 7, N'HyperMarket', -1, 100000, N'Normal', N'Simple', N'Mediano', N'Administrar supermercado', N'', 5, CAST(0x00009C1A00000000 AS DateTime), 4, 0, 1, 0, 1)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (5, 2, N'Galgo3', 50000, 10000, N'Normal', N'Complejo', N'Grande', N'Juego de galaxias', N'', 5, CAST(0x00009C1A00000000 AS DateTime), 4, 0, 1, 0, 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (15, 6, N'Hero', 10000, 5000, N'Premium', N'Simple', N'Mediano', N'Juego de héroes', N'', 4, CAST(0x00009C1A00000000 AS DateTime), 4, 0, 1, 0, 1)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (16, 6, N'Movies', 5000, 2000, N'Premium', N'Simple', N'Chico', N'Studio', N'Archivo63cce5db7ac51da005c5825c8017cd94', 4, CAST(0x00009C1A00000000 AS DateTime), 4, 1, 1, 0, 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (17, 1, N'lala', 500, 100, N'Normal', N'Simple', N'Chico', N'lalala', NULL, 5, CAST(0x00009C0401104559 AS DateTime), 4, 0, 0, 0, 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (18, 0, N'PruebaNivel', 270, 135, N'Normal', N'Simple', N'Chico', N'', N'', 5, CAST(0x00009C0F00000000 AS DateTime), 5, 0, 0, 0, 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (19, 6, N'PruebaNuevo', 5000, 2000, N'Normal', N'Simple', N'Chico', N'', N'', 5, CAST(0x00009C1A00000000 AS DateTime), 4, 0, 1, 0, 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (20, 7, N'PruebaNuevo 2', 540, 270, N'Normal', N'Simple', N'Mediano', N'', N'', 5, CAST(0x00009C1E00000000 AS DateTime), 5, 0, 0, 0, 1)
SET IDENTITY_INSERT [dbo].[Proyecto] OFF
/****** Objeto:  Table [dbo].[Oferta]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
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
	[Moneda] [int] NOT NULL,
 CONSTRAINT [PK_Oferta] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (IGNORE_DUP_KEY = OFF)
)
END
GO
SET IDENTITY_INSERT [dbo].[Oferta] ON
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario], [Moneda]) VALUES (200, 10, N'', N'Si', 2, 1, 0, 5, 4)
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario], [Moneda]) VALUES (800, 15, N'Oferta de prueba', N'Si', 3, 2, 0, 4, 4)
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario], [Moneda]) VALUES (3000, 20, N'Soy experto', N'No', 4, 3, 0, 4, 4)
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario], [Moneda]) VALUES (20000, 60, N'', N'Si', 5, 5, 0, 4, 4)
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario], [Moneda]) VALUES (200, 10, N'Holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', N'Si', 8, 4, 0, 4, 4)
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario], [Moneda]) VALUES (20, 3, N'', N'Si', 9, 4, 0, 4, 4)
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario], [Moneda]) VALUES (3000, 60, N'Se considera un alcance más corto', N'Si', 14, 15, 0, 5, 4)
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario], [Moneda]) VALUES (12, 12, N'', N'No', 15, 16, 0, 5, 4)
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario], [Moneda]) VALUES (500, 12, N'', N'No', 16, 19, 0, 4, 4)
SET IDENTITY_INSERT [dbo].[Oferta] OFF
/****** Objeto:  Table [dbo].[Contrato]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
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
INSERT [dbo].[Contrato] ([Id], [Version], [Proyecto], [Oferta], [CalifVendedor], [CalifComprador]) VALUES (2, 4, 1, 2, 17, 12)
INSERT [dbo].[Contrato] ([Id], [Version], [Proyecto], [Oferta], [CalifVendedor], [CalifComprador]) VALUES (3, 4, 2, 3, 13, 18)
INSERT [dbo].[Contrato] ([Id], [Version], [Proyecto], [Oferta], [CalifVendedor], [CalifComprador]) VALUES (4, 4, 3, 4, 14, 19)
INSERT [dbo].[Contrato] ([Id], [Version], [Proyecto], [Oferta], [CalifVendedor], [CalifComprador]) VALUES (5, 4, 5, 5, 15, 20)
INSERT [dbo].[Contrato] ([Id], [Version], [Proyecto], [Oferta], [CalifVendedor], [CalifComprador]) VALUES (6, 2, 4, 8, 16, NULL)
INSERT [dbo].[Contrato] ([Id], [Version], [Proyecto], [Oferta], [CalifVendedor], [CalifComprador]) VALUES (7, 1, 15, 14, 21, NULL)
INSERT [dbo].[Contrato] ([Id], [Version], [Proyecto], [Oferta], [CalifVendedor], [CalifComprador]) VALUES (8, 0, 19, 16, NULL, NULL)
SET IDENTITY_INSERT [dbo].[Contrato] OFF
/****** Objeto:  Default [DF_Proyecto_Cancelado]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF Not EXISTS (SELECT * FROM sys.default_constraints WHERE object_id = OBJECT_ID(N'[dbo].[DF_Proyecto_Cancelado]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
Begin
ALTER TABLE [dbo].[Proyecto] ADD  CONSTRAINT [DF_Proyecto_Cancelado]  DEFAULT ((0)) FOR [Cancelado]

End
GO
/****** Objeto:  Default [DF_Proyecto_Revisado]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF Not EXISTS (SELECT * FROM sys.default_constraints WHERE object_id = OBJECT_ID(N'[dbo].[DF_Proyecto_Revisado]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
Begin
ALTER TABLE [dbo].[Proyecto] ADD  CONSTRAINT [DF_Proyecto_Revisado]  DEFAULT ((0)) FOR [Revisado]

End
GO
/****** Objeto:  Default [DF_Proyecto_CanceladoAdmin]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF Not EXISTS (SELECT * FROM sys.default_constraints WHERE object_id = OBJECT_ID(N'[dbo].[DF_Proyecto_CanceladoAdmin]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
Begin
ALTER TABLE [dbo].[Proyecto] ADD  CONSTRAINT [DF_Proyecto_CanceladoAdmin]  DEFAULT ((0)) FOR [CanceladoAdmin]

End
GO
/****** Objeto:  Default [DF_Usuario_Nivel]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF Not EXISTS (SELECT * FROM sys.default_constraints WHERE object_id = OBJECT_ID(N'[dbo].[DF_Usuario_Nivel]') AND parent_object_id = OBJECT_ID(N'[dbo].[Usuario]'))
Begin
ALTER TABLE [dbo].[Usuario] ADD  CONSTRAINT [DF_Usuario_Nivel]  DEFAULT ('Normal') FOR [Nivel]

End
GO
/****** Objeto:  Default [DF_Usuario_Bloqueado]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF Not EXISTS (SELECT * FROM sys.default_constraints WHERE object_id = OBJECT_ID(N'[dbo].[DF_Usuario_Bloqueado]') AND parent_object_id = OBJECT_ID(N'[dbo].[Usuario]'))
Begin
ALTER TABLE [dbo].[Usuario] ADD  CONSTRAINT [DF_Usuario_Bloqueado]  DEFAULT ((0)) FOR [Bloqueado]

End
GO
/****** Objeto:  ForeignKey [FK_Contrato_Calificacion]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Calificacion]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato]  WITH CHECK ADD  CONSTRAINT [FK_Contrato_Calificacion] FOREIGN KEY([CalifComprador])
REFERENCES [dbo].[Calificacion] ([Id])
GO
/****** Objeto:  ForeignKey [FK_Contrato_Calificacion1]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Calificacion1]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato]  WITH CHECK ADD  CONSTRAINT [FK_Contrato_Calificacion1] FOREIGN KEY([CalifVendedor])
REFERENCES [dbo].[Calificacion] ([Id])
GO
/****** Objeto:  ForeignKey [FK_Contrato_Oferta]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Oferta]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato]  WITH CHECK ADD  CONSTRAINT [FK_Contrato_Oferta] FOREIGN KEY([Oferta])
REFERENCES [dbo].[Oferta] ([Id])
GO
/****** Objeto:  ForeignKey [FK_Contrato_Proyecto]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato]  WITH CHECK ADD  CONSTRAINT [FK_Contrato_Proyecto] FOREIGN KEY([Proyecto])
REFERENCES [dbo].[Proyecto] ([Id])
GO
/****** Objeto:  ForeignKey [FK_Oferta_Moneda]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Moneda]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta]  WITH CHECK ADD  CONSTRAINT [FK_Oferta_Moneda] FOREIGN KEY([Moneda])
REFERENCES [dbo].[Moneda] ([Id])
GO
/****** Objeto:  ForeignKey [FK_Oferta_Proyecto]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta]  WITH CHECK ADD  CONSTRAINT [FK_Oferta_Proyecto] FOREIGN KEY([Proyecto])
REFERENCES [dbo].[Proyecto] ([Id])
GO
/****** Objeto:  ForeignKey [FK_Oferta_Usuario]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta]  WITH CHECK ADD  CONSTRAINT [FK_Oferta_Usuario] FOREIGN KEY([Usuario])
REFERENCES [dbo].[Usuario] ([Id])
GO
/****** Objeto:  ForeignKey [FK_Proyecto_Moneda]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Proyecto_Moneda]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
ALTER TABLE [dbo].[Proyecto]  WITH CHECK ADD  CONSTRAINT [FK_Proyecto_Moneda] FOREIGN KEY([Moneda])
REFERENCES [dbo].[Moneda] ([Id])
GO
/****** Objeto:  ForeignKey [FK_Proyecto_Usuario]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Proyecto_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
ALTER TABLE [dbo].[Proyecto]  WITH CHECK ADD  CONSTRAINT [FK_Proyecto_Usuario] FOREIGN KEY([Usuario])
REFERENCES [dbo].[Usuario] ([Id])
GO
/****** Objeto:  ForeignKey [FK_Usuario_Pais]    Fecha de la secuencia de comandos: 05/25/2009 21:09:34 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Usuario_Pais]') AND parent_object_id = OBJECT_ID(N'[dbo].[Usuario]'))
ALTER TABLE [dbo].[Usuario]  WITH CHECK ADD  CONSTRAINT [FK_Usuario_Pais] FOREIGN KEY([Pais])
REFERENCES [dbo].[Pais] ([Id])
GO
