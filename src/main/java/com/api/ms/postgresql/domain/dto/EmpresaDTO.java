package com.api.ms.postgresql.domain.dto;

import com.api.ms.postgresql.domain.model.Contrato;
import com.api.ms.postgresql.domain.model.Token;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data

//@Builder = Builder é um padrão de projeto de software criacional que permite a separação da construção de
//um objeto complexo da sua representação, de forma que o mesmo processo de construção possa criar diferentes representações.

@Builder

// @AllArgsConstructor = essa anotação é responsável por gerar um construtor com um parâmetro para cada atributo de sua classe.

@AllArgsConstructor

//@NoArgsConstructor = essa anotação é responsável por gerar um construtor sem parâmetros,
//vale ressaltar que se tiver campos final na sua classe deverá usar um atributo force = true em sua anotação.

//Obs: A especificação do JPA diz: "A especificação JPA requer que todas as classes persistentes tenham um construtor no-arg.
//Este construtor pode ser público ou protegido. Como o compilador cria automaticamente um construtor no-arg padrão
//quando nenhum outro construtor é definido, apenas as classes que definem os construtores também deve incluir um construtor sem argumentos."

@NoArgsConstructor
public class EmpresaDTO {

    private Long id;
    private String password;
    private Set<Token> tokens;
    private Set<Contrato> contratos;
    private LocalDateTime dataCriacao;
    private LocalDateTime  dataAlteracao;

}
