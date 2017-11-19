package application;

import application.security.SCryptPasswordEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringMvcApplication {
    
    @Bean
    public SCryptPasswordEncoder SCryptPasswordEncoder() {
        return new SCryptPasswordEncoder();
    }

	public static void main(String[] args) {
		SpringApplication.run(SpringMvcApplication.class, args);
	}
}
