-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: sign_up
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `checkin_case`
--

DROP TABLE IF EXISTS `checkin_case`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checkin_case` (
  `checkin_id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `course_id` varchar(20) DEFAULT NULL,
  `teacher_id` varchar(20) DEFAULT NULL,
  `checkin_node` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`checkin_id`),
  UNIQUE KEY `checkin_node` (`checkin_node`),
  KEY `teacher_id` (`teacher_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `checkin_case_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`teacher_id`),
  CONSTRAINT `checkin_case_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`),
  CONSTRAINT `checkin_node_length` CHECK ((length(`checkin_node`) = 8))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkin_case`
--

LOCK TABLES `checkin_case` WRITE;
/*!40000 ALTER TABLE `checkin_case` DISABLE KEYS */;
INSERT INTO `checkin_case` VALUES (1,'2023-12-22 20:33:13','001','001','m702HJT3'),(2,'2023-12-23 20:33:13','001','001','m2323h13');
/*!40000 ALTER TABLE `checkin_case` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkin_record`
--

DROP TABLE IF EXISTS `checkin_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checkin_record` (
  `checkin_id` int NOT NULL AUTO_INCREMENT,
  `student_id` varchar(20) NOT NULL,
  `checkin_time` datetime DEFAULT NULL,
  `checkin_status` smallint DEFAULT NULL,
  PRIMARY KEY (`checkin_id`,`student_id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `checkin_record_ibfk_1` FOREIGN KEY (`checkin_id`) REFERENCES `checkin_case` (`checkin_id`),
  CONSTRAINT `checkin_record_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkin_record`
--

LOCK TABLES `checkin_record` WRITE;
/*!40000 ALTER TABLE `checkin_record` DISABLE KEYS */;
INSERT INTO `checkin_record` VALUES (1,'001','2023-12-22 20:39:09',1);
/*!40000 ALTER TABLE `checkin_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `course_id` varchar(20) NOT NULL,
  `course_name` varchar(100) DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES ('001','测试课程1','2023-12-21 14:00:00','2023-12-21 15:30:00');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `student_id` varchar(32) NOT NULL,
  `student_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `password` varchar(32) NOT NULL,
  PRIMARY KEY (`student_id`),
  CONSTRAINT `password_length_check` CHECK ((length(`password`) >= 6))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('001','admin','e10adc3949ba59abbe56e057f20f883e');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_course`
--

DROP TABLE IF EXISTS `student_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_course` (
  `student_id` varchar(20) NOT NULL,
  `course_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`student_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `student_course_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`),
  CONSTRAINT `student_course_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_course`
--

LOCK TABLES `student_course` WRITE;
/*!40000 ALTER TABLE `student_course` DISABLE KEYS */;
INSERT INTO `student_course` VALUES ('001','001');
/*!40000 ALTER TABLE `student_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher` (
  `teacher_id` varchar(32) NOT NULL,
  `teacher_name` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  PRIMARY KEY (`teacher_id`),
  CONSTRAINT `password_length` CHECK ((length(`password`) >= 6))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES ('001','admin','e10adc3949ba59abbe56e057f20f883e');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher_course`
--

DROP TABLE IF EXISTS `teacher_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher_course` (
  `teacher_id` varchar(20) NOT NULL,
  `course_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`teacher_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `teacher_course_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`teacher_id`),
  CONSTRAINT `teacher_course_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher_course`
--

LOCK TABLES `teacher_course` WRITE;
/*!40000 ALTER TABLE `teacher_course` DISABLE KEYS */;
INSERT INTO `teacher_course` VALUES ('001','001');
/*!40000 ALTER TABLE `teacher_course` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-24 16:48:00
