package com.api.ms.postgresql.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

//@Configuration

// Indica que uma classe declara um ou mais métodos @Bean e pode ser processada pelo contêiner Spring para gerar
// definições de bean e solicitações de serviço para esses beans em tempo de execução

@Configuration

//@RequiredArgsConstructor
//Gera um construtor com argumentos necessários. Os argumentos obrigatórios são campos finais e campos com restrições como @NonNull.

@RequiredArgsConstructor

public class JacksonConfig {

    private final ObjectMapper objectMapper;

    @PostConstruct
    public void configureObjectMapper() {
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
    }

}


