package br.com.delegation.bff.adpater.outbound;
// UserAdapter.java

import org.springframework.stereotype.Component;

import br.com.delegation.bff.adpater.outbound.erro.ErrorResponse;
import br.com.delegation.bff.core.port.AdapterPortUserService;

@Component
public class UserAdapterPortUser implements AdapterPortUserService {

    @Override
    public <T> T adaptarEntrada(Object requestBody, Class<T> requestType) {
        return requestType.cast(requestBody); // Simples cast como exemplo
    }

    @Override
    public <R> R adaptarResposta(Object response, Class<R> responseType) {
        return responseType.cast(response);  // Simples cast como exemplo
    }

    @SuppressWarnings("unchecked")
    @Override
    public ErrorResponse adaptarMensagemErro(String errorMessage) {
         // Aqui, você pode personalizar o código de erro, status e outros dados
         return new ErrorResponse("error", errorMessage, 500);
    }
}
