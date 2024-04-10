package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.producer.dj;

import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.MovimientosSayyyymm;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto.DatosVO;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.DataDJVO;
//import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.noconte.DataNoConteVO;

public class ProducerDataSaVO {

    public static DataSaVO getMensajeDataSa(DatosVO<DataDJVO> dataJson) {
        DataSaVO dataSaVO = new DataSaVO();
        dataSaVO.setMetadata(dataJson.getMetadata());
        dataSaVO.setCabecera(dataJson.getCabecera());
        Data data = new Data();
        dataSaVO.setData(data);
        return dataSaVO;
    }

    public static Movimientos getMovimientos(MovimientosSayyyymm movimientosSayyyymm) {
        Movimientos movimientos = new Movimientos();
        movimientos.setCabecera(movimientosSayyyymm.getCabecera());
        DataMovimientos dataMovimientos = new DataMovimientos();
        DatosCuentaAfectada datosCuentaAfectada = new DatosCuentaAfectada();
        datosCuentaAfectada.setNumCta(movimientosSayyyymm.getData().getDatosCuentaAfectada().getNumCta());
        datosCuentaAfectada.setMtoSaldoInicial(0.0);
        datosCuentaAfectada.setMtoSaldoActual(movimientosSayyyymm.getData().getControl().getMtoSaldoDespMov());
        datosCuentaAfectada.setIndInconsistencia("0");
        dataMovimientos.setDatosCuentaAfectada(datosCuentaAfectada);
        movimientos.setData(dataMovimientos);
        return movimientos;
    }

}
