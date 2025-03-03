package br.com.delegation.bff.dispatcher;


import org.springframework.stereotype.Component;

@Component
public class Dispatcher {

    public <T> String despacharMensagem(T resposta) {
        return "Resposta recebida: " + resposta.toString();
    }
}
