CREATE TABLE `books` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `author` CLOB,
  `launch_date` TIMESTAMP NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `title` CLOB
);
