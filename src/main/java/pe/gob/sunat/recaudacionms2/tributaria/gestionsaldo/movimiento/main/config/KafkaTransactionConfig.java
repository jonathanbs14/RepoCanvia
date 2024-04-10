package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.utils.ConstantesNamesBean;

/**
 * Clase que configura la transaccion para kafka Producer.
 *
 * @author jyauyo
 * */
@EnableTransactionManagement
@Configuration
public class KafkaTransactionConfig {

    /**
     * Metodo que sobre escribe el KafkaTransactionManager SOLO para el primer Producer indicado en el YAML
     *
     * @author jyauyo
     * */
    @Bean(name = ConstantesNamesBean.KAFKA_TRANSACCION_MANAGER)
    public KafkaTransactionManager kafkaTransactionManager(ProducerFactory producerFactory) {
        return new KafkaTransactionManager(producerFactory);
    }
}
