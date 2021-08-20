package com.ssti.avaliacao.api.service;

import com.ssti.avaliacao.api.bo.PautaBO;
import com.ssti.avaliacao.api.event.RecursoCriadoEvent;
import com.ssti.avaliacao.api.model.Pauta;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/pauta")
public class SvcPauta {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private PautaBO bo;

    @GetMapping("/listar")
    public List<Pauta> listar() throws Exception {
        return bo.listar();
    }

    @PostMapping("/criar")
    public ResponseEntity<Pauta> criar(@Valid @RequestBody Pauta pauta, HttpServletResponse response) throws Exception {
        Pauta pautaSalvo = bo.salvar(pauta);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, pautaSalvo.getIdPauta()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pautaSalvo);
    }

    @GetMapping("buscar-por-id/{id}")
    public ResponseEntity<Pauta> buscarPorId(@PathVariable Long id) {
        Pauta pauta = bo.buscarPorId(id);
        return pauta != null ? ResponseEntity.ok(pauta) : ResponseEntity.notFound().build();
    }

    @GetMapping("abrir-pauta/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void abrirPauta(@PathVariable Long id) {
        CompletableFuture.runAsync(() -> {
            try {
                bo.abrirPauta(id);
            } catch (Exception ex) {
                Logger.getLogger(Pauta.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
