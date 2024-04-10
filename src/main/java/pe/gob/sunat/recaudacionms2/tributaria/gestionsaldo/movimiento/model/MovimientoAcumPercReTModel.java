package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.model;

import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.Coeficientes;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.MovimientoAcumPercReT;

public interface MovimientoAcumPercReTModel {

    public void saveMovimientoAcumPercReT(MovimientoAcumPercReT movimientoAcumPercReT, String collectionName);

    public void saveMovimientoCoeficientes(Coeficientes coeficiente, String collectionName);


}
