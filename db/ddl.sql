

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE='NO_AUTO_VALUE_ON_ZERO', SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table operation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `operation`;

CREATE TABLE `operation` (
  `id_operation` bigint NOT NULL AUTO_INCREMENT,
  `type` enum('ADDITION','SUBSTRACTION','MULTIPLICATION','DIVISION','SQUARE_ROOT','RANDOM_STRING') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cost` double DEFAULT NULL,
  PRIMARY KEY (`id_operation`),
  UNIQUE KEY `uq_type` (`type`)
) ENGINE=InnoDB DEFAULT;



# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id_user` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `uq_username` (`username`)
) ENGINE=InnoDB DEFAULT;



# Dump of table user_record
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_record`;

CREATE TABLE `user_record` (
  `id_record` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint NOT NULL,
  `id_user_deleted` bigint DEFAULT NULL,
  `id_operation` bigint DEFAULT NULL,
  `amount` double NOT NULL,
  `user_balance` double NOT NULL,
  `operation_response` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `date_inserted` datetime NOT NULL,
  `date_inserted_uts` bigint NOT NULL,
  `date_deleted` datetime DEFAULT NULL,
  `date_deleted_uts` bigint DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `success` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_record`),
  KEY `idx_id_user` (`id_user`),
  KEY `idx_id_operation` (`id_operation`),
  KEY `idx_date_insert_uts` (`date_inserted_uts`),
  KEY `fk_record_userdeleted` (`id_user_deleted`),
  KEY `idx_success` (`success`),
  KEY `idx_deleted` (`deleted`),
  CONSTRAINT `fk_record_operation` FOREIGN KEY (`id_operation`) REFERENCES `operation` (`id_operation`),
  CONSTRAINT `fk_record_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`),
  CONSTRAINT `fk_record_userdeleted` FOREIGN KEY (`id_user_deleted`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB DEFAULT;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
