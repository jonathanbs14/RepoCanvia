package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.model.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.Coeficientes;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.DataMovimientoDJ621;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.MovimientoAcumPercReT;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.model.MovimientoAcumPercReTModel;

@Repository
public class MovimientoAcumPercReTModelImpl implements MovimientoAcumPercReTModel {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MovimientoAcumPercReTModelImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void saveMovimientoAcumPercReT(MovimientoAcumPercReT movimientoAcumPercReT, String collectionName) {
        mongoTemplate.save(movimientoAcumPercReT,collectionName);
    }
    public void saveMovimientoDJ621(DataMovimientoDJ621 dataMovimientoDJ621, String collectionName) {
        mongoTemplate.save(dataMovimientoDJ621,collectionName);
    }

    @Override
    public void saveMovimientoCoeficientes(Coeficientes coeficientes, String collectionName) {
        mongoTemplate.save(coeficientes,collectionName);
    }

}
