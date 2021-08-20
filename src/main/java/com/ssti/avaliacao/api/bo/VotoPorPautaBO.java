package com.ssti.avaliacao.api.bo;

import com.ssti.avaliacao.api.dto.ResultadoVotacaoDTO;
import com.ssti.avaliacao.api.integracaouserinfo.IntegracaoUserInfo;
import com.ssti.avaliacao.api.model.Pauta;
import com.ssti.avaliacao.api.model.Pessoa;
import com.ssti.avaliacao.api.model.VotoPorPauta;
import com.ssti.avaliacao.api.model.VotoPorPauta_;
import com.ssti.avaliacao.api.repository.VotoPorPautaRepository;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ANDERSON
 */
@Service
public class VotoPorPautaBO {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private VotoPorPautaRepository thisBo;

    @Autowired
    private PautaBO boPauta;

    @Autowired
    private PessoaBO boPessoa;

    public List<VotoPorPauta> listar() throws Exception {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<VotoPorPauta> criteria = builder.createQuery(VotoPorPauta.class);
        Root<VotoPorPauta> root = criteria.from(VotoPorPauta.class);

        criteria.orderBy(builder.asc(root.get(VotoPorPauta_.dataRegistro)));

        TypedQuery<VotoPorPauta> query = manager.createQuery(criteria);

        return query.getResultList();
    }

    public VotoPorPauta salvar(VotoPorPauta votoPorPauta) throws Exception {

        this.validarVoto(votoPorPauta);
        votoPorPauta.setDataRegistro(LocalDateTime.now());
        VotoPorPauta votoPorPautaSalvo = thisBo.save(votoPorPauta);

        return votoPorPautaSalvo;
    }

    private void validarVoto(VotoPorPauta votoPorPauta) throws Exception {
        if (votoPorPauta.getPauta() == null) {
            throw new Exception("É necessário informar a Pauta para votar.");
        }
        if (votoPorPauta.getPessoa() == null) {
            throw new Exception("É necessário informar a Pessoa para votar.");
        }
        Pessoa pessoa = this.boPessoa.buscarPorId(votoPorPauta.getPessoa().getIdPessoa());
        Pauta pauta = this.boPauta.buscarPorId(votoPorPauta.getPauta().getIdPauta());

        if (pessoa == null) {
            throw new Exception("Pessoa inexistente.");
        } else if (pauta == null) {
            throw new Exception("Pauta inexistente.");
        } else if (!pauta.getSituacao().equals(Pauta.PautaSituacaoEnum.VOTACAO_EM_ANDAMENTO.toString())) {
            throw new Exception("Votação não está recebendo votos.");
        } else if (votoPorPauta.getVoto() == null) {
            throw new Exception("É necessário informar o valor do seu voto.");
        } else if (!votoPorPauta.getVoto()
                .equals(VotoPorPauta.VotoValorEnum.SIM.toString())
                && !votoPorPauta.getVoto().equals(VotoPorPauta.VotoValorEnum.NAO.toString())) {
            throw new Exception("Somente são aceitos os valores SIM/NAO para votação.");
        }

        if (pessoa.getCpf() != null) {
            IntegracaoUserInfo.ResultadoUsersCpf resultado = IntegracaoUserInfo.verificaCpf(pessoa.getCpf());
            if (resultado == null) {
                throw new Exception("Usuário não pode executar essa operação. CPF Inválido.");
            } else if (!resultado.getStatus().equals(IntegracaoUserInfo.ResultadoUsersCpfStatusEnum.ABLE_TO_VOTE.toString())) {
                throw new Exception("Usuário não pode executar essa operação. Status:" + resultado.getStatus());
            }
        } else {
            throw new Exception("Usuário não pode votar pois não possui o cpf cadastrado");
        }

        List<VotoPorPauta> listaVoto = this.buscarPorIdPautaEIdPessoa(pauta.getIdPauta(), votoPorPauta.getPessoa().getIdPessoa());
        if (!listaVoto.isEmpty()) {
            throw new Exception("O associado pode votar somente uma vez por pauta.");
        }

    }

    public VotoPorPauta buscarPorId(Long id) {
        return thisBo.findById(id).get();
    }

    private List<VotoPorPauta> buscarPorIdPautaEIdPessoa(Long idPauta, Long idPessoa) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<VotoPorPauta> criteria = builder.createQuery(VotoPorPauta.class);
        Root<VotoPorPauta> root = criteria.from(VotoPorPauta.class);

        Predicate predicatePessoa = builder.equal(root.get(VotoPorPauta_.pessoa), idPessoa);
        Predicate predicatePauta = builder.equal(root.get(VotoPorPauta_.pauta), idPauta);

        Predicate predicate = builder.and(predicatePessoa, predicatePauta);

        criteria.where(predicate);

        TypedQuery<VotoPorPauta> query = manager.createQuery(criteria);

        return query.getResultList();
    }

    public List<ResultadoVotacaoDTO> pesquisarResultadoVotacao(Long idPauta) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<ResultadoVotacaoDTO> criteria = builder.
                createQuery(ResultadoVotacaoDTO.class);

        Root<VotoPorPauta> root = criteria.from(VotoPorPauta.class);

        criteria.select(builder.construct(ResultadoVotacaoDTO.class,
                root.get(VotoPorPauta_.voto),
                builder.count(root)
        ));

        Predicate predicate = builder.equal(root.get(VotoPorPauta_.pauta), idPauta);

        criteria.where(predicate);

        criteria.groupBy(root.get(VotoPorPauta_.voto));

        TypedQuery<ResultadoVotacaoDTO> typedQuery = manager
                .createQuery(criteria);
        List<ResultadoVotacaoDTO> listaResultado = typedQuery.getResultList();

        return listaResultado;

    }

}
