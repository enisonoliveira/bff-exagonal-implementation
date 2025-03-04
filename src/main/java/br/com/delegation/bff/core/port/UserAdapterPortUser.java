package br.com.delegation.bff.core.port;
// UserAdapter.java


import org.springframework.stereotype.Component;

import br.com.delegation.bff.core.dto.UserRequest;
import br.com.delegation.bff.core.dto.UserResponse;

@Component
public class UserAdapterPortUser  {

    public UserRequest adaptarEntrada(UserRequest requestBody, Class<UserRequest> requestType) {
        return requestType.cast(requestBody); // Simples cast como exemplo
    }

    public UserResponse adaptarResposta(UserResponse response, Class<UserResponse> responseType) {
        return responseType.cast(response);  // Simples cast como exemplo
    }

    
}
