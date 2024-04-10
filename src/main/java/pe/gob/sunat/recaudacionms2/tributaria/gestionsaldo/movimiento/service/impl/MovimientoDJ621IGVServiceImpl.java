package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.parametria.param0012.ParamItem0012;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.parametria.param0017.ParamItem0017;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.parametria.param0018.ParamItem0018;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.Coeficiente;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.parametria.param0020.DataParametria0020;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.parametria.param0020.ParamItem0020;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.repository.ParametriaRepository;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto.CabeceraDJ;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto.DatosVO;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.ConsultarPercepcionesRetencionesPeriodos;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.DataDJVO;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.Percepcion;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general.Casilla;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general.DetallePago;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general.Documento;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.service.MovimientoDJService;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.service.OperacionMovimientoService;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.utils.*;
import pe.gob.sunat.tecnologiams2.arquitectura.framework.microservices.core.util.JsonUtilParser;
import pe.gob.sunat.tecnologiams2.arquitectura.framework.microservices.kafka.exception.ErrorException;
import reactor.core.publisher.Mono;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

import static pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.utils.Constantes.CODIGO_RESPUESTA_ERROR;
import static pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.utils.Constantes.COD_SEGUIMIENTO_EN_SALDO_IGV;

/**
 * Clase que realiza la captura del evento
 *
 * @author jyauyo
 */
@Service
public  class MovimientoDJ621IGVServiceImpl implements MovimientoDJService{
    private static final Logger LOGGER = LoggerFactory.getLogger(MovimientoDJ621IGVServiceImpl.class);

    @Value("${kafka.consumer.topic")
    private String topicConsumer;
    @Value("${kafka.producers[0].topics[0]}")
    private String topic0;
    @Value("recaudacionms2-tributaria-gsc-movimiento-dj-backend")
    private String nombreMicro;
    @Autowired
    private JsonUtilParser jsonUtilParser;
    private Integer retry;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ParametriaRepository parametriaRepository;
    @Autowired
    private ConsultarPercepcionesRetencionesPeriodos conusltaPer;
    @Autowired
    private  OperacionMovimientoService operacionMovimientoService;

    public MovimientoDJ621IGVServiceImpl(ParametriaRepository parametriaRepository, OperacionMovimientoService operacionMovimientoService) {
        this.parametriaRepository = parametriaRepository;
        this.operacionMovimientoService = operacionMovimientoService;
    }



    @Override
    public Mono<Boolean> processMessage(String event, int retryNumber) {
        long inicioEjecucion = System.currentTimeMillis();
        LOGGER.info("Clase Principal", event);
        LOGGER.info("Se procesa el mensaje para el evento ==> {}", event);
        DatosVO<DataDJVO> dataJson;
        String numIdEventoSaldo = null;
        DataParametria0020 dataParametria0020 = null;
        try {
            dataJson = LibUtil.getDefaultObjectReader().forType(new TypeReference<DatosVO<DataDJVO>>() {
            }).readValue(Objects.toString(event, "null"));
            LOGGER.info("Json ==> {}", dataJson);
            LOGGER.info("F2_491203_eIGVDJ621.docx");
            LOGGER.info("CUS02 – Transformar datos SA – IGV");
            LOGGER.info("INICIO ==> 2. Valida fecha de inicio del modelo de SA-IGV");
            String codEvent = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getCodEventoSaldo).orElse("");
            Date fetchEvent = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getFecEventoSaldo).orElse(null);
            ParamItem0017 param0017 = parametriaRepository.findDataParametria0017();
            String numRuc = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getNumRuc).orElse("");
            String perTributario = Optional.of(dataJson).map(DatosVO::getData).map(DataDJVO::getDetallePago).map(DetallePago::getPerTributarioPago).orElse("");
            Date fecMovimiento = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getFecEventoSaldo).orElse(null);

            dataParametria0020 = new DataParametria0020();
            String indComponente = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getIndComponente).orElse("");
            numIdEventoSaldo = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getNumIdEventoSaldo).orElse("");
            //punto 2
            ParamItem0020 paramItem0020 = parametriaRepository.findDataParametria0020ByCodSeguimiento(COD_SEGUIMIENTO_EN_SALDO_IGV);
            UtilSeguimiento.enviarMensajeSeguimiento(paramItem0020, codEvent, indComponente, kafkaTemplate, topicConsumer, numIdEventoSaldo, nombreMicro, topic0, jsonUtilParser);

            //assert fetchEvent != null;
            if (fetchEvent != null) {
                if (!ComparaFechasUtil.validaInicioFechasSA(param0017.getFecInicioModeloSA(), fetchEvent)) {
                    LOGGER.info("Fecha del evento " + codEvent + "es menor a la fecha de inicio de vigencia del modelo de SA-IGV");
                    return Mono.empty();
                }
                LOGGER.info("FIN ==> 2. Valida fecha de inicio del modelo de SA-IGV");
            }


            LOGGER.info("INICIO ==> 3.Valida vigencia del evento DJ 621 en SA-IGV");
            if (ComparaFechasUtil.validaRangoFechasSA(param0017.getFecInicioModeloSA(), param0017.getFecFinModeloSA(), fetchEvent)) {
                LOGGER.info("FECHA DE APLICACIÓN DE REIMPUTACIÓN DENTRO DEL RANGO");
            } else {
                LOGGER.info("Evento " + codEvent + " no se encuentra vigente en el modelo de SA-IGV");
                return Mono.empty();
            }
            LOGGER.info("FIN ==> 3.Valida vigencia del evento DJ 621 en SA-IGV");

            LOGGER.info("INICIO ==> 4.Obtener movimientos y saldos vigentes");
            String idParam = "2";
            List<ParamItem0012> paramItem0012List = parametriaRepository.findParamItem0012ByCodEvento(codEvent, fetchEvent, idParam);
            if (MovDJUtil.isEmpty(paramItem0012List)) {
                LOGGER.info("Evento " + codEvent + " no tiene movimientos vigentes");
                return Mono.empty();
            }
            paramItem0012List.forEach(t -> LOGGER.info("Parametro Movimiento : {}", t.getDesMovimiento()));

            LOGGER.info("INICIO ==> 5");
            Optional<List<ParamItem0012>> resultado = Optional.of(paramItem0012List.stream().filter(t -> Constantes.djPermitidos.contains(t.getCodEvento()) && t.getDesMovimiento().startsWith(Constantes.CIERRE)).collect(Collectors.toList()));


            LOGGER.info("FIN ==> 5");

            LOGGER.info("FIN ==> 4. Obtener movimientos y saldos vigentes");

            LOGGER.info("INICIO ==> 6.Validar periodo del evento");
            ParamItem0018 paramItem0018 = parametriaRepository.findDataParametria0018();
            Integer periodoInicioModeloSA = Optional.of(paramItem0018).map(ParamItem0018::getPerInicioModeloSa).map(Integer::parseInt).orElse(0);
            Integer periodoDj = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getPerDocumento).map(Integer::parseInt).orElse(0);

            if (periodoDj < periodoInicioModeloSA) {
                LOGGER.info("Se invoca al CUS11-Registrar evento DJ 621 en cuarentena");

                operacionMovimientoService.cus11RegistrarEvento(dataJson);
                return Mono.empty();
            }
            LOGGER.info("FIN ==>6.Validar periodo del evento");

            LOGGER.info("INICIO ==> 7");
            //pendiente servicio
            LOGGER.info("FIN ==>7");

            LOGGER.info("INICIO ==> 8 Obtiene la fecha de movimiento ");
            Date fechaMovimiento = obtenerFechaMovimiento(codEvent, Optional.of(dataJson).map(DatosVO::getData).map(DataDJVO::getDocumento).orElse(null));

            LOGGER.info("FIN ==>8.Obtiene la fecha de movimiento ");

            LOGGER.info("INICIO ==> 9 Obtiene las percepciones y retenciones del periodo  ");
            double mtoRetenciones = 5564;
            double mtoPercepciones = 5564;


            double mayor = 0;
             // Percepcion per = conusltaPer.getPercepcion(numRuc,perTributario).block();
            //  mtoPercepciones = per.getMtoTotal();

          // double  mtoPercepciones = mayor;

            //String collectionName = "202401";

            LOGGER.info("FIN ==>9.Obtiene las percepciones y retenciones del periodo  ");

            LOGGER.info("INICIO ==> 10.Generar movimientos por cuenta  ");
           // paramItem0012List.forEach(t -> generarMovimiento(t.getCodCuenta(), dataJson, mtoRetenciones, mtoPercepciones, fecMovimiento));

            generarMovimiento( dataJson, mtoRetenciones, mtoPercepciones, fecMovimiento,paramItem0012List);
            LOGGER.info("FIN ==>10.Generar movimientos por cuenta ");

            LOGGER.info("INICIO ==> 11.Registrar el evento ");
            LOGGER.info("Se invoca al CUS-10-Almacenar evento, movimientos y saldos");

            LOGGER.info("FIN ==>11.Registrar el evento ");
            operacionMovimientoService.cus12GenerarMovimientoCierre(dataJson);
            return Mono.empty();
        } catch (Exception e) {
            StringWriter printStackTrace = new StringWriter();
            e.printStackTrace(new PrintWriter(printStackTrace));
            LOGGER.error("Ocurre Error => {}", e.getMessage());
            LOGGER.info("Informacion proceso:" + ExceptionUtils.getStackTrace(e));
            UtilSeguimiento.getMensajeSeguimientoError(
                    numIdEventoSaldo, dataParametria0020, topicConsumer,
                    topic0, event, nombreMicro, (System.currentTimeMillis() - inicioEjecucion),
                    CODIGO_RESPUESTA_ERROR, e, LOGGER, kafkaTemplate, retryNumber, retry);
            Thread.currentThread().interrupt();
            return Mono.error(new ErrorException(e));
        }


    }

    public Date obtenerFechaMovimiento(String codEvent, Documento documento) { //pendiente
        Date fecha = null;
        switch (codEvent) {
            case Constantes.djOriginal:
                fecha = documento.getFecDocumento();
                break;
            case Constantes.djSustitutoria:
                LOGGER.info("djSustitutoria");
                break;
            case Constantes.djRectificatoria:
                LOGGER.info("djRectificatoria");
                break;
            default:
                fecha = new Date();
        }
        return fecha;
    }

    public void generarMovimiento(DatosVO<DataDJVO> dataJson, double mtoPercepciones, double mtoRetenciones, Date fecMovimiento, List<ParamItem0012> paramItem0012List) {
        DataDJVO data = Optional.of(dataJson).map(DatosVO::getData).orElse(null);
        String codEvent = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getCodEventoSaldo).orElse("");
        CabeceraDJ cabeceraDJ = Optional.of(dataJson).map(DatosVO::getCabecera).orElse(null);
        List<Casilla> casillas = Optional.of(data).map(DataDJVO::getLisCasillas).orElse(null);
        List<String> cuentas =  new ArrayList<>();
        int i;
        for( i =0 ; i <paramItem0012List.size();i++){
            cuentas.add(paramItem0012List.get(i).getCodCuenta());
            i++;
        }
        String numRuc = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getNumRuc).orElse("");
        String perTributario = Optional.of(dataJson).map(DatosVO::getData).map(DataDJVO::getDetallePago).map(DetallePago::getPerTributarioPago).orElse("");
        String periodoDj = Optional.of(cabeceraDJ).map(CabeceraDJ::getPerDocumento).orElse(null);
        double coeficienteDeclarad =0 ;
        double coeficienteDeterminado =0 ;
        double dividendo = 0;
        double divisor = 0;
        //double mtoRetenciones = 0;

        if(cuentas.contains(Constantes.CA0020) ){

            Coeficiente coeficiente = new Coeficiente();
            LOGGER.info("Se invoca al CUS03-Generar movimiento de la CA0020");
            //operacionMovimientoService.cus03GenerarSaldosAcumulado(dataJson, casillas, coeficienteDeterminado, periodoDj, cabeceraDJ, dividendo, divisor);
            coeficienteDeterminado = operacionMovimientoService.cus03GenerarSaldosAcumulado(dataJson, casillas, coeficienteDeclarad, periodoDj, cabeceraDJ, dividendo, divisor);
            LOGGER.info("Se invoca al CUS10");
            LOGGER.info("RETORNA EL COEFICIENTE DETERMINADO = " + coeficienteDeterminado);
        }
        if(cuentas.contains(Constantes.CA0016) ||cuentas.contains(Constantes.CA0018) || cuentas.contains(Constantes.CA0019) ){
                   LOGGER.info("Se invoca al CUS04-Calcular tributo a pagar (CAS140) según lo determinado (MIGE)");
                  coeficienteDeterminado =0.5;
                  Casilla castilla140D  = operacionMovimientoService.cus04CalcularTributoPagar(LOGGER,casillas, coeficienteDeterminado);
                  LOGGER.info("Se invoca al CUS05-Generar movimientos de CA0016, CA0018, CA0019");
                  operacionMovimientoService.cus05GenerarMovimientosCA0016CA0018CA0019( castilla140D , coeficienteDeterminado , cabeceraDJ, casillas, mtoPercepciones , mtoRetenciones,codEvent,perTributario);
        }
        if(cuentas.contains(Constantes.CA0024) ){
                LOGGER.info("Se invoca al CUS06-Generar movimiento de la CA0024");
                coeficienteDeclarad  = operacionMovimientoService.cus06GenerarSaldosAcumulado(dataJson, casillas, coeficienteDeclarad,periodoDj, cabeceraDJ, dividendo, divisor);
                LOGGER.info("RETORNA EL COEFICIENTE DETERMINADO = " , coeficienteDeclarad);
        }
        if(cuentas.contains(Constantes.CA0021) || cuentas.contains(Constantes.CA0022) || cuentas.contains(Constantes.CA0023) ){
                Casilla castilla140D  = operacionMovimientoService.cus07CalcularImpuestoResultante( LOGGER,casillas, coeficienteDeclarad);
                LOGGER.info("CASILLA 140D = " , castilla140D);
                LOGGER.info("Se invoca al CUS08-Generar movimientos de CA0021, CA0022, CA0023");
                operacionMovimientoService.cus08GenerarMovimientosCA0021CA0022CA0023( castilla140D , coeficienteDeclarad , cabeceraDJ, casillas, mtoPercepciones , mtoRetenciones,codEvent,perTributario);
        }
        if(cuentas.contains(Constantes.CA0017) ){
               LOGGER.info("Se invoca al CUS09-Generar movimientos de CA0017");
               operacionMovimientoService.cus09GenerarSaldosAcumulado(dataJson, numRuc, fecMovimiento, perTributario, mtoRetenciones);
        }

    }
}
