package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.main.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 *
 * Clase que realiza la configuracion de multiDatasource para usar SpringData Repository
 *
 * @author jyauyo
 * */
@Configuration
public class MongoDbConfig {

    @Configuration
    @ConditionalOnProperty(
            value="configurationManager.datasource.mongodb.bdgestionsaldos.enabled",
            havingValue = "true",
            matchIfMissing = true)
    @EnableMongoRepositories(
            basePackages = "pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.repository")
    public class MongoDbDgGestionSaldosConfig {

        /**
         * Inicializamos transaccion a nivel de metodo
         *
         * @author jyauyo
         * */
        @Bean("dgbdgestionsaldos-transactionManager")
        MongoTransactionManager transactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
            return new MongoTransactionManager(mongoDatabaseFactory);
        }
    }
}
