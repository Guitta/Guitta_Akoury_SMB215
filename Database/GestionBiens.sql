-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 15, 2015 at 01:36 AM
-- Server version: 5.5.46-0ubuntu0.14.04.2
-- PHP Version: 5.5.9-1ubuntu4.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `GestionBiens`
--

-- --------------------------------------------------------

--
-- Table structure for table `center`
--

CREATE TABLE IF NOT EXISTS `center` (
  `center_id` int(11) NOT NULL AUTO_INCREMENT,
  `center_name` varchar(50) NOT NULL,
  PRIMARY KEY (`center_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `center`
--

INSERT INTO `center` (`center_id`, `center_name`) VALUES
(4, 'Bikfaya'),
(5, 'Beyrouth'),
(6, 'Nahr Ibrahim'),
(7, 'Baakline'),
(8, 'Tripoli');

-- --------------------------------------------------------

--
-- Table structure for table `groupe`
--

CREATE TABLE IF NOT EXISTS `groupe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `groupname` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `Description` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_2` (`username`,`groupname`),
  UNIQUE KEY `username_3` (`username`,`groupname`),
  KEY `username` (`username`,`groupname`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=27 ;

--
-- Dumping data for table `groupe`
--

INSERT INTO `groupe` (`id`, `username`, `groupname`, `Description`) VALUES
(1, 'admin', 'admin', 'Administrator'),
(2, 'user', 'user', 'User'),
(14, 'nadim', 'user', 'User'),
(26, 'admin', 'user', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE IF NOT EXISTS `item` (
  `item_id` int(11) NOT NULL AUTO_INCREMENT,
  `item_code` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL,
  `item_date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `item_name` varchar(255) NOT NULL,
  `type_id` int(11) NOT NULL,
  `item_specification` text NOT NULL,
  `location_id` int(11) NOT NULL,
  PRIMARY KEY (`item_id`),
  KEY `item_type` (`type_id`),
  KEY `location_id` (`location_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`item_id`, `item_code`, `item_date_created`, `item_name`, `type_id`, `item_specification`, `location_id`) VALUES
(1, 'L0001', '2015-11-02 03:11:01', 'Laptop', 2, 'NotBook', 1),
(2, 'L00002', '2015-11-04 01:11:09', 'ProBook', 2, 'HP ProBook i5', 2),
(3, 'L00003', '2015-11-11 01:11:45', 'Projector', 2, 'Projector ViewSonic', 1);

-- --------------------------------------------------------

--
-- Table structure for table `location`
--

CREATE TABLE IF NOT EXISTS `location` (
  `location_id` int(11) NOT NULL AUTO_INCREMENT,
  `center_id` int(11) NOT NULL,
  `salle_id` int(11) NOT NULL,
  `personnel_id` int(11) NOT NULL,
  PRIMARY KEY (`location_id`),
  KEY `center_id` (`center_id`,`salle_id`,`personnel_id`),
  KEY `personnel_id` (`personnel_id`),
  KEY `salle_id` (`salle_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=3 ;

--
-- Dumping data for table `location`
--

INSERT INTO `location` (`location_id`, `center_id`, `salle_id`, `personnel_id`) VALUES
(1, 5, 2, 1),
(2, 5, 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `personnel`
--

CREATE TABLE IF NOT EXISTS `personnel` (
  `personnel_id` int(11) NOT NULL AUTO_INCREMENT,
  `personnel_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`personnel_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=3 ;

--
-- Dumping data for table `personnel`
--

INSERT INTO `personnel` (`personnel_id`, `personnel_name`) VALUES
(1, 'Dr Pascal Fares'),
(2, 'Xxx Xxx');

-- --------------------------------------------------------

--
-- Table structure for table `salle`
--

CREATE TABLE IF NOT EXISTS `salle` (
  `salle_id` int(11) NOT NULL AUTO_INCREMENT,
  `salle_name` varchar(50) NOT NULL,
  PRIMARY KEY (`salle_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `salle`
--

INSERT INTO `salle` (`salle_id`, `salle_name`) VALUES
(1, '106'),
(2, '112'),
(3, '212');

-- --------------------------------------------------------

--
-- Table structure for table `testTable`
--

CREATE TABLE IF NOT EXISTS `testTable` (
  `id` int(11) NOT NULL,
  `name` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `testTable`
--

INSERT INTO `testTable` (`id`, `name`) VALUES
(0, 0),
(0, 0),
(0, 0),
(1, 2),
(1, 2),
(1, 2),
(1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE IF NOT EXISTS `transaction` (
  `transaction_id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `transaction_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `location_id_src` int(11) NOT NULL,
  `location_id_now` int(11) DEFAULT NULL,
  `location_id_dest` int(11) DEFAULT NULL,
  `transport_id` int(11) DEFAULT NULL,
  `status` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `transaction_item` (`item_id`),
  KEY `transaction_user` (`username`),
  KEY `transport_id` (`transport_id`),
  KEY `item_id` (`item_id`,`username`,`location_id_src`,`location_id_now`,`location_id_dest`),
  KEY `location_id_src` (`location_id_src`),
  KEY `location_id_now` (`location_id_now`),
  KEY `location_id_dest` (`location_id_dest`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`transaction_id`, `item_id`, `transaction_date`, `username`, `location_id_src`, `location_id_now`, `location_id_dest`, `transport_id`, `status`) VALUES
(1, 1, '2015-11-02 11:11:12', 'admin', 1, 1, 2, 1, 'Delivered');

-- --------------------------------------------------------

--
-- Table structure for table `transport`
--

CREATE TABLE IF NOT EXISTS `transport` (
  `transport_id` int(11) NOT NULL AUTO_INCREMENT,
  `personnel_id` int(11) NOT NULL,
  `transport_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`transport_id`),
  KEY `personnel_id` (`personnel_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=2 ;

--
-- Dumping data for table `transport`
--

INSERT INTO `transport` (`transport_id`, `personnel_id`, `transport_date`) VALUES
(1, 2, '2015-11-13 03:11:23');

-- --------------------------------------------------------

--
-- Table structure for table `type`
--

CREATE TABLE IF NOT EXISTS `type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `type`
--

INSERT INTO `type` (`type_id`, `type_name`) VALUES
(1, 'Consommable'),
(2, 'A long terme');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `register_dt` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`name`, `username`, `password`, `register_dt`) VALUES
('Guitta', 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '2015-09-12 09:09:50'),
('Nadim', 'nadim', '7f1282974437f511dacee4013a0814bf7cbab38dd07040a4b4a82cebd50d134e', '2015-11-01 07:11:10');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `item`
--
ALTER TABLE `item`
  ADD CONSTRAINT `item_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `type` (`type_id`),
  ADD CONSTRAINT `item_ibfk_2` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`);

--
-- Constraints for table `location`
--
ALTER TABLE `location`
  ADD CONSTRAINT `location_ibfk_1` FOREIGN KEY (`personnel_id`) REFERENCES `personnel` (`personnel_id`),
  ADD CONSTRAINT `location_ibfk_2` FOREIGN KEY (`salle_id`) REFERENCES `salle` (`salle_id`),
  ADD CONSTRAINT `location_ibfk_3` FOREIGN KEY (`center_id`) REFERENCES `center` (`center_id`);

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`transport_id`) REFERENCES `transport` (`transport_id`),
  ADD CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`username`) REFERENCES `users` (`username`),
  ADD CONSTRAINT `transaction_ibfk_3` FOREIGN KEY (`location_id_src`) REFERENCES `location` (`location_id`),
  ADD CONSTRAINT `transaction_ibfk_4` FOREIGN KEY (`location_id_now`) REFERENCES `location` (`location_id`),
  ADD CONSTRAINT `transaction_ibfk_5` FOREIGN KEY (`location_id_dest`) REFERENCES `location` (`location_id`),
  ADD CONSTRAINT `transaction_ibfk_6` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`);

--
-- Constraints for table `transport`
--
ALTER TABLE `transport`
  ADD CONSTRAINT `transport_ibfk_1` FOREIGN KEY (`personnel_id`) REFERENCES `personnel` (`personnel_id`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`username`) REFERENCES `groupe` (`username`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
