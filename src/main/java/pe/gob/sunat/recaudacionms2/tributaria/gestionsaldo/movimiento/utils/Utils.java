package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.utils;

import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general.Documento;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class Utils {

    public static String obtenerFechaMovimiento(String codEvent, Documento documento) { //pendiente
        String fecha = null;
        switch (codEvent) {
            case Constantes.djOriginal:
                fecha = documento.getFecDocumento().toString();
                break;
            case Constantes.djSustitutoria:

                break;
            case Constantes.djRectificatoria:

                break;
            default:
                fecha = "";
        }
        return fecha;
    }


    public static String obtenerTipoDJ(String codEvent){
        String dj = null;
        switch (codEvent) {
            case Constantes.djOriginal:
                dj = Constantes.Original;
                break;
            case Constantes.djSustitutoria:
                dj = Constantes.Sustitutoria;
                break;
            case Constantes.djRectificatoria:
                dj = Constantes.Rectificatoria;
                break;
            default:

        }


        return dj;
    }

    public static String obtenerTipoRetificatoria(String codEvent){
        String tipoR = null;
        switch (codEvent) {
            case Constantes.djRectificatoria:
                tipoR = Constantes.ConVeredicto;
                break;
            default:
                tipoR = "";

        }


        return tipoR;
    }


    public static String generarNombreCollectionMovimientosSA(){
        String nombre  = "";

        SimpleDateFormat format = new SimpleDateFormat(FormatoFecha.yyyymm);
        nombre= format.format(new Date());
        return "movimientosSa"+nombre;
    }

    public static String generarNombreCollectionCoeficientes(){
        String nombre  = "";

        SimpleDateFormat format = new SimpleDateFormat(FormatoFecha.yyyymm);
        nombre= format.format(new Date());
        return "coeficientes";
    }
}
