package br.com.delegation.bff.dispatcher;

import org.springframework.stereotype.Component;

@Component
public class Dispatcher {

    /**
     * Método para formatar e despachar a resposta recebida (tanto sucesso quanto erro).
     */
    public <T> ResponseFormat despacharMensagem(T resposta) {
        var responseFormat = new ResponseFormat();

        if (resposta == null) {
            responseFormat.setStatus("error");
            responseFormat.setMessage("Resposta vazia recebida");
        } else {
            responseFormat.setStatus("success");
            responseFormat.setData(resposta);
        }

        return responseFormat;
    }

    /**
     * Método para formatar a resposta de erro.
     */
    public ResponseFormat despacharErro(Throwable error) {
        var responseFormat = new ResponseFormat();
        responseFormat.setStatus("error");
        responseFormat.setCode("ERR_UNKNOWN");
        responseFormat.setMessage(error.getMessage());
        responseFormat.setData(new ErrorDetails(error.getMessage()));
        return responseFormat;
    }

    /**
     * Classe que representa o formato da resposta (status, mensagem, código e dados).
     */
    public static class ResponseFormat {
        private String status;
        private String message;
        private String code;
        private Object data;

        // Getters e Setters
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }

    /**
     * Classe para representar os detalhes do erro (como descrição do erro).
     */
    public static class ErrorDetails {
        private String errorDescription;

        public ErrorDetails(String errorDescription) {
            this.errorDescription = errorDescription;
        }

        public String getErrorDescription() {
            return errorDescription;
        }

        public void setErrorDescription(String errorDescription) {
            this.errorDescription = errorDescription;
        }
    }
}
