package br.com.delegation.bff.core.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import br.com.delegation.bff.core.dto.UserRequest;
import br.com.delegation.bff.core.service.ProcessorUserService;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.delegation.bff.core.dto.UserResponse;
import br.com.delegation.bff.core.port.GenericAdapter;
import br.com.delegation.bff.core.port.UserAdapterPortUser;

@Service
public class ProcessorUserServiceImpl implements ProcessorUserService {

    private final WebClient webClient;

    private final UserAdapterPortUser userAdapterPortUser;

    private final GenericAdapter genericAdapter;

    private static final Logger logger = LoggerFactory.getLogger(ProcessorUserServiceImpl.class);  // Adicionando o logger

    public ProcessorUserServiceImpl( WebClient webClient,UserAdapterPortUser userAdapterPortUser, GenericAdapter genericAdapter) {
        this.webClient = webClient;
        this.userAdapterPortUser=userAdapterPortUser;
        this.genericAdapter=genericAdapter;
    }

    /**
     * Processa o fluxo com entrada e saída específicas.
     *
     * @param requestBody Corpo da requisição.
     * @param requestType Tipo esperado para a requisição (específico).
     * @param responseType Tipo esperado para a resposta (específico).
     * @param backendUrl URL do backend para requisição.
     * @param <T> Tipo de entrada específico.
     * @param <R> Tipo de saída específico.
     * @return Mono com a resposta do backend adaptada.
     */
    @Override
    public  Mono<UserResponse> processarFluxoEspecifico(UserRequest requestBody, String backendUrl) {
        // Adapta o corpo de entrada para o tipo específico
        UserRequest adaptedInput = genericAdapter.adaptarEntrada(requestBody, UserRequest.class);
        try {
     
  // Fazer a requisição para o backend com WebClient
        return webClient.get()
                .uri(backendUrl) // URL dinâmica
               // .bodyValue(adaptedInput) // Envia o corpo de entrada adaptado
                .retrieve()
                .bodyToMono(UserResponse.class) // Retorna a resposta já como tipo específico
                .doOnSubscribe(subscription -> {
                    // Logando quando a requisição começa
                    logger.info("Iniciando requisição para: {}", backendUrl);
                    logger.info("Corpo da requisição: {}", adaptedInput);
                });
               
              
            } catch (Exception e) {
                 throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);


            }
    }
}
