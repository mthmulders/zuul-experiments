package tk.mulders.blog.zuul.gateway;

import tk.mulders.blog.zuul.gateway.filters.AuthenticatedUserFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringBootApplication
public class Gateway {
    public static void main(String[] args) {
        SpringApplication.run(Gateway.class, args);
    }

    @Bean
    public AuthenticatedUserFilter authenticatedUserFilter() {
        return new AuthenticatedUserFilter();
    }
}
