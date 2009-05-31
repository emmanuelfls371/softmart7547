USE [softmart]
GO
/****** Object:  ForeignKey [FK_Usuario_Pais]    Script Date: 05/31/2009 18:07:16 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Usuario_Pais]') AND parent_object_id = OBJECT_ID(N'[dbo].[Usuario]'))
ALTER TABLE [dbo].[Usuario] DROP CONSTRAINT [FK_Usuario_Pais]
GO
/****** Object:  ForeignKey [FK_Proyecto_Moneda]    Script Date: 05/31/2009 18:07:16 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Proyecto_Moneda]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
ALTER TABLE [dbo].[Proyecto] DROP CONSTRAINT [FK_Proyecto_Moneda]
GO
/****** Object:  ForeignKey [FK_Proyecto_Usuario]    Script Date: 05/31/2009 18:07:16 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Proyecto_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
ALTER TABLE [dbo].[Proyecto] DROP CONSTRAINT [FK_Proyecto_Usuario]
GO
/****** Object:  ForeignKey [FK_Oferta_Moneda]    Script Date: 05/31/2009 18:07:17 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Moneda]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta] DROP CONSTRAINT [FK_Oferta_Moneda]
GO
/****** Object:  ForeignKey [FK_Oferta_Proyecto]    Script Date: 05/31/2009 18:07:17 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta] DROP CONSTRAINT [FK_Oferta_Proyecto]
GO
/****** Object:  ForeignKey [FK_Oferta_Usuario]    Script Date: 05/31/2009 18:07:17 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta] DROP CONSTRAINT [FK_Oferta_Usuario]
GO
/****** Object:  ForeignKey [FK_Contrato_Calificacion]    Script Date: 05/31/2009 18:07:17 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Calificacion]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] DROP CONSTRAINT [FK_Contrato_Calificacion]
GO
/****** Object:  ForeignKey [FK_Contrato_Calificacion1]    Script Date: 05/31/2009 18:07:17 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Calificacion1]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] DROP CONSTRAINT [FK_Contrato_Calificacion1]
GO
/****** Object:  ForeignKey [FK_Contrato_Oferta]    Script Date: 05/31/2009 18:07:17 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Oferta]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] DROP CONSTRAINT [FK_Contrato_Oferta]
GO
/****** Object:  ForeignKey [FK_Contrato_Proyecto]    Script Date: 05/31/2009 18:07:17 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] DROP CONSTRAINT [FK_Contrato_Proyecto]
GO
/****** Object:  Table [dbo].[Contrato]    Script Date: 05/31/2009 18:07:17 ******/
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
/****** Object:  Table [dbo].[Oferta]    Script Date: 05/31/2009 18:07:17 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Moneda]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta] DROP CONSTRAINT [FK_Oferta_Moneda]
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta] DROP CONSTRAINT [FK_Oferta_Proyecto]
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta] DROP CONSTRAINT [FK_Oferta_Usuario]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Oferta]') AND type in (N'U'))
DROP TABLE [dbo].[Oferta]
GO
/****** Object:  Table [dbo].[Proyecto]    Script Date: 05/31/2009 18:07:16 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Proyecto_Moneda]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
ALTER TABLE [dbo].[Proyecto] DROP CONSTRAINT [FK_Proyecto_Moneda]
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Proyecto_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
ALTER TABLE [dbo].[Proyecto] DROP CONSTRAINT [FK_Proyecto_Usuario]
GO
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Proyecto_Cancelado]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Proyecto] DROP CONSTRAINT [DF_Proyecto_Cancelado]
END
GO
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Proyecto_Revisado]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Proyecto] DROP CONSTRAINT [DF_Proyecto_Revisado]
END
GO
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Proyecto_CanceladoAdmin]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Proyecto] DROP CONSTRAINT [DF_Proyecto_CanceladoAdmin]
END
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Proyecto]') AND type in (N'U'))
DROP TABLE [dbo].[Proyecto]
GO
/****** Object:  Table [dbo].[Usuario]    Script Date: 05/31/2009 18:07:16 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Usuario_Pais]') AND parent_object_id = OBJECT_ID(N'[dbo].[Usuario]'))
ALTER TABLE [dbo].[Usuario] DROP CONSTRAINT [FK_Usuario_Pais]
GO
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Usuario_Nivel]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Usuario] DROP CONSTRAINT [DF_Usuario_Nivel]
END
GO
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Usuario_Bloqueado]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Usuario] DROP CONSTRAINT [DF_Usuario_Bloqueado]
END
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Usuario]') AND type in (N'U'))
DROP TABLE [dbo].[Usuario]
GO
/****** Object:  Table [dbo].[Admin]    Script Date: 05/31/2009 18:07:16 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Admin]') AND type in (N'U'))
DROP TABLE [dbo].[Admin]
GO
/****** Object:  Table [dbo].[Calificacion]    Script Date: 05/31/2009 18:07:16 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Calificacion]') AND type in (N'U'))
DROP TABLE [dbo].[Calificacion]
GO
/****** Object:  Table [dbo].[Pais]    Script Date: 05/31/2009 18:07:16 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Pais]') AND type in (N'U'))
DROP TABLE [dbo].[Pais]
GO
/****** Object:  Table [dbo].[Moneda]    Script Date: 05/31/2009 18:07:16 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Moneda]') AND type in (N'U'))
DROP TABLE [dbo].[Moneda]
GO
/****** Object:  Table [dbo].[Moneda]    Script Date: 05/31/2009 18:07:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
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
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[Moneda] ON
INSERT [dbo].[Moneda] ([Nombre], [Conversion], [Id], [Version]) VALUES (N'Peso', 1, 4, 1)
INSERT [dbo].[Moneda] ([Nombre], [Conversion], [Id], [Version]) VALUES (N'Dolar', 0.27, 5, 1)
INSERT [dbo].[Moneda] ([Nombre], [Conversion], [Id], [Version]) VALUES (N'Euro', 0.2, 6, 1)
INSERT [dbo].[Moneda] ([Nombre], [Conversion], [Id], [Version]) VALUES (N'Yen', 32, 7, 1)
INSERT [dbo].[Moneda] ([Nombre], [Conversion], [Id], [Version]) VALUES (N'Yuan', 2.25, 8, 1)
SET IDENTITY_INSERT [dbo].[Moneda] OFF
/****** Object:  Table [dbo].[Pais]    Script Date: 05/31/2009 18:07:16 ******/
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
	[Nombre] [varchar](50) COLLATE Modern_Spanish_CI_AS NOT NULL,
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
/****** Object:  Table [dbo].[Calificacion]    Script Date: 05/31/2009 18:07:16 ******/
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
	[Comentario] [varchar](50) COLLATE Modern_Spanish_CI_AS NULL,
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
/****** Object:  Table [dbo].[Admin]    Script Date: 05/31/2009 18:07:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Admin]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Admin](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Login] [varchar](50) COLLATE Traditional_Spanish_CI_AS NOT NULL,
	[PasswordHash] [varchar](32) COLLATE Traditional_Spanish_CI_AS NOT NULL,
	[Version] [int] NOT NULL,
 CONSTRAINT [PK_Admin] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[Admin] ON
INSERT [dbo].[Admin] ([Id], [Login], [PasswordHash], [Version]) VALUES (1, N'arielmin', N'4900d0a19b6894a4a514e9ff3afcc2c0', 1)
INSERT [dbo].[Admin] ([Id], [Login], [PasswordHash], [Version]) VALUES (2, N'chechumin', N'd9e9516de4a32264146ecb0ed0b5dde2', 1)
SET IDENTITY_INSERT [dbo].[Admin] OFF
/****** Object:  Table [dbo].[Usuario]    Script Date: 05/31/2009 18:07:16 ******/
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
	[Nivel] [varchar](50) COLLATE Modern_Spanish_CI_AS NOT NULL CONSTRAINT [DF_Usuario_Nivel]  DEFAULT ('Normal'),
	[Bloqueado] [bit] NOT NULL CONSTRAINT [DF_Usuario_Bloqueado]  DEFAULT ((0)),
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
INSERT [dbo].[Usuario] ([Id], [Version], [Nombre], [Apellido], [Email], [Login], [PasswordHash], [Pais], [Ciudad], [CodPostal], [DescripPerfil], [PathLogo], [Nivel], [Bloqueado]) VALUES (4, 21, N'Ariel', N'Piechotka', N'piecho2k@gmail.com', N'ariel', N'4900d0a19b6894a4a514e9ff3afcc2c0', 1, N'Lujan', N'2222', N'Ing. Informatico y Electronico', NULL, N'Premium', 0)
INSERT [dbo].[Usuario] ([Id], [Version], [Nombre], [Apellido], [Email], [Login], [PasswordHash], [Pais], [Ciudad], [CodPostal], [DescripPerfil], [PathLogo], [Nivel], [Bloqueado]) VALUES (5, 47, N'Cecilia', N'Hagge', N'chechuhagge@hotmail.com', N'chechu', N'd9e9516de4a32264146ecb0ed0b5dde2', 1, N'Avellaneda', N'1111', N'Ing Informatica', NULL, N'Normal', 0)
INSERT [dbo].[Usuario] ([Id], [Version], [Nombre], [Apellido], [Email], [Login], [PasswordHash], [Pais], [Ciudad], [CodPostal], [DescripPerfil], [PathLogo], [Nivel], [Bloqueado]) VALUES (6, 0, N'asd', N'das', N'asd@as.com', N'asd', N'cc7a1b7d45671ade36f4998e1a485571', 1, N'Capital Federal', N'sio', N'', NULL, N'Normal', 0)
INSERT [dbo].[Usuario] ([Id], [Version], [Nombre], [Apellido], [Email], [Login], [PasswordHash], [Pais], [Ciudad], [CodPostal], [DescripPerfil], [PathLogo], [Nivel], [Bloqueado]) VALUES (8, 0, N'Marcio', N'Degiovannini', N'marcio@marcio.com.ar', N'marcio', N'1b150854805cbe12194c8dbc55c900cd', 1, N'Castelar', N'1712', N'', NULL, N'Normal', 0)
INSERT [dbo].[Usuario] ([Id], [Version], [Nombre], [Apellido], [Email], [Login], [PasswordHash], [Pais], [Ciudad], [CodPostal], [DescripPerfil], [PathLogo], [Nivel], [Bloqueado]) VALUES (12, 0, N'dsa', N'dasdsa', N'asd@d.com', N'osdfi', N'869806bf9fdf7663bb4760df1f7648e7', 1, N'fsiuoh', N'mmm', N'', NULL, N'Normal', 0)
INSERT [dbo].[Usuario] ([Id], [Version], [Nombre], [Apellido], [Email], [Login], [PasswordHash], [Pais], [Ciudad], [CodPostal], [DescripPerfil], [PathLogo], [Nivel], [Bloqueado]) VALUES (13, 4, N'a', N'b', N'c@c.com', N'd', N'8277e0910d750195b448797616e091ad', 1, N'd', N'd', N'', N'Logoec34d4711b096adb7c83318908ceecf8.gif', N'Normal', 0)
SET IDENTITY_INSERT [dbo].[Usuario] OFF
/****** Object:  Table [dbo].[Proyecto]    Script Date: 05/31/2009 18:07:16 ******/
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
	[Cancelado] [bit] NOT NULL CONSTRAINT [DF_Proyecto_Cancelado]  DEFAULT ((0)),
	[Revisado] [bit] NOT NULL CONSTRAINT [DF_Proyecto_Revisado]  DEFAULT ((0)),
	[CanceladoAdmin] [bit] NOT NULL CONSTRAINT [DF_Proyecto_CanceladoAdmin]  DEFAULT ((0)),
	[Destacado] [bit] NOT NULL,
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
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (1, 3, N'CivilizAlgo', 5000, 2000, N'Normal', N'Medio', N'Chico', N'', N'', 4, CAST(0x00009C1A00000000 AS DateTime), 4, 0, 1, 0, 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (2, 9, N'Hoteles Maximo', 2000, 1000, N'Normal', N'Complejo', N'Chico', N'Administrar hotel', N'', 5, CAST(0x00009C1A00000000 AS DateTime), 4, 0, 1, 0, 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (3, 2, N'Webalia', 10000, 5000, N'Premium', N'Medio', N'Chico', N'', N'', 5, CAST(0x00009C1A00000000 AS DateTime), 4, 0, 1, 0, 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (4, 10, N'HyperMarket', -1, 100000, N'Normal', N'Simple', N'Mediano', N'Administrar supermercado', N'', 5, CAST(0x00009C1A00000000 AS DateTime), 4, 0, 1, 0, 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (5, 2, N'Galgo3', 50000, 10000, N'Normal', N'Complejo', N'Grande', N'Juego de galaxias', N'', 5, CAST(0x00009C1A00000000 AS DateTime), 4, 0, 1, 0, 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (15, 7, N'Hero', 10000, 5000, N'Premium', N'Simple', N'Mediano', N'Juego de héroes', N'', 4, CAST(0x00009C1A00000000 AS DateTime), 4, 0, 1, 0, 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (16, 6, N'Movies', 5000, 2000, N'Premium', N'Simple', N'Chico', N'Studio', N'Archivo63cce5db7ac51da005c5825c8017cd94', 4, CAST(0x00009C1A00000000 AS DateTime), 4, 1, 1, 0, 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (17, 1, N'lala', 500, 100, N'Normal', N'Simple', N'Chico', N'lalala', NULL, 5, CAST(0x00009C0401104559 AS DateTime), 4, 0, 0, 0, 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (18, 0, N'PruebaNivel', 270, 135, N'Normal', N'Simple', N'Chico', N'', N'', 5, CAST(0x00009C0F00000000 AS DateTime), 5, 0, 0, 0, 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (19, 10, N'PruebaNuevo', 5000, 2000, N'Normal', N'Simple', N'Chico', N'', N'', 5, CAST(0x00009C1A00000000 AS DateTime), 4, 0, 1, 0, 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (20, 9, N'PruebaNuevo 2', 540, 270, N'Normal', N'Simple', N'Mediano', N'', N'', 5, CAST(0x00009C1E00000000 AS DateTime), 5, 0, 0, 1, 1)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (21, 10, N'prueba3', 540, 270, N'Normal', N'Simple', N'Chico', N'', N'', 5, CAST(0x00009C3400000000 AS DateTime), 5, 0, 1, 0, 0)
INSERT [dbo].[Proyecto] ([Id], [Version], [Nombre], [PresupuestoMax], [PresupuestoMin], [Nivel], [Dificultad], [Tamanio], [Descripcion], [PathArchivo], [Usuario], [Fecha], [Moneda], [Cancelado], [Revisado], [CanceladoAdmin], [Destacado]) VALUES (22, 1, N'asd', 500, 100, N'Normal', N'Simple', N'Mediano', N'', N'Archivo15343a5f285ee150e7e0b3298c968cf0.gif', 4, CAST(0x00009C2600000000 AS DateTime), 4, 0, 1, 0, 0)
SET IDENTITY_INSERT [dbo].[Proyecto] OFF
/****** Object:  Table [dbo].[Oferta]    Script Date: 05/31/2009 18:07:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Oferta]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Oferta](
	[Monto] [float] NOT NULL,
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
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_PADDING OFF
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
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario], [Moneda]) VALUES (10.300000190734863, 10, N'', N'Si', 17, 21, 0, 4, 5)
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario], [Moneda]) VALUES (3, 3, N'', N'Si', 18, 21, 0, 13, 5)
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario], [Moneda]) VALUES (4, 3, N'', N'Si', 19, 21, 0, 13, 5)
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario], [Moneda]) VALUES (5, 5, N'', N'Si', 20, 21, 0, 13, 5)
INSERT [dbo].[Oferta] ([Monto], [CantDias], [Descripcion], [Notificacion], [Id], [Proyecto], [Version], [Usuario], [Moneda]) VALUES (2, 2, N'2', N'Si', 21, 21, 0, 13, 5)
SET IDENTITY_INSERT [dbo].[Oferta] OFF
/****** Object:  Table [dbo].[Contrato]    Script Date: 05/31/2009 18:07:17 ******/
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
INSERT [dbo].[Contrato] ([Id], [Version], [Proyecto], [Oferta], [CalifVendedor], [CalifComprador]) VALUES (2, 4, 1, 2, 17, 12)
INSERT [dbo].[Contrato] ([Id], [Version], [Proyecto], [Oferta], [CalifVendedor], [CalifComprador]) VALUES (3, 4, 2, 3, 13, 18)
INSERT [dbo].[Contrato] ([Id], [Version], [Proyecto], [Oferta], [CalifVendedor], [CalifComprador]) VALUES (4, 4, 3, 4, 14, 19)
INSERT [dbo].[Contrato] ([Id], [Version], [Proyecto], [Oferta], [CalifVendedor], [CalifComprador]) VALUES (5, 4, 5, 5, 15, 20)
INSERT [dbo].[Contrato] ([Id], [Version], [Proyecto], [Oferta], [CalifVendedor], [CalifComprador]) VALUES (6, 2, 4, 8, 16, NULL)
INSERT [dbo].[Contrato] ([Id], [Version], [Proyecto], [Oferta], [CalifVendedor], [CalifComprador]) VALUES (7, 1, 15, 14, 21, NULL)
INSERT [dbo].[Contrato] ([Id], [Version], [Proyecto], [Oferta], [CalifVendedor], [CalifComprador]) VALUES (8, 0, 19, 16, NULL, NULL)
SET IDENTITY_INSERT [dbo].[Contrato] OFF
/****** Object:  ForeignKey [FK_Usuario_Pais]    Script Date: 05/31/2009 18:07:16 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Usuario_Pais]') AND parent_object_id = OBJECT_ID(N'[dbo].[Usuario]'))
ALTER TABLE [dbo].[Usuario]  WITH CHECK ADD  CONSTRAINT [FK_Usuario_Pais] FOREIGN KEY([Pais])
REFERENCES [dbo].[Pais] ([Id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Usuario_Pais]') AND parent_object_id = OBJECT_ID(N'[dbo].[Usuario]'))
ALTER TABLE [dbo].[Usuario] CHECK CONSTRAINT [FK_Usuario_Pais]
GO
/****** Object:  ForeignKey [FK_Proyecto_Moneda]    Script Date: 05/31/2009 18:07:16 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Proyecto_Moneda]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
ALTER TABLE [dbo].[Proyecto]  WITH CHECK ADD  CONSTRAINT [FK_Proyecto_Moneda] FOREIGN KEY([Moneda])
REFERENCES [dbo].[Moneda] ([Id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Proyecto_Moneda]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
ALTER TABLE [dbo].[Proyecto] CHECK CONSTRAINT [FK_Proyecto_Moneda]
GO
/****** Object:  ForeignKey [FK_Proyecto_Usuario]    Script Date: 05/31/2009 18:07:16 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Proyecto_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
ALTER TABLE [dbo].[Proyecto]  WITH CHECK ADD  CONSTRAINT [FK_Proyecto_Usuario] FOREIGN KEY([Usuario])
REFERENCES [dbo].[Usuario] ([Id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Proyecto_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Proyecto]'))
ALTER TABLE [dbo].[Proyecto] CHECK CONSTRAINT [FK_Proyecto_Usuario]
GO
/****** Object:  ForeignKey [FK_Oferta_Moneda]    Script Date: 05/31/2009 18:07:17 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Moneda]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta]  WITH CHECK ADD  CONSTRAINT [FK_Oferta_Moneda] FOREIGN KEY([Moneda])
REFERENCES [dbo].[Moneda] ([Id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Moneda]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta] CHECK CONSTRAINT [FK_Oferta_Moneda]
GO
/****** Object:  ForeignKey [FK_Oferta_Proyecto]    Script Date: 05/31/2009 18:07:17 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta]  WITH CHECK ADD  CONSTRAINT [FK_Oferta_Proyecto] FOREIGN KEY([Proyecto])
REFERENCES [dbo].[Proyecto] ([Id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta] CHECK CONSTRAINT [FK_Oferta_Proyecto]
GO
/****** Object:  ForeignKey [FK_Oferta_Usuario]    Script Date: 05/31/2009 18:07:17 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta]  WITH CHECK ADD  CONSTRAINT [FK_Oferta_Usuario] FOREIGN KEY([Usuario])
REFERENCES [dbo].[Usuario] ([Id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Oferta_Usuario]') AND parent_object_id = OBJECT_ID(N'[dbo].[Oferta]'))
ALTER TABLE [dbo].[Oferta] CHECK CONSTRAINT [FK_Oferta_Usuario]
GO
/****** Object:  ForeignKey [FK_Contrato_Calificacion]    Script Date: 05/31/2009 18:07:17 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Calificacion]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato]  WITH CHECK ADD  CONSTRAINT [FK_Contrato_Calificacion] FOREIGN KEY([CalifComprador])
REFERENCES [dbo].[Calificacion] ([Id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Calificacion]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] CHECK CONSTRAINT [FK_Contrato_Calificacion]
GO
/****** Object:  ForeignKey [FK_Contrato_Calificacion1]    Script Date: 05/31/2009 18:07:17 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Calificacion1]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato]  WITH CHECK ADD  CONSTRAINT [FK_Contrato_Calificacion1] FOREIGN KEY([CalifVendedor])
REFERENCES [dbo].[Calificacion] ([Id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Calificacion1]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] CHECK CONSTRAINT [FK_Contrato_Calificacion1]
GO
/****** Object:  ForeignKey [FK_Contrato_Oferta]    Script Date: 05/31/2009 18:07:17 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Oferta]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato]  WITH CHECK ADD  CONSTRAINT [FK_Contrato_Oferta] FOREIGN KEY([Oferta])
REFERENCES [dbo].[Oferta] ([Id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Oferta]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] CHECK CONSTRAINT [FK_Contrato_Oferta]
GO
/****** Object:  ForeignKey [FK_Contrato_Proyecto]    Script Date: 05/31/2009 18:07:17 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato]  WITH CHECK ADD  CONSTRAINT [FK_Contrato_Proyecto] FOREIGN KEY([Proyecto])
REFERENCES [dbo].[Proyecto] ([Id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contrato_Proyecto]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contrato]'))
ALTER TABLE [dbo].[Contrato] CHECK CONSTRAINT [FK_Contrato_Proyecto]
GO
