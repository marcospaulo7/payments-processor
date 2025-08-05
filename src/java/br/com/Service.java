package br.com;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Service {

    public  void execute(PaymentRequest request) {
        ApiRouteManager manager = new ApiRouteManager("http://pagamentos", "http://fallback");

        HealthChecker checker = new HealthChecker(manager);
        checker.start();

        TransactionSender sender = new TransactionSender(manager);

        // Exemplo de envio periódico
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {

            sender.sendTransaction(request);
        }, 0, 1, TimeUnit.SECONDS);
    }
}
