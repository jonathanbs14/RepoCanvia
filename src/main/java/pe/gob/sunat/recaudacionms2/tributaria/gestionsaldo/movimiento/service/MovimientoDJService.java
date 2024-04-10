package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.service;

import reactor.core.publisher.Mono;

import java.util.Date;

public interface MovimientoDJService {

    Mono<Boolean> processMessage(String event, int retryNumber);

}
