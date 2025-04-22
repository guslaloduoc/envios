package com.envios.envios;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PUBLICACIONES") // Nombre de la tabla en Oracle
@Data // Genera getters, setters, equals, hashCode, toString (de Lombok)
@NoArgsConstructor // Genera constructor sin argumentos (de Lombok)
@AllArgsConstructor // Genera constructor con todos los argumentos (de Lombok)
public class Publicacion {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publicacion_seq_generator")
	@SequenceGenerator(name = "publicacion_seq_generator", sequenceName = "PUBLICACIONES_SEQ", allocationSize = 1)
	@Column(name = "ID_PUBLICACION")
	private Long id;

	@Column(name = "TITULO", nullable = false, length = 255)
	private String titulo;

	@Column(name = "CONTENIDO", nullable = false, columnDefinition = "CLOB") // Usar CLOB para contenido largo
	private String contenido;

	@Column(name = "FECHA_CREACION", nullable = false)
	private LocalDateTime fechaCreacion;

	@Column(name = "AUTOR", length = 100) // Opcional, si no tienes entidad Usuario
	private String autor;

	@OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Comentario> comentarios = new ArrayList<>();

	@OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Calificacion> calificaciones = new ArrayList<>();

}