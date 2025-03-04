package br.com.delegation.bff.adpater.inbound;


import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.delegation.bff.core.dto.UserRequest;
import br.com.delegation.bff.core.dto.UserResponse;
import br.com.delegation.bff.core.service.Processor;
import br.com.delegation.bff.core.service.ProcessorUserService;
import reactor.core.publisher.Mono;

@RestController
public class RunController {

    private final Processor processor;
    private final ProcessorUserService processorUserService;

    public RunController(Processor processor, ProcessorUserService processorUserService) {
        this.processor = processor;
        this.processorUserService = processorUserService;
    }

    /**
     * Controlador para processar a requisição com tipos específicos de DTOs. A
     * entrada e saída são forçadas a usar UserRequest e UserResponse.
     */
    @PostMapping("/api/aggregated")
    public Mono<UserResponse> obterDados(
            @Validated
            @RequestBody UserRequest userRequest, // Forçando o uso do DTO UserRequest
            @RequestParam String backendUrl) {    // URL do backend para onde a requisição será feita

        // Chama o método de processamento específico, passando as classes de DTO diretamente
        return processorUserService.processarFluxoEspecifico(userRequest, backendUrl);
    }

    /**
     * Controlador para processar requisiçõe m fluxo genérico (sem tipos
     * específicos definidos).
     */
    @PostMapping("/api/aggregated/generic")
    public String obterDadosGenerico(
            @RequestBody ObjectNode userRequest, // Usando ObjectNode
            @RequestParam String backendUrl) {  // URL do backend

        return processor.obterDadosGenerico(userRequest,  backendUrl);
    }

}
