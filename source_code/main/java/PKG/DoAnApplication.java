package PKG;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"PKG.controller"})
@ComponentScan
public class DoAnApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoAnApplication.class, args);
	}
}
