package io.github.jfilak.adt.http2rfc;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    private static final String PORT_ENV = "HTTP2RFC_PORT";
    private static final int DEFAULT_PORT = 8080;
    private static final String ADT_PATH = "/sap/bc/adt/*";

    private Application() {
    }

    public static void main(String[] args) {
        int port = resolvePort();
        Javalin app = create().start(port);
        LOG.info("adt-http2rfc-tunnel listening on http://0.0.0.0:{}", port);
        Runtime.getRuntime().addShutdownHook(new Thread(app::stop, "http2rfc-shutdown"));
    }

    static Javalin create() {
        return Javalin.create(cfg -> cfg.showJavalinBanner = false)
                .get("/health", ctx -> ctx.json(new HealthStatus("UP")))
                .get(ADT_PATH, Application::dumpAdtRequest)
                .post(ADT_PATH, Application::dumpAdtRequest)
                .put(ADT_PATH, Application::dumpAdtRequest)
                .delete(ADT_PATH, Application::dumpAdtRequest)
                .patch(ADT_PATH, Application::dumpAdtRequest)
                .head(ADT_PATH, Application::dumpAdtRequest)
                .options(ADT_PATH, Application::dumpAdtRequest);
    }

    private static void dumpAdtRequest(Context ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("==== ADT HTTP request ====\n");
        sb.append(ctx.method()).append(' ').append(ctx.path());
        String query = ctx.queryString();
        if (query != null && !query.isEmpty()) {
            sb.append('?').append(query);
        }
        sb.append('\n');
        ctx.headerMap().forEach((name, value) ->
                sb.append(name).append(": ").append(value).append('\n'));
        sb.append('\n');
        sb.append(ctx.body());
        sb.append("\n==== end of request ====");
        System.out.println(sb);
        ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static int resolvePort() {
        String raw = System.getenv(PORT_ENV);
        if (raw == null || raw.isBlank()) {
            return DEFAULT_PORT;
        }
        try {
            return Integer.parseInt(raw.trim());
        } catch (NumberFormatException e) {
            LOG.warn("Invalid {}={}, falling back to {}", PORT_ENV, raw, DEFAULT_PORT);
            return DEFAULT_PORT;
        }
    }

    record HealthStatus(String status) {
    }
}
