package com.ssti.avaliacao.api.service;

import com.ssti.avaliacao.api.bo.VotoPorPautaBO;
import com.ssti.avaliacao.api.dto.ResultadoVotacaoDTO;
import com.ssti.avaliacao.api.event.RecursoCriadoEvent;
import com.ssti.avaliacao.api.model.VotoPorPauta;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/voto-por-pauta")
public class SvcVotoPorPauta {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private VotoPorPautaBO bo;

    @GetMapping("/listar")
    public List<VotoPorPauta> listar() throws Exception {
        return bo.listar();
    }

    @PostMapping("/votar")
    public ResponseEntity<VotoPorPauta> votar(@Valid @RequestBody VotoPorPauta votoPorPauta, HttpServletResponse response) throws Exception {
        VotoPorPauta votoPorPautaSalvo = bo.salvar(votoPorPauta);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, votoPorPautaSalvo.getIdVotoPorPauta()));
        return ResponseEntity.status(HttpStatus.CREATED).body(votoPorPautaSalvo);
    }

    @GetMapping("buscar-por-id/{id}")
    public ResponseEntity<VotoPorPauta> buscarPorId(@PathVariable Long id) {
        VotoPorPauta votoPorPauta = bo.buscarPorId(id);
        return votoPorPauta != null ? ResponseEntity.ok(votoPorPauta) : ResponseEntity.notFound().build();
    }

    @GetMapping("/buscar-resultado-votacao")
    public List<ResultadoVotacaoDTO> listar(@RequestParam Long idPauta) throws Exception {
        return bo.pesquisarResultadoVotacao(idPauta);
    }

}
