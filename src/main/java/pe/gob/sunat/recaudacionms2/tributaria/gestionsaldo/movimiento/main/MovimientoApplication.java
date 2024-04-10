package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.main;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"pe.gob.sunat"})
public class MovimientoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MovimientoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
