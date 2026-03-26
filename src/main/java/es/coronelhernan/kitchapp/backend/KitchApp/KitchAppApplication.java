package es.coronelhernan.kitchapp.backend.KitchApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(
		basePackages = {
				"es.coronelhernan.kitchapp.backend.KitchApp.domain",
				"es.coronelhernan.kitchapp.backend.KitchApp.infrastructure",
				"es.coronelhernan.kitchapp.backend.KitchApp.api"
		}
)
public class KitchAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(KitchAppApplication.class, args);
	}

}
