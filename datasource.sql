CREATE DATABASE  IF NOT EXISTS `sign_up` ;
USE `sign_up`;

CREATE TABLE teacher (
    teacher_id VARCHAR(32) NOT NULL,
    teacher_name VARCHAR(32) NOT NULL,
    password VARCHAR(32) NOT NULL,
    PRIMARY KEY (teacher_id),
    CONSTRAINT password_length CHECK (LENGTH(password) >= 6)
);

INSERT INTO sign_up.teacher (teacher_id, teacher_name, password) VALUES ('001', 'admin', 'e10adc3949ba59abbe56e057f20f883e')

CREATE TABLE student (
    student_id VARCHAR(32) NOT NULL,
    student_name VARCHAR(32) NOT NULL,
    password VARCHAR(32) NOT NULL,
    PRIMARY KEY (student_id),
    CONSTRAINT password_length_check CHECK (LENGTH(password) >= 6)
);

INSERT INTO sign_up.student (student_id, student_name, password) VALUES ('001', 'admin', 'e10adc3949ba59abbe56e057f20f883e')

CREATE TABLE teacher_course (
    teacher_id VARCHAR(20),
    course_id VARCHAR(20),
    PRIMARY KEY (teacher_id),
    FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id)
);

INSERT INTO sign_up.teacher_course (teacher_id, course_id) VALUES ('001', '001')

CREATE TABLE student_course (
    student_id VARCHAR(20),
    course_id VARCHAR(20),
    PRIMARY KEY (student_id),
    FOREIGN KEY (student_id) REFERENCES student(student_id)
);

INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('001', '001')

CREATE TABLE course (
    course_id VARCHAR(20) PRIMARY KEY,
    course_name VARCHAR(100),
    begin_time DATETIME,
    end_time DATETIME
);

INSERT INTO sign_up.course (course_id, course_name, begin_time, end_time) VALUES ('001', '测试课程1', '2023-12-21 14:00:00', '2023-12-21 15:30:00')