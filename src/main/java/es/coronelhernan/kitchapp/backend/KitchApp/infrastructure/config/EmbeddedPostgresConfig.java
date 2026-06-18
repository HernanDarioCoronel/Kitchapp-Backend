package es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.config;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@Profile("portable")
public class EmbeddedPostgresConfig {

    private static final int PG_PORT = 15432;

    @Bean(destroyMethod = "close")
    public EmbeddedPostgres embeddedPostgres() throws IOException {
        Path dataDir = Paths.get(System.getProperty("user.home"), ".kitchapp", "pgdata");
        Files.createDirectories(dataDir);
        return EmbeddedPostgres.builder()
                .setPort(PG_PORT)
                .setDataDirectory(dataDir)
                .start();
    }

    @Bean
    @Primary
    public DataSource dataSource(EmbeddedPostgres embeddedPostgres) {
        return embeddedPostgres.getPostgresDatabase();
    }
}
