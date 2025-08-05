package br.com;

import io.javalin.Javalin;

public class Server {
    public static void main(String[] args) {
        var app = Javalin.create()
                .start(9999);

        Service service = new Service();

        app.post("/payments", ctx -> {
            PaymentRequest request = ctx.bodyAsClass(PaymentRequest.class);
            service.execute(request);

            ctx.status(200).result("Pagamento recebido com sucesso");

        });
    }
}
