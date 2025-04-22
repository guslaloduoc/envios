package com.envios.envios.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalificacionResponseDto {

    private Long id;
    private Integer valor;
    private LocalDateTime fechaCreacion;
    private String autor;
    private Long publicacionId;
}