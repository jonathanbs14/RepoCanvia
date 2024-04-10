package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.service;

import org.slf4j.Logger;

import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto.CabeceraDJ;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto.DatosVO;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.DataDJVO;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general.Casilla;

import java.util.Date;
import java.util.List;

public interface OperacionMovimientoService {
    //void cus03GenerarSaldosAcumulado(DatosVO<DataDJVO> dataJson, List<Casilla> casillas, double coeficienteDeterminad, String periodoDj, CabeceraDJ cabeceraDJ,double dividendo, double divisor);
    double cus03GenerarSaldosAcumulado(DatosVO<DataDJVO> dataJson, List<Casilla> casillas, double coeficienteDeterminad, String periodoDj, CabeceraDJ cabeceraDJ, double dividendo, double divisor);


    Casilla   cus04CalcularTributoPagar(Logger LOG,List<Casilla> casillas,double coeficienteDeclarado);

    void cus05GenerarMovimientosCA0016CA0018CA0019(Casilla castilla140D, double coeficienteDeterminado, CabeceraDJ cabeceraDJ, List<Casilla> casillas , double mtoPercepciones , double mtoRetenciones,String codEvent,String perTributario);


    double cus06GenerarSaldosAcumulado(DatosVO<DataDJVO> dataJson, List<Casilla> casillas, double coeficienteDeterminad, String periodoDj, CabeceraDJ cabeceraDJ, double dividendo, double divisor);

    Casilla cus07CalcularImpuestoResultante(Logger LOG,List<Casilla> casillas,double coeficienteDeclarado);

    void cus08GenerarMovimientosCA0021CA0022CA0023(Casilla castilla140D, double coeficienteDeclarad, CabeceraDJ cabeceraDJ, List<Casilla> casillas , double mtoPercepciones , double mtoRetenciones,String codEvent,String perTributario);

    void cus09GenerarSaldosAcumulado(DatosVO<DataDJVO> dataJson, String numRuc, Date fecMovimiento, String perTributari,double mtoRetenciones);

    void cus10AlmacenarActualizarSaldos(String codCuenta);

    void cus11RegistrarEvento(DatosVO<DataDJVO> dataJson);
    void cus12GenerarMovimientoCierre( DatosVO<DataDJVO> dataJson);
}
