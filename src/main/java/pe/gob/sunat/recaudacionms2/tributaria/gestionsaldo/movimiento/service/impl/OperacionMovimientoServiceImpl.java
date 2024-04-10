package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.MovimientosSayyyymm;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.eventosanteriores.DocumentoAsociadoAfectado;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.eventosanteriores.EventosAnterioresDJ;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.general.Auditoria;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.general.MetadataGral;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.movimientossa.Cabecera;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.movimientossa.Data;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.movimientossa.DatosDocSustento;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.movimientossa.*;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.parametria.param0017.ParamItem0017;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.parametria.param0019.ParamItem0019;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.parametria.param0028.ParamItem0028;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.model.impl.MovimientosSayyyymmModelImpl;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.repository.EventosAnterioresDJRepository;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.repository.ParametriaRepository;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto.*;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto.Metadata;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.*;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general.*;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general.DatosMovimiento;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.model.MovimientoAcumPercReTModel;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.model.impl.MovimientoAcumPercReTModelImpl;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.service.OperacionMovimientoService;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.utils.ComparaFechasUtil;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.utils.Constantes;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.utils.MovDJUtil;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.utils.Utils;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.repository.EventosAnterioresRepository;
//import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.eventosanteriores.Cabecera;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;


//************
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.producer.dj.DataSaVO;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.producer.dj.Movimientos;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.producer.dj.ProducerDataSaVO;
import pe.gob.sunat.tecnologiams2.arquitectura.framework.microservices.kafka.exception.LoopException;
import pe.gob.sunat.tecnologiams2.arquitectura.framework.microservices.core.util.JsonUtilParser;
import org.springframework.beans.factory.annotation.Value;
import pe.gob.sunat.tecnologiams2.arquitectura.framework.microservices.kafka.exception.RetriesException;
//import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.client.MigeCliente;
//import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.client.mapper.Mige;
//************

@Service
public class OperacionMovimientoServiceImpl implements OperacionMovimientoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperacionMovimientoServiceImpl.class);
    @Autowired
    private MovimientoAcumPercReTModel movimientoAcumPercReTModel;

    @Autowired
    private EventosAnterioresRepository eventosAnterioresRepository;

    @Autowired
    private EventosAnterioresDJRepository eventosAnterioresDJRepository;

    @Autowired
    private MovimientosSayyyymmModelImpl movimientosSayyyymmModel;

    @Autowired
    private MovimientoAcumPercReTModelImpl movimientoRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ParametriaRepository parametriaRepository;

   // @Autowired
    private DataSaVO dataSaVO;

    public OperacionMovimientoServiceImpl() {
    }

    //************


    private List<Movimientos> lisMovimientos;

    @Value("${kafka.producers[0].topics[1]}")
    private String topic1;

    @Autowired
    private JsonUtilParser jsonUtilParser;
    //************

    @Override
    public double cus03GenerarSaldosAcumulado(DatosVO<DataDJVO> dataJson, List<Casilla> casillas, double coeficienteDeterminado, String periodoDj, CabeceraDJ cabeceraDJ, double dividendo, double divisor) {
        LOGGER.info("F2_491203_eIGVDJ621.docx");
        LOGGER.info("INICIO ==> 1.Obtener casillas de la DJ 621 que afectan al saldo CA0020");
        MovimientosSayyyymm movimientoCierreGenerado = new MovimientosSayyyymm();

        //*******************
        lisMovimientos = new ArrayList<>();
        dataSaVO = new DataSaVO();
        //*******************

        String ruc = Optional.of(cabeceraDJ).map(CabeceraDJ::getNumRuc).orElse("");
        String codDocumento = Optional.of(dataJson).map(DatosVO::getData).map(DataDJVO::getDocumento).map(Documento::getCodDocumento).orElse("");
        String periodo = Optional.of(cabeceraDJ).map(CabeceraDJ::getPerDocumento).orElse("");
        String numOrden = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getNumDocumento).orElse("");
        String montoMovi = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getNumDocumento).orElse("");
        String codEvento = dataJson.getCabecera().getCodEventoSaldo();
        Date fetchEvent = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getFecEventoSaldo).orElse(null);
        Date fecMov = Optional.of(cabeceraDJ).map(CabeceraDJ::getFecEventoSaldo).orElse(new Date());

        Casilla CAS100 = casillas.stream().filter(t -> Constantes.CAS100.contains(t.getNumCas())).toList().get(0);
        Casilla CAS160 = casillas.stream().filter(t -> Constantes.CAS160.contains(t.getNumCas())).toList().get(0);
        Casilla CAS154 = casillas.stream().filter(t -> Constantes.CAS154.contains(t.getNumCas())).toList().get(0);
        Casilla CAS105 = casillas.stream().filter(t -> Constantes.CAS105.contains(t.getNumCas())).toList().get(0);
        Casilla CAS106 = casillas.stream().filter(t -> Constantes.CAS106.contains(t.getNumCas())).toList().get(0);
        Casilla CAS102 = casillas.stream().filter(t -> Constantes.CAS102.contains(t.getNumCas())).toList().get(0);
        Casilla CAS162 = casillas.stream().filter(t -> Constantes.CAS162.contains(t.getNumCas())).toList().get(0);
        int flag = 0;
        try {
            //1.- Obtener casillas de la DJ621
            List<String> casFiltro = Arrays.asList(Constantes.CAS100, Constantes.CAS160, Constantes.CAS154, Constantes.CAS105, Constantes.CAS106, Constantes.CAS102, Constantes.CAS162);
            List<Casilla> saldo0020 = casillas.stream().filter(t -> casFiltro.contains(t.getNumCas())).toList();
            Casilla cas100 = saldo0020.get(0);
            Casilla cas160 = saldo0020.get(1);
            Casilla cas154 = saldo0020.get(2);
            Casilla cas105 = saldo0020.get(3);
            Casilla cas106 = saldo0020.get(4);
            Casilla cas102 = saldo0020.get(5);
            Casilla cas162 = saldo0020.get(6);
            Double valorCas100 = Double.valueOf(cas100.getValCas());
            Double valorCas160 = Double.valueOf(cas160.getValCas());
            Double valorCas154 = Double.valueOf(cas154.getValCas());
            Double valorCas105 = Double.valueOf(cas105.getValCas());
            Double valorCas106 = Double.valueOf(cas106.getValCas());
            Double valorCas102 = Double.valueOf(cas102.getValCas());
            Double valorCas162 = Double.valueOf(cas162.getValCas());

            if (!(((valorCas100 > 0.0) || (valorCas160 > 0.0) || (valorCas154 > 0.0)) && (valorCas105 > 0.0))) {
                coeficienteDeterminado = 0.0;

                MovimientosSayyyymm movimientoCA0020 = new MovimientosSayyyymm();
                Data dataCA0020 = new Data();
                //Metadata
                MetadataGral metadat = new MetadataGral();
                metadat.setNumIdEvento(cabeceraDJ.getNumIdEventoPadre());
                metadat.setNumVersionJSON("1");
                movimientoCA0020.setMetadata(metadat);
                //Cabecera
                Cabecera cabeceraCA0020 = new Cabecera();
                cabeceraCA0020.setCodMovimiento(cabeceraDJ.getCodDocumento());
                cabeceraCA0020.setFecMovimiento(cabeceraDJ.getFecDocumento());
                cabeceraCA0020.setNumIdMovimiento(cabeceraDJ.getNumDocumento());
                cabeceraCA0020.setCodDependencia(cabeceraDJ.getCodDependencia());
                cabeceraCA0020.setNumRuc(cabeceraDJ.getNumRuc());
                cabeceraCA0020.setCodEvento(codEvento);
                cabeceraCA0020.setNumIdEventoSaldo("");
                cabeceraCA0020.setPerTributario("010101");
                movimientoCA0020.setCabecera(cabeceraCA0020);

                DatosDocSustento documentoSusCA0020 = new DatosDocSustento();
                documentoSusCA0020.setCodDocumento("0621");
                documentoSusCA0020.setCodTributo("010101");
                documentoSusCA0020.setNumDocumento(cabeceraDJ.getNumDocumento());
                documentoSusCA0020.setCodTipoDJ(Utils.obtenerTipoDJ(codEvento));
                documentoSusCA0020.setCodTipoRecti(Utils.obtenerTipoRetificatoria(codEvento));
                documentoSusCA0020.setPerTributario(cabeceraDJ.getPerDocumento());
                documentoSusCA0020.setCodTributo(Utils.obtenerTipoRetificatoria(codEvento));
                documentoSusCA0020.setIndTipoBP("");
                dataCA0020.setDatosDocSustento(documentoSusCA0020);

                DatosCuentaAfectada cuentaAfectadaCA0020 = new DatosCuentaAfectada();
                cuentaAfectadaCA0020.setNumCta(Constantes.CA0020);
                cuentaAfectadaCA0020.setMtoAFavor(coeficienteDeterminado);
                dataCA0020.setDatosCuentaAfectada(cuentaAfectadaCA0020);
                //Datos que afectan al saldo acumulado
                DatosCalculoIGV datosSaldoAcumuladoCA0020 = new DatosCalculoIGV();
                datosSaldoAcumuladoCA0020.setVtasGravCas100 (valorCas100);
                datosSaldoAcumuladoCA0020.setMtoVentNetasLey31556(valorCas154);  //setVtasGravCas154(valorCas154); //mtoVentNetasLey31556 - setVtasGravCas154
                datosSaldoAcumuladoCA0020.setMtoVentasLey27037 (valorCas160);
                datosSaldoAcumuladoCA0020.setMtoVentasNoGravadas(valorCas105);
                datosSaldoAcumuladoCA0020.setMtoExportFacturadas(valorCas106);
                datosSaldoAcumuladoCA0020.setMtoDescuentosDevol(valorCas102);
                datosSaldoAcumuladoCA0020.setMtoDtosDevolLey27037(valorCas162);
                datosSaldoAcumuladoCA0020.setVtasNacionalesGrav(valorCas100 + valorCas154 + valorCas160);
                dataCA0020.setDatosCalculoIGV(datosSaldoAcumuladoCA0020);
                //Datos de control y gestión
                Control datosGestionControlCA0020 = new Control();
                //datosGestionControlCA0020.setIndEstado("A");
                datosGestionControlCA0020.setMtoSaldoDespMov(coeficienteDeterminado);
                datosGestionControlCA0020.setDividendo(dividendo);
                datosGestionControlCA0020.setDivisor(divisor);
                dataCA0020.setControl(datosGestionControlCA0020);
                movimientoCA0020.setData(dataCA0020);
                movimientosSayyyymmModel.saveMovimientosSayyyymmInDynamicCollection(movimientoCA0020, Utils.generarNombreCollectionMovimientosSA());
                dataSaVO = ProducerDataSaVO.getMensajeDataSa(dataJson);
                lisMovimientos.add(ProducerDataSaVO.getMovimientos(movimientoCA0020));

                //<<<<<<<<<<<<<<<<<<<<<<<<<<< INICIO GRABA EN COEFICIENTE EN PASO1
                Coeficientes coeficientes = new Coeficientes();
                pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.Data data = new pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.Data();
                //Metadata
                Metadata metadata = new Metadata();
                metadata.setNumIdEvento(dataJson.getMetadata().getNumIdEvento());
                metadata.setNomTopico(dataJson.getMetadata().getNomTopico());
                metadata.setFecEvento(dataJson.getMetadata().getFecEvento());
                metadata.setDesTabla(dataJson.getMetadata().getDesTabla());
                metadata.setIndOperacion(dataJson.getMetadata().getIndOperacion());
                metadata.setNumVersionJSON(dataJson.getMetadata().getNumVersionJSON());
                coeficientes.setMetadata(metadata);
                //Cabecera
                CabeceraDJ cabecera = new CabeceraDJ();
                cabecera.setNumIdEventoSaldo(dataJson.getCabecera().getNumIdEventoSaldo());
                cabecera.setCodEventoSaldo(dataJson.getCabecera().getCodEventoSaldo());
                cabecera.setFecEventoSaldo(dataJson.getCabecera().getFecEventoSaldo());
                cabecera.setCodNemoEventoSaldo(dataJson.getCabecera().getCodNemoEventoSaldo());
                cabecera.setPerDocumento(dataJson.getCabecera().getPerDocumento());
                cabecera.setNumRuc(dataJson.getCabecera().getNumRuc());
                cabecera.setCodTipoEvento(dataJson.getCabecera().getCodTipoEvento());
                cabecera.setCodDocumento(dataJson.getCabecera().getCodDocumento());
                cabecera.setNumDocumento(dataJson.getCabecera().getNumDocumento());
                cabecera.setFecDocumento(dataJson.getCabecera().getFecDocumento());
                cabecera.setCodDependencia(dataJson.getCabecera().getCodDependencia());
                cabecera.setIndComponente(dataJson.getCabecera().getIndComponente());
                coeficientes.setCabecera(cabecera);
                //Data
                Contribuyente contribuyentes = new Contribuyente();
                contribuyentes.setNumRuc(dataJson.getData().getContribuyente().getNumRuc());
                contribuyentes.setCodDependencia(dataJson.getData().getContribuyente().getCodDependencia());
                contribuyentes.setCodRegimenAfecto(dataJson.getData().getContribuyente().getCodRegimenAfecto());
                data.setContribuyente(contribuyentes);
                coeficientes.setData(data);
                // data.documentoSustento
                DocumentoSustento documentoSustento = new DocumentoSustento();
                List<Casilla> lisCasilla = new ArrayList<>();
                lisCasilla.add(CAS100);
                lisCasilla.add(CAS160);
                lisCasilla.add(CAS154);
                lisCasilla.add(CAS105);
                lisCasilla.add(CAS106);
                lisCasilla.add(CAS102);
                lisCasilla.add(CAS162);
                documentoSustento.setLisCasillas(lisCasilla);
                documentoSustento.setCodTributo(dataJson.getData().getDocumento().getCodTributo());
                documentoSustento.setCodDocSustentoCoefPorcCalculado(dataJson.getCabecera().getCodDocumento()); //CONSULTAR PARA VERIFICAR
                documentoSustento.setNunOrdenDocSustentoCoefCalculado(dataJson.getCabecera().getNumDocumento());
                documentoSustento.setPerTriDocSustentoCoefCalculado(dataJson.getCabecera().getPerDocumento());
                data.setDocumentoSustento(documentoSustento);
                coeficientes.setData(data);
                //Auditoria
                pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.Auditoria auditoria = new pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.Auditoria();
                auditoria.setCodUsuRegis("MLIBSR-coef =0 CAS100/154.../CAS162 = 0");
                auditoria.setFecRegis(new Date());
                auditoria.setCodUsuModif("MLIBSR-coef =0 CAS100/154.../CAS162 = 0");
                auditoria.setFecModif(new Date());
                coeficientes.setAuditoria(auditoria);
                //Registro en la colección
                movimientoAcumPercReTModel.saveMovimientoCoeficientes(coeficientes, Utils.generarNombreCollectionCoeficientes());
                //<<<<<<<<<<<<<<<<<<<<<<<<<<< FINAL GRABA EN COEFICIENTE EN PASO1

            } else {

                LOGGER.info("INICIO ==> 3.Obtener el parámetro de número de periodos a considerar en el coeficiente");
                //ParamItem0019 param0019 = parametriaRepository.findDataParametria0019();
                //int nPeriodosCoeficiente = Integer.parseInt(param0019.getnPeriodosCoeficiente());
                String perTributario = dataJson.getCabecera().getPerDocumento();
                LOGGER.info("FIN ==> 3.Obtener el parámetro de número de periodos a considerar en el coeficiente");

                // Validar Vigencia de MIGE en SA-IGV
                LOGGER.info("INICIO ==> VErificar si esta vigente la información del MIGE para SA-IGV ");
                ParamItem0028 param0028 = parametriaRepository.findParamItem0028();
                LOGGER.info("INICIO ==> 3.Valida vigencia del evento DJ 621 en SA-IGV");

                //Realiza los calculos

                int contadorPeriodos = 0;
                int nPeriodosCoeficiente = 1;
                String[][] array1 = {
                        {"CAS100", "100.00"},
                        {"CAS160", "200.00"},
                        {"CAS154", "300.00"},
                        {"CAS105", "50"},
                        {"CAS106", "100"},
                        {"CAS102", "10"},
                        {"CAS162", "20"}
                };


                while (contadorPeriodos < nPeriodosCoeficiente) {

                    if (ComparaFechasUtil.validaRangoFechasSA(param0028.getFecIniVigencia(), param0028.getFecFinVigencia(), fetchEvent)) {
                        LOGGER.info("Evento si se encuentra vigente en el modelo de SA-IGV y debe de buscar en el MIGE");
                        // Buscar en la información obtenida del MIGE que viene como ina lista de casillas
                        // Datos de ingreso : periodo, ruc, codTributo
                        flag=1;
                        //***0: Usando WebClient con esquema de reintentos
//                        Mige migeClientMapper;
//                        try {
//                            migeClientMapper = migeCliente.getMigeFromRestTemplate(event).block();
//                        } catch (Exception e){
//                            throw new RetriesException(e);
//                        }

                        //***********************************************************************
                        LOGGER.info(" inicio SIMULACION según lo recibido del (MIGE)");

                        // if (contadorPeriodos==0){
                        ArrayList<Double> listaDeDoubles = new ArrayList<>();

                        // Agregar valores al ArrayList
                        listaDeDoubles.add(100.00);
                        listaDeDoubles.add(200.00);
                        listaDeDoubles.add(300.00);
                        listaDeDoubles.add(50.00);
                        listaDeDoubles.add(100.00);
                        listaDeDoubles.add(10.00);
                        listaDeDoubles.add(20.00);

                        // Imprimir los valores del ArrayList
                        LOGGER.info("Valores en el ArrayList:");
                        double dCAS100 = 0.0;
                        double dCAS160 = 0.0;
                        double dCAS154 = 0.0;
                        double dCAS105 = 0.0;
                        double dCAS106 = 0.0;
                        double dCAS102 = 0.0;
                        double dCAS162 = 0.0;

                        double valorMigeCas100=0.0;
                        double valorMigeCas160=0.0;
                        double valorMigeCas154=0.0;
                        double valorMigeCas105=0.0;
                        double valorMigeCas106=0.0;
                        double valorMigeCas102=0.0;
                        double valorMigeCas162=0.0;

                        for (int i = 0; i < listaDeDoubles.size(); i++) {
                            if (i == 0) {
                                valorMigeCas100 = listaDeDoubles.get(0);
                            }
                            if (i == 1) {
                                valorMigeCas160 = listaDeDoubles.get(1);
                            }
                            if (i == 2) {
                                valorMigeCas154 = listaDeDoubles.get(2);
                            }
                            if (i == 3) {
                                valorMigeCas105 = listaDeDoubles.get(3);
                            }
                            if (i == 4) {
                                valorMigeCas106 = listaDeDoubles.get(4);
                            }
                            if (i == 5) {
                                valorMigeCas102 = listaDeDoubles.get(5);
                            }
                            if (i == 6) {
                                valorMigeCas162 = listaDeDoubles.get(6);
                            }
                        }
                        dividendo = dividendo + valorMigeCas100 + valorMigeCas154 + valorMigeCas160 - valorMigeCas102 - valorMigeCas162 + valorMigeCas106;
                        divisor = divisor + valorMigeCas100 + valorMigeCas154 + valorMigeCas160 - valorMigeCas102 - valorMigeCas162 + valorMigeCas106 + valorMigeCas105;
                        LOGGER.info(" Final SIMULACION según lo recibido del (MIGE)");
                        contadorPeriodos++;

                    } else {
                        LOGGER.info("Evento no se encuentra vigente en el modelo de SA-IGV");
                        //coeficienteDeterminado = 0.0;
                        flag=2;
                        List<String> casillaFiltro = Arrays.asList(Constantes.CAS100, Constantes.CAS160, Constantes.CAS154, Constantes.CAS105, Constantes.CAS106, Constantes.CAS102, Constantes.CAS162);
                        List<Casilla> saldos0020 = casillas.stream().filter(t -> casillaFiltro.contains(t.getNumCas())).toList();
                        Casilla casilla100 = saldos0020.get(0);
                        Casilla casilla160 = saldos0020.get(1);
                        Casilla casilla154 = saldos0020.get(2);
                        Casilla casilla105 = saldos0020.get(3);
                        Casilla casilla106 = saldos0020.get(4);
                        Casilla casilla102 = saldos0020.get(5);
                        Casilla casilla162 = saldos0020.get(6);
                        Double valCas100 = Double.valueOf(casilla100.getValCas());
                        Double valCas160 = Double.valueOf(casilla160.getValCas());
                        Double valCas154 = Double.valueOf(casilla154.getValCas());
                        Double valCas105 = Double.valueOf(casilla105.getValCas());
                        Double valCas106 = Double.valueOf(casilla106.getValCas());
                        Double valCas102 = Double.valueOf(casilla102.getValCas());
                        Double valCas162 = Double.valueOf(casilla162.getValCas());

                        dividendo = dividendo + valCas100 + valCas154 + valCas160 - valCas102 - valCas162 + valCas106;
                        divisor = divisor + valCas100 + valCas154 + valCas160 - valCas102 - valCas162 + valCas106 + valCas105;
                        contadorPeriodos++;

                    }
                }

                // Imprimir resultados antes de aplicar la fórmula
                LOGGER.info("Resultado dividendo antes de la fórmula: " + dividendo);
                LOGGER.info("Resultado divisor antes de la fórmula: " + divisor);

                // Calcular coeficienteDeterminado y redondear a 2 decimales
                coeficienteDeterminado = (dividendo / divisor) * 100;
                coeficienteDeterminado = Math.round(coeficienteDeterminado * 100.0) / 100.0;
                LOGGER.info("Coeficiente Determinado: " + coeficienteDeterminado);

                // inicio graba movimientos cuando el coeficiente es > a 0
                MovimientosSayyyymm movimientoCA0020 = new MovimientosSayyyymm();
                Data dataCA0020 = new Data();
                //Metadata
                MetadataGral metadat = new MetadataGral();
                metadat.setNumIdEvento(cabeceraDJ.getNumIdEventoPadre());
                metadat.setNumVersionJSON("77.7");
                movimientoCA0020.setMetadata(metadat);
                //Cabecera
                Cabecera cabeceraCA0020 = new Cabecera();
                cabeceraCA0020.setCodMovimiento(cabeceraDJ.getCodDocumento());
                cabeceraCA0020.setFecMovimiento(cabeceraDJ.getFecDocumento());
                cabeceraCA0020.setNumIdMovimiento(cabeceraDJ.getNumDocumento());
                cabeceraCA0020.setCodDependencia(cabeceraDJ.getCodDependencia());
                cabeceraCA0020.setNumRuc(cabeceraDJ.getNumRuc());
                cabeceraCA0020.setCodEvento(codEvento);
                cabeceraCA0020.setNumIdEventoSaldo("");
                cabeceraCA0020.setPerTributario("010101");
                movimientoCA0020.setCabecera(cabeceraCA0020);
                //Datos del movimiento
                DatosDocSustento documentoSusCA0020 = new DatosDocSustento();
                documentoSusCA0020.setCodDocumento("0621");
                documentoSusCA0020.setCodTributo("010101");
                documentoSusCA0020.setNumDocumento(cabeceraDJ.getNumDocumento());
                documentoSusCA0020.setCodTipoDJ(Utils.obtenerTipoDJ(codEvento));
                documentoSusCA0020.setCodTipoRecti(Utils.obtenerTipoRetificatoria(codEvento));
                documentoSusCA0020.setPerTributario(cabeceraDJ.getPerDocumento());
                documentoSusCA0020.setCodTributo(Utils.obtenerTipoRetificatoria(codEvento));
                documentoSusCA0020.setIndTipoBP("");
                dataCA0020.setDatosDocSustento(documentoSusCA0020);

                DatosCuentaAfectada cuentaAfectadaCA0020 = new DatosCuentaAfectada();
                cuentaAfectadaCA0020.setNumCta(Constantes.CA0020);
                cuentaAfectadaCA0020.setMtoAFavor(coeficienteDeterminado);
                dataCA0020.setDatosCuentaAfectada(cuentaAfectadaCA0020);
                //Datos que afectan al saldo acumulado
                DatosCalculoIGV datosSaldoAcumuladoCA0020 = new DatosCalculoIGV();
                datosSaldoAcumuladoCA0020.setVtasGravCas100 (valorCas100);
                datosSaldoAcumuladoCA0020.setMtoVentNetasLey31556(valorCas154);  //setVtasGravCas154(valorCas154); //mtoVentNetasLey31556 - setVtasGravCas154
                datosSaldoAcumuladoCA0020.setMtoVentasLey27037 (valorCas160);
                datosSaldoAcumuladoCA0020.setMtoVentasNoGravadas(valorCas105);
                datosSaldoAcumuladoCA0020.setMtoExportFacturadas(valorCas106);
                datosSaldoAcumuladoCA0020.setMtoDescuentosDevol(valorCas102);
                datosSaldoAcumuladoCA0020.setMtoDtosDevolLey27037(valorCas162);
                datosSaldoAcumuladoCA0020.setVtasNacionalesGrav(valorCas100 + valorCas154 + valorCas160);
                dataCA0020.setDatosCalculoIGV(datosSaldoAcumuladoCA0020);
                //Datos de control y gestión
                Control datosGestionControlCA0020 = new Control();
                //datosGestionControlCA0020.setIndEstado("A");
                datosGestionControlCA0020.setMtoSaldoDespMov(coeficienteDeterminado);
                datosGestionControlCA0020.setDividendo(dividendo);
                datosGestionControlCA0020.setDivisor(divisor);
                dataCA0020.setControl(datosGestionControlCA0020);
                movimientoCA0020.setData(dataCA0020);
                movimientosSayyyymmModel.saveMovimientosSayyyymmInDynamicCollection(movimientoCA0020, Utils.generarNombreCollectionMovimientosSA());
                dataSaVO = ProducerDataSaVO.getMensajeDataSa(dataJson);
                lisMovimientos.add(ProducerDataSaVO.getMovimientos(movimientoCA0020));

                //<<<<<<<<<<<<<<<<<<<<<<<<<<< INICIO GRABA EN COEFICIENTE > 0
                Coeficientes coeficientes = new Coeficientes();
                pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.Data data = new pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.Data();
                //Metadata
                Metadata metadata = new Metadata();
                metadata.setNumIdEvento(dataJson.getMetadata().getNumIdEvento());
                metadata.setNomTopico(dataJson.getMetadata().getNomTopico());
                metadata.setFecEvento(dataJson.getMetadata().getFecEvento());
                metadata.setDesTabla(dataJson.getMetadata().getDesTabla());
                metadata.setIndOperacion(dataJson.getMetadata().getIndOperacion());
                metadata.setNumVersionJSON(dataJson.getMetadata().getNumVersionJSON());
                coeficientes.setMetadata(metadata);
                //Cabecera
                CabeceraDJ cabecera = new CabeceraDJ();
                cabecera.setNumIdEventoSaldo(dataJson.getCabecera().getNumIdEventoSaldo());
                cabecera.setCodEventoSaldo(dataJson.getCabecera().getCodEventoSaldo());
                cabecera.setFecEventoSaldo(dataJson.getCabecera().getFecEventoSaldo());
                cabecera.setCodNemoEventoSaldo(dataJson.getCabecera().getCodNemoEventoSaldo());
                cabecera.setPerDocumento(dataJson.getCabecera().getPerDocumento());
                cabecera.setNumRuc(dataJson.getCabecera().getNumRuc());
                cabecera.setCodTipoEvento(dataJson.getCabecera().getCodTipoEvento());
                cabecera.setCodDocumento(dataJson.getCabecera().getCodDocumento());
                cabecera.setNumDocumento(dataJson.getCabecera().getNumDocumento());
                cabecera.setFecDocumento(dataJson.getCabecera().getFecDocumento());
                cabecera.setCodDependencia(dataJson.getCabecera().getCodDependencia());
                cabecera.setIndComponente(dataJson.getCabecera().getIndComponente());
                coeficientes.setCabecera(cabecera);
                //Data
                Contribuyente contribuyentes = new Contribuyente();
                contribuyentes.setNumRuc(dataJson.getData().getContribuyente().getNumRuc());
                contribuyentes.setCodDependencia(dataJson.getData().getContribuyente().getCodDependencia());
                contribuyentes.setCodRegimenAfecto(dataJson.getData().getContribuyente().getCodRegimenAfecto());
                data.setContribuyente(contribuyentes);
                coeficientes.setData(data);
                // data.documentoSustento
                DocumentoSustento documentoSustento = new DocumentoSustento();
                List<Casilla> lisCasilla = new ArrayList<>();
                lisCasilla.add(CAS100);
                lisCasilla.add(CAS160);
                lisCasilla.add(CAS154);
                lisCasilla.add(CAS105);
                lisCasilla.add(CAS106);
                lisCasilla.add(CAS102);
                lisCasilla.add(CAS162);
                documentoSustento.setLisCasillas(lisCasilla);
                documentoSustento.setCodTributo(dataJson.getData().getDocumento().getCodTributo());
                documentoSustento.setCodDocSustentoCoefPorcCalculado(dataJson.getCabecera().getCodDocumento()); //CONSULTAR PARA VERIFICAR
                documentoSustento.setNunOrdenDocSustentoCoefCalculado(dataJson.getCabecera().getNumDocumento());
                documentoSustento.setPerTriDocSustentoCoefCalculado(dataJson.getCabecera().getPerDocumento());
                data.setDocumentoSustento(documentoSustento);
                coeficientes.setData(data);
                //Auditoria
                pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.Auditoria auditoria = new pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.Auditoria();
                if (flag==1){
                    auditoria.setCodUsuRegis("MLIBSR-coef >0 valorMIGE (Vigente)");
                    auditoria.setFecRegis(new Date());
                    auditoria.setCodUsuModif("MLIBSR-coef >0 valorMIGE (Vigente)");
                    auditoria.setFecModif(new Date());
                    coeficientes.setAuditoria(auditoria);
                }

                if (flag==2){
                    auditoria.setCodUsuRegis("MLIBSR-coef >0 valor DJ (No vigente)");
                    auditoria.setFecRegis(new Date());
                    auditoria.setCodUsuModif("MLIBSR-coef >0 valor DJ (No vigente)");
                    auditoria.setFecModif(new Date());
                    coeficientes.setAuditoria(auditoria);
                }

                //Registro en la colección
                movimientoAcumPercReTModel.saveMovimientoCoeficientes(coeficientes, Utils.generarNombreCollectionCoeficientes());
                //<<<<<<<<<<<<<<<<<<<<<<<<<<< FIN GRABA EN COEFICIENTE > 0
            }
            enviarMensajesTopic();

        } catch (
                Exception e) {
            //Mensaje para el reproceso del evento
            Thread.currentThread().interrupt();
            //LOGGER.error("Evento ", codEvento ,": Error de interpretación del mensaje");
        }
        return coeficienteDeterminado;

    }

    private void enviarMensajesTopic() {
        dataSaVO.getData().setNumMovimientos(lisMovimientos.size());
        dataSaVO.getData().setLisMovimientos(lisMovimientos);
        try {
            kafkaTemplate.send(topic1, jsonUtilParser.entityToJson(dataSaVO)).get();
            LOGGER.info("Mensaje enviado correctamente " + jsonUtilParser.entityToJson(dataSaVO));
        } catch (InterruptedException | ExecutionException e){
            Thread.currentThread().interrupt();
            throw new LoopException(e);
        }
    }

    @Override
    public Casilla cus04CalcularTributoPagar(Logger LOG, List<Casilla> casillas, double coeficienteDeclarado) {
        LOGGER.info("F2_491203_eIGVDJ621.docx");
        LOGGER.info("CUS04-Calcular tributo a pagar (CAS140) según lo determinado (MIGE)");

        List<String> casFiltro = Arrays.asList(Constantes.CAS105, Constantes.CAS108, Constantes.CAS111, Constantes.CAS131);
        List<Casilla> saldo0020 = casillas.stream().filter(t -> casFiltro.contains(t.getNumCas())).toList();
        if (coeficienteDeclarado == 0) {
            coeficienteDeclarado = 1;
        }

        Casilla cas105 = saldo0020.get(0);
        Casilla cas108 = saldo0020.get(1);
        Casilla cas111 = saldo0020.get(2);
        Casilla cas131 = saldo0020.get(3);

        Double valorCas105 = Double.valueOf(cas105.getValCas());
        Double valorCas108 = Double.valueOf(cas108.getValCas());
        Double valorCas111 = Double.valueOf(cas111.getValCas());
        Double valorCas131 = Double.valueOf(cas131.getValCas());
        double valorCas178 = 0;
        double valorCas140D = 0;
        if (valorCas105 > 0) {
            if (valorCas111 == 0) {
                valorCas178 = valorCas108 * coeficienteDeclarado;
            } else {
                valorCas178 = valorCas108 + valorCas111 * coeficienteDeclarado;
            }
        }
        valorCas140D = valorCas131 - valorCas178;
        Casilla castilla140D = new Casilla();
        castilla140D.setNumCas(Constantes.CAS140D);
        castilla140D.setValCas(String.valueOf(valorCas140D));
        casillas.add(castilla140D);

        return castilla140D;
    }


    @Override
    public double cus06GenerarSaldosAcumulado(DatosVO<DataDJVO> dataJson, List<Casilla> casillas, double coeficienteDeclarad, String periodoDj, CabeceraDJ cabeceraDJ, double dividendo, double divisor) {
        LOGGER.info("F2_491203_eIGVDJ621.docx");
        LOGGER.info("INICIO ==> 1.Obtener casillas de la DJ 621 que afectan al saldo CA0024");
        String ruc = Optional.of(cabeceraDJ).map(CabeceraDJ::getNumRuc).orElse("");
        String codDocumento = Optional.of(dataJson).map(DatosVO::getData).map(DataDJVO::getDocumento).map(Documento::getCodDocumento).orElse("");
        String periodo = Optional.of(cabeceraDJ).map(CabeceraDJ::getPerDocumento).orElse("");
        String numOrden = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getNumDocumento).orElse("");
        String montoMovi = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getNumDocumento).orElse("");
        String codEvento = dataJson.getCabecera().getCodEventoSaldo();
        Date fetchEvent = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getFecEventoSaldo).orElse(null);
        Date fecMov = Optional.of(cabeceraDJ).map(CabeceraDJ::getFecEventoSaldo).orElse(new Date());

        Casilla CAS100 = casillas.stream().filter(t -> Constantes.CAS100.contains(t.getNumCas())).toList().get(0);
        Casilla CAS160 = casillas.stream().filter(t -> Constantes.CAS160.contains(t.getNumCas())).toList().get(0);
        Casilla CAS154 = casillas.stream().filter(t -> Constantes.CAS154.contains(t.getNumCas())).toList().get(0);
        Casilla CAS105 = casillas.stream().filter(t -> Constantes.CAS105.contains(t.getNumCas())).toList().get(0);
        Casilla CAS106 = casillas.stream().filter(t -> Constantes.CAS106.contains(t.getNumCas())).toList().get(0);
        Casilla CAS102 = casillas.stream().filter(t -> Constantes.CAS102.contains(t.getNumCas())).toList().get(0);
        Casilla CAS162 = casillas.stream().filter(t -> Constantes.CAS162.contains(t.getNumCas())).toList().get(0);

        try {
            //1.- Obtener casillas de la DJ621
            List<String> casFiltro = Arrays.asList(Constantes.CAS100, Constantes.CAS160, Constantes.CAS154, Constantes.CAS105, Constantes.CAS106, Constantes.CAS102, Constantes.CAS162);
            List<Casilla> saldo0024 = casillas.stream().filter(t -> casFiltro.contains(t.getNumCas())).toList();
            Casilla cas100 = saldo0024.get(0);
            Casilla cas160 = saldo0024.get(1);
            Casilla cas154 = saldo0024.get(2);
            Casilla cas105 = saldo0024.get(3);
            Casilla cas106 = saldo0024.get(4);
            Casilla cas102 = saldo0024.get(5);
            Casilla cas162 = saldo0024.get(6);
            Double valorCas100 = Double.valueOf(cas100.getValCas());
            Double valorCas160 = Double.valueOf(cas160.getValCas());
            Double valorCas154 = Double.valueOf(cas154.getValCas());
            Double valorCas105 = Double.valueOf(cas105.getValCas());
            Double valorCas106 = Double.valueOf(cas106.getValCas());
            Double valorCas102 = Double.valueOf(cas102.getValCas());
            Double valorCas162 = Double.valueOf(cas162.getValCas());

            if (!(((valorCas100 > 0.0) || (valorCas160 > 0.0) || (valorCas154 > 0.0)) && (valorCas105 > 0.0))) {
                coeficienteDeclarad = 0.0;

                MovimientosSayyyymm movimientoCA0024 = new MovimientosSayyyymm();
                Data dataCA0024 = new Data();
                //Metadata
                //LOGGER.info("Generar Metadata");
                MetadataGral metadat = new MetadataGral();
                metadat.setNumIdEvento(cabeceraDJ.getNumIdEventoPadre());
                metadat.setNumVersionJSON("77.7");
                movimientoCA0024.setMetadata(metadat);
                //Cabecera
                //LOGGER.info("Generar Cabecera");
                Cabecera cabeceraCA0024 = new Cabecera();
                cabeceraCA0024.setCodMovimiento(cabeceraDJ.getCodDocumento());
                cabeceraCA0024.setFecMovimiento(cabeceraDJ.getFecDocumento());
                cabeceraCA0024.setNumIdMovimiento(cabeceraDJ.getNumDocumento());
                cabeceraCA0024.setCodDependencia(cabeceraDJ.getCodDependencia());
                cabeceraCA0024.setNumRuc(cabeceraDJ.getNumRuc());
                cabeceraCA0024.setCodEvento(codEvento);
                cabeceraCA0024.setNumIdEventoSaldo("");
                cabeceraCA0024.setPerTributario("010101");
                movimientoCA0024.setCabecera(cabeceraCA0024);
                //Datos del movimiento
                LOGGER.info("Generar Datos del documento Sustento");
                DatosDocSustento documentoSusCA0024 = new DatosDocSustento();
                documentoSusCA0024.setCodDocumento("0621");
                documentoSusCA0024.setCodTributo("010101");
                documentoSusCA0024.setNumDocumento(cabeceraDJ.getNumDocumento());
                documentoSusCA0024.setCodTipoDJ(Utils.obtenerTipoDJ(codEvento));
                documentoSusCA0024.setCodTipoRecti(Utils.obtenerTipoRetificatoria(codEvento));
                documentoSusCA0024.setPerTributario(cabeceraDJ.getPerDocumento());
                documentoSusCA0024.setCodTributo(Utils.obtenerTipoRetificatoria(codEvento));
                documentoSusCA0024.setIndTipoBP("");
                dataCA0024.setDatosDocSustento(documentoSusCA0024);
                LOGGER.info("Generar Datos de cuenta Afectada");
                DatosCuentaAfectada cuentaAfectadaCA0024 = new DatosCuentaAfectada();
                cuentaAfectadaCA0024.setNumCta(Constantes.CA0020);
                cuentaAfectadaCA0024.setMtoAFavor(coeficienteDeclarad);
                dataCA0024.setDatosCuentaAfectada(cuentaAfectadaCA0024);
                //Datos que afectan al saldo acumulado
                LOGGER.info("Generar Datos que afectan al saldo acumulado");
                DatosCalculoIGV datosSaldoAcumuladoCA0024 = new DatosCalculoIGV();
                datosSaldoAcumuladoCA0024.setVtasGravCas100 (valorCas100);
                datosSaldoAcumuladoCA0024.setMtoVentNetasLey31556(valorCas154);
                datosSaldoAcumuladoCA0024.setMtoVentasLey27037 (valorCas160);
                datosSaldoAcumuladoCA0024.setMtoVentasNoGravadas(valorCas105);
                datosSaldoAcumuladoCA0024.setMtoExportFacturadas(valorCas106);
                datosSaldoAcumuladoCA0024.setMtoDescuentosDevol(valorCas102);
                datosSaldoAcumuladoCA0024.setMtoDtosDevolLey27037(valorCas162);
                datosSaldoAcumuladoCA0024.setVtasNacionalesGrav(valorCas100 + valorCas154 + valorCas160);
                dataCA0024.setDatosCalculoIGV(datosSaldoAcumuladoCA0024);
                //Datos de control y gestión
                LOGGER.info("Generar Datos de control y gestión");
                Control datosGestionControlCA0024 = new Control();
                //datosGestionControlCA0020.setIndEstado("A");
                datosGestionControlCA0024.setMtoSaldoDespMov(coeficienteDeclarad);
                datosGestionControlCA0024.setDividendo(dividendo);
                datosGestionControlCA0024.setDivisor(divisor);
                dataCA0024.setControl(datosGestionControlCA0024);
                movimientoCA0024.setData(dataCA0024);
                //movimientosSayyyymmModel.saveMovimientosSayyyymmInDynamicCollection(movimientoCA0024, Utils.generarNombreCollectionMovimientosSA());
                //lisMovimientos.add(ProducerDataSaVO.getMovimientos(movimientoCA0020));

                //<<<<<<<<<<<<<<<<<<<<<<<<<<< INICIO GRABA EN COEFICIENTE EN PASO1
                Coeficientes coeficientes = new Coeficientes();
                pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.Data data = new pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.Data();
                //Metadata
                LOGGER.info("Generar Metadata");
                Metadata metadata = new Metadata();
                metadata.setNumIdEvento(dataJson.getMetadata().getNumIdEvento());
                metadata.setNomTopico(dataJson.getMetadata().getNomTopico());
                metadata.setFecEvento(dataJson.getMetadata().getFecEvento());
                metadata.setDesTabla(dataJson.getMetadata().getDesTabla());
                metadata.setIndOperacion(dataJson.getMetadata().getIndOperacion());
                metadata.setNumVersionJSON(dataJson.getMetadata().getNumVersionJSON());
                coeficientes.setMetadata(metadata);
                //Cabecera
                //LOGGER.info("Generar Cabecera");
                CabeceraDJ cabecera = new CabeceraDJ();
                cabecera.setNumIdEventoSaldo(dataJson.getCabecera().getNumIdEventoSaldo());
                cabecera.setCodEventoSaldo(dataJson.getCabecera().getCodEventoSaldo());
                cabecera.setFecEventoSaldo(dataJson.getCabecera().getFecEventoSaldo());
                cabecera.setCodNemoEventoSaldo(dataJson.getCabecera().getCodNemoEventoSaldo());
                cabecera.setPerDocumento(dataJson.getCabecera().getPerDocumento());
                cabecera.setNumRuc(dataJson.getCabecera().getNumRuc());
                cabecera.setCodTipoEvento(dataJson.getCabecera().getCodTipoEvento());
                cabecera.setCodDocumento(dataJson.getCabecera().getCodDocumento());
                cabecera.setNumDocumento(dataJson.getCabecera().getNumDocumento());
                cabecera.setFecDocumento(dataJson.getCabecera().getFecDocumento());
                cabecera.setCodDependencia(dataJson.getCabecera().getCodDependencia());
                cabecera.setIndComponente(dataJson.getCabecera().getIndComponente());
                coeficientes.setCabecera(cabecera);
                //Data
                Contribuyente contribuyentes = new Contribuyente();
                contribuyentes.setNumRuc(dataJson.getData().getContribuyente().getNumRuc());
                contribuyentes.setCodDependencia(dataJson.getData().getContribuyente().getCodDependencia());
                contribuyentes.setCodRegimenAfecto(dataJson.getData().getContribuyente().getCodRegimenAfecto());
                data.setContribuyente(contribuyentes);
                coeficientes.setData(data);
                // data.documentoSustento
                DocumentoSustento documentoSustento = new DocumentoSustento();
                List<Casilla> lisCasilla = new ArrayList<>();
                lisCasilla.add(CAS100);
                lisCasilla.add(CAS160);
                lisCasilla.add(CAS154);
                lisCasilla.add(CAS105);
                lisCasilla.add(CAS106);
                lisCasilla.add(CAS102);
                lisCasilla.add(CAS162);
                documentoSustento.setLisCasillas(lisCasilla);
                documentoSustento.setCodTributo(dataJson.getData().getDocumento().getCodTributo());
                documentoSustento.setCodDocSustentoCoefPorcCalculado(dataJson.getCabecera().getCodDocumento()); //CONSULTAR PARA VERIFICAR
                documentoSustento.setNunOrdenDocSustentoCoefCalculado(dataJson.getCabecera().getNumDocumento());
                documentoSustento.setPerTriDocSustentoCoefCalculado(dataJson.getCabecera().getPerDocumento());
                data.setDocumentoSustento(documentoSustento);
                coeficientes.setData(data);
                //Auditoria
                pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.Auditoria auditoria = new pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.Auditoria();
                auditoria.setCodUsuRegis("MLIBSR-coeficiente=0");
                auditoria.setFecRegis(new Date());
                auditoria.setCodUsuModif("MLIBSR-coeficiente=0");
                auditoria.setFecModif(new Date());
                coeficientes.setAuditoria(auditoria);
                //Registro en la colección
                movimientoAcumPercReTModel.saveMovimientoCoeficientes(coeficientes, Utils.generarNombreCollectionCoeficientes());
                //<<<<<<<<<<<<<<<<<<<<<<<<<<< FINAL GRABA EN COEFICIENTE EN PASO1

            } else {

                LOGGER.info("INICIO ==> 3.Obtener el parámetro de número de periodos a considerar en el coeficiente");
                //ParamItem0019 param0019 = parametriaRepository.findDataParametria0019();
                //int nPeriodosCoeficiente = Integer.parseInt(param0019.getnPeriodosCoeficiente());
                String perTributario = dataJson.getCabecera().getPerDocumento();
                LOGGER.info("FIN ==> 3.Obtener el parámetro de número de periodos a considerar en el coeficiente");

                // Validar Vigencia de MIGE en SA-IGV
                LOGGER.info("INICIO ==> VErificar si esta vigente la información del MIGE para SA-IGV ");
                ParamItem0017 param0028 = parametriaRepository.findDataParametria0017(); // ALERT QUEDA PENDIENTEEEEEEEEEE parametria 0028
                LOGGER.info("INICIO ==> 3.Valida vigencia del evento DJ 621 en SA-IGV");

                //Realiza los calculos

                int contadorPeriodos = 0;
                int nPeriodosCoeficiente = 1;
                String[][] array1 = {
                        {"CAS100", "100.00"},
                        {"CAS160", "200.00"},
                        {"CAS154", "300.00"},
                        {"CAS105", "50"},
                        {"CAS106", "100"},
                        {"CAS102", "10"},
                        {"CAS162", "20"}
                };


                while (contadorPeriodos < nPeriodosCoeficiente) {

                    if (ComparaFechasUtil.validaRangoFechasSA(param0028.getFecInicioModeloSA(), param0028.getFecFinModeloSA(), fetchEvent)) {
                        LOGGER.info("Evento si se encuentra vigente en el modelo de SA-IGV y debe de buscar en el MIGE");
                        // Buscar en la información obtenida del MIGE que viene como ina lista de casillas
                        // Datos de ingreso : periodo, ruc, codTributo
                        //***********************************************************************
                        LOGGER.info(" inicio SIMULACION según lo recibido del (MIGE)");


                        LOGGER.info(" Final SIMULACION según lo recibido del (MIGE)");
                        contadorPeriodos++;

                    } else {
                        LOGGER.info("Evento no se encuentra vigente en el modelo de SA-IGV llama a la DJ activa");
//                        List<String> casillaFiltro = Arrays.asList(Constantes.CAS100, Constantes.CAS160, Constantes.CAS154, Constantes.CAS105, Constantes.CAS106, Constantes.CAS102, Constantes.CAS162);
//                        List<Casilla> saldos0024 = casillas.stream().filter(t -> casillaFiltro.contains(t.getNumCas())).toList();
//                        Casilla casilla100 = saldos0024.get(0);
//                        Casilla casilla160 = saldos0024.get(1);
//                        Casilla casilla154 = saldos0024.get(2);
//                        Casilla casilla105 = saldos0024.get(3);
//                        Casilla casilla106 = saldos0024.get(4);
//                        Casilla casilla102 = saldos0024.get(5);
//                        Casilla casilla162 = saldos0024.get(6);
//                        Double valCas100 = Double.valueOf(casilla100.getValCas());
//                        Double valCas160 = Double.valueOf(casilla160.getValCas());
//                        Double valCas154 = Double.valueOf(casilla154.getValCas());
//                        Double valCas105 = Double.valueOf(casilla105.getValCas());
//                        Double valCas106 = Double.valueOf(casilla106.getValCas());
//                        Double valCas102 = Double.valueOf(casilla102.getValCas());
//                        Double valCas162 = Double.valueOf(casilla162.getValCas());
//                        dividendo = dividendo + valCas100 + valCas154 + valCas160 - valCas102 - valCas162 + valCas106;
//                        divisor = divisor + valCas100 + valCas154 + valCas160 - valCas102 - valCas162 + valCas106 + valCas105;



                        contadorPeriodos++;

                    }
                }

                // Imprimir resultados antes de aplicar la fórmula
                LOGGER.info("Resultado dividendo antes de la fórmula: " + dividendo);
                LOGGER.info("Resultado divisor antes de la fórmula: " + divisor);

                // Calcular coeficienteDeterminado y redondear a 2 decimales
                coeficienteDeclarad = (dividendo / divisor) * 100;
                coeficienteDeclarad = Math.round(coeficienteDeclarad * 100.0) / 100.0;
                LOGGER.info("Coeficiente Determinado: " + coeficienteDeclarad);

                // inicio graba movimientos cuando el coeficiente es > a 0
                MovimientosSayyyymm movimientoCA0024 = new MovimientosSayyyymm();
                Data dataCA0024 = new Data();
                //Metadata
                LOGGER.info("Generar Metadata");
                MetadataGral metadat = new MetadataGral();
                metadat.setNumIdEvento(cabeceraDJ.getNumIdEventoPadre());
                metadat.setNumVersionJSON("77.7");
                movimientoCA0024.setMetadata(metadat);
                //Cabecera
                //LOGGER.info("Generar Cabecera");
                Cabecera cabeceraCA0024 = new Cabecera();
                cabeceraCA0024.setCodMovimiento(cabeceraDJ.getCodDocumento());
                cabeceraCA0024.setFecMovimiento(cabeceraDJ.getFecDocumento());
                cabeceraCA0024.setNumIdMovimiento(cabeceraDJ.getNumDocumento());
                cabeceraCA0024.setCodDependencia(cabeceraDJ.getCodDependencia());
                cabeceraCA0024.setNumRuc(cabeceraDJ.getNumRuc());
                cabeceraCA0024.setCodEvento(codEvento);
                cabeceraCA0024.setNumIdEventoSaldo("");
                cabeceraCA0024.setPerTributario("010101");
                movimientoCA0024.setCabecera(cabeceraCA0024);
                //Datos del movimiento
                LOGGER.info("Generar Datos del documento Sustento");
                DatosDocSustento documentoSusCA0024 = new DatosDocSustento();
                documentoSusCA0024.setCodDocumento("0621");
                documentoSusCA0024.setCodTributo("010101");
                documentoSusCA0024.setNumDocumento(cabeceraDJ.getNumDocumento());
                documentoSusCA0024.setCodTipoDJ(Utils.obtenerTipoDJ(codEvento));
                documentoSusCA0024.setCodTipoRecti(Utils.obtenerTipoRetificatoria(codEvento));
                documentoSusCA0024.setPerTributario(cabeceraDJ.getPerDocumento());
                documentoSusCA0024.setCodTributo(Utils.obtenerTipoRetificatoria(codEvento));
                documentoSusCA0024.setIndTipoBP("");
                dataCA0024.setDatosDocSustento(documentoSusCA0024);
                LOGGER.info("Generar Datos de cuenta Afectada");
                DatosCuentaAfectada cuentaAfectadaCA0024 = new DatosCuentaAfectada();
                cuentaAfectadaCA0024.setNumCta(Constantes.CA0020);
                cuentaAfectadaCA0024.setMtoAFavor(coeficienteDeclarad);
                dataCA0024.setDatosCuentaAfectada(cuentaAfectadaCA0024);
                //Datos que afectan al saldo acumulado
                LOGGER.info("Generar Datos que afectan al saldo acumulado");
                DatosCalculoIGV datosSaldoAcumuladoCA0024 = new DatosCalculoIGV();
                datosSaldoAcumuladoCA0024.setVtasGravCas100 (valorCas100);
                datosSaldoAcumuladoCA0024.setMtoVentNetasLey31556(valorCas154);
                datosSaldoAcumuladoCA0024.setMtoVentasLey27037 (valorCas160);
                datosSaldoAcumuladoCA0024.setMtoVentasNoGravadas(valorCas105);
                datosSaldoAcumuladoCA0024.setMtoExportFacturadas(valorCas106);
                datosSaldoAcumuladoCA0024.setMtoDescuentosDevol(valorCas102);
                datosSaldoAcumuladoCA0024.setMtoDtosDevolLey27037(valorCas162);
                datosSaldoAcumuladoCA0024.setVtasNacionalesGrav(valorCas100 + valorCas154 + valorCas160);
                dataCA0024.setDatosCalculoIGV(datosSaldoAcumuladoCA0024);
                //Datos de control y gestión
                LOGGER.info("Generar Datos de control y gestión");
                Control datosGestionControlCA0024 = new Control();
                datosGestionControlCA0024.setMtoSaldoDespMov(coeficienteDeclarad);
                datosGestionControlCA0024.setDividendo(dividendo);
                datosGestionControlCA0024.setDivisor(divisor);
                dataCA0024.setControl(datosGestionControlCA0024);
                movimientoCA0024.setData(dataCA0024);
                movimientosSayyyymmModel.saveMovimientosSayyyymmInDynamicCollection(movimientoCA0024, Utils.generarNombreCollectionMovimientosSA());
                //lisMovimientos.add(ProducerDataSaVO.getMovimientos(movimientoCA0020));

                //<<<<<<<<<<<<<<<<<<<<<<<<<<< INICIO GRABA EN COEFICIENTE > 0
                Coeficientes coeficientes = new Coeficientes();
                pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.Data data = new pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.Data();
                //Metadata
                LOGGER.info("Generar Metadata");
                Metadata metadata = new Metadata();
                metadata.setNumIdEvento(dataJson.getMetadata().getNumIdEvento());
                metadata.setNomTopico(dataJson.getMetadata().getNomTopico());
                metadata.setFecEvento(dataJson.getMetadata().getFecEvento());
                metadata.setDesTabla(dataJson.getMetadata().getDesTabla());
                metadata.setIndOperacion(dataJson.getMetadata().getIndOperacion());
                metadata.setNumVersionJSON(dataJson.getMetadata().getNumVersionJSON());
                coeficientes.setMetadata(metadata);
                //Cabecera
                //LOGGER.info("Generar Cabecera");
                CabeceraDJ cabecera = new CabeceraDJ();
                cabecera.setNumIdEventoSaldo(dataJson.getCabecera().getNumIdEventoSaldo());
                cabecera.setCodEventoSaldo(dataJson.getCabecera().getCodEventoSaldo());
                cabecera.setFecEventoSaldo(dataJson.getCabecera().getFecEventoSaldo());
                cabecera.setCodNemoEventoSaldo(dataJson.getCabecera().getCodNemoEventoSaldo());
                cabecera.setPerDocumento(dataJson.getCabecera().getPerDocumento());
                cabecera.setNumRuc(dataJson.getCabecera().getNumRuc());
                cabecera.setCodTipoEvento(dataJson.getCabecera().getCodTipoEvento());
                cabecera.setCodDocumento(dataJson.getCabecera().getCodDocumento());
                cabecera.setNumDocumento(dataJson.getCabecera().getNumDocumento());
                cabecera.setFecDocumento(dataJson.getCabecera().getFecDocumento());
                cabecera.setCodDependencia(dataJson.getCabecera().getCodDependencia());
                cabecera.setIndComponente(dataJson.getCabecera().getIndComponente());
                coeficientes.setCabecera(cabecera);
                //Data
                Contribuyente contribuyentes = new Contribuyente();
                contribuyentes.setNumRuc(dataJson.getData().getContribuyente().getNumRuc());
                contribuyentes.setCodDependencia(dataJson.getData().getContribuyente().getCodDependencia());
                contribuyentes.setCodRegimenAfecto(dataJson.getData().getContribuyente().getCodRegimenAfecto());
                data.setContribuyente(contribuyentes);
                coeficientes.setData(data);
                // data.documentoSustento
                DocumentoSustento documentoSustento = new DocumentoSustento();
                List<Casilla> lisCasilla = new ArrayList<>();
                lisCasilla.add(CAS100);
                lisCasilla.add(CAS160);
                lisCasilla.add(CAS154);
                lisCasilla.add(CAS105);
                lisCasilla.add(CAS106);
                lisCasilla.add(CAS102);
                lisCasilla.add(CAS162);
                documentoSustento.setLisCasillas(lisCasilla);
                documentoSustento.setCodTributo(dataJson.getData().getDocumento().getCodTributo());
                documentoSustento.setCodDocSustentoCoefPorcCalculado(dataJson.getCabecera().getCodDocumento()); //CONSULTAR PARA VERIFICAR
                documentoSustento.setNunOrdenDocSustentoCoefCalculado(dataJson.getCabecera().getNumDocumento());
                documentoSustento.setPerTriDocSustentoCoefCalculado(dataJson.getCabecera().getPerDocumento());
                data.setDocumentoSustento(documentoSustento);
                coeficientes.setData(data);
                //Auditoria
                pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.Auditoria auditoria = new pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.Auditoria();
                auditoria.setCodUsuRegis("MLIBSR-coeficiente>0");
                auditoria.setFecRegis(new Date());
                auditoria.setCodUsuModif("MLIBSR-coeficiente>0");
                auditoria.setFecModif(new Date());
                coeficientes.setAuditoria(auditoria);
                //Registro en la colección
                movimientoAcumPercReTModel.saveMovimientoCoeficientes(coeficientes, Utils.generarNombreCollectionCoeficientes());
                //<<<<<<<<<<<<<<<<<<<<<<<<<<< FIN GRABA EN COEFICIENTE > 0
            }
            //actualizarSaldosAcumulados();

        } catch (
                Exception e) {
            //Mensaje para el reproceso del evento
           // LOGGER.error("Evento " + codEvento + ": Error de interpretación del mensaje");
        }
        return coeficienteDeclarad;
    }

    @Override
    public Casilla cus07CalcularImpuestoResultante(Logger LOG, List<Casilla> casillas, double coeficienteDeclarado) {
        LOGGER.info("F2_491203_eIGVDJ621.docx");
        LOGGER.info("CUS07 – Calcular impuesto resultante o saldo a favor según lo declarado.");

        List<String> casFiltro = Arrays.asList(Constantes.CAS105, Constantes.CAS108, Constantes.CAS111, Constantes.CAS131);
        LOGGER.info("Obtiene las siguientes casillas de la DJ 621");
        List<Casilla> saldo0024 = casillas.stream().filter(t -> casFiltro.contains(t.getNumCas())).toList();


        if (MovDJUtil.isEmpty(saldo0024)) {
            LOGGER.info("No se encontro casillas afectos al saldo acumulado CA0020 -> {}", casFiltro);
            return null;
        }
        if (coeficienteDeclarado == 0) {
            coeficienteDeclarado = 1;
        }

        Casilla cas105 = saldo0024.get(0);
        Casilla cas108 = saldo0024.get(1);
        Casilla cas111 = saldo0024.get(2);
        Casilla cas131 = saldo0024.get(3);

        Double valorCas105 = Double.valueOf(cas105.getValCas());
        Double valorCas108 = Double.valueOf(cas108.getValCas());
        Double valorCas111 = Double.valueOf(cas111.getValCas());
        Double valorCas131 = Double.valueOf(cas131.getValCas());
        double valorCas178 = 0;
        double valorCas140D = 0;
        if (valorCas105 > 0) {
            if (valorCas111 == 0) {
                valorCas178 = valorCas108 * coeficienteDeclarado;
            } else {
                valorCas178 = valorCas108 + valorCas111 * coeficienteDeclarado;
            }
        }
        valorCas140D = valorCas131 + valorCas178;
        Casilla castilla140D = new Casilla();
        castilla140D.setNumCas(Constantes.CAS140D);
        castilla140D.setValCas(String.valueOf(valorCas140D));
        casillas.add(castilla140D);
        return castilla140D;


    }

    @Override
    public void cus08GenerarMovimientosCA0021CA0022CA0023(Casilla castilla140D, double coeficienteDeclarad, CabeceraDJ cabeceraDJ, List<Casilla> casillas, double mtoPercepciones, double mtoRetenciones, String codEvent, String perTributario) {
        LOGGER.info("F2_491203_eIGVDJ621.docx");
        LOGGER.info("CUS08 – Generar movimientos de CA0021, CA0022 y CA0023 ");
        String tributo = "010101";
        String ruc = Optional.of(cabeceraDJ).map(CabeceraDJ::getNumRuc).orElse("");
        String periodo = Optional.of(cabeceraDJ).map(CabeceraDJ::getPerDocumento).orElse("");
        Date fecMov = Optional.of(cabeceraDJ).map(CabeceraDJ::getFecEventoSaldo).orElse(new Date());
        Casilla CAS184 = casillas.stream().filter(t -> Constantes.CAS184.contains(t.getNumCas())).toList().get(0);
        Casilla CAS106 = casillas.stream().filter(t -> Constantes.CAS106.contains(t.getNumCas())).toList().get(0);
        double montoAFavorCA0021 = 0;
        double MntoAFavorCA0016 = 0;
        double MntoAFavorCA0021Anterior = 0;

        List<String> casFiltro = Arrays.asList(Constantes.CAS105, Constantes.CAS108, Constantes.CAS111, Constantes.CAS131);
        LOGGER.info("Obtiene las siguientes casillas de la DJ 621");
        List<Casilla> saldo0024 = casillas.stream().filter(t -> casFiltro.contains(t.getNumCas())).toList();


        Casilla cas105 = saldo0024.get(0);
        Casilla cas108 = saldo0024.get(1);
        Casilla cas111 = saldo0024.get(2);
        Casilla cas131 = saldo0024.get(3);
        Double valorCas105 = Double.valueOf(cas105.getValCas());
        Double valorCas108 = Double.valueOf(cas108.getValCas());
        Double valorCas111 = Double.valueOf(cas111.getValCas());
        Double valorCas131 = Double.valueOf(cas131.getValCas());


        MovimientosSayyyymm movCA00016 = movimientosSayyyymmModel.obtenerUnSaldoCAalaFechMovInDynamicCollection(Constantes.CA0016, tributo, ruc, periodo, fecMov, "movimientosSa202311");
        MovimientosSayyyymm movCA00021Anterior = movimientosSayyyymmModel.obtenerUnSaldoCAalaFechMovInDynamicCollection(Constantes.CA0021, tributo, ruc, periodo, fecMov, "movimientosSa202311");
        if (movCA00016 == null) {
            MntoAFavorCA0016 = 0;
        } else {
            MntoAFavorCA0016 = movCA00016.getData().getDatosCuentaAfectada().getMtoAFavor();
        }
        if (movCA00021Anterior == null) {
            MntoAFavorCA0021Anterior = 0;
        } else {
            MntoAFavorCA0021Anterior = movCA00021Anterior.getData().getDatosCuentaAfectada().getMtoAFavor();
        }
        double creditoAcumulado = 0;
        double creditoUtilizado = 0;
        double creditoPorAplicar = 0;
        double montoMovimientoPeriodo = 0;
        double montoMovimiento = 0;
        double CA0021Nuevo = 0;
        LOGGER.info("CUS08 – Generar movimientos de CA0021");


        if (Double.valueOf(castilla140D.getValCas())==0.0) {
            LOGGER.info("Si el impuesto resultante o saldo a favor declarado (CAS140D) es cero");

            if (MntoAFavorCA0016 < 0) {
                if (MntoAFavorCA0021Anterior < 0) {
                    movCA00021Anterior.getData().getDatosCuentaAfectada().setMtoAFavor(Double.valueOf(0));
                }
            }
            Double valor = Double.valueOf(castilla140D.getValCas()) - MntoAFavorCA0021Anterior;
            CAS184.setValCas(String.valueOf(valor));

            LOGGER.info("Generar movimientos para el Saldo de percepciones de periodos anteriores (CA0022)");
        } else if (Double.valueOf(CAS106.getValCas()) > 0) {
            LOGGER.info("Valida las exportaciones del contribuyente");
        } else {
            LOGGER.info("Genera movimiento para el Saldo a favor del periodo anterior del IGV (CA0021)");
            if (Double.valueOf(castilla140D.getValCas()) <= 0) {
                creditoAcumulado = Double.valueOf(castilla140D.getValCas()) * -1;
                double i = Double.valueOf(castilla140D.getValCas()) + MntoAFavorCA0016 * -1;
                CAS184.setValCas(String.valueOf(i));
            } else {
                CAS184.setValCas(String.valueOf(Double.valueOf(castilla140D.getValCas()) - MntoAFavorCA0016));
                if (Double.valueOf(castilla140D.getValCas()) >= MntoAFavorCA0021Anterior) {
                    creditoUtilizado = Double.valueOf(castilla140D.getValCas());
                } else {
                    creditoUtilizado = MntoAFavorCA0021Anterior;
                }

                if (Double.valueOf(castilla140D.getValCas()) >= MntoAFavorCA0016) {
                    creditoPorAplicar = MntoAFavorCA0016;
                } else {
                    creditoPorAplicar = Double.valueOf(castilla140D.getValCas());
                }
                if (creditoPorAplicar > 0) {
                    montoMovimientoPeriodo = creditoPorAplicar * -1;
                }
                montoMovimiento = creditoAcumulado + creditoUtilizado;
                CA0021Nuevo = MntoAFavorCA0021Anterior + montoMovimiento;
            }

            LOGGER.info("Generar el movimiento del saldo acumulado según la siguiente estructura");
            MovimientosSayyyymm movimientoAcumPercReTCA0021 = new MovimientosSayyyymm();
            Data dataMovimientoAcumPercReTCA0021 = new Data();
            Metadata meta = new Metadata();
            meta.setNumVersionJSON("1");
            //Metadata
            LOGGER.info("Generar Metadata");
            //Cabecera
            //LOGGER.info("Generar Cabecera");
            Cabecera cabeceraCA0021 = new Cabecera();
            cabeceraCA0021.setCodMovimiento(cabeceraDJ.getCodDocumento());
            cabeceraCA0021.setFecMovimiento(cabeceraDJ.getFecDocumento());
            cabeceraCA0021.setNumIdMovimiento(cabeceraDJ.getNumDocumento());
            cabeceraCA0021.setCodDependencia(cabeceraDJ.getCodDependencia());
            cabeceraCA0021.setNumRuc(cabeceraDJ.getNumRuc());
            cabeceraCA0021.setCodEvento(codEvent);
            cabeceraCA0021.setNumIdEventoSaldo("");
            cabeceraCA0021.setPerTributario("010101");
            movimientoAcumPercReTCA0021.setCabecera(cabeceraCA0021);


            //Datos del movimiento
            LOGGER.info("Generar Datos del documento Sustento");
            DatosDocSustento documentoSusCA0021 = new DatosDocSustento();
            documentoSusCA0021.setCodDocumento("0621");
            documentoSusCA0021.setCodTributo("010101");
            documentoSusCA0021.setNumDocumento(cabeceraDJ.getNumDocumento());
            documentoSusCA0021.setCodTipoDJ(Utils.obtenerTipoDJ(codEvent));
            documentoSusCA0021.setCodTipoRecti(Utils.obtenerTipoRetificatoria(codEvent));
            documentoSusCA0021.setPerTributario(cabeceraDJ.getPerDocumento());
            documentoSusCA0021.setCodTributo(Utils.obtenerTipoRetificatoria(codEvent));
            documentoSusCA0021.setIndTipoBP("");
            dataMovimientoAcumPercReTCA0021.setDatosDocSustento(documentoSusCA0021);

            LOGGER.info("Generar Datos de cuenta Afectada");
            DatosCuentaAfectada cuentaAfectadaCA0021 = new DatosCuentaAfectada();
            cuentaAfectadaCA0021.setNumCta(Constantes.CA0021);
            cuentaAfectadaCA0021.setMtoAFavor(montoAFavorCA0021);
            cuentaAfectadaCA0021.setMtoSaldoCtaALaFecha(Double.valueOf(castilla140D.getValCas()));
            dataMovimientoAcumPercReTCA0021.setDatosCuentaAfectada(cuentaAfectadaCA0021);

            //Datos que afectan al saldo acumulado
            LOGGER.info("Generar Datos que afectan al saldo acumulado");
            DatosCalculoIGV datosSaldoAcumuladoCA0021 = new DatosCalculoIGV();

            datosSaldoAcumuladoCA0021.setMtoCas108(valorCas105);
            datosSaldoAcumuladoCA0021.setMtoCas111(valorCas111);
            datosSaldoAcumuladoCA0021.setMtoCas131(valorCas131);
            datosSaldoAcumuladoCA0021.setMtoCas184(Double.valueOf(CAS184.getValCas()));
            datosSaldoAcumuladoCA0021.setMtoSaldoAnterior(MntoAFavorCA0021Anterior);
            datosSaldoAcumuladoCA0021.setMtoGenerado(creditoAcumulado);
            datosSaldoAcumuladoCA0021.setMtoUtilizado(creditoUtilizado);

            dataMovimientoAcumPercReTCA0021.setDatosCalculoIGV(datosSaldoAcumuladoCA0021);
            //Datos de control y gestión
            LOGGER.info("Generar Datos de control y gestión");
            Control datosGestionControlCA0021 = new Control();

            datosGestionControlCA0021.setIndEstado("A");
            datosGestionControlCA0021.setMtoSaldoAntesMov(MntoAFavorCA0021Anterior);
            datosGestionControlCA0021.setMtoSaldoDespMov(CA0021Nuevo);
            dataMovimientoAcumPercReTCA0021.setControl(datosGestionControlCA0021);
            movimientoAcumPercReTCA0021.setData(dataMovimientoAcumPercReTCA0021);

            movimientosSayyyymmModel.saveMovimientosSayyyymmInDynamicCollection(movimientoAcumPercReTCA0021, Utils.generarNombreCollectionMovimientosSA());
        }

        LOGGER.info("CUS08 – Generar movimientos de CA0022");
        MovimientosSayyyymm movCA00022Anterior = movimientosSayyyymmModel.obtenerUnSaldoCAalaFechMovInDynamicCollection(Constantes.CA0022, tributo, ruc, periodo, fecMov, "movimientosSa202310");


        Casilla CAS184DP = new Casilla();
        CAS184DP.setValCas("0");

        Casilla CAS171 = casillas.stream().filter(t -> Constantes.CAS171.contains(t.getNumCas())).toList().get(0);
        Casilla CAS168 = casillas.stream().filter(t -> Constantes.CAS168.contains(t.getNumCas())).toList().get(0);
        Casilla CAS178 = casillas.stream().filter(t -> Constantes.CAS178.contains(t.getNumCas())).toList().get(0);
        Casilla CAS184DPP = new Casilla();
        CAS184DPP.setNumCas(Constantes.CAS184DPP);
        double percepcionesPeriodo = mtoPercepciones;
        double percepcionesPeriodoUtilizado = 0;
        double percepcionesAcumulado = 0;
        double percepcionesPeriodosAnteriores = 0;
        double percepPAnterioresUtilizado = 0;
        montoMovimiento = 0;
        double MntoAFavorCA0022Anterior = 0;
        if (movCA00022Anterior == null) {
            MntoAFavorCA0022Anterior = 0;
        } else {
            MntoAFavorCA0022Anterior = movCA00022Anterior.getData().getDatosCuentaAfectada().getMtoAFavor();
        }
        if (MntoAFavorCA0022Anterior < 0) {
            movCA00022Anterior.getData().getDatosCuentaAfectada().setMtoAFavor(Double.valueOf(0));
        }
        LOGGER.info("Determinar las percepciones del periodo a acumular en la CA0018");
        percepcionesAcumulado = percepcionesPeriodo;
        if (Double.valueOf(CAS184.getValCas()) > 0) {
            LOGGER.info("Si el tributo a pagar o saldo a favor (CAS184) es menor o igual a cer8");
            CAS184.setValCas(CAS184DP.getValCas());
            LOGGER.info("No se genera el movimiento CA0022");
        } else {
            if (Double.valueOf(CAS184.getValCas()) > 0) {
                if (Double.valueOf(CAS171.getValCas())==0.0 && percepcionesPeriodo == 0) {
                    LOGGER.info("no existen percepciones del periodo a aplicar, por lo tanto, CAS184DPP=CAS184");
                    LOGGER.info("continua en el numeral 5.5 (percepciones de periodos anteriores).");
                } else if (Double.valueOf(CAS171.getValCas()) < percepcionesPeriodo) {
                    percepcionesPeriodo = Double.valueOf(CAS171.getValCas());
                } else if (Double.valueOf(CAS178.getValCas()) >= percepcionesPeriodo) {
                    percepcionesPeriodoUtilizado = percepcionesPeriodo;
                } else if (Double.valueOf(CAS178.getValCas()) < percepcionesPeriodo) {
                    percepcionesPeriodoUtilizado = Double.valueOf(CAS178.getValCas());
                }
                double resul = Double.valueOf(CAS184.getValCas()) - percepcionesPeriodoUtilizado;
                String valorCAS184 = String.valueOf(resul);
                ;
                CAS184DPP.setValCas(valorCAS184);
                if (Double.valueOf(CAS184DPP.getValCas())==0.0) {
                    LOGGER.info("asignar este valor al tributo a pagar o saldo a favor después de aplicadas las percepciones");

                    LOGGER.info("No se genera el movimiento CA0022");
                } else if (Double.valueOf(CAS184DPP.getValCas()) > 0) {
                    if (Double.valueOf(CAS168.getValCas())==0.0 && percepcionesPeriodo == 0) {
                        LOGGER.info("No existen percepciones de periodos anteriores a aplicar");
                        LOGGER.info("No se genera el movimiento CA0022");
                    } else {
                        if (Double.valueOf(CAS168.getValCas()) < percepcionesPeriodo) {
                            percepcionesPeriodosAnteriores = Double.valueOf(CAS168.getValCas());
                        } else {
                            percepcionesPeriodosAnteriores = percepcionesPeriodo;
                        }

                        if (Double.valueOf(CAS184DPP.getValCas()) >= percepcionesPeriodosAnteriores) {
                            percepPAnterioresUtilizado = percepcionesPeriodosAnteriores;
                        } else if (Double.valueOf(CAS184DPP.getValCas()) < percepcionesPeriodosAnteriores) {
                            percepPAnterioresUtilizado = Double.valueOf(CAS184DPP.getValCas());
                        }
                        double resultado = Double.valueOf(CAS184DPP.getValCas()) - percepPAnterioresUtilizado;
                        CAS184DP.setValCas(String.valueOf(resultado));
                        montoMovimiento = percepcionesAcumulado - percepcionesPeriodoUtilizado - percepPAnterioresUtilizado;
                        double CA0022Nuevo = MntoAFavorCA0022Anterior - montoMovimiento;

                        LOGGER.info("Gemerar el movimiento CA0022");
                        LOGGER.info("Generar el movimiento del saldo acumulado según la siguiente estructura");

                        LOGGER.info("Generar el movimiento del saldo acumulado según la siguiente estructura");
                        MovimientosSayyyymm movimientoAcumPercReTCA0022 = new MovimientosSayyyymm();
                        Data dataMovimientoAcumPercReTCA0022 = new Data();
                        Metadata meta = new Metadata();
                        meta.setNumVersionJSON("1");
                        //Metadata
                        LOGGER.info("Generar Metadata");
                        //Cabecera
                        //LOGGER.info("Generar Cabecera");
                        Cabecera cabeceraCA0022 = new Cabecera();
                        cabeceraCA0022.setCodMovimiento(cabeceraDJ.getCodDocumento());
                        cabeceraCA0022.setFecMovimiento(cabeceraDJ.getFecDocumento());
                        cabeceraCA0022.setNumIdMovimiento(cabeceraDJ.getNumDocumento());
                        cabeceraCA0022.setCodDependencia(cabeceraDJ.getCodDependencia());
                        cabeceraCA0022.setNumRuc(cabeceraDJ.getNumRuc());
                        cabeceraCA0022.setCodEvento(codEvent);
                        cabeceraCA0022.setNumIdEventoSaldo("");
                        cabeceraCA0022.setPerTributario("010101");
                        movimientoAcumPercReTCA0022.setCabecera(cabeceraCA0022);


                        //Datos del movimiento
                        LOGGER.info("Generar Datos del documento Sustento");
                        DatosDocSustento documentoSusCA0022 = new DatosDocSustento();
                        documentoSusCA0022.setCodDocumento("0621");
                        documentoSusCA0022.setCodTributo("010101");
                        documentoSusCA0022.setNumDocumento(cabeceraDJ.getNumDocumento());
                        documentoSusCA0022.setCodTipoDJ(Utils.obtenerTipoDJ(codEvent));
                        documentoSusCA0022.setCodTipoRecti(Utils.obtenerTipoRetificatoria(codEvent));
                        documentoSusCA0022.setPerTributario(cabeceraDJ.getPerDocumento());
                        documentoSusCA0022.setCodTributo(Utils.obtenerTipoRetificatoria(codEvent));
                        documentoSusCA0022.setIndTipoBP("");
                        dataMovimientoAcumPercReTCA0022.setDatosDocSustento(documentoSusCA0022);

                        LOGGER.info("Generar Datos de cuenta Afectada");
                        DatosCuentaAfectada cuentaAfectadaCA0022 = new DatosCuentaAfectada();
                        cuentaAfectadaCA0022.setNumCta(Constantes.CA0022);
                        cuentaAfectadaCA0022.setMtoAFavor(percepcionesPeriodo);
                        cuentaAfectadaCA0022.setMtoSaldoCtaALaFecha(Double.valueOf(castilla140D.getValCas()));
                        dataMovimientoAcumPercReTCA0022.setDatosCuentaAfectada(cuentaAfectadaCA0022);

                        //Datos que afectan al saldo acumulado
                        LOGGER.info("Generar Datos que afectan al saldo acumulado");
                        DatosCalculoIGV datosSaldoAcumuladoCA0022 = new DatosCalculoIGV();

                        datosSaldoAcumuladoCA0022.setMtoCas108(valorCas105);
                        datosSaldoAcumuladoCA0022.setMtoCas111(valorCas111);
                        datosSaldoAcumuladoCA0022.setMtoCas131(valorCas131);
                        datosSaldoAcumuladoCA0022.setMtoCas184(Double.valueOf(CAS184.getValCas()));
                        datosSaldoAcumuladoCA0022.setMtoCas184DP(Double.valueOf(CAS184DP.getValCas()));
                        datosSaldoAcumuladoCA0022.setMtoCas184DPP(Double.valueOf(CAS184DPP.getValCas()));
                        datosSaldoAcumuladoCA0022.setMtoSaldoAnterior(MntoAFavorCA0022Anterior);
                        datosSaldoAcumuladoCA0022.setMtoGenerado(percepcionesPeriodo);
                        datosSaldoAcumuladoCA0022.setMtoUtilizado(percepcionesPeriodoUtilizado);

                        dataMovimientoAcumPercReTCA0022.setDatosCalculoIGV(datosSaldoAcumuladoCA0022);
                        //Datos de control y gestión
                        LOGGER.info("Generar Datos de control y gestión");
                        Control datosGestionControlCA0022 = new Control();

                        datosGestionControlCA0022.setIndEstado("A");
                        datosGestionControlCA0022.setMtoSaldoAntesMov(MntoAFavorCA0022Anterior);
                        datosGestionControlCA0022.setMtoSaldoDespMov(CA0022Nuevo);
                        dataMovimientoAcumPercReTCA0022.setControl(datosGestionControlCA0022);
                        movimientoAcumPercReTCA0022.setData(dataMovimientoAcumPercReTCA0022);

                        movimientosSayyyymmModel.saveMovimientosSayyyymmInDynamicCollection(movimientoAcumPercReTCA0022, Utils.generarNombreCollectionMovimientosSA());

                    }
                }
            }
        }


        LOGGER.info("CUS08 – Generar movimientos de CA0023");
        tributo = "1011";
        MovimientosSayyyymm movCA00023Anterior = movimientosSayyyymmModel.obtenerUnSaldoCAalaFechMovInDynamicCollection(Constantes.CA0023, tributo, ruc, periodo, fecMov, "movimientosSa202310");
        double retencionesPeriodo = mtoRetenciones;
        Casilla CAS179 = casillas.stream().filter(t -> Constantes.CAS179.contains(t.getNumCas())).toList().get(0);
        Casilla CAS176 = casillas.stream().filter(t -> Constantes.CAS176.contains(t.getNumCas())).toList().get(0);
        Casilla CAS184DRP = new Casilla();
        CAS184DRP.setValCas(String.valueOf(retencionesPeriodo));
        CAS184DRP.setNumCas(Constantes.CAS184DRP);
        Casilla CAS184DR = new Casilla();
        CAS184DR.setValCas(String.valueOf(retencionesPeriodo));
        CAS184DR.setNumCas(Constantes.CAS184DR);
        double retencionesPeriodoAnteriores = 0;
        double retencionesPeriodoUtilizado = 0;
        double retencPAnterioresUtilizado = 0;
        LOGGER.info("Determinar las retenciones del periodo a acumular en el saldo CA0023. ");
        double retencionesAcumulado = mtoRetenciones;
        double MntoAFavorCA0023Anterior = 0;
        if (movCA00022Anterior == null) {
            MntoAFavorCA0023Anterior = 0;
        } else {
            MntoAFavorCA0023Anterior = movCA00023Anterior.getData().getDatosCuentaAfectada().getMtoAFavor();
        }

        if (Double.valueOf(CAS184DP.getValCas()) <= 0) {
            return;
        }


        if (Double.valueOf(CAS179.getValCas())==0.0 && percepcionesPeriodo == 0) {

        } else {
            if (Double.valueOf(CAS179.getValCas()) > retencionesPeriodo) {
                retencionesPeriodo = Double.valueOf(CAS179.getValCas());
            }

            if (Double.valueOf(CAS184DP.getValCas()) > retencionesPeriodo) {
                retencionesPeriodoUtilizado = retencionesPeriodo;
            } else {
                retencionesPeriodoUtilizado = Double.valueOf(CAS184DP.getValCas());
            }
            Double x = Double.valueOf(CAS184DP.getValCas()) - retencionesPeriodoUtilizado;
            CAS184DRP.setValCas(String.valueOf(x));
        }


        if (Double.valueOf(CAS184DP.getValCas())==0.0) {
            return;
        } else {
            if (Double.valueOf(CAS176.getValCas()).equals(MntoAFavorCA0023Anterior)) {
                retencionesPeriodoAnteriores = Double.valueOf(CAS176.getValCas());
            } else {
                retencionesPeriodoAnteriores = MntoAFavorCA0023Anterior;
            }

            if (Double.valueOf(CAS184DR.getValCas()) >= retencionesPeriodoAnteriores) {
                retencPAnterioresUtilizado = retencionesPeriodoAnteriores;
            }

            if (Double.valueOf(CAS184DRP.getValCas()) >= retencionesPeriodoAnteriores) {
                retencPAnterioresUtilizado = Double.valueOf(CAS184DRP.getValCas());
            }
            Double resultado2 = Double.valueOf(CAS184DRP.getValCas()) - retencPAnterioresUtilizado;
            CAS184DR.setValCas(String.valueOf(resultado2));
            LOGGER.info("Valor de CAS184DR " + resultado2);

            double montoMovimeinto = retencionesAcumulado - retencionesPeriodoUtilizado - retencPAnterioresUtilizado;
            LOGGER.info("Monto Movimiento CA0023 " + montoMovimeinto);

            double CA0023MtoAFavorNuevo = MntoAFavorCA0023Anterior + montoMovimeinto;

            LOGGER.info("CA0023 Nuevo " + CA0023MtoAFavorNuevo);

            LOGGER.info("CUS08 – Generar movimientos de retenciones de CA0023");
            LOGGER.info("Generar el movimiento del saldo acumulado según la siguiente estructura");
            MovimientosSayyyymm movimientoAcumPercReTCA0023 = new MovimientosSayyyymm();
            Data dataMovimientoAcumPercReTCA0023 = new Data();
            Metadata meta = new Metadata();
            meta.setNumVersionJSON("1");
            //Metadata
            LOGGER.info("Generar Metadata");
            //Cabecera
            //LOGGER.info("Generar Cabecera");
            Cabecera cabeceraCA0023 = new Cabecera();
            cabeceraCA0023.setCodMovimiento(cabeceraDJ.getCodDocumento());
            cabeceraCA0023.setFecMovimiento(cabeceraDJ.getFecDocumento());
            cabeceraCA0023.setNumIdMovimiento(cabeceraDJ.getNumDocumento());
            cabeceraCA0023.setCodDependencia(cabeceraDJ.getCodDependencia());
            cabeceraCA0023.setNumRuc(cabeceraDJ.getNumRuc());
            cabeceraCA0023.setCodEvento(codEvent);
            cabeceraCA0023.setNumIdEventoSaldo("");
            cabeceraCA0023.setPerTributario("010101");
            movimientoAcumPercReTCA0023.setCabecera(cabeceraCA0023);


            //Datos del movimiento
            LOGGER.info("Generar Datos del documento Sustento");
            DatosDocSustento documentoSusCA0023 = new DatosDocSustento();
            documentoSusCA0023.setCodDocumento("0621");
            documentoSusCA0023.setCodTributo("010101");
            documentoSusCA0023.setNumDocumento(cabeceraDJ.getNumDocumento());
            documentoSusCA0023.setCodTipoDJ(Utils.obtenerTipoDJ(codEvent));
            documentoSusCA0023.setCodTipoRecti(Utils.obtenerTipoRetificatoria(codEvent));
            documentoSusCA0023.setPerTributario(cabeceraDJ.getPerDocumento());
            documentoSusCA0023.setCodTributo(Utils.obtenerTipoRetificatoria(codEvent));
            documentoSusCA0023.setIndTipoBP("");
            dataMovimientoAcumPercReTCA0023.setDatosDocSustento(documentoSusCA0023);

            LOGGER.info("Generar Datos de cuenta Afectada");
            DatosCuentaAfectada cuentaAfectadaCA0023 = new DatosCuentaAfectada();
            cuentaAfectadaCA0023.setNumCta(Constantes.CA0023);
            cuentaAfectadaCA0023.setMtoAFavor(percepcionesPeriodo);
            cuentaAfectadaCA0023.setMtoSaldoCtaALaFecha(Double.valueOf(castilla140D.getValCas()));
            dataMovimientoAcumPercReTCA0023.setDatosCuentaAfectada(cuentaAfectadaCA0023);

            //Datos que afectan al saldo acumulado
            LOGGER.info("Generar Datos que afectan al saldo acumulado");
            DatosCalculoIGV datosSaldoAcumuladoCA0023 = new DatosCalculoIGV();

            datosSaldoAcumuladoCA0023.setMtoCas108(valorCas105);
            datosSaldoAcumuladoCA0023.setMtoCas111(valorCas111);
            datosSaldoAcumuladoCA0023.setMtoCas131(valorCas131);
            datosSaldoAcumuladoCA0023.setMtoCas184(Double.valueOf(CAS184.getValCas()));
            datosSaldoAcumuladoCA0023.setMtoCas184DP(Double.valueOf(CAS184DP.getValCas()));
            datosSaldoAcumuladoCA0023.setMtoCas184DPP(Double.valueOf(CAS184DPP.getValCas()));
            datosSaldoAcumuladoCA0023.setMtoSaldoAnterior(MntoAFavorCA0022Anterior);
            datosSaldoAcumuladoCA0023.setMtoGenerado(percepcionesPeriodo);
            datosSaldoAcumuladoCA0023.setMtoUtilizado(percepcionesPeriodoUtilizado);

            dataMovimientoAcumPercReTCA0023.setDatosCalculoIGV(datosSaldoAcumuladoCA0023);
            //Datos de control y gestión
            LOGGER.info("Generar Datos de control y gestión");
            Control datosGestionControlCA0023 = new Control();

            datosGestionControlCA0023.setIndEstado("A");
            // datosGestionControlCA0021.setNumIdMovimientoOrigen(0);
            //  datosGestionControlCA0021.setNumIdMovimientoOrigen(0);
            dataMovimientoAcumPercReTCA0023.setControl(datosGestionControlCA0023);
            movimientoAcumPercReTCA0023.setData(dataMovimientoAcumPercReTCA0023);

            movimientosSayyyymmModel.saveMovimientosSayyyymmInDynamicCollection(movimientoAcumPercReTCA0023, Utils.generarNombreCollectionMovimientosSA());


        }

        //   cus10AlmacenarActualizarSaldos(LOG);


    }

    @Override
    public void cus09GenerarSaldosAcumulado(DatosVO<DataDJVO> dataJson, String numRuc, Date fecMovimiento, String perTributario, double mtoRetenciones) {
        LOGGER.info("F2_491203_eIGVDJ621.docx");
        LOGGER.info("CUS09 – Generar movimientos de CA0017");

        String ruc = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getNumRuc).orElse("");
        String codDocumento = Optional.of(dataJson).map(DatosVO::getData).map(DataDJVO::getDocumento).map(Documento::getCodDocumento).orElse("");
        String numOrden  = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getNumDocumento).orElse("");
        Date fecDoc = dataJson.getData().getDocumento().getFecDocumento();
        String prDocumento = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getPerDocumento).orElse("");
        String coedTributo = "010101";
        String codEvento = dataJson.getCabecera().getCodEventoSaldo();

        double retencionesPeriodoUtilizado = 0;
        double retencPAnterioresUtilizado = 0;
        double retencionesAcumulado = mtoRetenciones;

        try {
            //1.- Obtiene casillas CAS189
            List<Casilla> lisCasillas189 = dataJson.getData().getLisCasillas();

            //2.- Valida que la casilla Importe a pagar
            lisCasillas189.forEach(x->{
                if(x.getNumCas().equals("189"))
                {
                    if(Double.valueOf(x.getValCas() ) < 0) {
                        LOGGER.info("Evento " + codEvento + " no tiene pago");
                    }
                }
            });
            //3. - Obtiene Saldo Actual
            MovimientosSayyyymm movimientosSayyyymm = movimientosSayyyymmModel.obtenerSaldoActual(ruc,prDocumento,fecDoc, Utils.generarNombreCollectionMovimientosSA());
            Double saldoActual = 0.00;
            if (movimientosSayyyymm == null){
                LOGGER.info("Monto Saldo de la cuenta a la fecha es nulo");
            }
            else {
                saldoActual = movimientosSayyyymm.getData().getDatosCuentaAfectada().getMtoSaldoCtaALaFecha();
            }
            //4.- Calcular el Saldo
            double montoMovimeinto  = retencionesAcumulado -retencionesPeriodoUtilizado - retencPAnterioresUtilizado;
            double nuevoSaldo = saldoActual + montoMovimeinto;

            //5.- Genera movimiento de saldo acumulado


            MovimientosSayyyymm movimientoCA0017 = new MovimientosSayyyymm();
            Data dataCA0017 = new Data();
            CabeceraDJ cabeceraDJ = Optional.of(dataJson).map(DatosVO::getCabecera).orElse(null);
            //Map<String, Object> sub_data = new HashMap<>();

            LOGGER.info("Generar Metadata");
            MetadataGral metadat = new MetadataGral();
           // metadat.setNumIdEvento(cabeceraDJ.getNumIdEventoPadre());
            metadat.setNumIdEvento(cabeceraDJ != null ? cabeceraDJ.getNumIdEventoPadre() : null);
            metadat.setNumVersionJSON("77.7");
            movimientoCA0017.setMetadata(metadat);

            //Cabecera
            //LOGGER.info("Generar Cabecera");
            Cabecera cabeceraCA0017 = new Cabecera();
            //cabeceraCA0017.setCodMovimiento(cabeceraDJ.getCodDocumento());
            cabeceraCA0017.setCodMovimiento(cabeceraDJ != null ? cabeceraDJ.getCodDocumento() : null);
            //cabeceraCA0017.setFecMovimiento(cabeceraDJ.getFecDocumento());
            cabeceraCA0017.setFecMovimiento(cabeceraDJ != null ? cabeceraDJ.getFecDocumento() : null);
            //cabeceraCA0017.setNumIdMovimiento(cabeceraDJ.getNumDocumento());
            cabeceraCA0017.setNumIdMovimiento(cabeceraDJ != null ? cabeceraDJ.getNumDocumento() : null);
            //cabeceraCA0017.setCodDependencia(cabeceraDJ.getCodDependencia());
            cabeceraCA0017.setCodDependencia(cabeceraDJ != null ? cabeceraDJ.getCodDependencia() : null);
            //cabeceraCA0017.setNumRuc(cabeceraDJ.getNumRuc());
            cabeceraCA0017.setNumRuc(cabeceraDJ != null ? cabeceraDJ.getNumRuc() : null);
            cabeceraCA0017.setCodEvento("");
            cabeceraCA0017.setNumIdEventoSaldo("");
            cabeceraCA0017.setPerTributario("010101");
            movimientoCA0017.setCabecera(cabeceraCA0017);

            //Contribuyente
            //Contribuyente contribuyenteCA0017 = new Contribuyente();
            //contribuyenteCA0017.setNumRuc(cabeceraDJ.getNumRuc());

            //Docuemento Sustento
            LOGGER.info("Generar Datos del documento Sustento");
            DatosDocSustento documentoSusCA0017 = new DatosDocSustento();
            documentoSusCA0017.setCodDocumento("0621");
            documentoSusCA0017.setCodTributo("010101");
            //documentoSusCA0017.setNumDocumento(cabeceraDJ.getNumDocumento());
            documentoSusCA0017.setNumDocumento(cabeceraDJ != null ? cabeceraDJ.getCodDocumento() : null);
            documentoSusCA0017.setCodTipoDJ(Utils.obtenerTipoDJ(codEvento));
            documentoSusCA0017.setCodTipoRecti(Utils.obtenerTipoRetificatoria(codEvento));
            //documentoSusCA0017.setPerTributario(cabeceraDJ.getPerDocumento());
            documentoSusCA0017.setPerTributario(cabeceraDJ != null ? cabeceraDJ.getPerDocumento() : null);
            documentoSusCA0017.setCodTributo(Utils.obtenerTipoRetificatoria(codEvento));
            documentoSusCA0017.setIndTipoBP("");
            dataCA0017.setDatosDocSustento(documentoSusCA0017);

            // Datos de Movimiento

            DatosMovimiento datosmovimientoCA0017 = new DatosMovimiento();
            //datosmovimientoCA0017.setCodSaldoAcumulado(dataJson.get);
            //sub_data.put("datosMov",datosmovimientoCA0017);

            //Datos de control y gestión
            LOGGER.info("Generar Datos de control y gestión");
            Control datosGestionControlCA0017 = new Control();
            datosGestionControlCA0017.setIndEstado("A");
            dataCA0017.setControl(datosGestionControlCA0017);
            movimientoCA0017.setData(dataCA0017);

            //movimientoRepository.saveMovimientoDJ621(dat, Utils.generarNombreCollectionMovimientosSA());
            movimientosSayyyymmModel.saveMovimientosSayyyymmInDynamicCollection(movimientoCA0017, Utils.generarNombreCollectionMovimientosSA());

            //Reintentos de invocación en el proceso
            LOGGER.info("Evento " + codEvento + ": Error de conectividad de BD");

            LOGGER.info("Se grabo correctamente");

        } catch (Exception e) {
            //Mensaje para el reproceso del evento
            //LOGGER.error("Evento " + codEvento + ": Error de interpretación del mensaje");
        }
    }

    @Override
    public void cus10AlmacenarActualizarSaldos(String codCuenta) {
        LOGGER.info("F2_491203_eIGVDJ621.docx");
        LOGGER.info("CUS10 – Almacenar movimientos y actualizar saldos");
    }

    @Override
    public void cus11RegistrarEvento(DatosVO<DataDJVO> dataJson) {
        LOGGER.info("F2_491203_eIGVDJ621.docx");
        LOGGER.info("CUS11 – Registrar evento DJ 621 en cuarentena ");

        //Registra la información en cuarentena
        DJ621 dj621 = new DJ621();
        String numRuc = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getNumRuc).orElse("");
        String codDependencia = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getCodDependencia).orElse("");
        String codDocumento = Optional.of(dataJson).map(DatosVO::getData).map(DataDJVO::getDocumento).map(Documento::getCodDocumento).orElse("");
        String numDocumento = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getNumDocumento).orElse("");
        String perDocumento = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getPerDocumento).orElse("");
        String codTributo = "010101";
        Date fecDocumento = dataJson.getData().getDocumento().getFecDocumento();
        BigDecimal mtoPagTotal = Optional.of(dataJson).map(DatosVO::getData).map(DataDJVO::getDetalleMontosPagados).map(DetalleMontosPagados::getMtoPagTotal).orElse(new BigDecimal("0"));
        List<Casilla> numCas = Optional.of(dataJson).map(DatosVO::getData).map(DataDJVO::getLisCasillas).orElse(null);
        String codEvento = dataJson.getCabecera().getCodEventoSaldo();
        LOGGER.info("CODIGO DE EVENTO ==> {} ", codEvento);
        try {
            dj621.setNumRuc(numRuc);
            dj621.setCodDependencia(codDependencia);
            dj621.setCodDocumento(codDocumento);
            dj621.setNumDocumento(numDocumento);
            dj621.setPerDocumento(perDocumento);
            //dj621.setCodTributo(codTributo);
            dj621.setFecDocumento(fecDocumento);
            dj621.setMtoPagTotal(mtoPagTotal);
            dj621.setNumCas(numCas);

            EventosAnterioresDJ eventosAnteriores = new EventosAnterioresDJ();
            pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.eventosanteriores.CabeceraDJ cabecera = new pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.eventosanteriores.CabeceraDJ();
            cabecera.setNumRuc(dataJson.getCabecera().getNumRuc());
            cabecera.setCodDependencia(dataJson.getCabecera().getCodDependencia());
            cabecera.setCodDocumento(dataJson.getCabecera().getCodDocumento());
            cabecera.setNumDocumento(dataJson.getCabecera().getNumDocumento());
            cabecera.setPerDocumento(dataJson.getCabecera().getPerDocumento());
            cabecera.setPerDocumento(dataJson.getData().getDocumento().getCodTributo());
            cabecera.setFecDocumento(dataJson.getData().getDocumento().getFecDocumento());
            //cabecera.setNumDocumento(dataJson.getData().getDocumento().getNumDocumento());
            //cabecera.setFecDocumento(dataJson.getCabecera().getFecDocumento());

            List<Casilla> listaCasilla = dataJson.getData().getLisCasillas();

            eventosAnteriores.setCabeceradj(cabecera);
            Map<String, Object> sub_data = new HashMap<>();

            pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.eventosanteriores.Documento documento = new pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.eventosanteriores.Documento();
            DocumentoAsociadoAfectado documentoAsociadoAfectado = new DocumentoAsociadoAfectado();
            //documentoAsociadoAfectado.setFecDocAsocAfect(dataJson.getData().getDocumento().getFecDocumento());
            documentoAsociadoAfectado.setCodDocAsocAfect(dataJson.getData().getDocumento().getCodDocumento());
            documentoAsociadoAfectado.setNumDocAsocAfect(dataJson.getData().getDocumento().getNumDocumento());

            documento.setDocAsociadoAfectado(documentoAsociadoAfectado);
            sub_data.put("documento",documento);

            eventosAnteriores.setData(sub_data);
            eventosAnterioresDJRepository.save(eventosAnteriores);

            //Reintentos de invocación en el proceso
            LOGGER.info("Evento " + codEvento + ":Error de conectividad de BD");

        } catch (Exception e) {
            //Mensaje para el reproceso del evento
            //LOGGER.error("Evento " + codEvento + ": Error de interpretación del mensaje");
        }

    }

    @Override
    public void cus12GenerarMovimientoCierre(DatosVO<DataDJVO> dataJson) {

        String ruc = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getNumRuc).orElse("");
        String codFormulario = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getCodDocumento).orElse("");
        String numOrden = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getNumDocumento).orElse("");
        Contribuyente con = Optional.of(dataJson).map(DatosVO::getData).map(DataDJVO::getContribuyente).orElse(null);
        String montoMovi = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getNumDocumento).orElse("");
        String codCuenta = Optional.of(dataJson).map(DatosVO::getCabecera).map(CabeceraDJ::getNumDocumento).orElse("");
        String inddIncon = "0";
        String codEvento = dataJson.getCabecera().getCodEventoSaldo();
        List<MovimientosSayyyymm> eventosA = new ArrayList<>();
        double montoGenerado= 0;
        List<String> casFiltro = Arrays.asList(Constantes.CAS105, Constantes.CAS108, Constantes.CAS111, Constantes.CAS131);
        LOGGER.info("Obtiene las siguientes casillas de la DJ 621");
        List<Casilla> saldo0024 = dataJson.getData().getLisCasillas().stream().filter(t -> casFiltro.contains(t.getNumCas())).toList();


        Casilla cas105 = saldo0024.get(0);
        Casilla cas108 = saldo0024.get(1);
        Casilla cas111 = saldo0024.get(2);
        Casilla cas131 = saldo0024.get(3);

        Double valorCas105  = Double.valueOf(cas105.getValCas());
        Double valorCas108  = Double.valueOf(cas108.getValCas());
        Double valorCas111  =Double.valueOf( cas111.getValCas());
        Double valorCas131  = Double.valueOf(cas131.getValCas());

        LOGGER.info("F2_491203_eIGVDJ621.docx");
        LOGGER.info("CUS12 - Generar movimientos de cierre de Declaración Jurada 621 Sustitutoria o Rectificatoria");

        LOGGER.info("CUS12 - Obtener movimientos");
       // int fechaAnterior = 202310;
        List<MovimientosSayyyymm> datos = movimientosSayyyymmModel.findListMovimientosSayyyymmByNumRucCodFormNumOrdTipDJ(ruc, codFormulario, numOrden, Utils.generarNombreCollectionMovimientosSA());

        if(datos.isEmpty()){
            inddIncon = "1";
            return ;
        }
        LOGGER.info("CUS12 - Validar estado " );

        LOGGER.info("CUS12 - Cambiar a “Inactivo” el estado del movimiento de la DJ 621 Activa.");


        LOGGER.info("CUS12 - Generar y enviar los movimientos con la información para cada SA");

        for (MovimientosSayyyymm datosVO : datos) {
            String cuenta = datosVO.getData().getDatosCuentaAfectada().getNumCta();
            Double montoMov = datosVO.getData().getMontoParaRegistrarIGV().getMtoTotalUtilizado();
            switch (cuenta) {

                case Constantes.CA0016, Constantes.CA0018, Constantes.CA0019, Constantes.CA0021, Constantes.CA0023, Constantes.CA0022:
                    LOGGER.info("CUS12 - CA016, CA018, CA019, CA0021, CA0022 y CA0023");
                    montoMov = montoMov * -1;

                    break;

                case Constantes.CA0024, Constantes.CA0020:
                    LOGGER.info("CUS07-Calcular tributo a pagar (CAS140) según lo declarado");
                    LOGGER.info("Se invoca al CUS08-Generar movimientos de CA0021, CA0022, CA0023");
                    LOGGER.info("CUS12 - CA020 y CA024");
                    montoMov = (double) 0;
                    break;

            }

            MovimientosSayyyymm movimienoSA = new MovimientosSayyyymm();
            Data dataMovimientoAcumPercReTCA0023 = new Data();
            CabeceraDJ cabeceraDJ = Optional.of(dataJson).map(DatosVO::getCabecera).orElse(null);
            // String codTributo = dataJson.getCabecera().get();
            Metadata meta = new Metadata();
            meta.setNumVersionJSON("1");
            //Metadata
            LOGGER.info("Generar Metadata");
            //Cabecera
           // LOGGER.info("Generar Cabecera");
            Cabecera cabeceraCA0023 = new Cabecera();
            //cabeceraCA0023.setCodMovimiento(cabeceraDJ.getCodDocumento());
            cabeceraCA0023.setCodMovimiento(cabeceraDJ != null ? cabeceraDJ.getCodDocumento() : null);
            //cabeceraCA0023.setFecMovimiento(cabeceraDJ.getFecDocumento());
            cabeceraCA0023.setFecMovimiento(cabeceraDJ != null ? cabeceraDJ.getFecDocumento() : null);
            //cabeceraCA0023.setNumIdMovimiento(cabeceraDJ.getNumDocumento());
            cabeceraCA0023.setNumIdMovimiento(cabeceraDJ != null ? cabeceraDJ.getNumDocumento() : null);
            //cabeceraCA0023.setCodDependencia(cabeceraDJ.getCodDependencia());
            cabeceraCA0023.setCodDependencia(cabeceraDJ != null ? cabeceraDJ.getCodDependencia() : null);
            //cabeceraCA0023.setNumRuc(cabeceraDJ.getNumRuc());
            cabeceraCA0023.setNumRuc(cabeceraDJ != null ? cabeceraDJ.getNumRuc() : null);
            cabeceraCA0023.setCodEvento("");
            cabeceraCA0023.setNumIdEventoSaldo("");
            cabeceraCA0023.setPerTributario("010101");
            movimienoSA.setCabecera(cabeceraCA0023);


            //Datos del movimiento
            LOGGER.info("Generar Datos del documento Sustento");
            DatosDocSustento documentoSusCA0023 = new DatosDocSustento();
            documentoSusCA0023.setCodDocumento("0621");
            documentoSusCA0023.setCodTributo("010101");
            //documentoSusCA0023.setNumDocumento(cabeceraDJ.getNumDocumento());
            documentoSusCA0023.setNumDocumento(cabeceraDJ != null ? cabeceraDJ.getNumDocumento() : null);
            documentoSusCA0023.setCodTipoDJ(Utils.obtenerTipoDJ(Utils.obtenerTipoDJ(codEvento)));
            documentoSusCA0023.setCodTipoRecti(Utils.obtenerTipoRetificatoria(Utils.obtenerTipoRetificatoria(codEvento)));
            //documentoSusCA0023.setPerTributario(cabeceraDJ.getPerDocumento());
            documentoSusCA0023.setPerTributario(cabeceraDJ != null ? cabeceraDJ.getPerDocumento() : null);
            documentoSusCA0023.setCodTributo(Utils.obtenerTipoRetificatoria(codEvento));
            documentoSusCA0023.setIndTipoBP("");
            dataMovimientoAcumPercReTCA0023.setDatosDocSustento(documentoSusCA0023);

            LOGGER.info("Generar Datos de cuenta Afectada");
            DatosCuentaAfectada cuentaAfectadaCA0023 = new DatosCuentaAfectada();
            cuentaAfectadaCA0023.setNumCta(Constantes.CA0023);
            //  cuentaAfectadaCA0023.setMtoAFavor(percepcionesPeriodo);
            //   cuentaAfectadaCA0023.setMtoSaldoCtaALaFecha(Double.valueOf(castilla140D.getValCas()));
            dataMovimientoAcumPercReTCA0023.setDatosCuentaAfectada(cuentaAfectadaCA0023);

            //Datos que afectan al saldo acumulado
            LOGGER.info("Generar Datos que afectan al saldo acumulado");
            DatosCalculoIGV datosSaldoAcumuladoCA0023 = new DatosCalculoIGV();

            datosSaldoAcumuladoCA0023.setMtoCas108(valorCas105);
            datosSaldoAcumuladoCA0023.setMtoCas111(valorCas111);
            datosSaldoAcumuladoCA0023.setMtoCas131(valorCas131);

            dataMovimientoAcumPercReTCA0023.setDatosCalculoIGV(datosSaldoAcumuladoCA0023);
            //Datos de control y gestión
            LOGGER.info("Generar Datos de control y gestión");
            Control datosGestionControlCA0023 = new Control();

            datosGestionControlCA0023.setIndEstado("A");
            // datosGestionControlCA0023.setIndSaldoInconsistente(inddIncon);
            // datosGestionControlCA0021.setNumIdMovimientoOrigen(0);
            //  datosGestionControlCA0021.setNumIdMovimientoOrigen(0);
            dataMovimientoAcumPercReTCA0023.setControl(datosGestionControlCA0023);
            movimienoSA.setData(dataMovimientoAcumPercReTCA0023);

            movimientosSayyyymmModel.saveMovimientosSayyyymmInDynamicCollection(movimienoSA, Utils.generarNombreCollectionMovimientosSA());

        }

    }

//    private Coeficientes generarMovimientoCoeficiente(DatosVO<DataDJVO> dataJson){
//        return generarMovimiento(dataJson);
//    }
//    public Coeficientes generarMovimiento(DatosVO<DataDJVO> dataJson) {
//        Coeficientes coeficientes =  new Coeficientes();
//        Map<String, Object> sub_data = new HashMap<>();
//
//        // Metadata
//        MetadataCoef metadata = new MetadataCoef();
//        metadata.setNumIdEvento(dataJson.getMetadata().getNumIdEvento());
//        metadata.setNomTopico(dataJson.getMetadata().getNomTopico());
//        metadata.setFecEvento(dataJson.getMetadata().getFecEvento());
//        metadata.setDesTabla(dataJson.getMetadata().getDesTabla());
//        metadata.setIndOperacion(dataJson.getMetadata().getIndOperacion());
//        metadata.setNumVersionJSON(dataJson.getMetadata().getNumVersionJSON());
//        coeficientes.setMetadata(metadata);
//
//        // cabecera
//        CabeceraCoef cabecera = new CabeceraCoef();
//        cabecera.setNumIdEventoSaldo(dataJson.getCabecera().getNumIdEventoSaldo());
//        cabecera.setCodEventoSaldo(dataJson.getCabecera().getCodEventoSaldo());
//        cabecera.setFecEventoSaldo(dataJson.getCabecera().getFecEventoSaldo());
//        cabecera.setCodNemoEventoSaldo(dataJson.getCabecera().getCodNemoEventoSaldo());
//        cabecera.setPerDocumento(dataJson.getCabecera().getPerDocumento());
//        cabecera.setNumRuc(dataJson.getCabecera().getNumRuc());
//        cabecera.setCodTipoEvento(dataJson.getCabecera().getCodTipoEvento());
//        cabecera.setCodDocumento(dataJson.getCabecera().getCodDocumento());
//        cabecera.setNumDocumento(dataJson.getCabecera().getNumDocumento());
//        cabecera.setFecDocumento(dataJson.getCabecera().getFecDocumento());
//        cabecera.setCodDependencia(dataJson.getCabecera().getCodDependencia());
//        cabecera.setIndComponente(dataJson.getCabecera().getIndComponente());
//        coeficientes.setCabecera(cabecera);
//
//        // Data - contribuyente
//        Contribuyente contribuyente = new Contribuyente();
//        contribuyente.setNumRuc(dataJson.getData().getContribuyente().getNumRuc());
//        contribuyente.setCodDependencia(dataJson.getData().getContribuyente().getCodDependencia());
//        contribuyente.setCodRegimenAfecto(dataJson.getData().getContribuyente().getCodRegimenAfecto());
//        sub_data.put("contribuyente", contribuyente);
//        coeficientes.setData(sub_data);
//
//        //documentoSustento.
//        Documento documento = new Documento();
//
//        DocumentoSustentoCoef documentoSustento = new DocumentoSustentoCoef();
//        documentoSustento.setCodTributo(dataJson.getData().getDocumento().getCodTributo());
//        documentoSustento.setCodDocSustentoCoefPorcCalculado(null); //PREGUNTAR SI SE AGREGA EN EL JSON INICIAL
//        documentoSustento.setMunOrdenDocSustentoCoefCalculado(null);
//        documentoSustento.setPerTriDocSustentoCoefCalculado(null);
//        documentoSustento.setFecProcesoCalculoCoef(null);
//        sub_data.put("documentoSustento", documentoSustento);
//
//        //List<Casillas> lisSaldos= new ArrayList<>();
//        //sub_data.put("lisCasillas", lisCasillas);*/
//
//        // Auditoria
//        pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.general.Auditoria auditoria = new Auditoria();
//        auditoria.setCodUsuRegis("MLIBSR-coeficiente");
//        auditoria.setFecRegis("");
//        auditoria.setCodUsuModif("MLIBSR-coeficiente");
//        auditoria.setFecModif("");
//        coeficientes.setAuditoria(auditoria);
//        return coeficientes;
//    }

    @Override
    public void cus05GenerarMovimientosCA0016CA0018CA0019(Casilla castilla140D, double coeficienteDeterminado, CabeceraDJ cabeceraDJ, List<Casilla> casillas, double mtoPercepciones, double mtoRetenciones, String codEvent, String perTributario) {
        LOGGER.info("F2_491203_eIGVDJ621.docx");
        LOGGER.info("CUS08 – Generar movimientos de CA0016, CA0018 y CA0019 ");
        String tributo = "010101";
        String ruc = Optional.of(cabeceraDJ).map(CabeceraDJ::getNumRuc).orElse("");
        String periodo = Optional.of(cabeceraDJ).map(CabeceraDJ::getPerDocumento).orElse("");
        Date fecMov = Optional.of(cabeceraDJ).map(CabeceraDJ::getFecEventoSaldo).orElse(new Date());
        Casilla CAS184 = casillas.stream().filter(t -> Constantes.CAS184.contains(t.getNumCas())).toList().get(0);
        Casilla CAS106 = casillas.stream().filter(t -> Constantes.CAS106.contains(t.getNumCas())).toList().get(0);
        double montoAFavorCA0021 = 0;
        double MntoAFavorCA0016 = 0;
        double MntoAFavorCA0021Anterior = 0;
        double montoAFavorCA0016 = 0;
        double MntoAFavorCA0016Anterior = 0;

        List<String> casFiltro = Arrays.asList(Constantes.CAS105, Constantes.CAS108, Constantes.CAS111, Constantes.CAS131);
        LOGGER.info("Obtiene las siguientes casillas de la DJ 621");
        List<Casilla> saldo0024 = casillas.stream().filter(t -> casFiltro.contains(t.getNumCas())).toList();


        Casilla cas105 = saldo0024.get(0);
        Casilla cas108 = saldo0024.get(1);
        Casilla cas111 = saldo0024.get(2);
        Casilla cas131 = saldo0024.get(3);
        Double valorCas105 = Double.valueOf(cas105.getValCas());
        Double valorCas108 = Double.valueOf(cas108.getValCas());
        Double valorCas111 = Double.valueOf(cas111.getValCas());
        Double valorCas131 = Double.valueOf(cas131.getValCas());


        MovimientosSayyyymm movCA00016 = movimientosSayyyymmModel.obtenerUnSaldoCAalaFechMovInDynamicCollection(Constantes.CA0016, tributo, ruc, periodo, fecMov, "movimientosSa202311");
        MovimientosSayyyymm movCA00021Anterior = movimientosSayyyymmModel.obtenerUnSaldoCAalaFechMovInDynamicCollection(Constantes.CA0021, tributo, ruc, periodo, fecMov, "movimientosSa202311");
        if (movCA00016 == null) {
            MntoAFavorCA0016 = 0;
        } else {
            MntoAFavorCA0016 = movCA00016.getData().getDatosCuentaAfectada().getMtoAFavor();
        }
        if (movCA00021Anterior == null) {
            MntoAFavorCA0021Anterior = 0;
        } else {
            MntoAFavorCA0021Anterior = movCA00021Anterior.getData().getDatosCuentaAfectada().getMtoAFavor();
        }
        double creditoAcumulado = 0;
        double creditoUtilizado = 0;
        double creditoPorAplicar = 0;
        double montoMovimientoPeriodo = 0;
        double montoMovimiento = 0;
        double CA0021Nuevo = 0;
        double CA0016Nuevo = 0;
        LOGGER.info("CUS05 – Generar movimientos de CA0021");
        if (Double.valueOf(castilla140D.getValCas())==0.0) {
            LOGGER.info("Si el impuesto resultante o saldo a favor declarado (CAS140D) es cero");

            if (MntoAFavorCA0016 < 0) {
                //if (MntoAFavorCA0021Anterior < 0) {
                    //movCA00021Anterior.getData().getDatosCuentaAfectada().setMtoAFavor(Double.valueOf(0));
                    movCA00016.getData().getDatosCuentaAfectada().setMtoAFavor(Double.valueOf(0));
               // }
            }
            Double valor = Double.valueOf(castilla140D.getValCas()) - MntoAFavorCA0016Anterior;
            CAS184.setValCas(String.valueOf(valor));

            LOGGER.info("Generar movimientos para el Saldo de percepciones de periodos anteriores (CA0022)");
        } else if (Double.valueOf(CAS106.getValCas()) > 0) {
            LOGGER.info("Valida las exportaciones del contribuyente");
        } else {
            LOGGER.info("Genera movimiento para el Saldo a favor del periodo anterior del IGV (CA0016)");
            if (Double.valueOf(castilla140D.getValCas()) <= 0) {
                creditoAcumulado = Double.valueOf(castilla140D.getValCas()) * -1;
                double i = Double.valueOf(castilla140D.getValCas()) + MntoAFavorCA0016 * -1;
                CAS184.setValCas(String.valueOf(i));
            } else {
                CAS184.setValCas(String.valueOf(Double.valueOf(castilla140D.getValCas()) - MntoAFavorCA0016));
                if (Double.valueOf(castilla140D.getValCas()) >= MntoAFavorCA0016Anterior) {
                    creditoUtilizado = Double.valueOf(castilla140D.getValCas());
                } else {
                    creditoUtilizado = MntoAFavorCA0016Anterior;
                }

                if (Double.valueOf(castilla140D.getValCas()) >= MntoAFavorCA0016) {
                    creditoPorAplicar = MntoAFavorCA0016;
                } else {
                    creditoPorAplicar = Double.valueOf(castilla140D.getValCas());
                }
                if (creditoPorAplicar > 0) {
                    montoMovimientoPeriodo = creditoPorAplicar * -1;
                }
                montoMovimiento = creditoAcumulado + creditoUtilizado;
                CA0016Nuevo = MntoAFavorCA0016Anterior + montoMovimiento;
            }

            LOGGER.info("Generar el movimiento del saldo acumulado según la siguiente estructura");
            MovimientosSayyyymm movimientoAcumPercReTCA0016 = new MovimientosSayyyymm();
            Data dataMovimientoAcumPercReTCA0016 = new Data();
            Metadata meta = new Metadata();
            meta.setNumVersionJSON("1");
            //Metadata
            LOGGER.info("Generar Metadata");
            //Cabecera
            //LOGGER.info("Generar Cabecera");
            Cabecera cabeceraCA0016 = new Cabecera();
            cabeceraCA0016.setCodMovimiento(cabeceraDJ.getCodDocumento());
            cabeceraCA0016.setFecMovimiento(cabeceraDJ.getFecDocumento());
            cabeceraCA0016.setNumIdMovimiento(cabeceraDJ.getNumDocumento());
            cabeceraCA0016.setCodDependencia(cabeceraDJ.getCodDependencia());
            cabeceraCA0016.setNumRuc(cabeceraDJ.getNumRuc());
            cabeceraCA0016.setCodEvento(codEvent);
            cabeceraCA0016.setNumIdEventoSaldo("");
            cabeceraCA0016.setPerTributario("010101");
            movimientoAcumPercReTCA0016.setCabecera(cabeceraCA0016);


            //Datos del movimiento
            LOGGER.info("Generar Datos del documento Sustento");
            DatosDocSustento documentoSusCA0016 = new DatosDocSustento();
            documentoSusCA0016.setCodDocumento("0621");
            documentoSusCA0016.setCodTributo("010101");
            documentoSusCA0016.setNumDocumento(cabeceraDJ.getNumDocumento());
            documentoSusCA0016.setCodTipoDJ(Utils.obtenerTipoDJ(codEvent));
            documentoSusCA0016.setCodTipoRecti(Utils.obtenerTipoRetificatoria(codEvent));
            documentoSusCA0016.setPerTributario(cabeceraDJ.getPerDocumento());
            documentoSusCA0016.setCodTributo(Utils.obtenerTipoRetificatoria(codEvent));
            documentoSusCA0016.setIndTipoBP("");
            dataMovimientoAcumPercReTCA0016.setDatosDocSustento(documentoSusCA0016);

            LOGGER.info("Generar Datos de cuenta Afectada");
            DatosCuentaAfectada cuentaAfectadaCA0016 = new DatosCuentaAfectada();
            cuentaAfectadaCA0016.setNumCta(Constantes.CA0016);
            cuentaAfectadaCA0016.setMtoAFavor(montoAFavorCA0016);
            cuentaAfectadaCA0016.setMtoSaldoCtaALaFecha(Double.valueOf(castilla140D.getValCas()));
            dataMovimientoAcumPercReTCA0016.setDatosCuentaAfectada(cuentaAfectadaCA0016);

            //Datos que afectan al saldo acumulado
            LOGGER.info("Generar Datos que afectan al saldo acumulado");
            DatosCalculoIGV datosSaldoAcumuladoCA0016 = new DatosCalculoIGV();

            datosSaldoAcumuladoCA0016.setMtoCas108(valorCas105);
            datosSaldoAcumuladoCA0016.setMtoCas111(valorCas111);
            datosSaldoAcumuladoCA0016.setMtoCas131(valorCas131);
            datosSaldoAcumuladoCA0016.setMtoCas184(Double.valueOf(CAS184.getValCas()));
            datosSaldoAcumuladoCA0016.setMtoSaldoAnterior(MntoAFavorCA0021Anterior);
            datosSaldoAcumuladoCA0016.setMtoGenerado(creditoAcumulado);
            datosSaldoAcumuladoCA0016.setMtoUtilizado(creditoUtilizado);

            dataMovimientoAcumPercReTCA0016.setDatosCalculoIGV(datosSaldoAcumuladoCA0016);
            //Datos de control y gestión
            LOGGER.info("Generar Datos de control y gestión");
            Control datosGestionControlCA0016 = new Control();

            datosGestionControlCA0016.setIndEstado("A");
            // datosGestionControlCA0021.setNumIdMovimientoOrigen(0);
            //  datosGestionControlCA0021.setNumIdMovimientoOrigen(0);
            dataMovimientoAcumPercReTCA0016.setControl(datosGestionControlCA0016);
            movimientoAcumPercReTCA0016.setData(dataMovimientoAcumPercReTCA0016);

            movimientosSayyyymmModel.saveMovimientosSayyyymmInDynamicCollection(movimientoAcumPercReTCA0016, Utils.generarNombreCollectionMovimientosSA());
        }

        LOGGER.info("CUS05 – Generar movimientos de CA0018");
        MovimientosSayyyymm movCA00018Anterior = movimientosSayyyymmModel.obtenerUnSaldoCAalaFechMovInDynamicCollection(Constantes.CA0018, tributo, ruc, periodo, fecMov, "movimientosSa202310");


        Casilla CAS184DP = new Casilla();
        CAS184DP.setValCas("0");

        Casilla CAS171 = casillas.stream().filter(t -> Constantes.CAS171.contains(t.getNumCas())).toList().get(0);
        Casilla CAS168 = casillas.stream().filter(t -> Constantes.CAS168.contains(t.getNumCas())).toList().get(0);
        Casilla CAS178 = casillas.stream().filter(t -> Constantes.CAS178.contains(t.getNumCas())).toList().get(0);
        Casilla CAS184DPP = new Casilla();
        CAS184DPP.setNumCas(Constantes.CAS184DPP);
        double percepcionesPeriodo = mtoPercepciones;
        double percepcionesPeriodoUtilizado = 0;
        double percepcionesAcumulado = 0;
        double percepcionesPeriodosAnteriores = 0;
        double percepPAnterioresUtilizado = 0;
        montoMovimiento = 0;
        double MntoAFavorCA0018Anterior = 0;
        if (movCA00018Anterior == null) {
            MntoAFavorCA0018Anterior = 0;
        } else {
            MntoAFavorCA0018Anterior = movCA00018Anterior.getData().getDatosCuentaAfectada().getMtoAFavor();
        }
        if (MntoAFavorCA0018Anterior < 0) {
            movCA00018Anterior.getData().getDatosCuentaAfectada().setMtoAFavor(Double.valueOf(0));
        }
        LOGGER.info("Determinar las percepciones del periodo a acumular en la CA0018");
        percepcionesAcumulado = percepcionesPeriodo;
        if (Double.valueOf(CAS184.getValCas()) > 0) {
            LOGGER.info("Si el tributo a pagar o saldo a favor (CAS184) es menor o igual a cer8");
            CAS184.setValCas(CAS184DP.getValCas());
            LOGGER.info("No se genera el movimiento CA0018");
        } else {
            if (Double.valueOf(CAS184.getValCas()) > 0) {
                if (Double.valueOf(CAS171.getValCas())==0.0 && percepcionesPeriodo == 0) {
                    LOGGER.info("no existen percepciones del periodo a aplicar, por lo tanto, CAS184DPP=CAS184");
                    LOGGER.info("continua en el numeral 5.5 (percepciones de periodos anteriores).");
                } else if (Double.valueOf(CAS171.getValCas()) < percepcionesPeriodo) {
                    percepcionesPeriodo = Double.valueOf(CAS171.getValCas());
                } else if (Double.valueOf(CAS184.getValCas()) >= percepcionesPeriodo) {
                    percepcionesPeriodoUtilizado = percepcionesPeriodo;
                } else if (Double.valueOf(CAS184.getValCas()) < percepcionesPeriodo) {
                    percepcionesPeriodoUtilizado = Double.valueOf(CAS184.getValCas());
                }
                double resul = Double.valueOf(CAS184.getValCas()) - percepcionesPeriodoUtilizado;
                String valorCAS184 = String.valueOf(resul);

                CAS184DPP.setValCas(valorCAS184);
                if (Double.valueOf(CAS184DPP.getValCas())==0.0) {
                    LOGGER.info("asignar este valor al tributo a pagar o saldo a favor después de aplicadas las percepciones");

                    LOGGER.info("No se genera el movimiento CA0018");
                } else if (Double.valueOf(CAS184DPP.getValCas()) > 0) {
                    if (Double.valueOf(CAS168.getValCas())==0.0 && percepcionesPeriodo == 0) {
                        LOGGER.info("No existen percepciones de periodos anteriores a aplicar");
                        LOGGER.info("No se genera el movimiento CA0018");
                    } else {
                        if (Double.valueOf(CAS168.getValCas()) < percepcionesPeriodo) {
                            percepcionesPeriodosAnteriores = Double.valueOf(CAS168.getValCas());
                        } else {
                            percepcionesPeriodosAnteriores = percepcionesPeriodo;
                        }

                        if (Double.valueOf(CAS184DPP.getValCas()) >= percepcionesPeriodosAnteriores) {
                            percepPAnterioresUtilizado = percepcionesPeriodosAnteriores;
                        } else if (Double.valueOf(CAS184DPP.getValCas()) < percepcionesPeriodosAnteriores) {
                            percepPAnterioresUtilizado = Double.valueOf(CAS184DPP.getValCas());
                        }
                        double resultado = Double.valueOf(CAS184DPP.getValCas()) - percepPAnterioresUtilizado;
                        CAS184DP.setValCas(String.valueOf(resultado));
                        montoMovimiento = percepcionesAcumulado - percepcionesPeriodoUtilizado - percepPAnterioresUtilizado;
                        double CA0018Nuevo = MntoAFavorCA0018Anterior - montoMovimiento;

                        LOGGER.info("Gemerar el movimiento CA0018");
                        LOGGER.info("Generar el movimiento del saldo acumulado según la siguiente estructura");

                        LOGGER.info("Generar el movimiento del saldo acumulado según la siguiente estructura");
                        MovimientosSayyyymm movimientoAcumPercReTCA0018 = new MovimientosSayyyymm();
                        Data dataMovimientoAcumPercReTCA0018 = new Data();
                        Metadata meta = new Metadata();
                        meta.setNumVersionJSON("1");
                        //Metadata
                        LOGGER.info("Generar Metadata");
                        //Cabecera
                        //LOGGER.info("Generar Cabecera");
                        Cabecera cabeceraCA0018 = new Cabecera();
                        cabeceraCA0018.setCodMovimiento(cabeceraDJ.getCodDocumento());
                        cabeceraCA0018.setFecMovimiento(cabeceraDJ.getFecDocumento());
                        cabeceraCA0018.setNumIdMovimiento(cabeceraDJ.getNumDocumento());
                        cabeceraCA0018.setCodDependencia(cabeceraDJ.getCodDependencia());
                        cabeceraCA0018.setNumRuc(cabeceraDJ.getNumRuc());
                        cabeceraCA0018.setCodEvento(codEvent);
                        cabeceraCA0018.setNumIdEventoSaldo("");
                        cabeceraCA0018.setPerTributario("010101");
                        movimientoAcumPercReTCA0018.setCabecera(cabeceraCA0018);


                        //Datos del movimiento
                        LOGGER.info("Generar Datos del documento Sustento");
                        DatosDocSustento documentoSusCA0018 = new DatosDocSustento();
                        documentoSusCA0018.setCodDocumento("0621");
                        documentoSusCA0018.setCodTributo("010101");
                        documentoSusCA0018.setNumDocumento(cabeceraDJ.getNumDocumento());
                        documentoSusCA0018.setCodTipoDJ(Utils.obtenerTipoDJ(codEvent));
                        documentoSusCA0018.setCodTipoRecti(Utils.obtenerTipoRetificatoria(codEvent));
                        documentoSusCA0018.setPerTributario(cabeceraDJ.getPerDocumento());
                        documentoSusCA0018.setCodTributo(Utils.obtenerTipoRetificatoria(codEvent));
                        documentoSusCA0018.setIndTipoBP("");
                        dataMovimientoAcumPercReTCA0018.setDatosDocSustento(documentoSusCA0018);

                        LOGGER.info("Generar Datos de cuenta Afectada");
                        DatosCuentaAfectada cuentaAfectadaCA0018 = new DatosCuentaAfectada();
                        cuentaAfectadaCA0018.setNumCta(Constantes.CA0018);
                        cuentaAfectadaCA0018.setMtoAFavor(percepcionesPeriodo);
                        cuentaAfectadaCA0018.setMtoSaldoCtaALaFecha(Double.valueOf(castilla140D.getValCas()));
                        dataMovimientoAcumPercReTCA0018.setDatosCuentaAfectada(cuentaAfectadaCA0018);

                        //Datos que afectan al saldo acumulado
                        LOGGER.info("Generar Datos que afectan al saldo acumulado");
                        DatosCalculoIGV datosSaldoAcumuladoCA0018 = new DatosCalculoIGV();

                        datosSaldoAcumuladoCA0018.setMtoCas108(valorCas105);
                        datosSaldoAcumuladoCA0018.setMtoCas111(valorCas111);
                        datosSaldoAcumuladoCA0018.setMtoCas131(valorCas131);
                        datosSaldoAcumuladoCA0018.setMtoCas184(Double.valueOf(CAS184.getValCas()));
                        datosSaldoAcumuladoCA0018.setMtoCas184DP(Double.valueOf(CAS184DP.getValCas()));
                        datosSaldoAcumuladoCA0018.setMtoCas184DPP(Double.valueOf(CAS184DPP.getValCas()));
                        datosSaldoAcumuladoCA0018.setMtoSaldoAnterior(MntoAFavorCA0018Anterior);
                        datosSaldoAcumuladoCA0018.setMtoGenerado(percepcionesPeriodo);
                        datosSaldoAcumuladoCA0018.setMtoUtilizado(percepcionesPeriodoUtilizado);

                        dataMovimientoAcumPercReTCA0018.setDatosCalculoIGV(datosSaldoAcumuladoCA0018);
                        //Datos de control y gestión
                        LOGGER.info("Generar Datos de control y gestión");
                        Control datosGestionControlCA0018 = new Control();

                        datosGestionControlCA0018.setIndEstado("A");
                        // datosGestionControlCA0021.setNumIdMovimientoOrigen(0);
                        //  datosGestionControlCA0021.setNumIdMovimientoOrigen(0);
                        dataMovimientoAcumPercReTCA0018.setControl(datosGestionControlCA0018);
                        movimientoAcumPercReTCA0018.setData(dataMovimientoAcumPercReTCA0018);

                        movimientosSayyyymmModel.saveMovimientosSayyyymmInDynamicCollection(movimientoAcumPercReTCA0018, Utils.generarNombreCollectionMovimientosSA());

                    }
                }
            }
        }


        LOGGER.info("CUS08 – Generar movimientos de CA0019");
        tributo = "1011";
        MovimientosSayyyymm movCA00019Anterior = movimientosSayyyymmModel.obtenerUnSaldoCAalaFechMovInDynamicCollection(Constantes.CA0019, tributo, ruc, periodo, fecMov, "movimientosSa202310");
        double retencionesPeriodo = mtoRetenciones;
        Casilla CAS179 = casillas.stream().filter(t -> Constantes.CAS179.contains(t.getNumCas())).toList().get(0);
        Casilla CAS176 = casillas.stream().filter(t -> Constantes.CAS176.contains(t.getNumCas())).toList().get(0);
        Casilla CAS184DRP = new Casilla();
        CAS184DRP.setValCas(String.valueOf(retencionesPeriodo));
        CAS184DRP.setNumCas(Constantes.CAS184DRP);
        Casilla CAS184DR = new Casilla();
        CAS184DR.setValCas(String.valueOf(retencionesPeriodo));
        CAS184DR.setNumCas(Constantes.CAS184DR);
        double retencionesPeriodoAnteriores = 0;
        double retencionesPeriodoUtilizado = 0;
        double retencPAnterioresUtilizado = 0;
        LOGGER.info("Determinar las retenciones del periodo a acumular en el saldo CA0019. ");
        double retencionesAcumulado = mtoRetenciones;
        double MntoAFavorCA0019Anterior = 0;
        if (movCA00019Anterior == null) {
            MntoAFavorCA0019Anterior = 0;
        } else {
            MntoAFavorCA0019Anterior = movCA00019Anterior.getData().getDatosCuentaAfectada().getMtoAFavor();
        }

        if (Double.valueOf(CAS184DP.getValCas()) <= 0) {
            return;
        }


        if (Double.valueOf(CAS179.getValCas())==0.0 && percepcionesPeriodo == 0) {

        } else {
            if (Double.valueOf(CAS179.getValCas()) > retencionesPeriodo) {
                retencionesPeriodo = Double.valueOf(CAS179.getValCas());
            }

            if (Double.valueOf(CAS184DP.getValCas()) > retencionesPeriodo) {
                retencionesPeriodoUtilizado = retencionesPeriodo;
            } else {
                retencionesPeriodoUtilizado = Double.valueOf(CAS184DP.getValCas());
            }
            Double x = Double.valueOf(CAS184DP.getValCas()) - retencionesPeriodoUtilizado;
            CAS184DRP.setValCas(String.valueOf(x));
        }


        if (Double.valueOf(CAS184DP.getValCas())==0.0) {
            return;
        } else {
            if (Double.valueOf(CAS176.getValCas()).equals(MntoAFavorCA0019Anterior)) {
                retencionesPeriodoAnteriores = Double.valueOf(CAS176.getValCas());
            } else {
                retencionesPeriodoAnteriores = MntoAFavorCA0018Anterior;
            }

            if (Double.valueOf(CAS184DR.getValCas()) >= retencionesPeriodoAnteriores) {
                retencPAnterioresUtilizado = retencionesPeriodoAnteriores;
            }

            if (Double.valueOf(CAS184DRP.getValCas()) >= retencionesPeriodoAnteriores) {
                retencPAnterioresUtilizado = Double.valueOf(CAS184DRP.getValCas());
            }
            Double resultado2 = Double.valueOf(CAS184DRP.getValCas()) - retencPAnterioresUtilizado;
            CAS184DR.setValCas(String.valueOf(resultado2));
            LOGGER.info("Valor de CAS184DR " + resultado2);

            double montoMovimeinto = retencionesAcumulado - retencionesPeriodoUtilizado - retencPAnterioresUtilizado;
            LOGGER.info("Monto Movimiento CA0019 " + montoMovimeinto);

            double CA0019MtoAFavorNuevo = MntoAFavorCA0019Anterior + montoMovimeinto;

            LOGGER.info("CA0019 Nuevo " + CA0019MtoAFavorNuevo);

            LOGGER.info("CUS05 – Generar movimientos de retenciones de CA0019");
            LOGGER.info("Generar el movimiento del saldo acumulado según la siguiente estructura");
            MovimientosSayyyymm movimientoAcumPercReTCA0019 = new MovimientosSayyyymm();
            Data dataMovimientoAcumPercReTCA0019 = new Data();
            Metadata meta = new Metadata();
            meta.setNumVersionJSON("1");
            //Metadata
            //LOGGER.info("Generar Metadata");
            //Cabecera
           // LOGGER.info("Generar Cabecera");
            Cabecera cabeceraCA0019 = new Cabecera();
            cabeceraCA0019.setCodMovimiento(cabeceraDJ.getCodDocumento());
            cabeceraCA0019.setFecMovimiento(cabeceraDJ.getFecDocumento());
            cabeceraCA0019.setNumIdMovimiento(cabeceraDJ.getNumDocumento());
            cabeceraCA0019.setCodDependencia(cabeceraDJ.getCodDependencia());
            cabeceraCA0019.setNumRuc(cabeceraDJ.getNumRuc());
            cabeceraCA0019.setCodEvento(codEvent);
            cabeceraCA0019.setNumIdEventoSaldo("");
            cabeceraCA0019.setPerTributario("010101");
            movimientoAcumPercReTCA0019.setCabecera(cabeceraCA0019);


            //Datos del movimiento
            LOGGER.info("Generar Datos del documento Sustento");
            DatosDocSustento documentoSusCA0019 = new DatosDocSustento();
            documentoSusCA0019.setCodDocumento("0621");
            documentoSusCA0019.setCodTributo("010101");
            documentoSusCA0019.setNumDocumento(cabeceraDJ.getNumDocumento());
            documentoSusCA0019.setCodTipoDJ(Utils.obtenerTipoDJ(codEvent));
            documentoSusCA0019.setCodTipoRecti(Utils.obtenerTipoRetificatoria(codEvent));
            documentoSusCA0019.setPerTributario(cabeceraDJ.getPerDocumento());
            documentoSusCA0019.setCodTributo(Utils.obtenerTipoRetificatoria(codEvent));
            documentoSusCA0019.setIndTipoBP("");
            dataMovimientoAcumPercReTCA0019.setDatosDocSustento(documentoSusCA0019);

            LOGGER.info("Generar Datos de cuenta Afectada");
            DatosCuentaAfectada cuentaAfectadaCA0019 = new DatosCuentaAfectada();
            cuentaAfectadaCA0019.setNumCta(Constantes.CA0019);
            cuentaAfectadaCA0019.setMtoAFavor(percepcionesPeriodo);
            cuentaAfectadaCA0019.setMtoSaldoCtaALaFecha(Double.valueOf(castilla140D.getValCas()));
            dataMovimientoAcumPercReTCA0019.setDatosCuentaAfectada(cuentaAfectadaCA0019);

            //Datos que afectan al saldo acumulado
            LOGGER.info("Generar Datos que afectan al saldo acumulado");
            DatosCalculoIGV datosSaldoAcumuladoCA0019 = new DatosCalculoIGV();

            datosSaldoAcumuladoCA0019.setMtoCas108(valorCas105);
            datosSaldoAcumuladoCA0019.setMtoCas111(valorCas111);
            datosSaldoAcumuladoCA0019.setMtoCas131(valorCas131);
            datosSaldoAcumuladoCA0019.setMtoCas184(Double.valueOf(CAS184.getValCas()));
            datosSaldoAcumuladoCA0019.setMtoCas184DP(Double.valueOf(CAS184DP.getValCas()));
            datosSaldoAcumuladoCA0019.setMtoCas184DPP(Double.valueOf(CAS184DPP.getValCas()));
            datosSaldoAcumuladoCA0019.setMtoSaldoAnterior(MntoAFavorCA0019Anterior);
            datosSaldoAcumuladoCA0019.setMtoGenerado(percepcionesPeriodo);
            datosSaldoAcumuladoCA0019.setMtoUtilizado(percepcionesPeriodoUtilizado);

            dataMovimientoAcumPercReTCA0019.setDatosCalculoIGV(datosSaldoAcumuladoCA0019);
            //Datos de control y gestión
            LOGGER.info("Generar Datos de control y gestión");
            Control datosGestionControlCA0019 = new Control();

            datosGestionControlCA0019.setIndEstado("A");
            // datosGestionControlCA0021.setNumIdMovimientoOrigen(0);
            //  datosGestionControlCA0021.setNumIdMovimientoOrigen(0);
            dataMovimientoAcumPercReTCA0019.setControl(datosGestionControlCA0019);
            movimientoAcumPercReTCA0019.setData(dataMovimientoAcumPercReTCA0019);

            movimientosSayyyymmModel.saveMovimientosSayyyymmInDynamicCollection(movimientoAcumPercReTCA0019, Utils.generarNombreCollectionMovimientosSA());


        }

        //   cus10AlmacenarActualizarSaldos(LOG);




    }


}
