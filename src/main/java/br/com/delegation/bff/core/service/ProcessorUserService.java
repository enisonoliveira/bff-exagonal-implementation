package br.com.delegation.bff.core.service;

import reactor.core.publisher.Mono;

public interface  ProcessorUserService {
           
    <T, R> Mono<R> processarFluxoEspecifico(Object requestBody, Class<T> requestType, Class<R> responseType, String backendUrl);

}
