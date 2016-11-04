-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 02, 2016 at 05:49 AM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `project`
--

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `LoginId` int(12) NOT NULL,
  `Email` varchar(32) NOT NULL,
  `Username` varchar(16) NOT NULL,
  `Password` varchar(32) NOT NULL,
  `firstname` varchar(32) NOT NULL,
  `lastname` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `stage1`
--

CREATE TABLE `stage1` (
  `LoginId` int(12) NOT NULL,
  `LFirstName` varchar(30) DEFAULT NULL,
  `LLastName` varchar(30) DEFAULT NULL,
  `SSN` varchar(40) DEFAULT NULL,
  `Address` varchar(60) DEFAULT NULL,
  `City` varchar(30) DEFAULT NULL,
  `Zipcode` int(5) DEFAULT NULL,
  `State` varchar(2) DEFAULT NULL,
  `LastChange` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`LoginId`),
  ADD UNIQUE KEY `LoginId` (`LoginId`),
  ADD UNIQUE KEY `Username` (`Username`);

--
-- Indexes for table `stage1`
--
ALTER TABLE `stage1`
  ADD PRIMARY KEY (`LoginId`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `stage1`
--
ALTER TABLE `stage1`
  ADD CONSTRAINT `stage1_ibfk_1` FOREIGN KEY (`LoginId`) REFERENCES `login` (`LoginId`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
