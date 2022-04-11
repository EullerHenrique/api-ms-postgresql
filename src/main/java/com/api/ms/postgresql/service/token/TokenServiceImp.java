package com.api.ms.postgresql.service.token;

import com.api.ms.postgresql.domain.model.Token;
import com.api.ms.postgresql.repository.TokenRepository;
import com.api.ms.postgresql.domain.dto.TokenDTO;
import com.api.ms.postgresql.domain.dto.util.CreateOrConvertDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

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



public class TokenServiceImp implements TokenService{

    private final TokenRepository tokenRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public TokenDTO gerarToken(Map<String, String> headers, Map<String, String> grants){

        if (grants.containsKey("grant_type") && grants.containsValue("client_credentials")) {


            Token token = Token.builder()
                    .accessToken(java.util.UUID.randomUUID().toString())
                    .expiresIn(Long.toString(LocalDateTime.now().plusMinutes(2).toInstant(ZoneOffset.UTC).toEpochMilli()))
                    .dataCriacao(LocalDateTime.now())
                    .tokenType("bearer")
                    .build();

            return (TokenDTO) CreateOrConvertDTO.create(tokenRepository.save(token));

        }

        return null;

    }

    public boolean verifyToken(String token) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Token> cq = cb.createQuery(Token.class);
        Root<Token> t = cq.from(Token.class);

        Predicate tokenPredicate = cb.equal(t.get("accessToken"), token);
        cq.where(tokenPredicate);

        TypedQuery<Token> query = em.createQuery(cq);

        try{
            query.getSingleResult();
            return true;
        }catch (Exception e){
            return false;
        }

    }


}
