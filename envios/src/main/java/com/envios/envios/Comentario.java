package com.envios.envios; // Ajusta el paquete según tu estructura

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "COMENTARIOS") // Nombre de la tabla en Oracle
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comentario {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comentario_seq_generator")
	@SequenceGenerator(name = "comentario_seq_generator", sequenceName = "COMENTARIOS_SEQ", allocationSize = 1)
	@Column(name = "ID_COMENTARIO")
	private Long id;

	@Column(name = "CONTENIDO", nullable = false, length = 1000) // Longitud adecuada para un comentario
	private String contenido;

	@Column(name = "FECHA_CREACION", nullable = false)
	private LocalDateTime fechaCreacion;

	@Column(name = "AUTOR", length = 100) // Opcional
	private String autor;

	// Relación Many-to-One con Publicacion
	// FetchType.LAZY es crucial aquí para evitar problemas N+1
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PUBLICACION", nullable = false) // Columna FK en la tabla COMENTARIOS
	private Publicacion publicacion; // El campo que apunta a la Publicacion a la que pertenece

}