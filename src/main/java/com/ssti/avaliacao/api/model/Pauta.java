package com.ssti.avaliacao.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author ANDERSON
 */
@Entity
@Table(name = "pauta")
public class Pauta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pauta")
    private Long idPauta;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "tempo_duracao")
    private Long tempoDuracao;
    @Column(name = "data_registro")
    private LocalDateTime dataRegistro;
    @Column(name = "data_abertura")
    private LocalDateTime dataAbertura;
    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;
    @Size(max = 45)
    @Column(name = "situacao")
    private String situacao;

    public Pauta() {
    }

    public Long getIdPauta() {
        return idPauta;
    }

    public void setIdPauta(Long idPauta) {
        this.idPauta = idPauta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getTempoDuracao() {
        return tempoDuracao;
    }

    public void setTempoDuracao(Long tempoDuracao) {
        this.tempoDuracao = tempoDuracao;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDateTime getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public enum PautaSituacaoEnum {

        AGUARDANDO_VOTACAO, VOTACAO_EM_ANDAMENTO, VOTACAO_ENCERRADA;
    }

}
