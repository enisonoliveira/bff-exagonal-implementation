package br.com.delegation.bff.core.port;

// AdapterPort.java
public interface AdapterPortUserService {
    <T> T adaptarEntrada(Object requestBody, Class<T> requestType);
    <R> R adaptarResposta(Object response, Class<R> responseType);
    <T> T adaptarMensagemErro(String message);
}
