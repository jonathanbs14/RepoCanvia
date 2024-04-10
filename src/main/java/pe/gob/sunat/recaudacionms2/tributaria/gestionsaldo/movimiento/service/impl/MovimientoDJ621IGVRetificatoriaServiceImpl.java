package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.Coeficiente;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.parametria.param0012.ParamItem0012;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.parametria.param0017.ParamItem0017;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.parametria.param0018.ParamItem0018;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.repository.ParametriaRepository;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto.CabeceraDJ;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto.DatosVO;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.ConsultarPercepcionesRetencionesPeriodos;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.DataDJVO;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general.Casilla;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general.DetallePago;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general.Documento;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.service.MovimientoDJService;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.service.OperacionMovimientoService;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.utils.ComparaFechasUtil;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.utils.Constantes;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.utils.LibUtil;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.utils.MovDJUtil;
import pe.gob.sunat.tecnologiams2.arquitectura.framework.microservices.core.util.JsonUtilParser;
import pe.gob.sunat.tecnologiams2.arquitectura.framework.microservices.kafka.exception.ErrorException;
import reactor.core.publisher.Mono;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class MovimientoDJ621IGVRetificatoriaServiceImpl implements MovimientoDJService {
    private static final Logger LOG = LoggerFactory.getLogger(MovimientoDJ621IGVServiceImpl.class);
    @Value("${kafka.consumer.topic")
    private String topicConsumer;
    @Value("${kafka.producers[0].topics[0]}")
    private String topic0;
    @Value("recaudacionms2-tributaria-gsc-movimiento-dj-backend")
    private String nombreMicro;
    @Autowired
    private JsonUtilParser jsonUtilParser;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ParametriaRepository parametriaRepository;
    @Autowired
    private ConsultarPercepcionesRetencionesPeriodos conusltaPer;
    @Autowired
    private  OperacionMovimientoService operacionMovimientoService;


    public MovimientoDJ621IGVRetificatoriaServiceImpl(ParametriaRepository parametriaRepository, OperacionMovimientoService operacionMovimientoService) {
        this.parametriaRepository = parametriaRepository;
        this.operacionMovimientoService = operacionMovimientoService;
    }



    @Override
    public Mono<Boolean> processMessage(String event, int retryNumber) {

        LOG.info("Clase Principal", event);
        LOG.info("Se procesa el mensaje para el evento ==> {}", event);
        try {
            DatosVO<DataDJVO> dataJson = LibUtil.getDefaultObjectReader().forType(new TypeReference<DatosVO<DataDJVO>>() {
            }).readValue(Objects.toString(event, "null"));
            LOG.info("Json ==> {}", dataJson);
            LOG.info("F2_491203_eIGVDJ621.docx");
            LOG.info("CUS02 – Transformar datos SA – IGV");
            LOG.info("INICIO ==> 2. Valida fecha de inicio del modelo de SA-IGV");
            String codEvent = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getCodEventoSaldo).orElse("");
            Date fetchEvent = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getFecEventoSaldo).orElse(null);
            ParamItem0017 param0017 = parametriaRepository.findDataParametria0017();
            String numRuc = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getNumRuc).orElse("");
            String perTributario = Optional.of(dataJson).map(DatosVO::getData).map(DataDJVO::getDetallePago).map(DetallePago::getPerTributarioPago).orElse("");

            Date fecMovimiento = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getFecEventoSaldo).orElse(null);

            String collectionName = "202401";

            assert fetchEvent != null;
            if (!ComparaFechasUtil.validaInicioFechasSA(param0017.getFecInicioModeloSA(), fetchEvent)) {
                LOG.info("Fecha del evento " + codEvent + "es menor a la fecha de inicio de vigencia del modelo de SA-IGV");
                return Mono.empty();
            }
            LOG.info("FIN ==> 2. Valida fecha de inicio del modelo de SA-IGV");



            LOG.info("INICIO ==> 3.Valida vigencia del evento DJ 621 en SA-IGV");
            if (ComparaFechasUtil.validaRangoFechasSA(param0017.getFecInicioModeloSA(), param0017.getFecFinModeloSA(), fetchEvent)) {
                LOG.info("FECHA DE APLICACIÓN DE REIMPUTACIÓN DENTRO DEL RANGO");
            } else {
                LOG.info("Evento " + codEvent + " no se encuentra vigente en el modelo de SA-IGV");
                return Mono.empty();
            }
            LOG.info("FIN ==> 3.Valida vigencia del evento DJ 621 en SA-IGV");

            LOG.info("INICIO ==> 4.Obtener movimientos y saldos vigentes");String idParam = "2";
            List<ParamItem0012> paramItem0012List = parametriaRepository.findParamItem0012ByCodEvento(codEvent, fetchEvent,idParam);
            if (MovDJUtil.isEmpty(paramItem0012List)) {
                LOG.info("Evento " + codEvent + " no tiene movimientos vigentes");
                return Mono.empty();
            }
            paramItem0012List.forEach(t -> LOG.info("Parametro Movimiento : {}", t.getDesMovimiento()));

            LOG.info("INICIO ==> 5");
            Optional<List<ParamItem0012>> resultado = Optional.of(paramItem0012List.stream().filter(t -> Constantes.djPermitidos.contains(t.getCodEvento()) && t.getDesMovimiento().startsWith(Constantes.CIERRE)).collect(Collectors.toList()));



            LOG.info("FIN ==> 5");

            LOG.info("FIN ==> 4. Obtener movimientos y saldos vigentes");

            LOG.info("INICIO ==> 6.Validar periodo del evento");
            ParamItem0018 paramItem0018 = parametriaRepository.findDataParametria0018();
            Integer periodoInicioModeloSA = Optional.of(paramItem0018).map(ParamItem0018::getPerInicioModeloSa).map(Integer::parseInt).orElse(0);
            Integer periodoDj = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getPerDocumento).map(Integer::parseInt).orElse(0);
/*
            if (periodoDj < periodoInicioModeloSA) {
                LOG.info("Se invoca al CUS09-Generar movimientos de CA0017");
                LOG.info("Se invoca al CUS11-Registrar evento DJ 621 en cuarentena");
                double mtoRetenciones  =50;

                //operacionMovimientoService.cus06GenerarSaldosAcumulado(dataJson, casillas, coeficienteDeclarad,periodoDj, cabeceraDJ);
               // operacionMovimientoService.cus09GenerarSaldosAcumulado(dataJson,numRuc,fecMovimiento,perTributario,collectionName,mtoRetenciones);
                //operacionMovimientoService.cus11RegistrarEvento(dataJson);
                return Mono.empty();
            }*/


            LOG.info("FIN ==>6.Validar periodo del evento");

            LOG.info("INICIO ==> 7");
            //pendiente servicio
            LOG.info("FIN ==>7");

            LOG.info("INICIO ==> 8 Obtiene la fecha de movimiento ");
            Date fechaMovimiento = obtenerFechaMovimiento(codEvent, Optional.of(dataJson).map(DatosVO::getData).map(DataDJVO::getDocumento).orElse(null));

            LOG.info("FIN ==>8.Obtiene la fecha de movimiento ");

            LOG.info("INICIO ==> 9 Obtiene las percepciones y retenciones del periodo  ");
            double mtoRetenciones  =50;


         /*   double mayor = 0;
            Percepcion per = conusltaPer.getPercepcion(numRuc,perTributario).block();
            if(per.getComprobantes().getFisico().getMtomtoPercepcion() > per.getComprobantes().getElectronico().getMtoPercepcion()){
                mayor = per.getComprobantes().getFisico().getMtomtoPercepcion();
            }else{
                mayor = per.getComprobantes().getElectronico().getMtoPercepcion();
            }*/
            double  mtoPercepciones = 50;

            //String collectionName = "202401";

            LOG.info("FIN ==>9.Obtiene las percepciones y retenciones del periodo  ");

            LOG.info("INICIO ==> 10.Generar movimientos por cuenta  ");
            paramItem0012List.forEach(t -> generarMovimiento(t.getCodCuenta() ,dataJson ,mtoRetenciones,mtoPercepciones,fecMovimiento,collectionName));
            LOG.info("FIN ==>10.Generar movimientos por cuenta ");

            LOG.info("INICIO ==> 11.Registrar el evento ");
            LOG.info("Se invoca al CUS-10-Almacenar evento, movimientos y saldos");
            //  operacionMovimientoService.cus10AlmacenarActualizarSaldos(LOG);
            LOG.info("FIN ==>11.Registrar el evento ");

            LOG.info("INICIO ==> 12.Registrar movimientos de cierre  ");
            operacionMovimientoService.cus12GenerarMovimientoCierre( dataJson);
            return Mono.empty();
        } catch (Exception e) {
            StringWriter printStackTrace = new StringWriter();
            e.printStackTrace(new PrintWriter(printStackTrace));
            LOG.error("Ocurre Error => {}", e.getMessage());
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
                LOG.info("djSustitutoria");
                break;
            case Constantes.djRectificatoria:
                LOG.info("djRectificatoria");
                break;
            default:
                fecha = new Date();
        }
        return fecha;
    }

    public void generarMovimiento(String codCuenta, DatosVO<DataDJVO> dataJson, double mtoPercepciones, double mtoRetenciones, Date fecMovimiento, String collectionName) {
        DataDJVO data = Optional.of(dataJson).map(DatosVO::getData).orElse(null);
        String codEvent = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getCodEventoSaldo).orElse("");
        CabeceraDJ cabeceraDJ = Optional.of(dataJson).map(DatosVO::getCabecera).orElse(null);
        List<Casilla> casillas = Optional.of(data).map(DataDJVO::getLisCasillas).orElse(null);
        // Casilla castilla140D = new Casilla();
        String numRuc = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getNumRuc).orElse("");
        String perTributario = Optional.of(dataJson).map(DatosVO::getData).map(DataDJVO::getDetallePago).map(DetallePago::getPerTributarioPago).orElse("");
        String periodoDj = Optional.of(cabeceraDJ).map(CabeceraDJ::getPerDocumento).orElse(null);
        double coeficienteDeclarad =0 ;
        double coeficienteDeterminado =0 ;
        double nPeriodosCoeficiente = 0 ;
        double dividendo = 0;
        double divisor = 0;
        //double mtoRetenciones = 0;
        switch (codCuenta) {
            case Constantes.CA0020:
                Coeficiente coeficiente =new Coeficiente();
                LOG.info("Se invoca al CUS03-Generar movimiento de la CA0020");
                operacionMovimientoService.cus03GenerarSaldosAcumulado(dataJson,casillas, coeficienteDeterminado,periodoDj,cabeceraDJ,dividendo, divisor);
                coeficienteDeterminado  = operacionMovimientoService.cus03GenerarSaldosAcumulado(dataJson, casillas, coeficienteDeclarad,periodoDj, cabeceraDJ, dividendo, divisor);
                LOG.info("RETORNA EL COEFICIENTE DETERMINADO = " + coeficienteDeterminado);
                break;
            case Constantes.CA0016, Constantes.CA0018, Constantes.CA0019:
                LOG.info("Se invoca al CUS04-Calcular tributo a pagar (CAS140) según lo determinado (MIGE)");
                // castilla140D  =  operacionMovimientoService.cus04CalcularTributoPagar(LOG,casillas, coeficienteDeterminado);
                coeficienteDeterminado =0.5;
                Casilla castilla140D  = operacionMovimientoService.cus04CalcularTributoPagar(LOG,casillas, coeficienteDeterminado);
                LOG.info("Se invoca al CUS05-Generar movimientos de CA0016, CA0018, CA0019");
                operacionMovimientoService.cus05GenerarMovimientosCA0016CA0018CA0019( castilla140D , coeficienteDeterminado , cabeceraDJ, casillas, mtoPercepciones , mtoRetenciones,codEvent,perTributario);
                break;
            case Constantes.CA0024:
                LOG.info("Se invoca al CUS06-Generar movimiento de la CA0024");

                //   coeficienteDeclarad  = operacionMovimientoService.cus06GenerarSaldosAcumulado(dataJson, casillas, coeficienteDeclarad,periodoDj, cabeceraDJ, dividendo, divisor);

                //  castilla140D  = operacionMovimientoService.cus07CalcularImpuestoResultante( LOG,casillas, coeficienteDeclarad);

                coeficienteDeclarad  = operacionMovimientoService.cus06GenerarSaldosAcumulado(dataJson, casillas, coeficienteDeclarad,periodoDj, cabeceraDJ,dividendo,divisor);
                castilla140D  = operacionMovimientoService.cus07CalcularImpuestoResultante( LOG,casillas, coeficienteDeclarad);
                LOG.info("Se invoca al CUS08-Generar movimientos de CA0021, CA0022, CA0023");
                operacionMovimientoService.cus08GenerarMovimientosCA0021CA0022CA0023( castilla140D , coeficienteDeclarad , cabeceraDJ, casillas, mtoPercepciones , mtoRetenciones,codEvent,perTributario);
                break;
            case Constantes.CA0017:
                LOG.info("Se invoca al CUS09-Generar movimientos de CA0017");

                operacionMovimientoService.cus09GenerarSaldosAcumulado(dataJson, numRuc, fecMovimiento, perTributario, mtoRetenciones);
                break;
        }
    }
}
