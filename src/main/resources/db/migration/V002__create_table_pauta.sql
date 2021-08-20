CREATE TABLE IF NOT EXISTS `pauta` (
  `id_pauta` INT(11) NOT NULL AUTO_INCREMENT,
  `descricao` TEXT NULL DEFAULT NULL,
  `tempo_duracao` INT(11) NULL DEFAULT 60,
  `data_registro` DATETIME NULL DEFAULT NULL,
  `data_abertura` DATETIME NULL DEFAULT NULL,
  `data_fechamento` DATETIME NULL DEFAULT NULL,
  `situacao` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id_pauta`));