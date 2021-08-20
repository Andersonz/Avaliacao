package com.ssti.avaliacao.api.model;

import java.time.LocalDateTime;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Pessoa.class)
public class Pessoa_ {

    public static volatile SingularAttribute<Pessoa, Long> idPessoa;
    public static volatile SingularAttribute<Pessoa, String> nome;
    public static volatile SingularAttribute<Pessoa, String> cpf;
    public static volatile SingularAttribute<Pessoa, LocalDateTime> dataRegistro;

}
