package com.envios.envios.service; // En el paquete service

import com.envios.envios.dto.CalificacionRequestDto;
import com.envios.envios.dto.CalificacionResponseDto;

import java.util.List;

public interface CalificacionService {

    // Crear una calificación para una publicación específica
    CalificacionResponseDto createCalificacion(CalificacionRequestDto calificacionRequestDto);

    // Obtener todas las calificaciones de una publicación
    List<CalificacionResponseDto> getCalificacionesByPublicacionId(Long publicacionId);

    // Obtener una calificación por su propio ID
    CalificacionResponseDto getCalificacionById(Long id);

    void deleteCalificacion(Long id);
}