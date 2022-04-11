package com.api.ms.postgresql.service.contrato;

import com.api.ms.postgresql.domain.dto.ContratoDTO;
import com.api.ms.postgresql.domain.dto.util.CreateOrConvertDTO;
import com.api.ms.postgresql.domain.model.Contrato;
import com.api.ms.postgresql.repository.ContratoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

public class ContratoServiceImp  implements ContratoService{

    private final ContratoRepository contratoRepository;

    @Override
    public ContratoDTO findById(Long id) {
        return (ContratoDTO) contratoRepository.findById(id).map(CreateOrConvertDTO::create).orElse(null);
    }

    @Override
    public Set<Object> findAll() {
        return contratoRepository.findAll().stream().map(CreateOrConvertDTO::create).collect(Collectors.toSet());
    }

    @Override
    public void save(Contrato contrato) {

        contratoRepository.save(contrato);
    }

    @Override
    public void deleteById(Long id) {
        contratoRepository.deleteById(id);
    }


}
