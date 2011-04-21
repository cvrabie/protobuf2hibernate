-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 21, 2011 at 05:07 PM
-- Server version: 5.1.44
-- PHP Version: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `p2h`
--

-- --------------------------------------------------------

--
-- Table structure for table `cats`
--

CREATE TABLE IF NOT EXISTS `cats` (
  `uuid` char(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `created` int(11) NOT NULL,
  `hairLength` float NOT NULL,
  `hairType` varchar(255) DEFAULT NULL,
  `owner` blob,
  PRIMARY KEY (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `cats_kittens`
--

CREATE TABLE IF NOT EXISTS `cats_kittens` (
  `cat_uuid` char(36) NOT NULL,
  `val` char(255) NOT NULL,
  KEY `cat_uuid` (`cat_uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `cat_friends`
--

CREATE TABLE IF NOT EXISTS `cat_friends` (
  `cat_uuid` char(36) NOT NULL,
  `friend_uuid` char(36) NOT NULL,
  PRIMARY KEY (`cat_uuid`,`friend_uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `cat_toys`
--

CREATE TABLE IF NOT EXISTS `cat_toys` (
  `cat_uuid` char(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  KEY `cat_uuid` (`cat_uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `dinos`
--

CREATE TABLE IF NOT EXISTS `dinos` (
  `uuid` char(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `bulk` blob,
  PRIMARY KEY (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `dogs`
--

CREATE TABLE IF NOT EXISTS `dogs` (
  `uuid` char(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `dogs_puppies`
--

CREATE TABLE IF NOT EXISTS `dogs_puppies` (
  `dog_uuid` char(36) NOT NULL,
  `indx` int(11) NOT NULL,
  `val` varchar(255) NOT NULL,
  KEY `dog_uuid` (`dog_uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
