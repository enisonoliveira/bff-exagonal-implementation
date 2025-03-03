package br.com.delegation.bff.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class URLUtils {

    // Método para codificar um valor em URL
    public static String encode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Erro ao codificar a URL", e);
        }
    }

    // Método para decodificar um valor de URL
    public static String decode(String value) {
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Erro ao decodificar a URL", e);
        }
    }

    // Método para obter parâmetros de consulta de uma URL
    public static Map<String, String> getQueryParams(String url) {
        Map<String, String> queryParams = new HashMap<>();

        try {
            // Extrai a parte dos parâmetros após o '?'
            String[] urlParts = url.split("\\?");
            if (urlParts.length > 1) {
                String query = urlParts[1];
                String[] pairs = query.split("&");

                // Para cada par "chave=valor"
                for (String pair : pairs) {
                    String[] keyValue = pair.split("=");
                    if (keyValue.length == 2) {
                        queryParams.put(decode(keyValue[0]), decode(keyValue[1]));
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao processar os parâmetros da URL", e);
        }

        return queryParams;
    }

    // Método para construir uma URL com parâmetros de consulta
    public static String buildUrlWithParams(String baseUrl, Map<String, String> queryParams) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl);

        if (queryParams != null && !queryParams.isEmpty()) {
            urlBuilder.append("?");
            queryParams.forEach((key, value) -> {
                urlBuilder.append(encode(key)).append("=").append(encode(value)).append("&");
            });
            // Remove o último "&"
            urlBuilder.setLength(urlBuilder.length() - 1);
        }

        return urlBuilder.toString();
    }

    // Método para adicionar ou atualizar um parâmetro na URL existente
    public static String addOrUpdateParam(String url, String paramName, String paramValue) {
        Map<String, String> queryParams = getQueryParams(url);
        queryParams.put(paramName, paramValue);
        return buildUrlWithParams(url.split("\\?")[0], queryParams);
    }

    // Método para verificar se a URL contém um parâmetro específico
    public static boolean hasQueryParam(String url, String paramName) {
        Map<String, String> queryParams = getQueryParams(url);
        return queryParams.containsKey(paramName);
    }

    // Método para extrair o domínio (sem parâmetros de consulta) de uma URL
    public static String getBaseUrl(String url) {
        try {
            String baseUrl = url.split("\\?")[0];
            return baseUrl;
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao processar a base da URL", e);
        }
    }
}
