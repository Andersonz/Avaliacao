package com.ssti.avaliacao.api.model;

import java.time.LocalDateTime;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Pauta.class)
public class Pauta_ {

    public static volatile SingularAttribute<Pauta, Long> idPauta;
    public static volatile SingularAttribute<Pauta, String> descricao;
    public static volatile SingularAttribute<Pauta, Long> tempoDuracao;
    public static volatile SingularAttribute<Pauta, LocalDateTime> dataRegistro;
    public static volatile SingularAttribute<Pauta, LocalDateTime> dataAbertura;
    public static volatile SingularAttribute<Pauta, LocalDateTime> dataFechamento;
    public static volatile SingularAttribute<Pauta, String> situacao;

}
