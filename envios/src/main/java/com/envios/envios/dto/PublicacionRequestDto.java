package com.envios.envios.dto; // Asegúrate que el paquete sea correcto

import jakarta.validation.constraints.NotBlank; // Importar anotaciones de validación
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Genera getters, setters, equals, hashCode, toString
@NoArgsConstructor // Genera constructor sin argumentos
@AllArgsConstructor // Genera constructor con todos los argumentos
public class PublicacionRequestDto {

    @NotBlank(message = "El título no puede estar vacío") // Validación: campo requerido y no solo espacios en blanco
    @Size(max = 255, message = "El título no puede exceder los 255 caracteres") // Validación: tamaño máximo
    private String titulo;

    @NotBlank(message = "El contenido no puede estar vacío")
    private String contenido; // Puedes añadir @Size si quieres un límite de caracteres para el contenido

    @Size(max = 100, message = "El autor no puede exceder los 100 caracteres")
    private String autor; // Este campo es opcional en la DB, pero si se envía, validar tamaño
}