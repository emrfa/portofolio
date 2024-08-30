-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 19, 2024 at 01:36 PM
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
-- Database: `travel`
--

-- --------------------------------------------------------

--
-- Table structure for table `msuser`
--

CREATE TABLE `msuser` (
  `userId` char(5) NOT NULL,
  `username` varchar(50) NOT NULL,
  `userEmail` varchar(50) NOT NULL,
  `userPassword` varchar(50) NOT NULL,
  `userGender` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `msuser`
--

INSERT INTO `msuser` (`userId`, `username`, `userEmail`, `userPassword`, `userGender`) VALUES
('US001', 'Paul', 'paulhtg@gmail.com', 'paul1234', 'Male'),
('US002', 'paul2', 'paul2@gmail.com', 'paul1234', 'Male'),
('US003', 'paul3', 'paul123@gmail.com', 'paul1234', 'Male');

-- --------------------------------------------------------

--
-- Table structure for table `penerbangan`
--

CREATE TABLE `penerbangan` (
  `penerbanganId` char(5) NOT NULL,
  `rute` varchar(50) NOT NULL,
  `tanggalBerangkat` date NOT NULL,
  `maskapaiPenerbangan` varchar(50) NOT NULL,
  `kelasPenerbangan` varchar(50) NOT NULL,
  `durasiPenerbangan` varchar(50) NOT NULL,
  `bandaraKeberangkatan` varchar(50) NOT NULL,
  `bandaraKedatangan` varchar(50) NOT NULL,
  `jenisPesawat` varchar(50) NOT NULL,
  `price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `penerbangan`
--

INSERT INTO `penerbangan` (`penerbanganId`, `rute`, `tanggalBerangkat`, `maskapaiPenerbangan`, `kelasPenerbangan`, `durasiPenerbangan`, `bandaraKeberangkatan`, `bandaraKedatangan`, `jenisPesawat`, `price`) VALUES
('PE001', 'Jakarta-Bali', '2024-07-24', 'Sriwijaya', 'Ekonomi', '1 Jam', 'Soekarno Hatta Airport', 'I Gusti Ngurah Rai Airport', ' Boeing 737-800', 1000000),
('PE002', 'Jakarta-Bali', '2024-07-24', 'Sriwijaya', 'Bisnis', '1 Jam', 'Soekarno Hatta Airport', 'I Gusti Ngurah Rai Airport', ' Boeing 737-800', 1200000),
('PE003', 'Jakarta-Surabaya', '2024-08-18', 'Citilink', 'Ekonomi', '1 Jam 35 Menit', 'Soekarno Hatta Airport', 'Juanda', 'Airbus A320', 1000000),
('PE004', 'Jakarta-Surabaya', '2024-08-18', 'Citilink', 'Bisnis', '1 Jam 35 Menit', 'Soekarno Hatta Airport', 'Juanda', 'Airbus A320', 1100000),
('PE005', 'Jakarta-Bali', '2024-09-01', 'Garuda Indonesia', 'Ekonomi', '1 jam 50 menit', 'Soekarno-Hatta International Airport', 'Ngurah Rai International Airport', 'Boeing 737', 1500000),
('PE006', 'Surabaya-Makassar', '2024-09-02', 'Lion Air', 'Bisnis', '2 jam 20 menit', 'Juanda International Airport', 'Sultan Hasanuddin International Airport', 'Airbus A320', 2500000),
('PE007', 'Medan-Padang', '2024-09-03', 'Citilink', 'Ekonomi', '1 jam 40 menit', 'Kualanamu International Airport', 'Minangkabau International Airport', 'Boeing 737', 1400000),
('PE008', 'Bandung-Yogyakarta', '2024-09-04', 'Sriwijaya Air', 'Bisnis', '1 jam 30 menit', 'Husein Sastranegara International Airport', 'Adisucipto International Airport', 'Airbus A320', 2700000),
('PE009', 'Palembang-Jakarta', '2024-09-05', 'Garuda Indonesia', 'Ekonomi', '1 jam 25 menit', 'Sultan Mahmud Badaruddin II International Airport', 'Soekarno-Hatta International Airport', 'Boeing 737', 1600000),
('PE010', 'Yogyakarta-Bali', '2024-09-06', 'Lion Air', 'Bisnis', '1 jam 55 menit', 'Adisucipto International Airport', 'Ngurah Rai International Airport', 'Airbus A320', 2800000),
('PE011', 'Banjarmasin-Medan', '2024-09-07', 'Citilink', 'Ekonomi', '2 jam 10 menit', 'Syamsudin Noor International Airport', 'Kualanamu International Airport', 'Boeing 737', 1500000),
('PE012', 'Jakarta-Surabaya', '2024-09-08', 'Sriwijaya Air', 'Bisnis', '1 jam 20 menit', 'Soekarno-Hatta International Airport', 'Juanda International Airport', 'Airbus A320', 2900000),
('PE013', 'Makassar-Bali', '2024-09-09', 'Garuda Indonesia', 'Ekonomi', '2 jam 25 menit', 'Sultan Hasanuddin International Airport', 'Ngurah Rai International Airport', 'Boeing 737', 1700000),
('PE014', 'Padang-Makassar', '2024-09-10', 'Lion Air', 'Bisnis', '2 jam 15 menit', 'Minangkabau International Airport', 'Sultan Hasanuddin International Airport', 'Airbus A320', 3000000),
('PE015', 'Bandung-Jakarta', '2024-09-11', 'Citilink', 'Ekonomi', '1 jam 10 menit', 'Husein Sastranegara International Airport', 'Soekarno-Hatta International Airport', 'Boeing 737', 1400000),
('PE016', 'Yogyakarta-Surabaya', '2024-09-12', 'Sriwijaya Air', 'Bisnis', '1 jam 40 menit', 'Adisucipto International Airport', 'Juanda International Airport', 'Airbus A320', 2800000),
('PE017', 'Medan-Jakarta', '2024-09-13', 'Garuda Indonesia', 'Ekonomi', '2 jam 30 menit', 'Kualanamu International Airport', 'Soekarno-Hatta International Airport', 'Boeing 737', 1800000),
('PE018', 'Palembang-Bali', '2024-09-14', 'Lion Air', 'Bisnis', '2 jam 5 menit', 'Sultan Mahmud Badaruddin II International Airport', 'Ngurah Rai International Airport', 'Airbus A320', 3100000),
('PE019', 'Banjarmasin-Jakarta', '2024-09-15', 'Citilink', 'Ekonomi', '2 jam 15 menit', 'Syamsudin Noor International Airport', 'Soekarno-Hatta International Airport', 'Boeing 737', 1600000),
('PE020', 'Jakarta-Padang', '2024-09-16', 'Sriwijaya Air', 'Bisnis', '2 jam 25 menit', 'Soekarno-Hatta International Airport', 'Minangkabau International Airport', 'Airbus A320', 2900000),
('PE021', 'Makassar-Jakarta', '2024-09-17', 'Garuda Indonesia', 'Ekonomi', '2 jam 5 menit', 'Sultan Hasanuddin International Airport', 'Soekarno-Hatta International Airport', 'Boeing 737', 1500000),
('PE022', 'Medan-Palembang', '2024-09-18', 'Lion Air', 'Bisnis', '2 jam 20 menit', 'Kualanamu International Airport', 'Sultan Mahmud Badaruddin II International Airport', 'Airbus A320', 2600000),
('PE023', 'Yogyakarta-Banjarmasin', '2024-09-19', 'Citilink', 'Ekonomi', '2 jam 30 menit', 'Adisucipto International Airport', 'Syamsudin Noor International Airport', 'Boeing 737', 1700000),
('PE024', 'Bandung-Padang', '2024-09-20', 'Sriwijaya Air', 'Bisnis', '2 jam 10 menit', 'Husein Sastranegara International Airport', 'Minangkabau International Airport', 'Airbus A320', 2800000),
('PE025', 'Jakarta-Makassar', '2024-09-21', 'Garuda Indonesia', 'Ekonomi', '2 jam 35 menit', 'Soekarno-Hatta International Airport', 'Sultan Hasanuddin International Airport', 'Boeing 737', 1800000),
('PE026', 'Jakarta-Makassar', '2024-09-22', 'Batik Air', 'Ekonomi', '2 jam 30 menit', 'Soekarno-Hatta International Airport', 'Sultan Hasanuddin International Airport', 'Boeing 737', 1550000),
('PE027', 'Surabaya-Jakarta', '2024-09-23', 'Wings Air', 'Bisnis', '1 jam 15 menit', 'Juanda International Airport', 'Soekarno-Hatta International Airport', 'Airbus A320', 2600000),
('PE028', 'Medan-Yogyakarta', '2024-09-24', 'Garuda Indonesia', 'Ekonomi', '2 jam 20 menit', 'Kualanamu International Airport', 'Adisucipto International Airport', 'Boeing 737', 1700000),
('PE029', 'Bandung-Palembang', '2024-09-25', 'AirAsia', 'Bisnis', '2 jam 5 menit', 'Husein Sastranegara International Airport', 'Sultan Mahmud Badaruddin II International Airport', 'Airbus A320', 2900000),
('PE030', 'Yogyakarta-Surabaya', '2024-09-26', 'Sriwijaya Air', 'Ekonomi', '1 jam 35 menit', 'Adisucipto International Airport', 'Juanda International Airport', 'Boeing 737', 1500000),
('PE031', 'Makassar-Banjarmasin', '2024-09-27', 'Citilink', 'Bisnis', '1 jam 50 menit', 'Sultan Hasanuddin International Airport', 'Syamsudin Noor International Airport', 'Airbus A320', 2800000),
('PE032', 'Palembang-Jakarta', '2024-09-28', 'Lion Air', 'Ekonomi', '1 jam 20 menit', 'Sultan Mahmud Badaruddin II International Airport', 'Soekarno-Hatta International Airport', 'Boeing 737', 1450000),
('PE033', 'Jakarta-Bali', '2024-09-29', 'Garuda Indonesia', 'Bisnis', '1 jam 55 menit', 'Soekarno-Hatta International Airport', 'Ngurah Rai International Airport', 'Airbus A320', 2900000),
('PE034', 'Bandung-Makassar', '2024-09-30', 'Batik Air', 'Ekonomi', '2 jam 25 menit', 'Husein Sastranegara International Airport', 'Sultan Hasanuddin International Airport', 'Boeing 737', 1600000),
('PE035', 'Surabaya-Bali', '2024-10-01', 'AirAsia', 'Bisnis', '1 jam 50 menit', 'Juanda International Airport', 'Ngurah Rai International Airport', 'Airbus A320', 2700000),
('PE036', 'Medan-Jakarta', '2024-10-02', 'Sriwijaya Air', 'Ekonomi', '2 jam 35 menit', 'Kualanamu International Airport', 'Soekarno-Hatta International Airport', 'Boeing 737', 1750000),
('PE037', 'Yogyakarta-Bali', '2024-10-03', 'Citilink', 'Bisnis', '1 jam 45 menit', 'Adisucipto International Airport', 'Ngurah Rai International Airport', 'Airbus A320', 3000000),
('PE038', 'Palembang-Makassar', '2024-10-04', 'Lion Air', 'Ekonomi', '2 jam 15 menit', 'Sultan Mahmud Badaruddin II International Airport', 'Sultan Hasanuddin International Airport', 'Boeing 737', 1550000),
('PE039', 'Jakarta-Medan', '2024-10-05', 'Garuda Indonesia', 'Bisnis', '2 jam 40 menit', 'Soekarno-Hatta International Airport', 'Kualanamu International Airport', 'Airbus A320', 3100000),
('PE040', 'Bandung-Jakarta', '2024-10-06', 'Batik Air', 'Ekonomi', '1 jam 20 menit', 'Husein Sastranegara International Airport', 'Soekarno-Hatta International Airport', 'Boeing 737', 1450000),
('PE041', 'Surabaya-Palembang', '2024-10-07', 'AirAsia', 'Bisnis', '2 jam 5 menit', 'Juanda International Airport', 'Sultan Mahmud Badaruddin II International Airport', 'Airbus A320', 2900000),
('PE042', 'Medan-Bali', '2024-10-08', 'Citilink', 'Ekonomi', '2 jam 50 menit', 'Kualanamu International Airport', 'Ngurah Rai International Airport', 'Boeing 737', 1800000),
('PE043', 'Yogyakarta-Surabaya', '2024-10-09', 'Sriwijaya Air', 'Bisnis', '1 jam 50 menit', 'Adisucipto International Airport', 'Juanda International Airport', 'Airbus A320', 2750000);

-- --------------------------------------------------------

--
-- Table structure for table `reservation`
--

CREATE TABLE `reservation` (
  `reservationId` char(5) NOT NULL,
  `userId` char(5) NOT NULL,
  `penerbanganId` char(5) NOT NULL,
  `rute` varchar(50) NOT NULL DEFAULT 'None',
  `maskapaiPenerbangan` varchar(50) NOT NULL DEFAULT 'None',
  `tanggalKeberangkatan` date NOT NULL,
  `totalPrice` decimal(10,2) NOT NULL DEFAULT 0.00,
  `layananTambahan` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `msuser`
--
ALTER TABLE `msuser`
  ADD PRIMARY KEY (`userId`);

--
-- Indexes for table `penerbangan`
--
ALTER TABLE `penerbangan`
  ADD PRIMARY KEY (`penerbanganId`);

--
-- Indexes for table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`reservationId`),
  ADD KEY `userId` (`userId`),
  ADD KEY `penerbanganId` (`penerbanganId`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `msuser` (`userId`) ON DELETE CASCADE,
  ADD CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`penerbanganId`) REFERENCES `penerbangan` (`penerbanganId`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
