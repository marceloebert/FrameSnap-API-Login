package com.fiap.framesnap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.fiap.framesnap")
public class FrameSnapApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrameSnapApplication.class, args);
	}

}
