-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 17, 2023 at 06:09 AM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `healthyscanner`
--

-- --------------------------------------------------------

--
-- Table structure for table `fisik`
--

CREATE TABLE `fisik` (
  `fisikid` char(5) NOT NULL,
  `userId` char(5) NOT NULL,
  `jumlahlangkah` int(11) NOT NULL,
  `jaraktempuh` int(11) NOT NULL,
  `durasilatihan` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `fisik`
--

INSERT INTO `fisik` (`fisikid`, `userId`, `jumlahlangkah`, `jaraktempuh`, `durasilatihan`) VALUES
('FI001', 'US001', 2, 3, 4),
('FI002', 'US001', 1, 2, 3),
('FI003', 'US001', 1, 1, 1),
('FI004', 'US001', 5, 5, 5),
('FI005', 'US001', 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tidur`
--

CREATE TABLE `tidur` (
  `pemantauId` char(5) NOT NULL,
  `userId` char(5) NOT NULL,
  `hari` varchar(50) NOT NULL,
  `jumlahjamtidur` int(11) NOT NULL,
  `kualitas` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tidur`
--

INSERT INTO `tidur` (`pemantauId`, `userId`, `hari`, `jumlahjamtidur`, `kualitas`) VALUES
('TI001', 'US001', 'Senin', 4, 'Kurang'),
('TI002', 'US001', 'Selasa', 7, 'Cukup'),
('TI003', 'US001', 'Rabu', 9, 'Bagus'),
('TI004', 'US001', 'Minggu', 6, 'Cukup');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userId` char(5) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userId`, `username`, `password`, `email`) VALUES
('US001', 'rexy', 'rexy123', 'rexy@gmail.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `fisik`
--
ALTER TABLE `fisik`
  ADD PRIMARY KEY (`fisikid`),
  ADD KEY `userId` (`userId`);

--
-- Indexes for table `tidur`
--
ALTER TABLE `tidur`
  ADD PRIMARY KEY (`pemantauId`),
  ADD KEY `userId` (`userId`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userId`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `fisik`
--
ALTER TABLE `fisik`
  ADD CONSTRAINT `fisik_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`);

--
-- Constraints for table `tidur`
--
ALTER TABLE `tidur`
  ADD CONSTRAINT `tidur_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
