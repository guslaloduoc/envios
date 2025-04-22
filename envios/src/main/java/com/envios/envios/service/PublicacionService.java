package com.envios.envios.service;

import com.envios.envios.dto.PromedioCalificacionDto;
import com.envios.envios.dto.PublicacionRequestDto;
import com.envios.envios.dto.PublicacionResponseDto;

import java.util.List;

public interface PublicacionService {

    // Método para crear una nueva publicación
    PublicacionResponseDto createPublicacion(PublicacionRequestDto publicacionRequestDto);

    // Método para obtener todas las publicaciones
    List<PublicacionResponseDto> getAllPublicaciones();

    // Método para obtener una publicación por su ID
    PublicacionResponseDto getPublicacionById(Long id);

    // Método para actualizar una publicación existente
    PublicacionResponseDto updatePublicacion(Long id, PublicacionRequestDto publicacionRequestDto);

    // Método para eliminar una publicación por su ID
    void deletePublicacion(Long id);

    // Método para obtener el promedio de calificación de una publicación específica
    PromedioCalificacionDto getPromedioCalificacion(Long id);

}