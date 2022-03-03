-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 20, 2022 at 10:43 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hy359`
--

-- --------------------------------------------------------

--
-- Table structure for table `bloodtest`
--

CREATE TABLE `bloodtest` (
  `bloodtest_id` int(11) NOT NULL,
  `amka` varchar(11) NOT NULL,
  `test_date` date NOT NULL,
  `medical_center` varchar(100) NOT NULL,
  `blood_sugar` double DEFAULT NULL,
  `blood_sugar_level` varchar(10) DEFAULT NULL,
  `cholesterol` double DEFAULT NULL,
  `cholesterol_level` varchar(10) DEFAULT NULL,
  `iron` double DEFAULT NULL,
  `iron_level` varchar(10) DEFAULT NULL,
  `vitamin_d3` double DEFAULT NULL,
  `vitamin_d3_level` varchar(10) DEFAULT NULL,
  `vitamin_b12` double DEFAULT NULL,
  `vitamin_b12_level` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bloodtest`
--

INSERT INTO `bloodtest` (`bloodtest_id`, `amka`, `test_date`, `medical_center`, `blood_sugar`, `blood_sugar_level`, `cholesterol`, `cholesterol_level`, `iron`, `iron_level`, `vitamin_d3`, `vitamin_d3_level`, `vitamin_b12`, `vitamin_b12_level`) VALUES
(1, '03069200000', '2021-10-11', 'pagni', 100, 'Normal', 220, 'High', 20, 'Normal', 30, 'Normal', 50, 'Low'),
(2, '03069200000', '2021-10-14', 'pagni', 90, 'Normal', 210, 'High', 24, 'Normal', 32, 'Normal', 45, 'Low'),
(3, '03069200000', '2021-10-14', 'pagni', 110, 'High', 190, 'Low', 70, 'Normal', 70, 'Normal', 300, 'Normal'),
(4, '01018044444', '1992-07-05', 'bigBouri', 20, 'Low', 12, 'Normal', 60, 'Normal', 67, 'Normal', 45, 'Low'),
(5, '01018044444', '1992-07-05', 'bigBouri', 20, 'Low', 12, 'Normal', 60, 'Normal', 67, 'Normal', 45, 'Low'),
(6, '01018044444', '1992-07-05', 'bigBouri', 29, 'Low', 14, 'Normal', 93, 'Normal', 57, 'Normal', 45, 'Low'),
(7, '03069200000', '1992-07-05', 'bigBouri', 20, 'Low', 12, 'Normal', 60, 'Normal', 67, 'Normal', 45, 'Low'),
(8, '03069200000', '2021-10-11', 'just a center', 50, 'Low', 13, 'Normal', 123, 'Normal', 87, 'Normal', 67, 'Low'),
(9, '03069200000', '2021-10-12', 'bigBouri', 35, 'Low', 60, 'Normal', 24, 'Low', 92, 'Normal', 36, 'Low');

-- --------------------------------------------------------

--
-- Table structure for table `doctors`
--

CREATE TABLE `doctors` (
  `doctor_id` int(11) NOT NULL,
  `username` varchar(30) NOT NULL,
  `email` varchar(40) NOT NULL,
  `password` varchar(32) NOT NULL,
  `firstname` varchar(20) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `birthdate` date NOT NULL,
  `gender` varchar(7) NOT NULL,
  `amka` varchar(11) NOT NULL,
  `country` varchar(30) NOT NULL,
  `city` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `lat` double DEFAULT NULL,
  `lon` double DEFAULT NULL,
  `telephone` varchar(14) NOT NULL,
  `height` int(11) DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `blooddonor` tinyint(1) DEFAULT NULL,
  `bloodtype` varchar(7) NOT NULL,
  `specialty` varchar(30) NOT NULL,
  `doctor_info` varchar(500) NOT NULL,
  `certified` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `doctors`
--

INSERT INTO `doctors` (`doctor_id`, `username`, `email`, `password`, `firstname`, `lastname`, `birthdate`, `gender`, `amka`, `country`, `city`, `address`, `lat`, `lon`, `telephone`, `height`, `weight`, `blooddonor`, `bloodtype`, `specialty`, `doctor_info`, `certified`) VALUES
(1, 'papadakis', 'pap@gmail.com', 'aoeu', 'Manos', 'Papadakos', '1982-10-03', 'Male', '03108200123', 'Greece', 'Heraklion', 'Evans 83', 35.3361866, 25.1342504, '2810123456', 500, 80, 1, 'B -', 'Parisi', 'Exw spoudasi parisi kai ', 1),
(2, 'stefanos', 'stefanos@doctor.gr', 'abcd12$3', 'Stefanos', 'Kapelakis', '1958-01-10', 'Male', '10015800234', 'Greece', 'Heraklion', 'Kalokairinou 50', 35.3376963, 25.1276121, '2810654321', 170, 68, 0, 'B+', 'Pathologist', 'O kaluteros giatros gia ti gripi.', 1),
(3, 'papadopoulou', 'papadopoulou@doctor.gr', 'doct12##', 'Eleni', 'Papopoulou', '1980-05-05', 'Female', '05058000123', 'Greece', 'Heraklion', 'Machis Kritis 10', 35.3375925, 25.1219286, '2810281028', 170, 60, 1, 'AB+', 'GeneralDoctor', 'Exei kanei metaptyxiakes spoudes stin ameriki.', 1),
(4, 'aggelopoulos', 'aggelopoulos@doctor.gr', 'agge58$1', 'Giorgos', 'Aggelopoulos', '1978-01-12', 'Male', '01127800111', 'Greece', 'Heraklion', 'Leoforos Knossou 200', 35.3152534, 25.1474208, '2811111111', 175, 60, 1, 'A-', 'Pathologist', 'Kathigitis iatrikis sto panepistimio.', 1),
(5, 'papatheodorou', 'papatheodorou@doctor.gr', 'papap$75', 'Konstantina', 'Papatheodorou', '1968-01-03', 'Female', '03016800111', 'Greece', 'Heraklion', 'Leoforos 62 Martyron 100', 35.3361846, 35.3361846, '2811121111', 160, 65, 1, 'A-', 'Pathologist', 'Exei empiria se zaxaro kai xolisterini.', 0),
(16, 'DoctaCockta', 'docta@cod.com', 'aoeu4$aoeu', 'Docta', 'Cockta', '1980-01-01', 'Male', '01018011112', 'Greece', 'Heraklion', 'merkouri', 35.3388586, 25.0558958, '6989596286', 190, 90, 1, 'A-', 'pathologist', 'aoeuaoeu', 0),
(17, 'yoloIsMyLife', 'yolo@yol.gr', 'aoeu4$aoeu', 'Mike', 'Mixelakis', '1980-01-01', 'female', '01018055522', 'Greece', 'Attica', 'bizaniou 6A', 37.9927411, 23.7658145, '6977377776', 180, 80, 1, 'B+', 'pathologist', 'My name is ', 1);

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `message_id` int(11) NOT NULL,
  `doctor_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `date_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `message` varchar(1000) NOT NULL,
  `sender` varchar(15) DEFAULT NULL,
  `blood_donation` tinyint(1) DEFAULT NULL,
  `bloodtype` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`message_id`, `doctor_id`, `user_id`, `date_time`, `message`, `sender`, `blood_donation`, `bloodtype`) VALUES
(1, 1, 1, '2021-10-11 12:11:00', 'Den eimai kala', 'user', 0, 'null'),
(2, 1, 1, '2021-10-11 15:11:00', 'Eisai kala', 'doctor', 0, 'null'),
(3, 1, 1, '2021-10-11 16:11:00', 'Re 8a se kanw kainourgio', 'user', 0, 'null'),
(5, 1, 13, '2022-01-17 22:16:57', 'Ante re apo dw', 'doctor', 0, 'null'),
(6, 1, 1, '2021-03-16 17:11:00', '8a fugeis apo ton xarti', 'user', 0, 'null'),
(18, 1, 12, '2022-01-17 22:46:20', ' Eimai gucci gata', 'user', 0, 'null'),
(19, 1, 12, '2022-01-17 22:46:34', ' bale allo ena kai meta stin basi', 'user', 0, 'null'),
(20, 1, 1, '2022-01-18 00:48:39', ' aoeuaoeuaoeu', 'user', 0, 'null'),
(21, 2, 1, '2022-01-18 00:52:55', ' ti lei re kapelo', 'user', 0, 'null'),
(22, 2, 1, '2022-01-18 00:55:31', ' aoeuaoeuaoeu', 'user', 0, 'null'),
(23, 2, 1, '2022-01-18 00:58:18', ' se kanw kapelo', 'doctor', 0, 'null'),
(25, 1, 1, '2022-01-18 01:01:29', ' ntrepomai', 'user', 0, 'null'),
(30, 1, 1, '2022-01-18 01:07:48', ' aoeu', 'user', 0, 'null'),
(31, 1, 1, '2022-01-18 01:08:08', ' aoeuaoeu', 'user', 0, 'null'),
(32, 1, 11, '2022-01-18 01:31:29', ' aoeu', 'doctor', 0, 'null'),
(33, 1, 2, '2022-01-18 01:43:56', ' hola', 'doctor', 0, 'null'),
(34, 1, 1, '2022-01-18 19:21:04', ' trws fasoli ?', 'doctor', 0, 'null'),
(35, 1, 13, '2022-01-18 19:21:16', ' Yooooo su', 'doctor', 0, 'null'),
(36, 1, 1, '2022-01-18 19:38:31', ' pou se re kuparisi', 'user', 0, 'null');

-- --------------------------------------------------------

--
-- Table structure for table `randevouz`
--

CREATE TABLE `randevouz` (
  `randevouz_id` int(11) NOT NULL,
  `doctor_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `date_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `price` int(11) NOT NULL,
  `doctor_info` varchar(500) DEFAULT NULL,
  `user_info` varchar(500) DEFAULT NULL,
  `status` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `randevouz`
--

INSERT INTO `randevouz` (`randevouz_id`, `doctor_id`, `user_id`, `date_time`, `price`, `doctor_info`, `user_info`, `status`) VALUES
(1, 1, 1, '2022-01-18 19:38:07', 50, 'Krata covid pass', '', 'selected'),
(2, 1, 1, '2022-01-18 19:37:06', 10, ' krata paso covid', 'null', 'free'),
(3, 2, 1, '2022-01-17 23:34:51', 60, 'Krata covid pass.', '', 'done'),
(7, 2, 1, '2022-01-16 10:41:35', 80, 'Bara ', 'null', 'free'),
(15, 1, 1, '2022-01-18 19:37:10', 10, ' Free', 'null', 'free'),
(21, 1, 0, '2022-01-16 10:48:05', 11, ' aoeu', 'null', 'done'),
(22, 1, 1, '2022-01-16 10:48:02', 32, ' aoeuaoeu', 'null', 'done'),
(23, 1, 1, '2022-01-17 21:33:16', 16, ' aoeu', '', 'cancelled'),
(24, 1, 1, '2022-01-18 19:19:08', 40, ' covid', 'null', 'done'),
(25, 1, 11, '2022-01-16 13:10:50', 10, ' oeui', 'null', 'done'),
(26, 1, 13, '2022-01-16 13:53:39', 11, ' aou', 'null', 'done'),
(27, 1, 1, '2022-01-17 21:33:37', 14, ' aoeuaoeu', '', 'selected'),
(28, 1, 1, '2022-01-17 21:33:43', 29, ' aoeueejjj', 'null', 'selected'),
(29, 1, 12, '2022-01-16 13:51:33', 26, ' omorfia', 'null', 'done'),
(30, 1, 0, '2022-01-27 09:19:00', 76, ' Fere covid', 'null', 'free');

-- --------------------------------------------------------

--
-- Table structure for table `treatment`
--

CREATE TABLE `treatment` (
  `treatment_id` int(11) NOT NULL,
  `doctor_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `treatment_text` varchar(1000) DEFAULT NULL,
  `bloodtest_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `treatment`
--

INSERT INTO `treatment` (`treatment_id`, `doctor_id`, `user_id`, `start_date`, `end_date`, `treatment_text`, `bloodtest_id`) VALUES
(1, 1, 1, '2021-10-26', '2021-11-09', 'Xapia gia xolisterini 3 fores ti mera', 1),
(2, 1, 1, '2021-12-15', '2022-01-05', 'Covid agwgi', 1),
(5, 1, 1, '2022-01-16', '2022-01-22', ' depon', 3),
(6, 1, 1, '2022-01-16', '2025-02-17', ' Agwgi', 3),
(7, 1, 1, '2022-01-19', '2022-01-26', ' Fae donut', 3);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(30) NOT NULL,
  `email` varchar(40) NOT NULL,
  `password` varchar(32) NOT NULL,
  `firstname` varchar(20) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `birthdate` date NOT NULL,
  `gender` varchar(7) NOT NULL,
  `amka` varchar(11) NOT NULL,
  `country` varchar(30) NOT NULL,
  `city` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `lat` double DEFAULT NULL,
  `lon` double DEFAULT NULL,
  `telephone` varchar(14) NOT NULL,
  `height` int(11) DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `blooddonor` tinyint(1) DEFAULT NULL,
  `bloodtype` varchar(7) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `email`, `password`, `firstname`, `lastname`, `birthdate`, `gender`, `amka`, `country`, `city`, `address`, `lat`, `lon`, `telephone`, `height`, `weight`, `blooddonor`, `bloodtype`) VALUES
(1, 'mountanton', 'lola@gmail.com', 'root', 'Michalis', 'Mountanton', '1992-06-03', 'Male', '03069200000', 'Athen', 'Heraklion', 'CSD Voutes', 35, 25.0722869, '1234567890', 180, 69.16, 1, '0'),
(2, 'admin', 'admin@admin.gr', 'admin12*', 'Admin', 'Admin', '1970-01-01', 'Male', '01234567890', 'Wuatemala', 'Heraklion', 'Liontaria', 0.5, 0.1, '281000000', 180, 90, 1, 'A+'),
(8, 'mbastakis', 'mbastakis@gmail.com', 'aoeu4$aoeu', 'Mike', 'Bastakis', '1980-01-01', 'Male', '01018044444', 'Greece', 'Heraklion', 'merkouri', 35.3388586, 25.0558958, '123456789', 100, 20, 2, 'A-'),
(11, 'Emanouela', 'mbastakise@gmail.com', 'aoeu4$aoeu', 'Emmanouel', 'Neratzouli', '1980-01-01', 'female', '01018044444', 'Greece', 'Heraklion', 'merkouri', 35.3388586, 25.0558958, '123456789', 100, 20, 2, 'A-'),
(12, 'Psipsinel', 'psipsinel@gmail.com', 'aoeu4$aoeu', 'Gata', 'Psipsinel', '1980-01-01', 'female', '01018044444', 'Greece', 'Heraklion', 'merkouri', 35.3388586, 25.0558958, '123456789', 100, 20, 2, 'A-'),
(13, 'Apisteutos', 'epic@gmail.com', 'aoeu4$aoeu', 'Apisteutos', 'Man', '1980-01-01', 'Male', '01018044444', 'Greece', 'Heraklion', 'merkouri', 35.3388586, 25.0558958, '123456789', 100, 20, 2, 'A-'),
(14, 'KingLoukas', 'loukas@thetitan.gr', 'aoeu4$aoeu', 'Loukas', 'Mertzanis', '1980-01-01', 'Male', '01018011111', 'Greece', 'Heraklion', 'merkouri', 35.3388586, 25.0558958, '6989596286', 180, 75, 2, 'A-'),
(18, 'tehmike5', 'mymail@gmail.com', 'aoeu4$aoeu', 'Loukas', 'Mount', '1980-01-01', 'Male', '01018055354', 'Greece', 'heraklion', 'monis kardiotisis 13', 37.9604174, 23.7649149, '6977777276', 189, 89, 1, '0+');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bloodtest`
--
ALTER TABLE `bloodtest`
  ADD PRIMARY KEY (`bloodtest_id`);

--
-- Indexes for table `doctors`
--
ALTER TABLE `doctors`
  ADD PRIMARY KEY (`doctor_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`message_id`),
  ADD KEY `doctor_id` (`doctor_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `randevouz`
--
ALTER TABLE `randevouz`
  ADD PRIMARY KEY (`randevouz_id`),
  ADD KEY `doctor_id` (`doctor_id`);

--
-- Indexes for table `treatment`
--
ALTER TABLE `treatment`
  ADD PRIMARY KEY (`treatment_id`),
  ADD KEY `doctor_id` (`doctor_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `bloodtest_id` (`bloodtest_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bloodtest`
--
ALTER TABLE `bloodtest`
  MODIFY `bloodtest_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `doctors`
--
ALTER TABLE `doctors`
  MODIFY `doctor_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `message_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT for table `randevouz`
--
ALTER TABLE `randevouz`
  MODIFY `randevouz_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `treatment`
--
ALTER TABLE `treatment`
  MODIFY `treatment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `message_ibfk_1` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`doctor_id`),
  ADD CONSTRAINT `message_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `randevouz`
--
ALTER TABLE `randevouz`
  ADD CONSTRAINT `randevouz_ibfk_1` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`doctor_id`);

--
-- Constraints for table `treatment`
--
ALTER TABLE `treatment`
  ADD CONSTRAINT `treatment_ibfk_1` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`doctor_id`),
  ADD CONSTRAINT `treatment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `treatment_ibfk_3` FOREIGN KEY (`bloodtest_id`) REFERENCES `bloodtest` (`bloodtest_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
