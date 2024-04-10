package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general.ComprobantesPercepcion;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general.ElectronicoPercepcion;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general.FisicoPercepcion;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general.PercepcionProxy;
import pe.gob.sunat.tecnologiams2.arquitectura.framework.microservices.core.exception.ErrorEntity;
import pe.gob.sunat.tecnologiams2.arquitectura.framework.microservices.core.exception.UnprocessableEntityException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class ConsultarPercepcionesRetencionesPeriodos {
//http://plataformaunica.k8s.sunat.peru/v1/recaudacion/tributaria/cuentacontrol/percepcion/sujeto/{numRuc}?periodo={pertributario}
    @Value("$path")
    private String ruta;

    @Value("${reintentoApiSunat}")
    private int reintentos;

    @Value("${timeIntervalApiSunat}")
    private long tiempoEntreReintento;


    public Mono<Percepcion> getPercepcion(String numruc,  String perTributario){
        WebClient webClient = WebClient.builder().baseUrl(ruta).build();

        return webClient.get().uri(uriBuilder -> {
                    return uriBuilder
                            .path("v1/recaudacion/tributaria/cuentacontrol/percepcion/sujetos/")
                            .path("{numRuc}")
                            .path("?")
                            .path("periodo)")
                            .path("?")
                            .build(numruc,perTributario)
                            ;

                })
                .retrieve()
                .bodyToMono(Percepcion.class).map(
                        percepcionProxy -> {
                           // contribuyenteProxy.getDdpEstado();
                            Percepcion percepcion = new Percepcion();
                            percepcion.setMtoTotal(percepcionProxy.getMtoTotal());
                            ComprobantesPercepcion com= new ComprobantesPercepcion();
                            FisicoPercepcion fisico = new FisicoPercepcion();
                            fisico.setCantidxad(percepcion.getComprobantes().getFisico().getCantidxad());
                            fisico.setMtomtoPercepcion(percepcion.getComprobantes().getFisico().getMtomtoPercepcion());
                            ElectronicoPercepcion electronicoPercepcion = new ElectronicoPercepcion();
                            electronicoPercepcion.setMtoPercepcion(percepcion.getComprobantes().getElectronico().getMtoPercepcion());
                            electronicoPercepcion.setCantidxad(percepcion.getComprobantes().getElectronico().getCantidxad());
                            com.setElectronico(electronicoPercepcion);
                            com.setFisico(fisico);
                            percepcion.setComprobantes(com);
                            percepcion.setTotalComprobantes(percepcionProxy.getTotalComprobantes());
                            return percepcion;
                        })
                .retryWhen(Retry.backoff(reintentos, Duration.ofSeconds(tiempoEntreReintento)).filter(this::retry)
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                            throw new UnprocessableEntityException(new ErrorEntity(4224045, "Error al obtener contribuyente en api"));
                        })).doOnError(throwable -> {
                    if(!(throwable instanceof UnprocessableEntityException)){
                        throw new UnprocessableEntityException(new ErrorEntity(42224, "Error al invocar al servicio consulta"));
                    }
                });
    }

    public boolean retry(Throwable throwable) {
        if(throwable instanceof WebClientResponseException){
            int code = ((WebClientResponseException) throwable).getStatusCode().value();
            return (code==502 || code == 503 || code ==504 || code == 404);
        }

        return !(throwable instanceof NullPointerException);
    }

}
