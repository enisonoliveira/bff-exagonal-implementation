package br.com.delegation.bff.core.processor;

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
     * 
     * @param requestBody Corpo da requisição.
     * @param requestType Tipo esperado para a requisição (específico).
     * @param responseType Tipo esperado para a resposta (específico).
     * @param backendUrl URL do backend para requisição.
     * @param <T> Tipo de entrada específico.
     * @param <R> Tipo de saída específico.
     * @return Mono com a resposta do backend adaptada.
     */
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

    /**
     * Processa o fluxo com entrada e saída genéricas.
     * 
     * @param requestBody Corpo da requisição.
     * @param requestType Tipo esperado para a requisição (genérico).
     * @param responseType Tipo esperado para a resposta (genérico).
     * @param backendUrl URL do backend para requisição.
     * @param <T> Tipo de entrada genérico.
     * @param <R> Tipo de saída genérico.
     * @return Mono com a resposta do backend adaptada.
     */
    public <T, R> Mono<R> processarFluxoGenerico(Object requestBody, Class<T> requestType, Class<R> responseType, String backendUrl) {
        // Adapta o corpo de entrada para o tipo genérico
        T adaptedInput = genericAdapter.adaptarEntrada(requestBody, requestType);

        // Faz a requisição para o backend com WebClient
        return webClient.post()
                .uri(backendUrl)  // URL dinâmica
                .bodyValue(adaptedInput)  // Envia o corpo de entrada adaptado
                .retrieve()
                .bodyToMono(Object.class)  // Retorna a resposta como um tipo genérico (Object)
                .map(response -> genericAdapter.adaptarResposta(response, responseType)) // Adapta a resposta para o tipo genérico
                .onErrorResume(e -> Mono.just(GenericAdapter.adaptarMensagemErro(e.getMessage()))); // Tratamento de erro
    }
}
