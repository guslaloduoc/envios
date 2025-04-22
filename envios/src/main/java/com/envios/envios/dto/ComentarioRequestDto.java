package com.envios.envios.dto; // En la carpeta dto

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; // Importar para validar publicacionId
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioRequestDto {

    @NotBlank(message = "El contenido del comentario no puede estar vacío")
    @Size(max = 1000, message = "El contenido del comentario no puede exceder los 1000 caracteres")
    private String contenido;

    @Size(max = 100, message = "El autor del comentario no puede exceder los 100 caracteres")
    private String autor; // Opcional

    @NotNull(message = "Se debe especificar el ID de la publicación")
    private Long publicacionId; // Para saber a qué publicación pertenece
}