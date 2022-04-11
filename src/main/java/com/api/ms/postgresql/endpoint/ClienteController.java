package com.api.ms.postgresql.endpoint;

import com.api.ms.postgresql.domain.dto.ClienteDTO;
import com.api.ms.postgresql.domain.dto.responses.MultipleDataResponseMessage;
import com.api.ms.postgresql.domain.dto.responses.SingleDataResponseMessage;
import com.api.ms.postgresql.domain.view.Views;
import com.api.ms.postgresql.service.cliente.ClienteService;
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

@RequestMapping("/cliente/v1")

//@RequiredArgsConstructor
//Gera um construtor com argumentos necessários. Os argumentos obrigatórios são campos finais e campos com restrições como @NonNull.

@RequiredArgsConstructor

public class ClienteController {

    private final ClienteService clienteService;

    @JsonView({Views.ClinteView.class})
    @GetMapping(value = "/{id}")
    public SingleDataResponseMessage findById(@PathVariable Long id) {
        try {
            ClienteDTO cliente = clienteService.findById(id);
            if (cliente != null) {
                return SingleDataResponseMessage.builder().status(200).data(cliente).build();
            } else {
                return SingleDataResponseMessage.builder().status(404).data(null).build();
            }
        } catch (Exception ex) {
            return SingleDataResponseMessage.builder().error(ex.toString()).message(ex.getMessage()).status(500)
                    .build();
        }
    }

    @JsonView({Views.ClinteView.class})
    @GetMapping("/all")
    public MultipleDataResponseMessage findAll() {
        try {
            return MultipleDataResponseMessage.builder().status(200).data(clienteService.findAll()).build();
        } catch (Exception ex) {
            return MultipleDataResponseMessage.builder().error(ex.toString()).message(ex.getMessage()).status(500)
                    .build();
        }
    }

    @PostMapping("/save")
    public SingleDataResponseMessage save(@RequestBody ClienteDTO clienteDTO) {
        try {
            clienteService.save(clienteDTO);
            return SingleDataResponseMessage.builder().status(201).data(null).build();
        } catch (Exception ex) {
            return SingleDataResponseMessage.builder().error(ex.toString()).message(ex.getMessage()).status(500)
                    .build();
        }
    }

    @GetMapping("/delete/{id}")
    public SingleDataResponseMessage delete(@PathVariable Long id) {
        try {
            clienteService.deleteById(id);
            return SingleDataResponseMessage.builder().status(201).data(null).build();
        } catch (Exception ex) {
            return SingleDataResponseMessage.builder().error(ex.toString()).message(ex.getMessage()).status(500)
                    .build();
        }
    }


}
