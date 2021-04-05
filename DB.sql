-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: event_management
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `budgets`
--

DROP TABLE IF EXISTS `budgets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `budgets` (
  `event_reference_no` varchar(25) DEFAULT NULL,
  `particulars` varchar(45) DEFAULT NULL,
  `amount` int DEFAULT NULL,
  UNIQUE KEY `composite` (`event_reference_no`,`particulars`),
  KEY `event_reference_no_idx` (`event_reference_no`),
  CONSTRAINT `event_reference` FOREIGN KEY (`event_reference_no`) REFERENCES `events` (`event_reference_no`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budgets`
--

LOCK TABLES `budgets` WRITE;
/*!40000 ALTER TABLE `budgets` DISABLE KEYS */;
INSERT INTO `budgets` VALUES ('E001','banner',5000),('E009','refreshment',1000),('E009','banner',200);
/*!40000 ALTER TABLE `budgets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `events` (
  `department_name` varchar(50) DEFAULT NULL,
  `applied_date` date DEFAULT NULL,
  `event_reference_no` varchar(45) NOT NULL,
  `event_title` varchar(45) DEFAULT NULL,
  `event_type` varchar(45) DEFAULT NULL,
  `event_level` varchar(45) DEFAULT NULL,
  `no_of_days` int DEFAULT NULL,
  `iqac_aegis` varchar(10) DEFAULT NULL,
  `sponsoring_agency` varchar(5) DEFAULT NULL,
  `collaborators` varchar(5) DEFAULT NULL,
  `from_date` date DEFAULT NULL,
  `to_date` date DEFAULT NULL,
  `venue` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`event_reference_no`),
  UNIQUE KEY `composite` (`event_title`,`from_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
INSERT INTO `events` VALUES ('MCA','2021-03-24','E001','Symposium','inter college event','college level',1,'yes','yes','no','2021-03-25','2021-03-25','gurunanak college'),('civil','2021-03-24','E009','Civilans','inter college event','college',2,'no','no','no','2021-03-26','2021-03-26','Auditorium');
/*!40000 ALTER TABLE `events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guests`
--

DROP TABLE IF EXISTS `guests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guests` (
  `event_reference_no` varchar(45) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `designation` varchar(45) DEFAULT NULL,
  `organization` varchar(45) DEFAULT NULL,
  `about` varchar(500) DEFAULT NULL,
  UNIQUE KEY `composite` (`event_reference_no`,`name`),
  KEY `event_reference_no_idx` (`event_reference_no`),
  CONSTRAINT `event_reference_no` FOREIGN KEY (`event_reference_no`) REFERENCES `events` (`event_reference_no`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guests`
--

LOCK TABLES `guests` WRITE;
/*!40000 ALTER TABLE `guests` DISABLE KEYS */;
INSERT INTO `guests` VALUES ('E001','developer','dhass','senior software engineer','tcs','He is Senior Software Engineer at TCS.'),('E009','Judge','gautham','Designer','Oracle',NULL),('E001','Security','Sriram','Analyst','Primefort','He is a Security Analyst in Primefort');
/*!40000 ALTER TABLE `guests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `photos`
--

DROP TABLE IF EXISTS `photos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `photos` (
  `event_reference_no` varchar(25) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  KEY `fk_photos_1_idx` (`event_reference_no`),
  CONSTRAINT `fk_photos_1` FOREIGN KEY (`event_reference_no`) REFERENCES `events` (`event_reference_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `photos`
--

LOCK TABLES `photos` WRITE;
/*!40000 ALTER TABLE `photos` DISABLE KEYS */;
INSERT INTO `photos` VALUES ('E001','/photos/E001/guru_nanak_logo.jpeg'),('E001','/photos/E001/leaf_banner_violet.png'),('E001','/photos/E001/leaf_banner_violet.png'),('E001','/photos/E001/0f33e4bd48b4e8a3ad85bdb2f5f69ac3.jpg'),('E009','/photos/E009/logo_guru.jpeg'),('E009','/photos/E009/0f33e4bd48b4e8a3ad85bdb2f5f69ac3.jpg'),('E001','/photos/E001/guru_nanak_logo.jpeg');
/*!40000 ALTER TABLE `photos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-05 11:28:30
