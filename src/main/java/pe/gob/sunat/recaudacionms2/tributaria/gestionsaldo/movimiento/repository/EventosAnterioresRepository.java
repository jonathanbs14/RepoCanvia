package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto.EventosAnteriores;


import java.util.List;

@Repository
public interface EventosAnterioresRepository extends MongoRepository<EventosAnteriores, String> {
    @Query("{ 'cabecera.numRuc' : ?0, 'cabecera.getCodDocumento' : ?1, 'cabecera.getNumDocumento' : ?2 }")
    List<EventosAnteriores> findByNumRucNumForCodOrd(String numRuc, String numOrd, String codFor);

}
