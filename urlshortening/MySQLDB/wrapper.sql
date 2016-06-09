-- MySQL dump 10.13  Distrib 5.5.46, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: urlshortener
-- ------------------------------------------------------
-- Server version	5.5.46-0ubuntu0.14.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `applicationsettings`
--
CREATE DATABASE urlshortener;
USE urlshortener;

DROP TABLE IF EXISTS `applicationsettings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `applicationsettings` (
  `keyname` varchar(255) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`keyname`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `applicationsettings`
--

LOCK TABLES `applicationsettings` WRITE;
/*!40000 ALTER TABLE `applicationsettings` DISABLE KEYS */;
INSERT INTO `applicationsettings` VALUES ('checkurl','disabled'),('pagenotfoundurl','http://www.snapdeal.com'),('rooturl','52.0.9.28/'),('imsip','http://ims-stage2-external-128503578.ap-southeast-1.elb.amazonaws.com/'),('imsport','443'),('imsclientkey','1@bp6&yrum6&'),('imsclientid','97B6E8D3D2A8EFFB'),('imstimeout','20000'),('userMachineIdentifier','mobileapi');
/*!40000 ALTER TABLE `applicationsettings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `urldetails`
--

DROP TABLE IF EXISTS `urldetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `urldetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shortenedurl` varchar(255) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`url`),
  UNIQUE KEY `shortenedurl` (`shortenedurl`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `urldetails`
--

LOCK TABLES `urldetails` WRITE;
/*!40000 ALTER TABLE `urldetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `urldetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `urlhitcount`
--

DROP TABLE IF EXISTS `urlhitcount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `urlhitcount` (
  `shortenedurl` varchar(255) NOT NULL,
  `count` int(11) DEFAULT NULL,
  PRIMARY KEY (`shortenedurl`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `urlhitcount`
--

LOCK TABLES `urlhitcount` WRITE;
/*!40000 ALTER TABLE `urlhitcount` DISABLE KEYS */;
/*!40000 ALTER TABLE `urlhitcount` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-26 11:50:05-- MySQL dump 10.13  Distrib 5.5.46, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: urlshortener
-- ------------------------------------------------------
-- Server version	5.5.46-0ubuntu0.14.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `applicationsettings`
--
CREATE DATABASE urlshortener;
USE urlshortener;

DROP TABLE IF EXISTS `applicationsettings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `applicationsettings` (
  `keyname` varchar(255) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`keyname`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `applicationsettings`
--

LOCK TABLES `applicationsettings` WRITE;
/*!40000 ALTER TABLE `applicationsettings` DISABLE KEYS */;
INSERT INTO `applicationsettings` VALUES ('checkurl','disabled'),('pagenotfoundurl','http://www.snapdeal.com'),('rooturl','52.0.9.28/'),('imsip','http://ims-stage2-external-128503578.ap-southeast-1.elb.amazonaws.com/'),('imsport','443'),('imsclientkey','1@bp6&yrum6&'),('imsclientid','97B6E8D3D2A8EFFB'),('imstimeout','20000'),('userMachineIdentifier','mobileapi');
/*!40000 ALTER TABLE `applicationsettings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `urldetails`
--

DROP TABLE IF EXISTS `urldetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `urldetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shortenedurl` varchar(255) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`url`),
  UNIQUE KEY `shortenedurl` (`shortenedurl`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `urldetails`
--

LOCK TABLES `urldetails` WRITE;
/*!40000 ALTER TABLE `urldetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `urldetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `urlhitcount`
--

DROP TABLE IF EXISTS `urlhitcount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `urlhitcount` (
  `shortenedurl` varchar(255) NOT NULL,
  `count` int(11) DEFAULT NULL,
  PRIMARY KEY (`shortenedurl`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `urlhitcount`
--

LOCK TABLES `urlhitcount` WRITE;
/*!40000 ALTER TABLE `urlhitcount` DISABLE KEYS */;
/*!40000 ALTER TABLE `urlhitcount` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-26 11:50:05-- MySQL dump 10.13  Distrib 5.5.46, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: urlshortener
-- ------------------------------------------------------
-- Server version	5.5.46-0ubuntu0.14.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `applicationsettings`
--
CREATE DATABASE urlshortener;
USE urlshortener;

DROP TABLE IF EXISTS `applicationsettings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `applicationsettings` (
  `keyname` varchar(255) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`keyname`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `applicationsettings`
--

LOCK TABLES `applicationsettings` WRITE;
/*!40000 ALTER TABLE `applicationsettings` DISABLE KEYS */;
INSERT INTO `applicationsettings` VALUES ('checkurl','disabled'),('pagenotfoundurl','http://www.snapdeal.com'),('rooturl','52.0.9.28/'),('imsip','http://ims-stage2-external-128503578.ap-southeast-1.elb.amazonaws.com/'),('imsport','443'),('imsclientkey','1@bp6&yrum6&'),('imsclientid','97B6E8D3D2A8EFFB'),('imstimeout','20000'),('userMachineIdentifier','mobileapi');
/*!40000 ALTER TABLE `applicationsettings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `urldetails`
--

DROP TABLE IF EXISTS `urldetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `urldetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shortenedurl` varchar(255) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`url`),
  UNIQUE KEY `shortenedurl` (`shortenedurl`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `urldetails`
--

LOCK TABLES `urldetails` WRITE;
/*!40000 ALTER TABLE `urldetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `urldetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `urlhitcount`
--

DROP TABLE IF EXISTS `urlhitcount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `urlhitcount` (
  `shortenedurl` varchar(255) NOT NULL,
  `count` int(11) DEFAULT NULL,
  PRIMARY KEY (`shortenedurl`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `urlhitcount`
--

LOCK TABLES `urlhitcount` WRITE;
/*!40000 ALTER TABLE `urlhitcount` DISABLE KEYS */;
/*!40000 ALTER TABLE `urlhitcount` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-26 11:50:05
