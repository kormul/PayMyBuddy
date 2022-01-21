SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE DATABASE IF NOT EXISTS `paymybuddy` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `paymybuddy`;

DROP TABLE IF EXISTS `connections`;
CREATE TABLE `connections` (
  `id` int(11) NOT NULL,
  `connection_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `transactions`;
CREATE TABLE `transactions` (
  `id` int(11) NOT NULL,
  `amount` double DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `fee` double DEFAULT NULL,
  `receiver` int(11) DEFAULT NULL,
  `transmitter` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `currency` double DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `pseudo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


ALTER TABLE `connections`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK62y8qblm7a5kvbeigy3lm08lp` (`connection_id`),
  ADD KEY `FKltpo1ymtaafd67hx5tls1db6u` (`user_id`);

ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK9o43uaqw7vwkwhukuesd0t336` (`receiver`),
  ADD KEY `FKqep1wbn39twjnghxtbyt5hw4r` (`transmitter`);

ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `connections`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `connections`
  ADD CONSTRAINT `FK62y8qblm7a5kvbeigy3lm08lp` FOREIGN KEY (`connection_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKltpo1ymtaafd67hx5tls1db6u` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `transactions`
  ADD CONSTRAINT `FK9o43uaqw7vwkwhukuesd0t336` FOREIGN KEY (`receiver`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKqep1wbn39twjnghxtbyt5hw4r` FOREIGN KEY (`transmitter`) REFERENCES `users` (`id`);
COMMIT;