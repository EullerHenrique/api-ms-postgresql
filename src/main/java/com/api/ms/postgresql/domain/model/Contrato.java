package com.api.ms.postgresql.domain.model;

import com.api.ms.postgresql.domain.enumeration.StatusEnum;
import com.api.ms.postgresql.domain.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


//@Data = @Data é uma anotação que gera o código padronizado para classes Java: getters para todos os campos,
//setters para todos os campos não-finais e o toString apropriado, equals e implementações hashCode
//que envolvem os campos da classe.


@Data

//@Entity = A anotação @Entity é utilizada para informar que uma classe também é uma entidade.
//A partir disso, a JPA estabelecerá a ligação entre a entidade e uma tabela de mesmo nome no banco de dados,
//onde os dados de objetos desse tipo poderão ser persistidos.
//Uma entidade representa, na Orientação a Objetos, uma tabela do banco de dados, e cada instância dessa entidade
//representa uma linha dessa tabela.

@Entity

//@Builder = Builder é um padrão de projeto de software criacional que permite a separação da construção de
//um objeto complexo da sua representação, de forma que o mesmo processo de construção possa criar diferentes representações.

@Builder

//@DynamicUpdate
//É uma anotação de nível de classe que pode ser aplicada a uma entidade JPA.
// Ela garante que o Hibernate utilize apenas as colunas modificadas na instrução SQL que gera para a atualização de uma entidade.

@DynamicUpdate

//@NoArgsConstructor = essa anotação é responsável por gerar um construtor sem parâmetros,
//vale ressaltar que se tiver campos final na sua classe deverá usar um atributo force = true em sua anotação.

@NoArgsConstructor

//@AllArgsConstructor = essa anotação é responsável por gerar um construtor com um parâmetro para cada atributo de sua classe.

@AllArgsConstructor

//Obs: A especificação do JPA diz: "A especificação JPA requer que todas as classes persistentes tenham um construtor no-arg.
//Este construtor pode ser público ou protegido. Como o compilador cria automaticamente um construtor no-arg padrão
//quando nenhum outro construtor é definido, apenas as classes que definem os construtores também deve incluir um construtor sem argumentos."


//@Table

//Essa anotação do JPA é usada para adicionar o nome da tabela no banco de dados

@Table(name="contratos")
public class Contrato implements Serializable {

    //@Id = Id da tabela

    @JsonView({Views.ContratoView.class, Views.ClinteView.class, Views.EmpresaView.class})

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    //@EqualsAndHashCode: https://www.youtube.com/watch?v=_DQPepq6Vng
    //Solução do erro: https://stackoverflow.com/questions/59724177/unable-to-evaluate-the-expression-method-threw-org-hibernate-exception-genericj
    //Solução do erro de recursão infinita: https://pt.stackoverflow.com/questions/242288/infinite-recursion-stackoverflowerror-erro-ao-listar-produtos-com-categorias


    // https://www.baeldung.com/jpa-many-to-many

    @JsonView({Views.ContratoView.class, Views.EmpresaView.class})

    @EqualsAndHashCode.Exclude
    @ManyToOne()
    @JoinColumn(name="cliente_id")
    private Cliente cliente;


    @JsonView({Views.ContratoView.class, Views.ClinteView.class})

    @EqualsAndHashCode.Exclude
    @ManyToOne()
    @JoinColumn(name="empresa_id")
    private Empresa empresa;


    @JsonView({Views.ContratoView.class, Views.ClinteView.class, Views.EmpresaView.class})

    @Enumerated(EnumType.ORDINAL)
    private StatusEnum status;


    @JsonView({Views.ContratoView.class, Views.ClinteView.class, Views.EmpresaView.class})

    @Version
    private Long versao;


    @JsonView({Views.ContratoView.class, Views.ClinteView.class, Views.EmpresaView.class})

    private LocalDateTime  dataVencimento;


    @JsonView({Views.ContratoView.class, Views.ClinteView.class, Views.EmpresaView.class})

    private LocalDateTime dataCriacao;


    @JsonView({Views.ContratoView.class, Views.ClinteView.class, Views.EmpresaView.class})

    private LocalDateTime  dataAlteracao;


}
