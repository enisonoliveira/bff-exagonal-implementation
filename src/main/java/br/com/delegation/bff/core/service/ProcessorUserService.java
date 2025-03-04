package br.com.delegation.bff.core.service;

import br.com.delegation.bff.core.dto.UserRequest;
import br.com.delegation.bff.core.dto.UserResponse;
import reactor.core.publisher.Mono;

public interface  ProcessorUserService {
           
    public  Mono<UserResponse> processarFluxoEspecifico(UserRequest requestBody, String backendUrl);


}
