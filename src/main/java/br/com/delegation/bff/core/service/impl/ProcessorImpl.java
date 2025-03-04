package br.com.delegation.bff.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.delegation.bff.core.port.GenericAdapter;
import br.com.delegation.bff.core.service.Processor;
import reactor.core.publisher.Mono;

@Service
public class ProcessorImpl implements  Processor {

    private  final WebClient webClient;
    private final GenericAdapter genericAdapter;

    @Autowired
    public ProcessorImpl(GenericAdapter genericAdapter,WebClient webClient) {
        this.webClient = webClient;
        this.genericAdapter = genericAdapter;
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
    @Override
    public String obterDadosGenerico( ObjectNode userRequest,  String backendUrl) {
        try {
            // Convert requestBody to JsonNode or any other appropriate type
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.valueToTree(userRequest);
            
            // Example: modify jsonNode if needed
            if (jsonNode instanceof ObjectNode) {
                ObjectNode objectNode = (ObjectNode) jsonNode;
                objectNode.put("newField", "value");
            }
    
            // Use WebClient to send the request
            return  webClient.post()
                    .uri(backendUrl)
                    .bodyValue(jsonNode)  // Body is a JsonNode or ObjectNode
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
    
}

