package br.com.delegation.bff.adpater;

import org.springframework.stereotype.Component;

// Exceções personalizadas para cada tipo de erro de adaptação
class InputAdaptationException extends RuntimeException {
    public InputAdaptationException(String message, Throwable cause) {
        super(message, cause);
    }
}

class ParamAdaptationException extends RuntimeException {
    public ParamAdaptationException(String message, Throwable cause) {
        super(message, cause);
    }
}

class ResponseAdaptationException extends RuntimeException {
    public ResponseAdaptationException(String message, Throwable cause) {
        super(message, cause);
    }
}

@Component
public class ControllerAdapter {

    // Método para adaptar o corpo da requisição para o tipo desejado
    public <T> T adaptarEntrada(Object input, Class<T> inputClass) {
        try {
            if (inputClass.isInstance(input)) {
                return inputClass.cast(input);  // Adaptação simples usando reflexão
            } else {
                throw new InputAdaptationException("Tipo incompatível ao adaptar a entrada", null);
            }
        } catch (Exception e) {
            throw new InputAdaptationException("Erro ao adaptar a entrada", e);
        }
    }

    // Método para adaptar a URL e os parâmetros de consulta
    public <T> T adaptarParametro(String paramValue, Class<T> paramClass) {
        try {
            if (paramClass.isInstance(paramValue)) {
                return paramClass.cast(paramValue);  // Converte o valor para o tipo desejado
            } else {
                throw new ParamAdaptationException("Tipo incompatível ao adaptar o parâmetro", null);
            }
        } catch (Exception e) {
            throw new ParamAdaptationException("Erro ao adaptar o parâmetro", e);
        }
    }

    // Adapta a resposta de saída, caso seja necessário
    public <T> T adaptarResposta(Object response, Class<T> responseClass) {
        try {
            if (responseClass.isInstance(response)) {
                return responseClass.cast(response);  // Adaptação para o tipo de resposta
            } else {
                throw new ResponseAdaptationException("Tipo incompatível ao adaptar a resposta", null);
            }
        } catch (Exception e) {
            throw new ResponseAdaptationException("Erro ao adaptar a resposta", e);
        }
    }

    // Adaptar a mensagem de erro
    @SuppressWarnings("unchecked")
    public <T> T adaptarMensagemErro(String message) {
        // A conversão explícita é necessária, pois T será tratado como String
        return (T) ("Erro: " + message);
    }
}
