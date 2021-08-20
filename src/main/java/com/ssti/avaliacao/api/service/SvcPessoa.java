package com.ssti.avaliacao.api.service;

import com.ssti.avaliacao.api.bo.PessoaBO;
import com.ssti.avaliacao.api.event.RecursoCriadoEvent;
import com.ssti.avaliacao.api.integracaouserinfo.IntegracaoUserInfo;
import com.ssti.avaliacao.api.model.Pessoa;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
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
@RequestMapping("/v1/pessoa")
public class SvcPessoa {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private PessoaBO bo;

    @GetMapping("/listar")
    public List<Pessoa> listar() throws Exception {
        return bo.listar();
    }

    @PostMapping("/criar")
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) throws Exception {
        pessoa.setDataRegistro(LocalDateTime.now());
        Pessoa pessoaSalvo = bo.salvar(pessoa);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalvo.getIdPessoa()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalvo);
    }

    @GetMapping("buscar-por-id/{id}")
    public ResponseEntity<Pessoa> buscarPorId(@PathVariable Long id) {
        Pessoa pessoa = bo.buscarPorId(id);
        return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
    }

}
