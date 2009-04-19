USE [softmart]
GO
/****** Object:  ForeignKey [FK_Ciudad_Pais]    Script Date: 04/19/2009 14:38:14 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Ciudad_Pais]') AND parent_object_id = OBJECT_ID(N'[dbo].[Ciudad]'))
ALTER TABLE [dbo].[Ciudad] DROP CONSTRAINT [FK_Ciudad_Pais]
GO
/****** Object:  ForeignKey [FK_Contrato_Calificacion]    Script Date: 04/19/2009 14:38:14 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Calificacion]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] DROP CONSTRAINT [FK_Contrato_Calificacion]
GO
/****** Object:  ForeignKey [FK_Contrato_Calificacion1]    Script Date: 04/19/2009 14:38:14 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Calificacion1]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] DROP CONSTRAINT [FK_Contrato_Calificacion1]
GO
/****** Object:  ForeignKey [FK_Contrato_Oferta]    Script Date: 04/19/2009 14:38:14 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Oferta]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] DROP CONSTRAINT [FK_Contrato_Oferta]
GO
/****** Object:  ForeignKey [FK_Contrato_Proyecto]    Script Date: 04/19/2009 14:38:14 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] DROP CONSTRAINT [FK_Contrato_Proyecto]
GO
/****** Object:  ForeignKey [FK_Oferta_Proyecto]    Script Date: 04/19/2009 14:38:14 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta] DROP CONSTRAINT [FK_Oferta_Proyecto]
GO
/****** Object:  ForeignKey [FK_Oferta_Usuario]    Script Date: 04/19/2009 14:38:14 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta] DROP CONSTRAINT [FK_Oferta_Usuario]
GO
/****** Object:  ForeignKey [FK_Proyecto_Usuario]    Script Date: 04/19/2009 14:38:14 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Proyecto_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
ALTER TABLE [dbo].[Proyecto] DROP CONSTRAINT [FK_Proyecto_Usuario]
GO
/****** Object:  ForeignKey [FK_Usuario_Ciudad]    Script Date: 04/19/2009 14:38:14 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Usuario_Ciudad]') AND parent_object_id = OBJECT_ID(N'[dbo].[Usuario]'))
ALTER TABLE [dbo].[Usuario] DROP CONSTRAINT [FK_Usuario_Ciudad]
GO
/****** Object:  Table [dbo].[Contrato]    Script Date: 04/19/2009 14:38:14 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Calificacion]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] DROP CONSTRAINT [FK_Contrato_Calificacion]
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Calificacion1]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] DROP CONSTRAINT [FK_Contrato_Calificacion1]
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Oferta]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] DROP CONSTRAINT [FK_Contrato_Oferta]
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] DROP CONSTRAINT [FK_Contrato_Proyecto]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Contrato]') AND type in (N'U'))
DROP TABLE [dbo].[Contrato]
GO
/****** Object:  Table [dbo].[Oferta]    Script Date: 04/19/2009 14:38:14 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta] DROP CONSTRAINT [FK_Oferta_Proyecto]
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta] DROP CONSTRAINT [FK_Oferta_Usuario]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Oferta]') AND type in (N'U'))
DROP TABLE [dbo].[Oferta]
GO
/****** Object:  Table [dbo].[Proyecto]    Script Date: 04/19/2009 14:38:14 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Proyecto_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
ALTER TABLE [dbo].[Proyecto] DROP CONSTRAINT [FK_Proyecto_Usuario]
GO
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Proyecto_Cancelado]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Proyecto] DROP CONSTRAINT [DF_Proyecto_Cancelado]
END
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Proyecto]') AND type in (N'U'))
DROP TABLE [dbo].[Proyecto]
GO
/****** Object:  Table [dbo].[Usuario]    Script Date: 04/19/2009 14:38:14 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Usuario_Ciudad]') AND parent_object_id = OBJECT_ID(N'[dbo].[Usuario]'))
ALTER TABLE [dbo].[Usuario] DROP CONSTRAINT [FK_Usuario_Ciudad]
GO
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Usuario_Nivel]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Usuario] DROP CONSTRAINT [DF_Usuario_Nivel]
END
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Usuario]') AND type in (N'U'))
DROP TABLE [dbo].[Usuario]
GO
/****** Object:  Table [dbo].[Ciudad]    Script Date: 04/19/2009 14:38:14 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Ciudad_Pais]') AND parent_object_id = OBJECT_ID(N'[dbo].[Ciudad]'))
ALTER TABLE [dbo].[Ciudad] DROP CONSTRAINT [FK_Ciudad_Pais]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Ciudad]') AND type in (N'U'))
DROP TABLE [dbo].[Ciudad]
GO
/****** Object:  Table [dbo].[Calificacion]    Script Date: 04/19/2009 14:38:14 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Calificacion]') AND type in (N'U'))
DROP TABLE [dbo].[Calificacion]
GO
/****** Object:  Table [dbo].[Pais]    Script Date: 04/19/2009 14:38:14 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Pais]') AND type in (N'U'))
DROP TABLE [dbo].[Pais]
GO
/****** Object:  Table [dbo].[Pais]    Script Date: 04/19/2009 14:38:14 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Pais]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Pais](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Version] [int] NULL,
	[Nombre] [varchar](50) NOT NULL,
 CONSTRAINT [PK_Pais] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[Pais] ON
INSERT [dbo].[Pais] ([Id], [Version], [Nombre]) VALUES (1, 1, N'Argentina')
SET IDENTITY_INSERT [dbo].[Pais] OFF
/****** Object:  Table [dbo].[Calificacion]    Script Date: 04/19/2009 14:38:14 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Calificacion]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Calificacion](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Version] [int] NOT NULL,
	[Calificacion] [int] NOT NULL,
	[Comentario] [varchar](50) NULL,
 CONSTRAINT [PK_Calificacion] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[Calificacion] ON
INSERT [dbo].[Calificacion] ([Id], [Version], [Calificacion], [Comentario]) VALUES (3, 0, 10, N'Excelente!')
INSERT [dbo].[Calificacion] ([Id], [Version], [Calificacion], [Comentario]) VALUES (4, 0, 8, N'Muy bueno')
INSERT [dbo].[Calificacion] ([Id], [Version], [Calificacion], [Comentario]) VALUES (5, 0, 8, N'Muy bueno')
INSERT [dbo].[Calificacion] ([Id], [Version], [Calificacion], [Comentario]) VALUES (6, 0, 2, N'anduvo mal')
INSERT [dbo].[Calificacion] ([Id], [Version], [Calificacion], [Comentario]) VALUES (7, 0, 5, N'malo')
INSERT [dbo].[Calificacion] ([Id], [Version], [Calificacion], [Comentario]) VALUES (8, 0, 9, N'bieeen')
INSERT [dbo].[Calificacion] ([Id], [Version], [Calificacion], [Comentario]) VALUES (9, 0, 3, N'mal')
INSERT [dbo].[Calificacion] ([Id], [Version], [Calificacion], [Comentario]) VALUES (10, 0, 6, N'maso')
SET IDENTITY_INSERT [dbo].[Calificacion] OFF
/****** Object:  Table [dbo].[Ciudad]    Script Date: 04/19/2009 14:38:14 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Ciudad]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Ciudad](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Version] [int] NOT NULL,
	[Nombre] [varchar](70) NOT NULL,
	[Pais] [int] NOT NULL,
 CONSTRAINT [PK_Ciudad] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[Ciudad] ON
INSERT [dbo].[Ciudad] ([Id], [Version], [Nombre], [Pais]) VALUES (2, 1, N'Buenos Aires', 1)
SET IDENTITY_INSERT [dbo].[Ciudad] OFF
/****** Object:  Table [dbo].[Usuario]    Script Date: 04/19/2009 14:38:14 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Usuario]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Usuario](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Version] [int] NOT NULL,
	[Nombre] [varchar](50) NOT NULL,
	[Apellido] [varchar](50) NOT NULL,
	[Email] [varchar](255) NOT NULL,
	[Login] [varchar](50) NOT NULL,
	[PasswordHash] [varchar](32) NOT NULL,
	[Ciudad] [int] NOT NULL,
	[CodPostal] [varchar](50) NOT NULL,
	[DescripPerfil] [varchar](max) NULL,
	[PathLogo] [varchar](255) NULL,
	[Nivel] [varchar](50) NOT NULL CONSTRAINT [DF_Usuario_Nivel]  DEFAULT ('Normal'),
 CONSTRAINT [PK_Usuario] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[Usuario] ON
INSERT [dbo].[Usuario] ([Id], [Version], [Nombre], [Apellido], [Email], [Login], [PasswordHash], [Ciudad], [CodPostal], [DescripPerfil], [PathLogo], [Nivel]) VALUES (4, 12, N'Ariel', N'Piechotka', N'arielpie@hotmail.com', N'ariel', N'4900d0a19b6894a4a514e9ff3afcc2c0', 2, N'2222', N'Ing. Informatico y Electronico', NULL, N'Normal')
INSERT [dbo].[Usuario] ([Id], [Version], [Nombre], [Apellido], [Email], [Login], [PasswordHash], [Ciudad], [CodPostal], [DescripPerfil], [PathLogo], [Nivel]) VALUES (5, 11, N'Cecilia', N'Hagge', N'chechuhagge@hotmail.com', N'chechu', N'd9e9516de4a32264146ecb0ed0b5dde2', 2, N'1111', N'Ing Informatica', NULL, N'Normal')
SET IDENTITY_INSERT [dbo].[Usuario] OFF
/****** Object:  Table [dbo].[Proyecto]    Script Date: 04/19/2009 14:38:14 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Proyecto]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Proyecto](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Version] [int] NOT NULL,
	[Nombre] [varchar](50) NOT NULL,
	[PresupuestoMax] [int] NOT NULL,
	[PresupuestoMin] [int] NOT NULL,
	[Nivel] [varchar](50) NOT NULL,
	[Dificultad] [varchar](50) NOT NULL,
	[Tamanio] [varchar](50) NOT NULL,
	[Descripcion] [varchar](50) NULL,
	[PathArchivo] [varchar](255) NULL,
	[Usuario] [int] NOT NULL,
	[Fecha] [datetime] NULL,
	[Moneda] [varchar](50) NOT NULL,
	[Cancelado] [bit] NOT NULL CONSTRAINT [DF_Proyecto_Cancelado]  DEFAULT ((0)),
 CONSTRAINT [PK_Proyecto] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[Proyecto] ON
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado]) VALUES (1, 2, N'CivilizAlgo', 5000, 2000, N'Normal', N'Medio', N'Chico', N'', N'', 4, CAST(0x00009BFA00000000 AS DateTime), N'Peso', 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado]) VALUES (2, 1, N'Hoteles Maximo', 2000, 1000, N'Normal', N'Complejo', N'Chico', N'Administrar hotel', N'', 5, CAST(0x00009BF300000000 AS DateTime), N'Peso', 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado]) VALUES (3, 1, N'Webalia', 10000, 5000, N'Premium', N'Medio', N'Chico', N'', N'', 5, CAST(0x00009BF000000000 AS DateTime), N'Peso', 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado]) VALUES (4, 2, N'HyperMarket', -1, 100000, N'Normal', N'Simple', N'Mediano', N'Administrar supermercado', N'', 5, CAST(0x00009BFB00000000 AS DateTime), N'Peso', 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado]) VALUES (5, 1, N'Galgo3', 50000, 10000, N'Normal', N'Complejo', N'Grande', N'Juego de galaxias', N'', 5, CAST(0x00009C1600000000 AS DateTime), N'Peso', 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado]) VALUES (15, 1, N'Hero', 10000, 5000, N'Premium', N'Simple', N'Mediano', N'Juego de héroes', N'', 4, CAST(0x00009BED00000000 AS DateTime), N'Euro', 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado]) VALUES (16, 1, N'Movies', 5000, 2000, N'Premium', N'Simple', N'Chico', N'Studio', N'Archivo63cce5db7ac51da005c5825c8017cd94', 4, CAST(0x00009BFB00000000 AS DateTime), N'Yuan', 0)
SET IDENTITY_INSERT [dbo].[Proyecto] OFF
/****** Object:  Table [dbo].[Oferta]    Script Date: 04/19/2009 14:38:14 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Oferta]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Oferta](
	[Monto] [int] NOT NULL,
	[CantDias] [int] NOT NULL,
	[Descripcion] [varchar](50) NULL,
	[Notificacion] [varchar](2) NOT NULL,
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Proyecto] [int] NOT NULL,
	[Version] [int] NULL,
	[Usuario] [int] NOT NULL,
	[Moneda] [varchar](50) NOT NULL,
 CONSTRAINT [PK_Oferta] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[Oferta] ON
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario], [Moneda]) VALUES (200, 10, N'', N'Si', 2, 1, 0, 5, N'Peso')
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario], [Moneda]) VALUES (800, 15, N'Oferta de prueba', N'Si', 3, 2, 0, 4, N'Peso')
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario], [Moneda]) VALUES (3000, 20, N'Soy experto', N'No', 4, 3, 0, 4, N'Peso')
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario], [Moneda]) VALUES (20000, 60, N'', N'Si', 5, 5, 0, 4, N'Peso')
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario], [Moneda]) VALUES (200, 10, N'Holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', N'Si', 8, 4, 0, 4, N'Peso')
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario], [Moneda]) VALUES (20, 3, N'', N'Si', 9, 4, 0, 4, N'Peso')
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario], [Moneda]) VALUES (3000, 60, N'Se considera un alcance más corto', N'Si', 14, 15, 0, 5, N'Euro')
SET IDENTITY_INSERT [dbo].[Oferta] OFF
/****** Object:  Table [dbo].[Contrato]    Script Date: 04/19/2009 14:38:14 ******/
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
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET IDENTITY_INSERT [dbo].[Contrato] ON
INSERT [dbo].[Contrato] ([Id], [Version], [Proyecto], [Oferta], [CalifVendedor], [CalifComprador]) VALUES (2, 2, 1, 2, 3, 7)
INSERT [dbo].[Contrato] ([Id], [Version], [Proyecto], [Oferta], [CalifVendedor], [CalifComprador]) VALUES (3, 2, 2, 3, 8, 9)
INSERT [dbo].[Contrato] ([Id], [Version], [Proyecto], [Oferta], [CalifVendedor], [CalifComprador]) VALUES (4, 2, 3, 4, 4, 5)
INSERT [dbo].[Contrato] ([Id], [Version], [Proyecto], [Oferta], [CalifVendedor], [CalifComprador]) VALUES (5, 2, 5, 5, 6, 10)
SET IDENTITY_INSERT [dbo].[Contrato] OFF
/****** Object:  ForeignKey [FK_Ciudad_Pais]    Script Date: 04/19/2009 14:38:14 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Ciudad_Pais]') AND parent_object_id = OBJECT_ID(N'[dbo].[Ciudad]'))
ALTER TABLE [dbo].[Ciudad]  WITH CHECK ADD  CONSTRAINT [FK_Ciudad_Pais] FOREIGN KEY([Pais])
REFERENCES [dbo].[Pais] ([Id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Ciudad_Pais]') AND parent_object_id = OBJECT_ID(N'[dbo].[Ciudad]'))
ALTER TABLE [dbo].[Ciudad] CHECK CONSTRAINT [FK_Ciudad_Pais]
GO
/****** Object:  ForeignKey [FK_Contrato_Calificacion]    Script Date: 04/19/2009 14:38:14 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Calificacion]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato]  WITH CHECK ADD  CONSTRAINT [FK_Contrato_Calificacion] FOREIGN KEY([CalifComprador])
REFERENCES [dbo].[Calificacion] ([Id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Calificacion]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] CHECK CONSTRAINT [FK_Contrato_Calificacion]
GO
/****** Object:  ForeignKey [FK_Contrato_Calificacion1]    Script Date: 04/19/2009 14:38:14 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Calificacion1]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato]  WITH CHECK ADD  CONSTRAINT [FK_Contrato_Calificacion1] FOREIGN KEY([CalifVendedor])
REFERENCES [dbo].[Calificacion] ([Id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Calificacion1]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] CHECK CONSTRAINT [FK_Contrato_Calificacion1]
GO
/****** Object:  ForeignKey [FK_Contrato_Oferta]    Script Date: 04/19/2009 14:38:14 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Oferta]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato]  WITH CHECK ADD  CONSTRAINT [FK_Contrato_Oferta] FOREIGN KEY([Oferta])
REFERENCES [dbo].[Oferta] ([Id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Oferta]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] CHECK CONSTRAINT [FK_Contrato_Oferta]
GO
/****** Object:  ForeignKey [FK_Contrato_Proyecto]    Script Date: 04/19/2009 14:38:14 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato]  WITH CHECK ADD  CONSTRAINT [FK_Contrato_Proyecto] FOREIGN KEY([Proyecto])
REFERENCES [dbo].[Proyecto] ([Id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] CHECK CONSTRAINT [FK_Contrato_Proyecto]
GO
/****** Object:  ForeignKey [FK_Oferta_Proyecto]    Script Date: 04/19/2009 14:38:14 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta]  WITH CHECK ADD  CONSTRAINT [FK_Oferta_Proyecto] FOREIGN KEY([Proyecto])
REFERENCES [dbo].[Proyecto] ([Id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta] CHECK CONSTRAINT [FK_Oferta_Proyecto]
GO
/****** Object:  ForeignKey [FK_Oferta_Usuario]    Script Date: 04/19/2009 14:38:14 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta]  WITH CHECK ADD  CONSTRAINT [FK_Oferta_Usuario] FOREIGN KEY([Usuario])
REFERENCES [dbo].[Usuario] ([Id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta] CHECK CONSTRAINT [FK_Oferta_Usuario]
GO
/****** Object:  ForeignKey [FK_Proyecto_Usuario]    Script Date: 04/19/2009 14:38:14 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Proyecto_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
ALTER TABLE [dbo].[Proyecto]  WITH CHECK ADD  CONSTRAINT [FK_Proyecto_Usuario] FOREIGN KEY([Usuario])
REFERENCES [dbo].[Usuario] ([Id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Proyecto_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
ALTER TABLE [dbo].[Proyecto] CHECK CONSTRAINT [FK_Proyecto_Usuario]
GO
/****** Object:  ForeignKey [FK_Usuario_Ciudad]    Script Date: 04/19/2009 14:38:14 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Usuario_Ciudad]') AND parent_object_id = OBJECT_ID(N'[dbo].[Usuario]'))
ALTER TABLE [dbo].[Usuario]  WITH CHECK ADD  CONSTRAINT [FK_Usuario_Ciudad] FOREIGN KEY([Ciudad])
REFERENCES [dbo].[Ciudad] ([Id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Usuario_Ciudad]') AND parent_object_id = OBJECT_ID(N'[dbo].[Usuario]'))
ALTER TABLE [dbo].[Usuario] CHECK CONSTRAINT [FK_Usuario_Ciudad]
GO
