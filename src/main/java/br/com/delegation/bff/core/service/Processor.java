package br.com.delegation.bff.core.service;

import reactor.core.publisher.Mono;

public interface  Processor {
        <T, R> Mono<R> processarFluxoEspecifico(Object requestBody, Class<T> requestType, Class<R> responseType, String backendUrl);
        <T, R> Mono<R> processarFluxoGenerico(Object requestBody, Class<T> requestType, Class<R> responseType, String backendUrl) ;
}
