package br.com;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HealthChecker {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ApiRouteManager routeManager;

    public HealthChecker(ApiRouteManager routeManager) {
        this.routeManager = routeManager;
    }

    public void start() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this::checkHealth, 0, 5, TimeUnit.SECONDS);
    }

    private void checkHealth() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(routeManager.getActiveUrl() + "/health"))
                .timeout(Duration.ofSeconds(1))
                .GET()
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    boolean healthy = response.statusCode() >= 200 && response.statusCode() < 500;
                    routeManager.setPrimaryHealthy(healthy);
                    System.out.println("[HealthCheck] Principal API está " + (healthy ? "saudável" : "com problemas"));
                })
                .exceptionally(ex -> {
                    routeManager.setPrimaryHealthy(false);
                    System.out.println("[HealthCheck] Falha ao checar API principal: " + ex.getMessage());
                    return null;
                });
    }
}
