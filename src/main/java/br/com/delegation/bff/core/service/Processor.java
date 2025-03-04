package br.com.delegation.bff.core.service;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface  Processor {
    public String obterDadosGenerico( ObjectNode userRequest,  String backendUrl);

}
