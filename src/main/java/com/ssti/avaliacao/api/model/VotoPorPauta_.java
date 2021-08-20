package com.ssti.avaliacao.api.model;

import java.time.LocalDateTime;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(VotoPorPauta.class)
public class VotoPorPauta_ {

    public static volatile SingularAttribute<VotoPorPauta, Long> idVotoPorPauta;
    public static volatile SingularAttribute<VotoPorPauta, String> voto;
    public static volatile SingularAttribute<VotoPorPauta, LocalDateTime> dataRegistro;
    public static volatile SingularAttribute<VotoPorPauta, Pessoa> pessoa;
    public static volatile SingularAttribute<VotoPorPauta, Pauta> pauta;

}
