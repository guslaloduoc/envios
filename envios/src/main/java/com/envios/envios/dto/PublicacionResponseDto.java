package com.envios.envios.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import org.springframework.hateoas.RepresentationModel;

@Data // Genera getters, setters, equals, hashCode, toString
@NoArgsConstructor // Genera constructor sin argumentos
@AllArgsConstructor // Genera constructor con todos los argumentos
public class PublicacionResponseDto extends RepresentationModel<PublicacionResponseDto> {

    private Long id;
    private String titulo;
    private String contenido;
    private LocalDateTime fechaCreacion;
    private String autor;

}