CREATE DATABASE  IF NOT EXISTS `sign_up` ;
USE `sign_up`;

CREATE TABLE teacher (
    teacherId VARCHAR(32) NOT NULL,
    teacherName VARCHAR(32) NOT NULL,
    password VARCHAR(32) NOT NULL,
    PRIMARY KEY (teacherId),
    CONSTRAINT password_length CHECK (LENGTH(password) >= 6)
);

INSERT INTO sign_up.teacher (teacherId, teacherName, password) VALUES ('001', 'admin', 'e10adc3949ba59abbe56e057f20f883e')