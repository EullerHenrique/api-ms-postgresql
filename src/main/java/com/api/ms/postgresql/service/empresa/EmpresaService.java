package com.api.ms.postgresql.service.empresa;

import com.api.ms.postgresql.domain.model.Empresa;
import com.api.ms.postgresql.domain.dto.EmpresaDTO;
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
public interface EmpresaService {

    EmpresaDTO findById(Long id);
    EmpresaDTO findByIdAndPassword(Long id, String password);
    Set<Object> findAll();
    void save(Empresa empresa);


}
