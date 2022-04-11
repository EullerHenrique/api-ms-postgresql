package com.api.ms.postgresql.service.empresa;

import com.api.ms.postgresql.domain.dto.ContratoDTO;
import com.api.ms.postgresql.domain.model.Empresa;
import com.api.ms.postgresql.domain.model.Token;
import com.api.ms.postgresql.repository.EmpresaRepository;
import com.api.ms.postgresql.domain.dto.EmpresaDTO;
import com.api.ms.postgresql.domain.dto.util.CreateOrConvertDTO;
import lombok.RequiredArgsConstructor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

//@Service

//A anotação @Service é usada em sua camada de serviço e anota classes que realizam tarefas de serviço, muitas vezes
//você não a usa, mas em muitos casos você usa essa anotação para representar uma prática recomendada. Por exemplo,
//você poderia chamar diretamente uma classe DAO para persistir um objeto em seu banco de dados, mas isso é horrível.
//É muito bom chamar uma classe de serviço que chama um DAO. Isso é uma boa coisa para executar o padrão de separação
//de interesses.

@Service

//@Transactional
//https://www.devmedia.com.br/conheca-o-spring-transactional-annotations/32472
//"A boa prática é sempre colocar o @Transactional nos métodos que precisam de transação, por exemplo: salvar, alterar,
//excluir, etc., pois assim você garante que eles vão ser executados dentro um contexto transacional e o rollback
//será feito caso ocorra algum erro."

@Transactional

//@RequiredArgsConstructor
//Gera um construtor com argumentos necessários. Os argumentos obrigatórios são campos finais e campos com restrições como @NonNull.

@RequiredArgsConstructor

public class EmpresaServiceImp implements EmpresaService{

    /*

    Interface usada para interagir com o contexto de persistência.
    Uma instância de EntityManager está associada a um contexto de persistência. Um contexto de persistência é um
    conjunto de instâncias de entidade em que para qualquer identidade de entidade persistente existe uma instância de
    entidade única. Dentro do contexto de persistência, as instâncias da entidade e seu ciclo de vida são gerenciados.
    A API EntityManager é usada para criar e remover instâncias de entidades persistentes, para localizar entidades
    por sua chave primária e para consultar entidades.
    O conjunto de entidades que podem ser gerenciadas por uma determinada instância de EntityManager é definido por uma
    unidade de persistência. Uma unidade de persistência define o conjunto de todas as classes que estão relacionadas
    ou agrupadas pela aplicação, e que devem ser colocadas em seu mapeamento para um único banco de dados.

    */
    @PersistenceContext
    private EntityManager em;

    private final EmpresaRepository empresaRepository;

    @Override
    public EmpresaDTO findById(Long id) {

        return (EmpresaDTO) empresaRepository.findById(id).map(CreateOrConvertDTO::create).orElse(null);

    }

    @Override
    public EmpresaDTO findByIdAndPassword(Long id, String password){

        Session session = em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Empresa.class);

        //WHERE id={id}
        criteria.add(Restrictions.idEq(id));
        //AND WHERE password={password}
        criteria.add(Restrictions.eq("password", password));

        try {
            Empresa empresa = (Empresa) criteria.uniqueResult();
            return (EmpresaDTO) CreateOrConvertDTO.create(empresa);
        }catch (NullPointerException e){
            return null;
        }

    }

    @Override
    public Set<Object> findAll() {
        return empresaRepository.findAll().stream().map(CreateOrConvertDTO::create).collect(Collectors.toSet());

    }


    @Override
    public void save(Empresa empresa){
        empresaRepository.save(empresa);
    }




}

//criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//criteria.createAlias("contratos", "c", JoinType.LEFT_OUTER_JOIN);