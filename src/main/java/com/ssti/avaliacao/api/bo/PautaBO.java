package com.ssti.avaliacao.api.bo;

import com.ssti.avaliacao.api.model.Pauta;
import com.ssti.avaliacao.api.model.Pauta_;
import com.ssti.avaliacao.api.repository.PautaRepository;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ANDERSON
 */
@Service
public class PautaBO {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private PautaRepository thisBo;

    public List<Pauta> listar() throws Exception {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Pauta> criteria = builder.createQuery(Pauta.class);
        Root<Pauta> root = criteria.from(Pauta.class);

        criteria.orderBy(builder.asc(root.get(Pauta_.idPauta)));

        TypedQuery<Pauta> query = manager.createQuery(criteria);

        return query.getResultList();
    }

    public Pauta salvar(Pauta pauta) throws Exception {
        pauta.setSituacao(Pauta.PautaSituacaoEnum.AGUARDANDO_VOTACAO.toString());
        pauta.setDataRegistro(LocalDateTime.now());
        if (pauta.getTempoDuracao() == null) {
            /* 
            Se o tempo de duração não for informado, devo atribuir o valor 1 que se refere à 1 minuto
             */
            pauta.setTempoDuracao(1L);
        }

        Pauta pautaSalvo = thisBo.save(pauta);

        return pautaSalvo;
    }

    public Pauta buscarPorId(Long id) {
        return thisBo.findById(id).get();
    }

    public void abrirPauta(Long id) throws Exception {
        Pauta pauta = this.buscarPorId(id);
        if (pauta == null) {
            throw new Exception("Pauta com id: " + id + " inexistente.");
        } else if (pauta.getSituacao().equals(Pauta.PautaSituacaoEnum.VOTACAO_EM_ANDAMENTO.toString())
                || pauta.getSituacao().equals(Pauta.PautaSituacaoEnum.VOTACAO_ENCERRADA.toString())) {
            throw new Exception("Impossível abrir votação para Pauta com id: " + id + ", a votação EM ANDAMENTO ou ENCERRADA.");
        }

        try {

            pauta.setSituacao(Pauta.PautaSituacaoEnum.VOTACAO_EM_ANDAMENTO.toString());
            pauta.setDataAbertura(LocalDateTime.now());
            pauta = thisBo.save(pauta);
            System.out.println("Abriu votação");

            Long duracaoEmMinutos = pauta.getTempoDuracao();
            if (duracaoEmMinutos == null) {
                /* 
                 Verifico se o tempo duração está presente, em caso contrário devo atribuir o valor 1 que se refere à 1 minuto
                 */
                duracaoEmMinutos = 1L;
            }

            // 60000 corresponde à 1(um) minuto
            Thread.sleep(60000 * duracaoEmMinutos);
            /*
            Aguardo até o término da votação para então fechar a mesma
             */

            this.fecharPauta(id);
        } catch (Exception e) {
            System.err.println("Erro ao fechar pauta: " + e.getMessage());
        }
    }

    public Pauta fecharPauta(Long id) {
        Pauta pauta = this.buscarPorId(id);
        pauta.setSituacao(Pauta.PautaSituacaoEnum.VOTACAO_ENCERRADA.toString());
        pauta.setDataFechamento(LocalDateTime.now());
        pauta = thisBo.save(pauta);

        return pauta;
    }

}
