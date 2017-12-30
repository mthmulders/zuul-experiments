package tk.mulders.blog.zuul.backend;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static spark.Spark.before;
import static spark.Spark.get;

@Slf4j
public class App {
    public static void main(String[] args) {
        // 1. Register a filter that reads a custom HTTP header added by the gateway.
        before("/*", (req, res) -> {
            final String username = req.headers("username");
            req.attribute("username", username);
            log.info("Processing request for user {}", username);
        });
        // 2. Handle requests by replying with a friendly greeting.
        get("/*", "text/html", (req, res) -> {
            final String username = req.attribute("username");
            final String greeting = Optional.ofNullable(username).orElse("world");
            return String.format("Hello, %s", greeting);
        });
    }
}
