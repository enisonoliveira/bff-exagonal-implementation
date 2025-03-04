package br.com.delegation.bff.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.delegation.bff.core.port.AdapterPortUserService;
import br.com.delegation.bff.core.service.ProcessorUserService;
import reactor.core.publisher.Mono;

@Service
public class ProcessorUserServiceImpl implements  ProcessorUserService {

    private  final WebClient webClient;
    private final AdapterPortUserService adapter ;

   @Autowired
    public ProcessorUserServiceImpl(AdapterPortUserService adapter,WebClient webClient) {
        this.adapter = adapter;
        this.webClient=webClient;
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
    public <T, R> Mono<R> processarFluxoEspecifico(Object requestBody, Class<T> requestType, Class<R> responseType, String backendUrl) {
        // Adapta o corpo de entrada para o tipo específico
        T adaptedInput = adapter.adaptarEntrada(requestBody, requestType);
        // Faz a requisição para o backend com WebClient
        return webClient.post()
                .uri(backendUrl)  // URL dinâmica
                .bodyValue(adaptedInput)  // Envia o corpo de entrada adaptado
                .retrieve()
                .bodyToMono(responseType)  // Retorna a resposta já como tipo específico
                .onErrorResume(e -> Mono.just(adapter.adaptarMensagemErro(e.getMessage())));  // Tratamento de erro
    }

}
