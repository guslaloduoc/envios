package com.envios.envios.service; // En el paquete service

import com.envios.envios.dto.ComentarioRequestDto;
import com.envios.envios.dto.ComentarioResponseDto;

import java.util.List;

public interface ComentarioService {

    // Crear un comentario para una publicación específica
    ComentarioResponseDto createComentario(ComentarioRequestDto comentarioRequestDto);

    // Obtener todos los comentarios de una publicación
    List<ComentarioResponseDto> getComentariosByPublicacionId(Long publicacionId);

    // Obtener un comentario por su propio ID
    ComentarioResponseDto getComentarioById(Long id);

    // Actualizar un comentario (por su ID)
    ComentarioResponseDto updateComentario(Long id, ComentarioRequestDto comentarioRequestDto);

    // Eliminar un comentario (por su ID)
    void deleteComentario(Long id);
}