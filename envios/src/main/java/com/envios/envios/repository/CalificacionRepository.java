package com.envios.envios.repository;

import com.envios.envios.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    // Método para encontrar calificaciones por el ID de la publicación a la que
    List<Calificacion> findByPublicacionId(Long publicacionId);

    // Método para calcular el promedio de calificación para una publicación
    @Query("SELECT AVG(c.valor) FROM Calificacion c WHERE c.publicacion.id = :publicacionId")
    Double findAverageValorByPublicacionId(@Param("publicacionId") Long publicacionId);
}