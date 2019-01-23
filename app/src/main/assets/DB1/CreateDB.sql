-- это коменнтарий!
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

DROP TABLE IF EXISTS tProduct;

CREATE TABLE tProduct
(
    ProductID      INTEGER       PRIMARY KEY AUTOINCREMENT,
    ProductGroupID INTEGER       NOT NULL,
    Name           VARCHAR (100) NOT NULL
);


DROP TABLE IF EXISTS tProductGroup;

CREATE TABLE tProductGroup (
    ProductGroup INTEGER       PRIMARY KEY AUTOINCREMENT,
    Name         VARCHAR (100) NOT NULL
);


DROP TABLE IF EXISTS tSession;

CREATE TABLE tSession (
    SessionID INTEGER  PRIMARY KEY AUTOINCREMENT,
    UserID    INTEGER  NOT NULL,
    StartDate DATETIME NOT NULL
);


DROP TABLE IF EXISTS tUser;

CREATE TABLE tUser (
    UserID    INTEGER      NOT NULL
                           PRIMARY KEY AUTOINCREMENT,
    FirstName  VARCHAR (50) NOT NULL,
    MiddleName VARCHAR (50) NOT NULL,
    LastName   VARCHAR (50) NOT NULL
);


DROP TABLE IF EXISTS tWork;

CREATE TABLE tWork (
    WorkID    INTEGER         PRIMARY KEY AUTOINCREMENT,
    SessionID INTEGER         NOT NULL,
    ProductID INTEGER         NOT NULL,
    Qty       NUMERIC (12, 2) NOT NULL,
    InDate    DATETIME
);


DROP INDEX IF EXISTS XAK01tSession;

CREATE UNIQUE INDEX XAK01tSession ON tSession (
    StartDate,
    UserID
);


DROP INDEX IF EXISTS XPKSession;

CREATE UNIQUE INDEX XPKSession ON tSession (
    SessionID
);


DROP INDEX IF EXISTS XPKtProduct;

CREATE UNIQUE INDEX XPKtProduct ON tProduct (
    ProductID
);


DROP INDEX IF EXISTS XPKtProductGroup;

CREATE UNIQUE INDEX XPKtProductGroup ON tProductGroup (
    ProductGroup
);


DROP INDEX IF EXISTS XPKtSession;

CREATE UNIQUE INDEX XPKtSession ON tSession (
    SessionID
);


DROP INDEX IF EXISTS XPKtUser;

CREATE UNIQUE INDEX XPKtUser ON tUser (
    UserID
);


DROP INDEX IF EXISTS XPKtWork;

CREATE UNIQUE INDEX XPKtWork ON tWork (
    WorkID
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
