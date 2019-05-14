package pl.dk.outboxdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OutboxDemoApp {

	public static void main(String[] args) {
		SpringApplication.run(OutboxDemoApp.class, args);
	}
}
