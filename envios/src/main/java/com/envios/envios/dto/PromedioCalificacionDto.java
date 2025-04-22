package com.envios.envios.dto; // Asegúrate que el paquete sea correcto

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Genera getters, setters, equals, hashCode, toString
@NoArgsConstructor // Genera constructor sin argumentos
@AllArgsConstructor // Genera constructor con todos los argumentos
public class PromedioCalificacionDto {

    private Double promedio; // El valor del promedio
}