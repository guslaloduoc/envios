package com.envios.envios.dto; // En la carpeta dto

import jakarta.validation.constraints.Max; // Importar para validación de rango
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalificacionRequestDto {

    @NotNull(message = "El valor de la calificación no puede ser nulo")
    @Min(value = 1, message = "El valor de la calificación debe ser al menos 1") // Validación de rango
    @Max(value = 5, message = "El valor de la calificación no puede exceder de 5") // Validación de rango
    private Integer valor; // Por ejemplo, un valor entero entre 1 y 5

    // @Size(max = 100, message = "El autor de la calificación no puede exceder los
    // 100 caracteres")
    private String autor; // Opcional

    @NotNull(message = "Se debe especificar el ID de la publicación")
    private Long publicacionId; // Para saber a qué publicación pertenece
}