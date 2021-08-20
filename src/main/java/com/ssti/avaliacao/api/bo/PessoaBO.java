package com.ssti.avaliacao.api.bo;

import com.ssti.avaliacao.api.model.Pessoa;
import com.ssti.avaliacao.api.model.Pessoa_;
import com.ssti.avaliacao.api.repository.PessoaRepository;
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
public class PessoaBO {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private PessoaRepository thisBo;

    public List<Pessoa> listar() throws Exception {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
        Root<Pessoa> root = criteria.from(Pessoa.class);

        criteria.orderBy(builder.asc(root.get(Pessoa_.nome)));

        TypedQuery<Pessoa> query = manager.createQuery(criteria);

        return query.getResultList();
    }

    public Pessoa salvar(Pessoa pessoa) throws Exception {
        Pessoa pessoaSalvo = thisBo.save(pessoa);

        return pessoaSalvo;
    }

    public Pessoa buscarPorId(Long id) {
        return thisBo.findById(id).get();
    }

}
