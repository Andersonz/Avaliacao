package com.ssti.avaliacao.api.repository;

import com.ssti.avaliacao.api.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
