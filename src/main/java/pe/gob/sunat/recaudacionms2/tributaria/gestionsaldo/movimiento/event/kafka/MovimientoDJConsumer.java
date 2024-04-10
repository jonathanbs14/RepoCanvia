package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.event.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.service.MovimientoDJService;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.utils.ConstantesNamesBean;
import pe.gob.sunat.tecnologiams2.arquitectura.framework.microservices.kafka.config.consumer.EventConsumer;

/**
 * Clase que consumer mensaje Kafka del evento Boleta 1663
 *
 * @author jyauyo
 * */
public class MovimientoDJConsumer implements EventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MovimientoDJConsumer.class);

    private MovimientoDJService movimientoDJService;

    public void setMovimientoDJService(MovimientoDJService movimientoDJService) {
        this.movimientoDJService = movimientoDJService;
    }

    @Override
    @KafkaListener(id = "${kafka.consumer.clientId}", topics = "${kafka.consumer.topic}", groupId = "${kafka.consumer.consumerGroup}")
    @Transactional(transactionManager = ConstantesNamesBean.KAFKA_TRANSACCION_MANAGER)
    public void consume(ConsumerRecord record, Acknowledgment ack,@Header(KafkaHeaders.DELIVERY_ATTEMPT) int retryNumber) {
        logger.info("Consuming in CapturadorNoConteConsumer the event {} from topic {} with offset {}", record.value().toString(), record.topic(), record.offset());

        movimientoDJService.processMessage(record.value().toString(), retryNumber)
                .doOnSuccess(response -> {
                    logger.info("CapturadorNoConteConsumer:completado con exito!!");
                    ack.acknowledge();
                })
                .block();
    }
}
