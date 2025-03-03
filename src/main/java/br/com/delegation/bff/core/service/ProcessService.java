package br.com.delegation.bff.core.service;


import org.springframework.stereotype.Service;

import  br.com.delegation.bff.core.processor.Processor;
import br.com.delegation.bff.dispatcher.Dispatcher;
import reactor.core.publisher.Mono;

@Service
public class ProcessService {

    private final Processor processor;
    private final Dispatcher dispatcher;

    public ProcessService(Processor processor, Dispatcher dispatcher) {
        this.processor = processor;
        this.dispatcher = dispatcher;
    }

    // Método genérico para processar qualquer tipo de entrada e saída
    public <T, R> Mono<String> processarRequisicao(Object requestBody, Class<T> requestType, Class<R> responseType, String backendUrl) {
        return processor.processarFluxoGenerico(requestBody, requestType, responseType, backendUrl)
                .map(dispatcher::despacharMensagem)
                .onErrorResume(e -> Mono.just("Erro ao processar a solicitação: " + e.getMessage()));
    }
}
