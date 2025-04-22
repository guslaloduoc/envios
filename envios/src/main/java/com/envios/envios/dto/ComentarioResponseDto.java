package com.envios.envios.dto; // En la carpeta dto

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioResponseDto {

    private Long id; // ID del comentario
    private String contenido;
    private LocalDateTime fechaCreacion;
    private String autor;
    private Long publicacionId; // ID de la publicación a la que pertenece
}