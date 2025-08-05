package br.com;

public class ApiRouteManager {
    private final String primaryUrl;
    private final String fallbackUrl;
    private volatile boolean primaryHealthy = true;

    public ApiRouteManager(String primaryUrl, String fallbackUrl) {
        this.primaryUrl = primaryUrl;
        this.fallbackUrl = fallbackUrl;
    }

    public String getActiveUrl() {
        return primaryHealthy ? primaryUrl : fallbackUrl;
    }

    public void setPrimaryHealthy(boolean healthy) {
        this.primaryHealthy = healthy;
    }

    public boolean isPrimaryHealthy() {
        return primaryHealthy;
    }
}
