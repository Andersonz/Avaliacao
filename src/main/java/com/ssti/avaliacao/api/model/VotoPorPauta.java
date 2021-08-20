package com.ssti.avaliacao.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author ANDERSON
 */
@Entity
@Table(name = "voto_por_pauta")
public class VotoPorPauta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_voto_por_pauta")
    private Long idVotoPorPauta;
    @Size(max = 3)
    @Column(name = "voto")
    private String voto;
    @Column(name = "data_registro")
    private LocalDateTime dataRegistro;
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id_pessoa")
    @ManyToOne(optional = false)
    private Pessoa pessoa;
    @JoinColumn(name = "id_pauta", referencedColumnName = "id_pauta")
    @ManyToOne(optional = false)
    private Pauta pauta;

    public VotoPorPauta() {
    }

    public Long getIdVotoPorPauta() {
        return idVotoPorPauta;
    }

    public void setIdVotoPorPauta(Long idVotoPorPauta) {
        this.idVotoPorPauta = idVotoPorPauta;
    }

    public String getVoto() {
        return voto;
    }

    public void setVoto(String voto) {
        this.voto = voto;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Pauta getPauta() {
        return pauta;
    }

    public void setPauta(Pauta pauta) {
        this.pauta = pauta;
    }

    public enum VotoValorEnum {

        SIM, NAO;
    }

}
