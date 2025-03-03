package br.com.delegation.bff.adpater.outbound.erro;

public class ErrorResponse {
    private String status;
    private String message;
    private int code;
    private long timestamp;

    // Construtores
    public ErrorResponse(String status, String message, int code) {
        this.status = status;
        this.message = message;
        this.code = code;
        this.timestamp = System.currentTimeMillis();  // Marca de tempo atual
    }

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
