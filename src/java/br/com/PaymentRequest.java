package br.com;

public class PaymentRequest {
    public String correlationId;
    public double amount;

    // Construtor vazio obrigatório para o Jackson
    public PaymentRequest() {}

    // Construtor opcional
    public PaymentRequest(String correlationId, double amount) {
        this.correlationId = correlationId;
        this.amount = amount;
    }
}
