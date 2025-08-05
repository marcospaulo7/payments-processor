package br.com;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TransactionSender {
    private final ApiRouteManager routeManager;
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public TransactionSender(ApiRouteManager routeManager) {
        this.routeManager = routeManager;
    }

    public void sendTransaction(PaymentRequest jsonPayload) {
        String targetUrl = routeManager.getActiveUrl() + "/pagamento";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(targetUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload.toString()))
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    System.out.println("Status: " + response.statusCode());
                })
                .exceptionally(ex -> {
                    System.out.println("Erro ao enviar: " + ex.getMessage());
                    return null;
                });
    }
}
