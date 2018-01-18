-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: system
-- ------------------------------------------------------
-- Server version	5.7.20-0ubuntu0.16.04.1

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
-- Table structure for table `sys_department`
--

DROP TABLE IF EXISTS `sys_department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `full_path` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `location_code` varchar(255) DEFAULT NULL,
  `department_code` varchar(255) DEFAULT NULL,
  `parent` tinyint(1) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modifier` varchar(255) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `version` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_department`
--

LOCK TABLES `sys_department` WRITE;
/*!40000 ALTER TABLE `sys_department` DISABLE KEYS */;
INSERT INTO `sys_department` VALUES (52,'00000068','集团','00000068','集团',NULL,NULL,1,'启用',NULL,NULL,NULL,NULL,0),(53,'00000069','CEO','00000068-00000069','集团-CEO','00000001','00000068',1,'启用',NULL,NULL,NULL,NULL,0),(54,'00000070','COO','00000068-00000070','集团-COO','00000001','00000068',1,'启用',NULL,NULL,NULL,NULL,0),(55,'00000071','CTO','00000068-00000071','集团-CTO','00000001','00000068',1,'启用',NULL,NULL,NULL,NULL,0),(56,'00000072','CFO','00000068-00000072','集团-CFO','00000001','00000068',1,'启用',NULL,NULL,NULL,NULL,0),(57,'00000073','CHO','00000068-00000073','集团-CHO','00000001','00000068',0,'启用',NULL,NULL,NULL,NULL,0),(58,'00000074','Lib','00000068-00000071-00000074','集团-CTO-Lib','00000001','00000071',1,'启用',NULL,NULL,NULL,NULL,0),(59,'00000075','Account','00000068-00000072-00000075','集团-CFO-Account','00000001','00000072',0,'启用',NULL,NULL,NULL,NULL,0),(60,'00000076','主任1','00000068-00000070-00000076','集团-COO-主任1','00000001','00000070',1,'启用',NULL,NULL,NULL,NULL,0),(63,'00000079','v12','00000068-00000079','集团-v12','00000001','00000069',1,'启用',NULL,NULL,NULL,NULL,0),(64,'00000080','CMO','00000068-00000080','集团-CMO','00000001','00000068',0,'启用',NULL,NULL,NULL,NULL,0),(66,'00000084','v11','00000068-00000069-00000084','集团-CEO-v11','00000001','00000079',0,'启用',NULL,NULL,NULL,'2018-01-17 00:37:31',2),(67,'00000085','aaa','00000068-00000085','集团-aaa','00000001','00000068',0,'启用',NULL,NULL,NULL,NULL,0),(68,'00000086','bbb','00000086','bbb',NULL,NULL,0,'启用',NULL,NULL,NULL,NULL,0),(69,'00000087','ccc','00000087','ccc',NULL,NULL,0,'启用',NULL,NULL,NULL,NULL,0),(70,'00000088','dddd','00000068-00000069-00000084-00000088','集团-CEO-v11-dddd','00000001','00000084',0,'删除',NULL,NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `sys_department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_location`
--

DROP TABLE IF EXISTS `sys_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modifier` varchar(255) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `version` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_location`
--

LOCK TABLES `sys_location` WRITE;
/*!40000 ALTER TABLE `sys_location` DISABLE KEYS */;
INSERT INTO `sys_location` VALUES (1,'00000001','CCPARK','启用',NULL,NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `sys_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_part_time`
--

DROP TABLE IF EXISTS `sys_part_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_part_time` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(255) DEFAULT NULL,
  `position_code` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modifier` varchar(255) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `version` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_part_time`
--

LOCK TABLES `sys_part_time` WRITE;
/*!40000 ALTER TABLE `sys_part_time` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_part_time` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_position`
--

DROP TABLE IF EXISTS `sys_position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_position` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `department_code` varchar(255) DEFAULT NULL,
  `position_code` varchar(255) DEFAULT NULL,
  `manager` tinyint(1) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modifier` varchar(255) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `version` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_position`
--

LOCK TABLES `sys_position` WRITE;
/*!40000 ALTER TABLE `sys_position` DISABLE KEYS */;
INSERT INTO `sys_position` VALUES (1,'00000001','机构负责人','00000068',NULL,1,'启用',NULL,NULL,NULL,NULL,0),(2,'00000002','岗位负责人','00000069',NULL,0,'启用',NULL,NULL,NULL,NULL,0),(3,'00000003','岗位负责人','00000068',NULL,0,'启用',NULL,NULL,NULL,NULL,0),(4,'00000004','岗位负责人','00000071','00000003',1,'启用',NULL,NULL,NULL,NULL,0),(5,'00000005','岗位负责人','00000072',NULL,1,'启用',NULL,NULL,NULL,NULL,0),(6,'00000006','岗位负责人','00000073',NULL,1,'启用',NULL,NULL,NULL,NULL,0),(7,'00000007','aaa','00000069','00000003',0,'启用',NULL,NULL,NULL,NULL,0),(8,'00000008','aaa',NULL,NULL,0,'启用',NULL,NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `sys_position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_resource`
--

DROP TABLE IF EXISTS `sys_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `parent_code` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modifier` varchar(255) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `version` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_resource`
--

LOCK TABLES `sys_resource` WRITE;
/*!40000 ALTER TABLE `sys_resource` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_resource_position`
--

DROP TABLE IF EXISTS `sys_resource_position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_resource_position` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `resource_code` varchar(255) NOT NULL,
  `position_code` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_resource_position`
--

LOCK TABLES `sys_resource_position` WRITE;
/*!40000 ALTER TABLE `sys_resource_position` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_resource_position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `birthday` varchar(255) DEFAULT NULL,
  `entry_day` varchar(255) DEFAULT NULL,
  `regular_day` varchar(255) DEFAULT NULL,
  `leave_day` varchar(255) DEFAULT NULL,
  `department_code` varchar(255) DEFAULT NULL,
  `position_code` varchar(255) DEFAULT NULL,
  `head_img` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modifier` varchar(255) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `version` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-18 23:43:53