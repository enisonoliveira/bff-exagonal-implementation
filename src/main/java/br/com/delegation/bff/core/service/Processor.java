package br.com.delegation.bff.core.service;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import br.com.delegation.bff.core.port.AdapterPort;
import br.com.delegation.bff.core.port.GenericAdapter;
import reactor.core.publisher.Mono;

@Component
public class Processor {

    private final WebClient webClient;
    private final AdapterPort adapter;
    private final GenericAdapter genericAdapter;

    public Processor(WebClient webClient, AdapterPort adapter, GenericAdapter genericAdapter) {
        this.webClient = webClient;
        this.adapter = adapter;
        this.genericAdapter = genericAdapter;
    }

    /**
     * Processa o fluxo com entrada e saída específicas.
     */
    public <T, R> Mono<R> processarFluxoEspecifico(Object requestBody, Class<T> requestType, Class<R> responseType, String backendUrl) {
        // Adapta o corpo da requisição
        T adaptedInput = adapter.adaptarEntrada(requestBody, requestType);
    
        // Faz a requisição ao backend
        return webClient.post()
                .uri(backendUrl)
                .bodyValue(adaptedInput)
                .retrieve()
                .bodyToMono(responseType) // Retorna como tipo específico
                .onErrorResume(e -> Mono.just(adapter.adaptarMensagemErro(e.getMessage())));  // Em caso de erro
    }

    /**
     * Processa o fluxo com entrada e saída genéricas.
     */
    public <T, R> Mono<R> processarFluxoGenerico(Object requestBody, Class<T> requestType, Class<R> responseType, String backendUrl) {
        // Adapta o corpo da requisição para tipo genérico
        T adaptedInput = genericAdapter.adaptarEntrada(requestBody, requestType);

        // Faz a requisição ao backend
        return webClient.post()
                .uri(backendUrl)
                .bodyValue(adaptedInput)
                .retrieve()
                .bodyToMono(Object.class)  // Espera a resposta como tipo genérico
                .map(response -> genericAdapter.adaptarResposta(response, responseType))  // Adapta a resposta para o tipo esperado
                .onErrorResume(e -> Mono.just(adapter.adaptarMensagemErro(e.getMessage())));  // Em caso de erro
    }
}
