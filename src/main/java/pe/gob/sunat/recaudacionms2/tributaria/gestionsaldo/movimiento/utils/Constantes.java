package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.utils;

import java.util.Arrays;
import java.util.List;

public class Constantes {


    /*----------------------------------------------------------------------------------------
    CONSTANTES DEL DJ
    -----------------------------------------------------------------------------------------*/
    public static final String CIERRE = "Cierre";
    public static final String djOriginal = "Evt015";
    public static final String djSustitutoria = "Evt021";
    public static final String djRectificatoria = "Evt027";

    public static final String Original = "Original";
    public static final String Sustitutoria = "Sustitutoria";

    public static  final  String ConVeredicto = "Con veredicto";
    public static  final  String SinVeredicto = "Sin veredicto";
    public static final String Rectificatoria = "Rectificatoria";
    public static final List<String> djPermitidos = Arrays.asList(djSustitutoria,djRectificatoria);
    public static final String CA0020 = "CA0020";
    public static final String CA0016 = "CA0016";
    public static final String CA0018  = "CA0018";
    public static final String CA0019   = "CA0019";
    public static final String CA0024   = "CA0024";
    public static final String CA0021   = "CA0021";
    public static final String CA0022   = "CA0022";
    public static final String CA0023   = "CA0023";
    public static final String CA0017   = "CA0017";
    // casillas que afectan al saldo CA0020
    public static final String CAS100   = "100";
    public static final String CAS160   = "160";
    public static final String CAS154   = "154";
    public static final String CAS105   = "105";
    public static final String CAS106   = "106";
    public static final String CAS102   = "102";
    public static final String CAS162   = "162";

    public static final String CAS131   = "131";
    public static final String CAS108   = "108";
    public static final String CAS111   = "111";
    public static final String CAS140D   = "140D";
    public static final String CAS184DP   = "184DP";

    public static final String CAS184DPP   = "184DPP";
    public static final String CAS184DRP   = "184DRP";
    public static final String CAS184DR   = "184DR";
    public static final String CAS184  = "184";
    public static final String CAS171  = "171";
    public static final String CAS168   = "168";

    public static final String CAS178   = "178";
    public static final String CAS179   = "179";
    public static final String CAS176   = "176";

    /*----------------------------------------------------------------------------------------
    CONSTANTES DE NOMBRE DE COLECCION
     -----------------------------------------------------------------------------------------*/
    public static final String NOMBRE_BASE_COLECCION = "movimientosSa";

    /*----------------------------------------------------------------------------------------
    CODIGOS O FILTROS DE PARAMETRIA
    ------------------------------------------------------------------------------------------ */
    public static final String COD_ETAPA = "02";
    public static final String COD_SEGUIMIENTO_EN_SALDO_IGV = "0003";
    public static final String DES_SEGUIMIENTO = "En movimiento IGV";
    public static final String ID_COMPONENTE_ERROR = "0";
    public static final String ID_CODRESULTADO_ERROR = "0";
    public static final String ID_CODRESULTADO_EXITOSO = "1";
    public static final String ID_COD_SERVICIO_MONGO = "mdb";
    public static final String ID_COD_SERVICIO_INFORMIX = "ifx";
    public static final String ID_COD_SERVICIO_KAFKA = "kfk";
    public static final String ID_COD_SERVICIO_EVENTO = "evt";
    public static final String ID_COD_SERVICIO_API = "api";
    public static final String ID_COD_ETAPA_MOVIMIENTO = "02";
    public static final String ID_COD_COMPONENTE_RENTA = "1";
    public static final String IND_EXTORNO_0038 = "E";
    public static final String IND_ANULACION_0038 = "A";

    /*----------------------------------------------------------------------------------------
     codigos respuestas seguimiento
     ------------------------------------------------------------------------------------------*/

    public static final String CODIGO_RESPUESTA_EXITOSO = "0";
    public static final String CODIGO_RESPUESTA_ERROR = "1";






    /*----------------------------------------------------------------------------------------
    CODIGO DE PARAMETROS PARA REALIZAR BUSQUEDA  EN LA COLECCION PARAMETRIA Y OBTNER LAS FECHAS
    ------------------------------------------------------------------------------------------ */
    public static final String COD_PARAMETRO_0017 = "0017";
    public static final String COD_PARAMETRO_0010 = "0010";
    public static final String COD_PARAMETRO_0038 = "0038";
    public static final String COD_INDICADOR_0038 = "A";
    //public static final String COD_PARAMETRO_0012 = "0012";

    public static final String COD_DOCUMENTO_0013 = "9005";

    /*----------------------------------------------------------------------------------------
     REDIS
    ------------------------------------------------------------------------------------------ */
    public static Integer MINUTOS_DURACION_REDIS_DEFAULT = 60;
    public static Integer MINUTOS_DURACION_REDIS_UN_DIA = 60 * 24;
    public static final String PARAMETRIA_BASE_0010 = "parametria/eventos/0010";
    public static final String PARAMETRIA_BASE_0020 = "parametria/estadosseguimiento/0020";
    public static final String PARAMETRIA_BASE_0061 = "parametria/diasveredicto/0061";

    /*----------------------------------------------------------------------------------------
 CODIGOS DE COMPONENTE :-> 0-Todos, 1-Renta, 2-IGV
 ------------------------------------------------------------------------------------------*/
    public static final String ID_COMPONENTE_TODOS = "0";
    public static final String ID_COMPONENTE_RENTA = "1";
    public static final String ID_COMPONENTE_IGV = "2";
    /*----------------------------------------------------------------------------------------
     codigos respuestas seguimiento
     ------------------------------------------------------------------------------------------*/



    /*----------------------------------------------------------------------------------------
    CONSTANTES MOVIMIENTOSSA
    -----------------------------------------------------------------------------------------*/
    public static String NOMBRE_TIPO_MOVIMIENTO_ACTIVO = "A";
    public static String NOMBRE_TIPO_MOVIMIENTO_CIERRE = "C";
    public static String NOMBRE_TIPO_MOVIMIENTO_INACTIVO = "I";
    public static String IND_SALDO_CONSISTENTE = "0";
    public static String IND_SALDO_INCONSISTENTE = "1";

    /*----------------------------------------------------------------------------------------
    CONSTANTES DE NOMBRE DE COLECCION
     -----------------------------------------------------------------------------------------*/

    /*----------------------------------------------------------------------------------------
    CONSTANTES DE CUENTAS AFECTADAS
    -----------------------------------------------------------------------------------------*/

    public static final String ESTADO_ACTIVO = "Activo";
    public static final String ESTADO_A = "A";
    public static final String ESTADO_INACTIVO = "Inactivo";
    public static final String ESTADO_I = "I";
    public static final String ESTADO_CIERRE = "Cierre";
    public static List<String> LIST_ESTADO_ACTIVO = Arrays.asList("Activo", "A", "ACTIVO");
    public static List<String> LIST_ESTADO_INACTIVO = Arrays.asList("Inactivo", "I", "INACTIVO");
    //public static final String ESTADOS = ESTADO_ACTIVO + ",A" + "," + ESTADO_INACTIVO + ",I";

    public static List<String> LIST_ESTADOS = Arrays.asList(ESTADO_ACTIVO, "A", ESTADO_INACTIVO, "I");
    public static final String IND_EXTORNO_ANULACION = "E";//E=Extorno
    public static final String COD_MOVIMIENTO = "mIGV030.01";

    public static String TIPO_IGV_041 = "41";
    public static String TIPO_IGV_042 = "42";
    public static String MENSAJE_ENVIO_CUARENTENA = "Evento de aplicación de reimputación por solicitud de pagos con error aprobada pasa a Cuarentena";
    public static String MENSAJE_GENERANDO_MOVIMIENTOS_IGV = "Evento ingresa al ModeloSA. Generando Movimientos";
    public static String COD_MOVIMIENTO_CIERRE_IGV041 = "mIGV041.01";
    public static String COD_MOVIMIENTO_REGISTRO_IGV041 = "mIGV041.02";
    public static String COD_MOVIMIENTO_CIERRE_IGV042 = "mIGV042.01";
    public static String COD_MOVIMIENTO_REGISTRO_IGV042 = "mIGV042.02";

    public static String COD_MOVIMIENTO_CIERRE_IGV033 = "mIGV033.01";
    public static String COD_MOVIMIENTO_REGISTRO_IGV033 = "mIGV033.02";
    public static String COD_MOVIMIENTO_CIERRE_IGV039 = "mIGV039.01";
    public static String COD_MOVIMIENTO_REGISTRO_IGV039 = "mIGV039.02";
    public static final String DES_MOVIMIENTO = "Extorno de Pago";
    /*----------------------------------------------------------------------------------------
    CONSTANTES DE FECHAS
    -----------------------------------------------------------------------------------------*/
    public static final String ZONA_HORARIA = "GMT-5:00";
    public static final String MMyyyySlash = "MM/yyyy";
    public static final String ddMMyyyyHHmmss = "dd-MM-yyyy HH:mm:ss";
    public static final String ddMMyyyyHHmmssv2 = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String yyyyMMddTHHmmssSSSXXX = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    public static final String yyyyMMddTHHmmssSSSSS = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS";
    public static final String yyyyMM = "yyyyMM";
    public static final String ddMMyyyyv2 = "yyyy-MM-dd";
    /*----------------------------------------------------------------------------------------
    CONSTANTES DE FORMULARIOS DE LAS BOLETAS DE PAGO
    -----------------------------------------------------------------------------------------*/
    public static final String COD_TRIBUTO_IGV_010101 = "010101";
    public static final String COD_TRIBUTO_IGV_010108 = "010108";
    public static List<String> COD_TRIBUTO_IGV_DESEADOS = Arrays.asList("010101", "0101018");
    public static final String COD_FORM_BP_1052 = "1052";//data.codDocumento//del Json de entrada
    public static final String COD_FORM_BP_1252 = "1252";
    public static final List<String> COD_FORM_BP_1662_1672 = Arrays.asList("1662", "1672");
    public static final List<String> COD_FORM_BP_1663_1671 = Arrays.asList("1663", "1671");
    public static final String COD_FORM_BP_1260 = "1260";
    public static final List<String> COD_FORM_BP_1660_1661_1668 = Arrays.asList("1660", "1661", "1668");
    public static final List<String> COD_CASILLAS_OPCIONALES_016_017 = Arrays.asList("016", "017");











}
