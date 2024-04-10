package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MovDJUtil {
    public static boolean validarTipoOperacion(String tipoEvento, List<String> listPermitidos) {
        return listPermitidos.contains(tipoEvento);
    }
    public static String dateToStringFormat(Date date, String format) {
        try {
            Format formatter = new SimpleDateFormat(format);
            return formatter.format(date);
        } catch (Exception e) {
            return null;
        }
    }
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof String) {
            return object.toString().trim().isEmpty();
        }
        if (object instanceof StringBuilder) {
            return object.toString().trim().isEmpty();
        }
        if (object instanceof List<?>) {
            return ((List<?>) object).isEmpty();
        }
        if (object instanceof Map<?, ?>) {
            return ((Map<?, ?>) object).isEmpty();
        }
        return false;
    }
}
