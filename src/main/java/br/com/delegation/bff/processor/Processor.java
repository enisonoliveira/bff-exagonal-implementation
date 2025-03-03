package br.com.delegation.bff.processor;


import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.delegation.bff.adpater.ControllerAdapter;
import reactor.core.publisher.Mono;

@Component
public class Processor {

    private final WebClient webClient;
    private final ControllerAdapter adapter;

    public Processor(WebClient webClient, ControllerAdapter adapter) {
        this.webClient = webClient;
        this.adapter = adapter;
    }

    // Tornar o método processar mais genérico, aceitando diferentes tipos de entrada e saída
    @SuppressWarnings("unchecked")
    public <T, R> Mono<R> processarFluxo(Object requestBody, Class<T> requestType, Class<R> responseType, String backendUrl) {
        // Adapta o corpo de entrada conforme o tipo especificado
        T adaptedInput = adapter.adaptarEntrada(requestBody, requestType);

        // Faz a requisição para o backend com WebClient
        return webClient.post()
                .uri(backendUrl)  // URL dinâmica
                .bodyValue(adaptedInput)  // Envia o corpo de entrada adaptado
                .retrieve()
                .bodyToMono(Object.class)  // Retorna como um tipo genérico para adaptação
                .map(response -> adapter.adaptarResposta(response, responseType)) // Adapta a resposta para o tipo de saída
                .onErrorResume(e -> Mono.just(adapter.adaptarMensagemErro(e.getMessage())));
    }
}
