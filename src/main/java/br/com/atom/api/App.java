package br.com.atom.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(scanBasePackages = { "br.com.atom.nschecker.controllers", "br.com.atom.nschecker.modules", "br.com.atom.nsplanner.controllers","br.com.atom.nsplanner.modules"})
//@ComponentScan("com.nschecker.controllers")
@Configuration
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
}
