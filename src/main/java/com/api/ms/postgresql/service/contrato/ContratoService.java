package com.api.ms.postgresql.service.contrato;

import com.api.ms.postgresql.domain.model.Contrato;
import com.api.ms.postgresql.domain.dto.ContratoDTO;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.Set;

@Validated

//@Transactional

//https://www.devmedia.com.br/conheca-o-spring-transactional-annotations/32472
//"A boa prática é sempre colocar o @Transactional nos métodos que precisam de transação, por exemplo: salvar, alterar,
//excluir, etc., pois assim você garante que eles vão ser executados dentro um contexto transacional e o rollback
//será feito caso ocorra algum erro

@Transactional
public interface ContratoService {

    ContratoDTO findById(Long id);
    Set<Object> findAll();
    void save(Contrato contrato);
    void deleteById(Long id);

}
