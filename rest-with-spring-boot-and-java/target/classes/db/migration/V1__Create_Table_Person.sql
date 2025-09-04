

CREATE TABLE IF NOT EXISTS `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(80) NOT NULL,
  `last_name` varchar(80) NOT NULL,
  `address` varchar(100) NOT NULL,
  `gender` varchar(6) NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `person` (`id`, `address`, `first_name`, `gender`, `last_name`) VALUES
    (1, 'São Paulo - Brasil', 'Ayrton', 'Male', 'Senna'),
    (2, 'Anchiano - Italy', 'Leonardo', 'Male', 'da Vinci'),
    (3, 'Porbandar - India', 'Indira', 'Female', 'Gandhi'),
    (4, 'São Paulo - Brasil', 'Ayrton', 'Male', 'Senna'),
    (5, 'São Paulo - Brasil', 'Ayrton', 'Male', 'Senna'),
    (6, 'São Paulo - Brasil', 'Ayrton', 'Male', 'Senna'),
    (7, 'São Paulo - Brasil', 'Ayrton', 'Male', 'Senna'),
    (8, 'São Paulo - Brasil', 'Ayrton', 'Male', 'Senna'),
    (9, 'São Paulo - Brasil', 'Ayrton', 'Male', 'Senna'),
    (10, 'São Paulo - Brasil', 'Ayrton', 'Male', 'Senna'),
    (11, 'São Paulo - Brasil', 'Ayrton', 'Male', 'Senna'),
    (12, 'São Paulo - Brasil', 'Ayrton', 'Male', 'Senna'),