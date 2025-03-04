package br.com.delegation.bff.adapter.inbound;

import br.com.delegation.bff.adpater.inbound.RunController;
import br.com.delegation.bff.core.dto.UserRequest;
import br.com.delegation.bff.core.dto.UserResponse;
import br.com.delegation.bff.core.service.Processor;
import br.com.delegation.bff.core.service.ProcessorUserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;

@WebFluxTest(RunController.class)
class RunControllerTest {

    private MockWebServer mockWebServer;
    private WebTestClient webTestClient;

    @Mock
    private Processor processor;

    @Mock
    private ProcessorUserService processorUserService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private RunController runController;

    @BeforeEach
    void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        webTestClient = WebTestClient.bindToController(runController)
                .build();
    }

    @Test
    void testObterDados() {

        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("testuser@example.com");
        userRequest.setName("Enison Oliveira");
        
        UserResponse mockResponse = new UserResponse("Enison", "enisonoliveira@hotmail.com");

        when(processorUserService.processarFluxoEspecifico(any(), any()))
                .thenReturn(Mono.just(mockResponse));

        webTestClient.post()
                .uri("/api/aggregated?backendUrl='http://localhost:8080'")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRequest) // Pass the UserRequest as the request body
                .exchange()
                .expectStatus().isOk() // Expect a successful response
                .expectBody(UserResponse.class) // Validate the response body type

                .value(response -> {
                    assertEquals(mockResponse.getName(), response.getName());
                });
    }

    @Test
    void testObterDadosGenerico() throws Exception {

        String fakeResponse = "{\"message\":\"Success\"}";

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(fakeResponse)
                .addHeader("Content-Type", "application/json"));

        ObjectNode userRequest = objectMapper.createObjectNode();
        userRequest.put("username", "testuser");
        userRequest.put("email", "testuser@example.com");

        when(processor.obterDadosGenerico(any(), any()))
                .thenReturn(fakeResponse);

        webTestClient.post()
                .uri("/api/aggregated/generic?backendUrl=http://localhost:8080")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRequest) // Pass the ObjectNode as the request body
                .exchange()
                .expectStatus().isOk() // Expect a successful response
                .expectBody(String.class) // Validate the response body type
                .value(response -> {
                    System.out.println("Response body: " + response);
                    assertEquals(fakeResponse, response);
                });

    }

}





  