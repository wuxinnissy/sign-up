CREATE DATABASE  IF NOT EXISTS `sign_up` ;
USE `sign_up`;

CREATE TABLE teacher (
    teacher_id VARCHAR(32) NOT NULL,
    teacher_name VARCHAR(32) NOT NULL,
    password VARCHAR(32) NOT NULL,
    PRIMARY KEY (teacher_id),
    CONSTRAINT password_length CHECK (LENGTH(password) >= 6)
)CHARACTER SET utf8;

INSERT INTO sign_up.teacher (teacher_id, teacher_name, password) VALUES ('001', 'admin', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO sign_up.teacher (teacher_id, teacher_name, password) VALUES ('002', '李蒙', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO sign_up.teacher (teacher_id, teacher_name, password) VALUES ('003', '李金喜', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO sign_up.teacher (teacher_id, teacher_name, password) VALUES ('004', '樊钧', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO sign_up.teacher (teacher_id, teacher_name, password) VALUES ('005', '刘羽朦', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO sign_up.teacher (teacher_id, teacher_name, password) VALUES ('006', '赵天鸿', 'e10adc3949ba59abbe56e057f20f883e');

CREATE TABLE student (
    student_id VARCHAR(32) NOT NULL,
    student_name VARCHAR(32) CHARACTER SET utf8 NOT NULL,
    password VARCHAR(32) NOT NULL,
    PRIMARY KEY (student_id),
    CONSTRAINT password_length_check CHECK (LENGTH(password) >= 6)
)CHARACTER SET utf8;

INSERT INTO sign_up.student (student_id, student_name, password) VALUES ('001', 'admin', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO sign_up.student (student_id, student_name, password) VALUES ('002', '蔡领聪', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO sign_up.student (student_id, student_name, password) VALUES ('003', '张子权', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO sign_up.student (student_id, student_name, password) VALUES ('004', '谢鸿超', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO sign_up.student (student_id, student_name, password) VALUES ('005', '李永铖', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO sign_up.student (student_id, student_name, password) VALUES ('006', '郑霖', 'e10adc3949ba59abbe56e057f20f883e');

CREATE TABLE course (
    course_id VARCHAR(20) PRIMARY KEY,
    course_name VARCHAR(100),
    begin_time DATETIME,
    end_time DATETIME
)CHARACTER SET utf8;

INSERT INTO sign_up.course (course_id, course_name, begin_time, end_time) VALUES ('001', 'test', '2023-12-27 14:00:00', '2023-12-27 15:30:00');
INSERT INTO sign_up.course (course_id, course_name, begin_time, end_time) VALUES ('002', '嵌入式系统', '2023-12-27 14:00:00', '2023-12-27 15:30:00');
INSERT INTO sign_up.course (course_id, course_name, begin_time, end_time) VALUES ('003', '软件工程-理论', '2023-12-28 08:30:00', '2023-12-28 10:00:00');
INSERT INTO sign_up.course (course_id, course_name, begin_time, end_time) VALUES ('004', '软件工程-实验', '2023-12-28 10:20:00', '2023-12-28 11:50:00');
INSERT INTO sign_up.course (course_id, course_name, begin_time, end_time) VALUES ('005', '数据库系统-理论', '2023-12-28 14:00:00', '2023-12-28 15:30:00');
INSERT INTO sign_up.course (course_id, course_name, begin_time, end_time) VALUES ('006', '数据库系统-实验', '2023-12-28 15:50:00', '2023-12-28 17:20:00');
INSERT INTO sign_up.course (course_id, course_name, begin_time, end_time) VALUES ('007', '计算机网络-理论', '2023-12-29 10:20:00', '2023-12-29 11:50:00');
INSERT INTO sign_up.course (course_id, course_name, begin_time, end_time) VALUES ('008', '计算机网络-实验', '2023-12-29 14:00:00', '2023-12-29 17:20:00');
INSERT INTO sign_up.course (course_id, course_name, begin_time, end_time) VALUES ('009', '云计算技术-理论', '2023-12-29 18:50:00', '2023-12-29 20:10:00');
INSERT INTO sign_up.course (course_id, course_name, begin_time, end_time) VALUES ('010', '云计算技术-实验', '2023-12-29 20:30:00', '2023-12-29 21:50:00');

CREATE TABLE teacher_course (
    teacher_id VARCHAR(20),
    course_id VARCHAR(20),
    FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id),
    FOREIGN KEY (course_id) REFERENCES course(course_id)
    
)CHARACTER SET utf8;

INSERT INTO sign_up.teacher_course (teacher_id, course_id) VALUES ('001', '001');

INSERT INTO sign_up.teacher_course (teacher_id, course_id) VALUES ('002', '002');

INSERT INTO sign_up.teacher_course (teacher_id, course_id) VALUES ('003', '003');
INSERT INTO sign_up.teacher_course (teacher_id, course_id) VALUES ('003', '004');

INSERT INTO sign_up.teacher_course (teacher_id, course_id) VALUES ('004', '005');
INSERT INTO sign_up.teacher_course (teacher_id, course_id) VALUES ('004', '006');

INSERT INTO sign_up.teacher_course (teacher_id, course_id) VALUES ('005', '007');
INSERT INTO sign_up.teacher_course (teacher_id, course_id) VALUES ('005', '008');

INSERT INTO sign_up.teacher_course (teacher_id, course_id) VALUES ('006', '009');
INSERT INTO sign_up.teacher_course (teacher_id, course_id) VALUES ('006', '010');

CREATE TABLE student_course (
    student_id VARCHAR(20),
    course_id VARCHAR(20),
    FOREIGN KEY (student_id) REFERENCES student(student_id),
    FOREIGN KEY (course_id) REFERENCES course(course_id)
)CHARACTER SET utf8;

INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('001', '001');

INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('002', '002');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('002', '003');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('002', '004');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('002', '005');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('002', '006');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('002', '007');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('002', '008');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('002', '009');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('002', '010');

INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('003', '002');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('003', '003');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('003', '004');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('003', '005');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('003', '006');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('003', '007');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('003', '008');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('003', '009');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('003', '010');

INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('004', '002');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('004', '003');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('004', '004');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('004', '005');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('004', '006');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('004', '007');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('004', '008');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('004', '009');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('004', '010');

INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('005', '002');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('005', '003');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('005', '004');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('005', '005');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('005', '006');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('005', '007');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('005', '008');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('005', '009');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('005', '010');

INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('006', '002');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('006', '003');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('006', '004');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('006', '005');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('006', '006');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('006', '007');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('006', '008');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('006', '009');
INSERT INTO sign_up.student_course (student_id, course_id) VALUES ('006', '010');


CREATE TABLE checkin_case (
    checkin_id INT AUTO_INCREMENT,
    create_time DATETIME,
    course_id VARCHAR(20),
    teacher_id VARCHAR(20),
    checkin_node VARCHAR(8),
    PRIMARY KEY(checkin_id),
    UNIQUE(checkin_node),
    CONSTRAINT checkin_node_length CHECK (LENGTH(checkin_node)=8),
    FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id),
    FOREIGN KEY (course_id) REFERENCES course(course_id)
)CHARACTER SET utf8;

INSERT INTO checkin_case VALUES (1,'2023-12-22 20:33:13','001','001','m702HJT3');

CREATE TABLE checkin_record (
    checkin_id INT AUTO_INCREMENT,
    student_id VARCHAR(20),
    checkin_time DATETIME,
    checkin_status SMALLINT,
    PRIMARY KEY (checkin_id, student_id),
    FOREIGN KEY (checkin_id) REFERENCES checkin_case(checkin_id),
    FOREIGN KEY (student_id) REFERENCES student(student_id)
)CHARACTER SET utf8;

INSERT INTO checkin_record VALUES (1,'001','2023-12-22 20:39:09',1);