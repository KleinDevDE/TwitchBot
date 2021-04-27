package de.kleindev.twitchbot.objects.databases.base.objects;

public enum ColumnType {
    TINYINT,
    SMALLINT,
    MEDIUMINT,
    INT,
    BIGINT,
    BIT,
    FLOAT,
    DOUBLE,
    DECIMAL,
    CHAR,
    VARCHAR,
    TINYTEXT,
    TEXT,
    MEDIUMTEXT, //TODO Erro: missing key length
    LONGTEXT,
    JSON,
    BINARY,
    VARBINARY,
    TINYBLOB,
    BLOB,
    MEDIUMBLOB,
    LONGBLOB,
    DATE,
    TIME,
    YEAR,
    DATETIME,
    TIMESTAMP,
    POINT,
    LINESTRING,
    POLYGON,
    GEOMETRY,
    MULTIPOINT,
    MULTILINESTRING,
    MULTIPOLYGON,
    GEOMETRYCOLLECTION,
    UNKNOWN,
    ENUM,
    SET;
}
