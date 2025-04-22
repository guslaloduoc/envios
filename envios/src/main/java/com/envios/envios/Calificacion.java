package com.envios.envios;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "CALIFICACIONES") // Nombre de la tabla en Oracle
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Calificacion {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "calificacion_seq_generator")
	@SequenceGenerator(name = "calificacion_seq_generator", sequenceName = "CALIFICACIONES_SEQ", allocationSize = 1)
	@Column(name = "ID_CALIFICACION")
	private Long id;

	@Column(name = "VALOR", nullable = false)
	private Integer valor;

	@Column(name = "FECHA_CREACION", nullable = false)
	private LocalDateTime fechaCreacion;

	@Column(name = "AUTOR", length = 100)
	private String autor;

	// Relación Many-to-One con Publicacion
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PUBLICACION", nullable = false) // Columna FK en la tabla CALIFICACIONES
	private Publicacion publicacion; // El campo que apunta a la Publicacion calificada

	// Validación básica a nivel de entidad (opcional aquí, pero más común en DTOs)
	// @Min(1) @Max(5) // Requiere dependencia de validación
	// private Integer valor;
}