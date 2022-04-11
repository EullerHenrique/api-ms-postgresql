package com.api.ms.postgresql.endpoint;

import com.api.ms.postgresql.domain.dto.ContratoDTO;
import com.api.ms.postgresql.domain.dto.responses.MultipleDataResponseMessage;
import com.api.ms.postgresql.domain.dto.responses.SingleDataResponseMessage;
import com.api.ms.postgresql.domain.dto.util.CreateOrConvertDTO;
import com.api.ms.postgresql.domain.model.Contrato;
import com.api.ms.postgresql.domain.view.Views;
import com.api.ms.postgresql.service.contrato.ContratoService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//@RestController: @Controller + @ResponseBody

//@Controller

//A anotação @Controller é uma anotação usada no framework Spring MVC (o componente do Spring Framework
//usado para implementar o aplicativo da Web). A anotação @Controller indica que uma classe particular serve como
//controlador. A anotação @Controller atua como um estereótipo para a classe anotada, indicando sua função.
//O despachante verifica essas classes anotadas em busca de métodos mapeados e detecta as anotações @RequestMapping.

//@ResponseBody em cada metódo
//A anotação @ResponseBody informa a um controlador que o objeto retornado é serializado automaticamente
//em JSON e passado de volta para o objeto HttpResponse .

@RestController

//@RequestMapping

//A anotação @RequestMapping mapeia uma classe, ou seja, associa uma URI a uma classe. Ao acessar a URL,
//os metódos mapeados da classe podem ser acessados.

@RequestMapping("/contrato/v1")

//@RequiredArgsConstructor
//Gera um construtor com argumentos necessários. Os argumentos obrigatórios são campos finais e campos com restrições como @NonNull.

@RequiredArgsConstructor

public class ContratoController {

    private final ContratoService contratoService;

    @JsonView({Views.ContratoView.class})
    @GetMapping(value = "/{id}")
    public SingleDataResponseMessage findById(@PathVariable Long id) {
        try {
            ContratoDTO contrato = contratoService.findById(id);
            if (contrato != null) {
                return SingleDataResponseMessage.builder().status(200).data(contrato).build();
            } else {
                return SingleDataResponseMessage.builder().status(404).data(null).build();
            }
        } catch (Exception ex) {
            return SingleDataResponseMessage.builder().error(ex.toString()).message(ex.getMessage()).status(500)
                    .build();
        }
    }

    @JsonView({Views.ContratoView.class})
    @GetMapping("/all")
    public MultipleDataResponseMessage findAll() {
        try {
            return MultipleDataResponseMessage.builder().status(200).data(contratoService.findAll()).build();
        } catch (Exception ex) {
            return MultipleDataResponseMessage.builder().error(ex.toString()).message(ex.getMessage()).status(500)
                    .build();
        }
    }


    @PostMapping("/save")
    public SingleDataResponseMessage save(@RequestBody ContratoDTO contratoDTO) {
        try {
            contratoService.save((Contrato) CreateOrConvertDTO.convert(contratoDTO));
            return SingleDataResponseMessage.builder().status(201).data(null).build();
        } catch (Exception ex) {
            return SingleDataResponseMessage.builder().error(ex.toString()).message(ex.getMessage()).status(500)
                    .build();
        }
    }

    @GetMapping("/delete/{id}")
    public SingleDataResponseMessage delete(@PathVariable Long id) {
        try {
            contratoService.deleteById(id);
            return SingleDataResponseMessage.builder().status(201).data(null).build();
        } catch (Exception ex) {
            return SingleDataResponseMessage.builder().error(ex.toString()).message(ex.getMessage()).status(500)
                    .build();
        }
    }

}
