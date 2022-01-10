SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE DATABASE IF NOT EXISTS `paymybuddy` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `paymybuddy`;

DROP TABLE IF EXISTS `connections`;
CREATE TABLE `connections` (
  `id` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `connectionid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `transactions`;
CREATE TABLE `transactions` (
  `id` int(11) NOT NULL,
  `amount` double NOT NULL,
  `description` text NOT NULL,
  `transmitter` int(11) NOT NULL,
  `receiver` int(11) NOT NULL,
  `fee` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `mail` text NOT NULL,
  `pseudo` text NOT NULL,
  `password` text NOT NULL,
  `currency` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


ALTER TABLE `connections`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);


ALTER TABLE `connections`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;