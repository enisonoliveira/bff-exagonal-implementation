package br.com.delegation.bff.adpater.inbound;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.delegation.bff.core.dto.UserRequest;
import br.com.delegation.bff.core.dto.UserResponse;
import br.com.delegation.bff.core.service.Processor;
import reactor.core.publisher.Mono;

@RestController
public class Runner {

    private final Processor processor;

    public Runner(Processor processor) {
        this.processor = processor;
    }

    /**
     * Controlador para processar a requisição com tipos específicos de DTOs. A
     * entrada e saída são forçadas a usar UserRequest e UserResponse.
     */
    @GetMapping("/api/aggregated")
    public Mono<UserResponse> obterDados(
            @Validated
             @RequestBody UserRequest userRequest, // Forçando o uso do DTO UserRequest
            @RequestParam String backendUrl) {    // URL do backend para onde a requisição será feita

        // Chama o método de processamento específico, passando as classes de DTO diretamente
        return processor.processarFluxoEspecifico(userRequest, UserRequest.class, UserResponse.class, backendUrl);
    }

    

    /**
     * Controlador para processar requisiçõe m fluxo genérico (sem tipos específicos definidos).
     */
    @GetMapping("/api/aggregated/generic")
    public Mono<Object> obterDadosGenerico(
             @RequestBody Object userRequest,    // Entrada genérica de dados
            @RequestParam String backendUrl) {  // URL do backend

        // Chama o método de processamento genérico
        return processor.processarFluxoGenerico(userRequest, Object.class, Object.class, backendUrl);
    }
}
