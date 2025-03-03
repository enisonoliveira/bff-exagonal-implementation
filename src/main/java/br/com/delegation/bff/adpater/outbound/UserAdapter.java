package br.com.delegation.bff.adpater.outbound;
// UserAdapter.java

import org.springframework.stereotype.Component;

import br.com.delegation.bff.core.port.AdapterPort;

@Component
public class UserAdapter implements AdapterPort {

    @Override
    public <T> T adaptarEntrada(Object requestBody, Class<T> requestType) {
        return requestType.cast(requestBody); // Simples cast como exemplo
    }

    @Override
    public <R> R adaptarResposta(Object response, Class<R> responseType) {
        return responseType.cast(response);  // Simples cast como exemplo
    }

    @Override
    public String adaptarMensagemErro(String errorMessage) {
        return "Erro: " + errorMessage;
    }
}
