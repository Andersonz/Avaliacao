CREATE TABLE IF NOT EXISTS `voto_por_pauta` (
  `id_voto_por_pauta` INT(11) NOT NULL AUTO_INCREMENT,
  `voto` VARCHAR(3) NULL DEFAULT NULL,
  `data_registro` DATETIME NULL DEFAULT NULL,
  `id_pessoa` INT(11) NOT NULL,
  `id_pauta` INT(11) NOT NULL,
  PRIMARY KEY (`id_voto_por_pauta`),
  INDEX `fk_pessoa_has_pauta_pauta1_idx` (`id_pauta` ASC),
  INDEX `fk_pessoa_has_pauta_pessoa_idx` (`id_pessoa` ASC),
  CONSTRAINT `fk_pessoa_has_pauta_pessoa`
    FOREIGN KEY (`id_pessoa`)
    REFERENCES `pessoa` (`id_pessoa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pessoa_has_pauta_pauta1`
    FOREIGN KEY (`id_pauta`)
    REFERENCES `pauta` (`id_pauta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);