package com.ssti.avaliacao.api.dto;

/**
 *
 * @author Anderson
 */
public class ResultadoVotacaoDTO {

    private String valor;
    private Long total;

    public ResultadoVotacaoDTO(String valor, Long total) {
        this.valor = valor;
        this.total = total;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

}
