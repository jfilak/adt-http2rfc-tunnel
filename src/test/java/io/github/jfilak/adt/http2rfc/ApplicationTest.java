package io.github.jfilak.adt.http2rfc;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationTest {

    @Test
    void healthEndpointReturnsUp() {
        Javalin app = Application.create();
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/health");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("\"status\":\"UP\"");
        });
    }
}
