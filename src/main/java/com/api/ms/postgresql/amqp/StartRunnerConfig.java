package com.api.ms.postgresql.amqp;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import com.api.ms.postgresql.domain.enumeration.StatusEnum;
import com.api.ms.postgresql.domain.model.Cliente;
import com.api.ms.postgresql.domain.model.Contrato;
import com.api.ms.postgresql.domain.model.Empresa;
import com.api.ms.postgresql.domain.model.Token;
import com.api.ms.postgresql.repository.ClienteRepository;
import com.api.ms.postgresql.repository.ContratoRepository;
import com.api.ms.postgresql.repository.EmpresaRepository;
import com.api.ms.postgresql.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration

// Indica que uma classe declara um ou mais métodos @Bean e pode ser processada pelo contêiner Spring para gerar
// definições de bean e solicitações de serviço para esses beans em tempo de execução

@Configuration

//@RequiredArgsConstructor
//Gera um construtor com argumentos necessários. Os argumentos obrigatórios são campos finais e campos com restrições como @NonNull.

@RequiredArgsConstructor

public class StartRunnerConfig {

    private final ClienteRepository clienteRepository;
    private final ContratoRepository contratoRepository;
    private final EmpresaRepository empresaRepository;
    private final TokenRepository tokenRepository;

    private Empresa empresa;
    private Set<Cliente> clientes;
    private Set<Token> tokens;
    private Set<Contrato> contratos;

    @Bean
    public ApplicationRunner initializer() {
        return args -> {
            tokenRepository
                    .saveAll(
                            tokens =
                                    new HashSet<>(Arrays.asList(
                                            criarToken(),
                                            criarToken(),
                                            criarToken()
                                    ))
                    );
            clienteRepository
                    .saveAll(
                            clientes =
                                    new HashSet<>(Arrays.asList(
                                            criarCliente("Euller_1"),
                                            criarCliente("Euller_2"),
                                            criarCliente("Euller_3")
                                    ))
                    );
            empresaRepository
                    .save(
                            empresa = criarEmpresa()
                    );
            contratoRepository
                    .saveAll(
                            contratos = criarContratos()
                    );
        };

    }


    public Cliente criarCliente(String nome) {
        return Cliente.builder()
                .nome(nome)
                .dataCriacao(LocalDateTime.now())
                .build();
    }

    public Contrato criarContrato(Long numero, Cliente c, Empresa e){
        return Contrato.builder()
                .cliente(c)
                .empresa(e)
                .status(StatusEnum.ATIVO)
                .dataCriacao(LocalDateTime.now())
                .build();
    }

    public Token criarToken(){
        return Token.builder()
                .accessToken(UUID.randomUUID().toString())
                .expiresIn(Long.toString(LocalDateTime.now().plusMinutes(2).toInstant(ZoneOffset.UTC).toEpochMilli()))
                .dataCriacao(LocalDateTime.now())
                .build();

    }

    public Empresa criarEmpresa(){
        return Empresa.builder()
                .tokens(tokens)
                .password("fd9047a1-1364-4bbe-a629-2970e9387328")
                .dataCriacao(LocalDateTime.now())
                .dataAlteracao(LocalDateTime.now())
                .build();
    }

    public Set<Contrato> criarContratos(){

        Set<Contrato> contratos = new HashSet<>();

        clientes.forEach(c -> {
            for(int i = 0; i < 3; i++) {
                contratos.add(criarContrato(new Random().nextLong(), c, empresa));
            }
        });

        return contratos;
    }



}