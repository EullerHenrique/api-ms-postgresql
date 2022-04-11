package com.api.ms.postgresql.endpoint;
import com.api.ms.postgresql.domain.dto.EmpresaDTO;
import com.api.ms.postgresql.domain.dto.TokenDTO;
import com.api.ms.postgresql.domain.dto.responses.MultipleDataResponseMessage;
import com.api.ms.postgresql.domain.dto.responses.SingleDataResponseMessage;
import com.api.ms.postgresql.domain.dto.util.CreateOrConvertDTO;
import com.api.ms.postgresql.domain.model.Empresa;
import com.api.ms.postgresql.domain.model.Token;
import com.api.ms.postgresql.domain.view.Views;
import com.api.ms.postgresql.service.empresa.EmpresaService;
import com.api.ms.postgresql.service.token.TokenService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

@RequestMapping("/empresa/v1")

//@RequiredArgsConstructor
//Gera um construtor com argumentos necessários. Os argumentos obrigatórios são campos finais e campos com restrições como @NonNull.

@RequiredArgsConstructor

public class EmpresaController {

    private final TokenService tokenService;
    private final EmpresaService empresaService;

    @PostMapping(value = "/{id}/adicionar-token")
    public SingleDataResponseMessage adicionarToken(@PathVariable Long id, @RequestHeader Map<String, String> headers, @RequestBody Map<String, String> grants) {

         EmpresaDTO empresaDTO = empresaService.findById(id);;

        if(empresaDTO != null) {

            try {

                 empresaDTO = empresaService.findByIdAndPassword(id, headers.get("grant_type"));

                if(empresaDTO != null) {

                     Empresa empresa = (Empresa) CreateOrConvertDTO.convert(empresaDTO);

                    TokenDTO tokenDTO = tokenService.gerarToken(headers, grants);
                    Token token = (Token) CreateOrConvertDTO.convert(tokenDTO);

                    empresa.getTokens().add(token);
                    empresaService.save(empresa);

                    return SingleDataResponseMessage.builder().status(200).data(token).build();

                }else{

                    return SingleDataResponseMessage.builder().status(404).data(null).message("Senha incorreta").build();

                }

            } catch (Exception ex) {
                return SingleDataResponseMessage.builder().error(ex.toString()).message(ex.getMessage()).status(500)
                        .build();
            }
        }else {
            return SingleDataResponseMessage.builder().status(404).data(null).message("Empresa não encontrada").build();
        }
    }

    @JsonView({Views.EmpresaView.class})
    @GetMapping(value = "/{id}")
    public SingleDataResponseMessage findById(@RequestHeader Map<String, String> headers, @PathVariable Long id) {

        if(tokenService.verifyToken(headers.get("token"))) {
            try {
                EmpresaDTO empresaDTO = empresaService.findById(id);
                if (empresaDTO != null) {
                    return SingleDataResponseMessage.builder().status(200).data(empresaDTO).build();
                } else {
                    return SingleDataResponseMessage.builder().status(404).data(null).message("Empresa não encontrada").build();
                }
            } catch (Exception ex) {
                return SingleDataResponseMessage.builder().error(ex.toString()).message(ex.getMessage()).status(500)
                        .build();
            }
        }
        return SingleDataResponseMessage.builder().status(404).data(null).message("Token incorreto").build();

    }

    @JsonView({Views.EmpresaView.class})
    @GetMapping("/all")
    public MultipleDataResponseMessage findAll(@RequestHeader Map<String, String> headers) {

        if(tokenService.verifyToken(headers.get("token"))) {
            try {
                return MultipleDataResponseMessage.builder().status(200).data(empresaService.findAll()).build();
            } catch (Exception ex) {
                return MultipleDataResponseMessage.builder().error(ex.toString()).message(ex.getMessage()).status(500)
                        .build();
            }
        }
        return MultipleDataResponseMessage.builder().status(404).data(null).message("Token incorreto").build();

    }


    @PostMapping("/save")
    public SingleDataResponseMessage save(@RequestHeader Map<String, String> headers, @RequestBody EmpresaDTO empresaDTO) {

        if(tokenService.verifyToken(headers.get("token"))) {
            try {
                empresaService.save((Empresa) CreateOrConvertDTO.convert(empresaDTO));
                return SingleDataResponseMessage.builder().status(201).data(null).build();
            } catch (Exception ex) {
                return SingleDataResponseMessage.builder().error(ex.toString()).message(ex.getMessage()).status(500)
                        .build();
            }
        }
        return SingleDataResponseMessage.builder().status(404).data(null).message("Token incorreto").build();

    }

}
