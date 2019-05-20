package pl.dk.outboxeldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OutboxEventListenerApp {

	public static void main(String[] args) {
		SpringApplication.run(OutboxEventListenerApp.class, args);
	}
}
