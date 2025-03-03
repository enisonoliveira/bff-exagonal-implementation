package br.com.delegation.bff.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.delegation.bff.service.ProcessService;
import reactor.core.publisher.Mono;

@RestController
public class Runner {

    private final ProcessService processService;

    public Runner(ProcessService processService) {
        this.processService = processService;
    }

    // Controlador que agora usa tipos genéricos
    @GetMapping("/api/aggregated")
    public <T, R> Mono<String> obterDados(
            @RequestBody Object userRequest,    // Pode ser qualquer tipo de entrada
            @RequestParam String backendUrl,
            @RequestParam Class<T> requestType,  // Tipo de entrada passado como parâmetro
            @RequestParam Class<R> responseType) {  // Tipo de saída passado como parâmetro
        return processService.processarRequisicao(userRequest, requestType, responseType, backendUrl);
    }
}
