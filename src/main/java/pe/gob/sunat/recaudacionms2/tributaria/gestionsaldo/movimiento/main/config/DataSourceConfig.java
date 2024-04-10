package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.main.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;


/**
 * Configuracion de datsources con repositories para usar SpringData Repository
 *
 * @author jyauyo
 * */
@Configuration
public class DataSourceConfig {

    /**
     * Clase que inicializa 2da, 3era, ... BD con entityManagerFactory y transactionManager
     *
     * @author jyauyo
     * */
    @Configuration
    @ConditionalOnProperty(
            value="configurationManager.datasource.informix.pdtinternet.enabled",
            havingValue = "true",
            matchIfMissing = true)
    @EnableJpaRepositories(
            basePackages = "pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basepdtinternet.repository",
            entityManagerFactoryRef = "dcpdtinternet-entityManagerFactory",
            transactionManagerRef = "dcpdtinternet-transactionManager")
    public class DataSourcePdtinternetConfig {

        @Autowired(required = false)
        @Qualifier("dcpdtinternet-entityManagerFactory")
        private EntityManagerFactory entityManagerFactory;

        @Bean("dcpdtinternet-transactionManager")
        public PlatformTransactionManager transactionManager() {
            JpaTransactionManager transactionManager = new JpaTransactionManager();
            transactionManager.setEntityManagerFactory(entityManagerFactory);
            return transactionManager;
        }
    }
}
