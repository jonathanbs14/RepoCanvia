package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.client.mapper.Contribuyente;
import pe.gob.sunat.tecnologiams2.arquitectura.framework.microservices.core.exception.ErrorEntity;
import pe.gob.sunat.tecnologiams2.arquitectura.framework.microservices.core.exception.UnprocessableEntityException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class ContribuyenteCliente {

    @Value("${rutaApiSunat:}")
    private String ruta;

    /**
     * Usando RestTemplate
     *
     * @author jyauyo
     * */
    public Mono<Contribuyente> getContribuyenteFromRestTemplate(String ruc){
        WebClient webClient = WebClient.builder().baseUrl(ruta).build();

        return webClient.get().uri(uriBuilder -> {
                    return uriBuilder
                            .path("contribuyente/registro/e/contribuyentes/")
                            .path("{ruc}").build(ruc);
                })
                .retrieve()
                .bodyToMono(ContribuyenteProxy.class).map(
                        contribuyenteProxy -> {
                            contribuyenteProxy.getDdpEstado();
                            Contribuyente contribuyente = new Contribuyente();
                            contribuyente.setEstado(contribuyenteProxy.getDdpEstado());
                            contribuyente.setNumRuc(contribuyenteProxy.getDdpNumruc());
                            contribuyente.setRazonSocial(contribuyenteProxy.getDdpNombre());
                            return contribuyente;
                        })
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                            throw new UnprocessableEntityException(new ErrorEntity(4224045, "No existe tertereeeeeeeeeeeeeeeeeeee"));
                        }));//.doOnError(e-> {throw new UnprocessableEntityException(new ErrorEntity(422404, "No existe Contribuyente"));})
        //.transformDeferred(CircuitBreakerOperator.of(circuitBreaker));
    }
}
