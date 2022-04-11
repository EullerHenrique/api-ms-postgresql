package com.api.ms.postgresql.service.cliente;

import com.api.ms.postgresql.domain.dto.ClienteDTO;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.Set;

//@Validated


@Validated

//@Transactional

//https://www.devmedia.com.br/conheca-o-spring-transactional-annotations/32472
//"A boa prática é sempre colocar o @Transactional nos métodos que precisam de transação, por exemplo: salvar, alterar,
//excluir, etc., pois assim você garante que eles vão ser executados dentro um contexto transacional e o rollback
//será feito caso ocorra algum erro

@Transactional
public interface ClienteService {

    ClienteDTO findById(Long id);
    Set<Object> findAll();
    void save(ClienteDTO cliente);
    void deleteById(Long id);

}
