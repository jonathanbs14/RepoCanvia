package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.utils;

import java.time.YearMonth;
import java.util.Date;

public class ComparaFechasUtil {

    public static boolean validaRangoFechasSA (Date fechaInicio, Date fechaFin, Date fechaComparar) {
        return fechaComparar.after(fechaInicio) && fechaComparar.before(fechaFin);
    }

    public static boolean validaInicioFechasSA (Date fechaInicio, Date fechaComparar) {
        return fechaComparar.after(fechaInicio);
    }

}
