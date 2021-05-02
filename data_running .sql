-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 02, 2021 at 09:57 AM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `data_running`
--

-- --------------------------------------------------------

--
-- Table structure for table `add_event`
--

CREATE TABLE `add_event` (
  `id_add` int(20) NOT NULL,
  `id_user` int(10) NOT NULL,
  `pic_event` varchar(200) DEFAULT NULL,
  `name_event` varchar(100) DEFAULT NULL,
  `name_producer` varchar(100) DEFAULT NULL,
  `date_reg_start` varchar(20) DEFAULT NULL,
  `date_reg_end` varchar(20) DEFAULT NULL,
  `bank` varchar(100) NOT NULL,
  `num_bank` varchar(100) NOT NULL,
  `objective` varchar(2000) DEFAULT NULL,
  `detail` varchar(5000) DEFAULT NULL,
  `name_organizer` varchar(50) NOT NULL,
  `tel` varchar(10) NOT NULL,
  `type` int(1) NOT NULL,
  `status_event` int(11) NOT NULL,
  `detail_cancel` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `add_event`
--

INSERT INTO `add_event` (`id_add`, `id_user`, `pic_event`, `name_event`, `name_producer`, `date_reg_start`, `date_reg_end`, `bank`, `num_bank`, `objective`, `detail`, `name_organizer`, `tel`, `type`, `status_event`, `detail_cancel`) VALUES
(187, 84, 'https://firebasestorage.googleapis.com/v0/b/vistual-running.appspot.com/o/images%2F83f1eed1-dbad-4a17-abd5-84f8fa699f66?alt=media&token=6ab60891-52c8-4489-9cc9-4486f87d1921', 'ก้าวคนละก้าว', 'gun', '6/4/2021', '13/4/2021', 'ธนาคารกสิกรไทย', '23-456-89', 'test', 'test', 'toon', '0923956498', 1, 1, ''),
(190, 84, 'https://firebasestorage.googleapis.com/v0/b/vistual-running.appspot.com/o/images%2Fda3eb5cb-c0e4-441f-9392-d308ddcdf690?alt=media&token=3c567edf-65bd-4a8a-bc5e-a3bc31c7768c', 'Khonkaen marathon 2020', 'Khonkaen', '20/4/2021', '27/4/2021', 'ธนาคารกสิกรไทย', '123-4567-890', 'test\n', 'test\n', 'กิตินันท์ คนสอน', '0923956498', 1, 1, 'รายละเอียดไม่ชัดเจน'),
(193, 84, 'https://firebasestorage.googleapis.com/v0/b/vistual-running.appspot.com/o/images%2F108dc5e8-a12f-4131-bed1-c8adf67f7ace?alt=media&token=b63524b4-b069-4e10-936b-4855ab826800', 'BangSaen Marathon ', 'Bangsaen', '21/4/2021', '28/4/2021', 'ธนาคารกรุงไทย', '123-4567-890', 'Test', 'test', 'Gun', '0923956498', 2, 2, 'รายละเอียดไม่ชัดเจน'),
(195, 84, 'https://firebasestorage.googleapis.com/v0/b/vistual-running.appspot.com/o/images%2F7052f4d9-842c-4bc5-9caa-ae5da99067dc?alt=media&token=110db5a2-e455-4f7d-b70a-d01bf4d0d9f5', 'test', 'test', '23/4/2021', '30/4/2021', 'ธนาคารกสิกรไทย', '145678', 'test', 'tets', 'test', '142', 1, 1, '');

-- --------------------------------------------------------

--
-- Table structure for table `detail_distance`
--

CREATE TABLE `detail_distance` (
  `Detail_id` int(11) NOT NULL,
  `id_add` int(11) NOT NULL,
  `name_event` varchar(40) NOT NULL,
  `name_distance` varchar(40) NOT NULL,
  `distance` int(3) NOT NULL,
  `price` int(10) NOT NULL,
  `num_register` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detail_distance`
--

INSERT INTO `detail_distance` (`Detail_id`, `id_add`, `name_event`, `name_distance`, `distance`, `price`, `num_register`) VALUES
(77, 187, 'ก้าวคนละก้าว', ' Fun Run', 4, 200, 200),
(78, 187, 'ก้าวคนละก้าว', 'Mini Marathon', 11, 200, 200),
(83, 190, 'Khonkaen marathon 2020', 'Fun Run', 4, 200, 100),
(84, 190, 'Khonkaen marathon 2020', 'Haft Marathon', 21, 200, 100),
(85, 190, 'Khonkaen marathon 2020', 'Mini Marathon', 11, 200, 100),
(90, 193, 'BangSaen Marathon ', 'MINI Marathon', 11, 200, 100),
(91, 193, 'BangSaen Marathon ', 'HAFT Marathon', 21, 200, 100),
(93, 195, 'test', 'test', 11, 100, 100);

-- --------------------------------------------------------

--
-- Table structure for table `history_payment`
--

CREATE TABLE `history_payment` (
  `id_history_payment` int(10) NOT NULL,
  `id_user` int(10) NOT NULL,
  `id_add` int(10) NOT NULL,
  `name_event` varchar(40) NOT NULL,
  `first_name` varchar(40) NOT NULL,
  `last_name` varchar(40) NOT NULL,
  `image_link` varchar(200) NOT NULL,
  `date` varchar(10) NOT NULL,
  `time` varchar(5) NOT NULL,
  `bank` varchar(50) NOT NULL,
  `type_submit` int(10) NOT NULL,
  `detail_cancel` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `history_payment`
--

INSERT INTO `history_payment` (`id_history_payment`, `id_user`, `id_add`, `name_event`, `first_name`, `last_name`, `image_link`, `date`, `time`, `bank`, `type_submit`, `detail_cancel`) VALUES
(41, 94, 187, 'ก้าวคนละก้าว', 'ธีริทธิ์', 'สุมาลัย', 'https://firebasestorage.googleapis.com/v0/b/vistual-running.appspot.com/o/images%2F940fbc5a-0e23-4a8e-af21-7739373f45fc?alt=media&token=3e0408ee-b031-4940-987a-3cfb51a977ce', '22/4/2021', '23:18', 'ธนาคารทหารไทย', 2, 'โอนเกินจำนวน'),
(42, 94, 187, 'ก้าวคนละก้าว', 'ธีริทธิ์', 'สุมาลัย', 'https://firebasestorage.googleapis.com/v0/b/vistual-running.appspot.com/o/images%2F6ad3709d-5b8d-4ef5-a1f6-e6a9cdbc3aaf?alt=media&token=98cd5834-a0b8-4842-bc14-e29049cc67e7', '22/4/2021', '23:20', 'ธนาคารกสิกรไทย', 1, '12'),
(43, 94, 193, 'BangSaen Marathon ', 'ธีริทธิ์', 'สุมาลัย', 'https://firebasestorage.googleapis.com/v0/b/vistual-running.appspot.com/o/images%2F32e99b00-b27d-41d9-8851-a8bf9643e85f?alt=media&token=3a89d995-20b1-4165-9abf-384ae487444f', '22/4/2021', '23:30', 'ธนาคารกรุงไทย', 1, '13'),
(46, 94, 195, 'test', 'ธีริทธิ์', 'สุมาลัย', 'https://firebasestorage.googleapis.com/v0/b/vistual-running.appspot.com/o/images%2Fe09e9afb-b206-46a0-b54c-3624ee898895?alt=media&token=9f8cfa89-7bd0-4274-a08b-98ecfd6b2084', '23/4/2021', '11:40', 'ธนาคารกสิกรไทย', 2, 'ยอดไม่ครบ');

-- --------------------------------------------------------

--
-- Table structure for table `history_uploadstatic`
--

CREATE TABLE `history_uploadstatic` (
  `id_history_UploadStatic` int(10) NOT NULL,
  `id_user` int(10) NOT NULL,
  `id_add` int(10) NOT NULL,
  `date` varchar(100) NOT NULL,
  `distance` float(10,2) NOT NULL,
  `cal` varchar(100) NOT NULL,
  `pace` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `history_uploadstatic`
--

INSERT INTO `history_uploadstatic` (`id_history_UploadStatic`, `id_user`, `id_add`, `date`, `distance`, `cal`, `pace`) VALUES
(29, 92, 187, '7/4/2021', 4.00, '100', ''),
(30, 92, 187, '7/4/2021', 4.00, '100', ''),
(36, 94, 187, '22/4/2021', 5.39, '200', '10.2'),
(37, 94, 193, '22/4/2021', 5.39, '430', '10.3'),
(38, 94, 193, '22/4/2021', 8.39, '680', '5.30'),
(39, 94, 193, '22/4/2021', 2.39, '200', '6.30'),
(41, 94, 187, '25/4/2021', 4.74, '200', '1.5');

-- --------------------------------------------------------

--
-- Table structure for table `reg_address`
--

CREATE TABLE `reg_address` (
  `id_address` int(11) NOT NULL,
  `id_user` int(10) NOT NULL,
  `id_add` int(10) NOT NULL,
  `Address` varchar(100) NOT NULL,
  `District` varchar(100) NOT NULL,
  `MueangDistrict` varchar(100) NOT NULL,
  `province` varchar(20) NOT NULL,
  `Country_number` varchar(10) NOT NULL,
  `Name` varchar(20) NOT NULL,
  `Tel` varchar(20) NOT NULL,
  `transport` varchar(20) NOT NULL,
  `pacel_number` varchar(11) NOT NULL,
  `type_submit_reward` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `reg_address`
--

INSERT INTO `reg_address` (`id_address`, `id_user`, `id_add`, `Address`, `District`, `MueangDistrict`, `province`, `Country_number`, `Name`, `Tel`, `transport`, `pacel_number`, `type_submit_reward`) VALUES
(62, 91, 188, '123 ม.16 ', 'ในเมือง', 'เมือง', 'ขอนแก่น', '40002', 'นฤนาท คุณธรรม', '0879185345', 'EMS', '12346789', 0),
(63, 92, 187, '123', 'ในเมือง', 'เมือง', 'ขอนแก่น', '40002', 'สุคนธ์  บัญจันทร์', '0973188636', 'ems', '123456', 0),
(68, 94, 187, 'หอฟิวเจอร์', 'ศิลา', 'เมือง', 'ขอนแก่น', '40000', 'กิตินันท์ คนสอน', '0923956498', 'Kerry', '10245', 0),
(69, 94, 193, 'หอฟิวเจอร์', 'ศิลา', 'เมือง', 'ขอนแก่น', '40000', 'กิตินันท์ คนสอน', '0923956498', '', '', 0),
(71, 94, 195, '1', '1', '1', '1', '1', '1', '1', '', '', 0),
(74, 85, 187, '2', '2', '2', '2', '2', '2', '2', '', '', 0),
(75, 94, 195, '1', '1', '1', '1', '1', '1', '1', '', '', 0);

-- --------------------------------------------------------

--
-- Table structure for table `reg_event`
--

CREATE TABLE `reg_event` (
  `id_reg_event` int(11) NOT NULL,
  `id_add` int(40) NOT NULL,
  `id_user` int(10) NOT NULL,
  `first_name` varchar(40) NOT NULL,
  `last_name` varchar(40) NOT NULL,
  `Tel` varchar(20) NOT NULL,
  `id_card` varchar(50) NOT NULL,
  `nationality` varchar(50) NOT NULL,
  `blood` varchar(5) NOT NULL,
  `distance` varchar(20) NOT NULL,
  `emergency` varchar(20) NOT NULL,
  `relation` varchar(10) NOT NULL,
  `relationTel` varchar(20) NOT NULL,
  `name_event` varchar(50) DEFAULT NULL,
  `name_producer` varchar(40) NOT NULL,
  `pic_event` varchar(1000) NOT NULL,
  `status_reward` int(2) NOT NULL,
  `status_send` int(10) NOT NULL,
  `type_submit` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `reg_event`
--

INSERT INTO `reg_event` (`id_reg_event`, `id_add`, `id_user`, `first_name`, `last_name`, `Tel`, `id_card`, `nationality`, `blood`, `distance`, `emergency`, `relation`, `relationTel`, `name_event`, `name_producer`, `pic_event`, `status_reward`, `status_send`, `type_submit`) VALUES
(119, 187, 92, 'สุคนธ์', 'บุญจันทร์', '09731886366', '123456789123', 'ไทย', 'B', '4', 'บอย', 'พี่', '1234567890', 'ก้าวคนละก้าว', 'gun', 'https://firebasestorage.googleapis.com/v0/b/vistual-running.appspot.com/o/images%2F83f1eed1-dbad-4a17-abd5-84f8fa699f66?alt=media&token=6ab60891-52c8-4489-9cc9-4486f87d1921', 1, 1, 1),
(124, 187, 94, 'กิตินันท์', 'คนสอน', '0923956498', '1300101186415', 'ไทย', 'AB', '4', 'แสตมป์', 'ญาติ', '124', 'ก้าวคนละก้าว', 'gun', 'https://firebasestorage.googleapis.com/v0/b/vistual-running.appspot.com/o/images%2F83f1eed1-dbad-4a17-abd5-84f8fa699f66?alt=media&token=6ab60891-52c8-4489-9cc9-4486f87d1921', 1, 1, 1),
(125, 193, 94, 'กิตินันท์', 'คนสอน', '0923956498', '1300101186415', 'ไทย', 'AB', '11', 'เนี้ยบ', 'ญาติ', '1150', 'BangSaen Marathon ', 'Bangsaen', 'https://firebasestorage.googleapis.com/v0/b/vistual-running.appspot.com/o/images%2F108dc5e8-a12f-4131-bed1-c8adf67f7ace?alt=media&token=b63524b4-b069-4e10-936b-4855ab826800', 0, 0, 1),
(134, 195, 94, 'test', 'test', '120', '11', 'thai', 'AB', '11', 'dad', 'บิดา', '111', 'test', 'test', 'https://firebasestorage.googleapis.com/v0/b/vistual-running.appspot.com/o/images%2F7052f4d9-842c-4bc5-9caa-ae5da99067dc?alt=media&token=110db5a2-e455-4f7d-b70a-d01bf4d0d9f5', 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` int(4) NOT NULL,
  `pic_profile` varchar(1000) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(16) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `Tel` varchar(10) NOT NULL,
  `id_card` varchar(15) NOT NULL,
  `bd_date` varchar(20) NOT NULL,
  `weight` varchar(10) CHARACTER SET utf8 NOT NULL,
  `height` varchar(10) CHARACTER SET utf8 NOT NULL,
  `type` varchar(20) NOT NULL,
  `Status` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `pic_profile`, `first_name`, `last_name`, `email`, `password`, `gender`, `Tel`, `id_card`, `bd_date`, `weight`, `height`, `type`, `Status`) VALUES
(84, 'https://firebasestorage.googleapis.com/v0/b/vistual-running.appspot.com/o/images%2F42ade531-ee55-41f8-88ab-758e1229e0ac?alt=media&token=6d934d51-9f67-4f99-b4ca-73e821bff2bf', 'Rambo', 'Rambo', 'kitinun@kkumail.com', '1234567', 'ชาย', '1145', '11456789', '23/4/2006', '56456456', '4564654', 'ผู้จัดกิจกรรม', ''),
(85, 'https://firebasestorage.googleapis.com/v0/b/vistual-running.appspot.com/o/images%2Feec3f5a8-659b-4802-a920-36498cb285dd?alt=media&token=3afdf172-1a16-44cf-9835-9c1cbd52355d', 'ติณห์', 'ผุยคำพิ', 'teerit_sa@kkumail.com', '123456', 'ชาย', '0845631489', '1300101186415', '22/4/2021', '464654', '456456', 'นักวิ่ง', ''),
(91, '', 'นฤนาท', 'คุณธรรม', 'narukh@kku.ac.th', '123456789', 'หญิง', '', '', '1/2/1986', '82', '158', 'นักวิ่ง', ''),
(92, '', ' สุคนธ์', 'บุญจันทร์', 'ksukho@kku.ac.th', '12346', 'หญิง', '', '', '18/7/1975', '57', '155', 'นักวิ่ง', ''),
(94, 'https://firebasestorage.googleapis.com/v0/b/vistual-running.appspot.com/o/images%2F42664f79-4540-424f-9335-dee71b514a30?alt=media&token=08f3bec1-9ab0-4780-8a03-3dd404f22653', 'test', 'test', 'gunza.za3344@gmail.com', '123456', 'ชาย', '120', '11', '23/4/2021', '', '', 'นักวิ่ง', '');

-- --------------------------------------------------------

--
-- Table structure for table `user_admin`
--

CREATE TABLE `user_admin` (
  `id` int(11) NOT NULL,
  `username` varchar(10) NOT NULL,
  `password` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_admin`
--

INSERT INTO `user_admin` (`id`, `username`, `password`) VALUES
(0, 'admin2', '1234'),
(1, 'admin1', '1234');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `add_event`
--
ALTER TABLE `add_event`
  ADD PRIMARY KEY (`id_add`);

--
-- Indexes for table `detail_distance`
--
ALTER TABLE `detail_distance`
  ADD PRIMARY KEY (`Detail_id`);

--
-- Indexes for table `history_payment`
--
ALTER TABLE `history_payment`
  ADD PRIMARY KEY (`id_history_payment`);

--
-- Indexes for table `history_uploadstatic`
--
ALTER TABLE `history_uploadstatic`
  ADD PRIMARY KEY (`id_history_UploadStatic`);

--
-- Indexes for table `reg_address`
--
ALTER TABLE `reg_address`
  ADD PRIMARY KEY (`id_address`);

--
-- Indexes for table `reg_event`
--
ALTER TABLE `reg_event`
  ADD PRIMARY KEY (`id_reg_event`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- Indexes for table `user_admin`
--
ALTER TABLE `user_admin`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `add_event`
--
ALTER TABLE `add_event`
  MODIFY `id_add` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=196;

--
-- AUTO_INCREMENT for table `detail_distance`
--
ALTER TABLE `detail_distance`
  MODIFY `Detail_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=94;

--
-- AUTO_INCREMENT for table `history_payment`
--
ALTER TABLE `history_payment`
  MODIFY `id_history_payment` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT for table `history_uploadstatic`
--
ALTER TABLE `history_uploadstatic`
  MODIFY `id_history_UploadStatic` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT for table `reg_address`
--
ALTER TABLE `reg_address`
  MODIFY `id_address` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=76;

--
-- AUTO_INCREMENT for table `reg_event`
--
ALTER TABLE `reg_event`
  MODIFY `id_reg_event` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=135;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=95;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
