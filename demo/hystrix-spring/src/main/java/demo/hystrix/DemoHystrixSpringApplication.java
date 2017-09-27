package demo.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class DemoHystrixSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoHystrixSpringApplication.class, args);
    }
}
