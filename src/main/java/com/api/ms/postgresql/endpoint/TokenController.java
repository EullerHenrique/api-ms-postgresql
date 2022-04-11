package com.api.ms.postgresql.endpoint;

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

@RequestMapping("/token/v1")

//@RequiredArgsConstructor
//Gera um construtor com argumentos necessários. Os argumentos obrigatórios são campos finais e campos com restrições como @NonNull.

@RequiredArgsConstructor

public class TokenController {



}