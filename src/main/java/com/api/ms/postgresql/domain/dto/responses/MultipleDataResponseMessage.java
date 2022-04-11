package com.api.ms.postgresql.domain.dto.responses;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data = @Data é uma anotação que gera o código padronizado para classes Java: getters para todos os campos,
//setters para todos os campos não-finais e o toString apropriado, equals e implementações hashCode
//que envolvem os campos da classe.

@Data

@AllArgsConstructor

//@NoArgsConstructor = essa anotação é responsável por gerar um construtor sem parâmetros,
//vale ressaltar que se tiver campos final na sua classe deverá usar um atributo force = true em sua anotação.

@NoArgsConstructor

//Obs: A especificação do JPA diz: "A especificação JPA requer que todas as classes persistentes tenham um construtor no-arg.
//Este construtor pode ser público ou protegido. Como o compilador cria automaticamente um construtor no-arg padrão
//quando nenhum outro construtor é definido, apenas as classes que definem os construtores também deve incluir um construtor sem argumentos."

public class MultipleDataResponseMessage extends ResponseMessage {
	private Set<Object> data;

	@Builder
	public MultipleDataResponseMessage(int status, String error, String message, Set<Object> data) {
		super(status, error, message);
		this.data = data;
	}

}
