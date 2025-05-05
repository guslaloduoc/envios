package com.envios.envios.controller;

import com.envios.envios.dto.ComentarioRequestDto;
import com.envios.envios.dto.ComentarioResponseDto;
import com.envios.envios.service.ComentarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// --- Añade estos imports para HATEOAS ---
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
// Importa el controlador de publicaciones si vas a enlazar a sus métodos
import com.envios.envios.controller.PublicacionController;
// Importa los métodos estáticos para facilitar la construcción de enlaces
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    // Endpoint POST anidado: Crear un comentario para una Publicacion específica
    // POST /api/publicaciones/{publicacionId}/comentarios
    @PostMapping("/api/publicaciones/{publicacionId}/comentarios")
    @ResponseStatus(HttpStatus.CREATED) // 201 Created
    public ComentarioResponseDto createComentario(
            @PathVariable Long publicacionId, // Obtenemos el ID de la Publicacion de la URL
            @Valid @RequestBody ComentarioRequestDto comentarioRequestDto) {
        comentarioRequestDto.setPublicacionId(publicacionId);
        return comentarioService.createComentario(comentarioRequestDto);
    }

    // Endpoint GET anidado: Obtener todos los comentarios de una Publicacion
    // GET /api/publicaciones/{publicacionId}/comentarios
   // Endpoint GET anidado: Obtener todos los comentarios de una Publicacion (CON HATEOAS)
// GET /api/publicaciones/{publicacionId}/comentarios
@GetMapping("/api/publicaciones/{publicacionId}/comentarios")
// Retornamos ResponseEntity<List<...>> para tener más control sobre la respuesta HTTP
public ResponseEntity < List < ComentarioResponseDto >> getComentariosByPublicacionId(@PathVariable Long publicacionId) {
	// 1. Obtener la lista de DTOs desde el servicio
	List < ComentarioResponseDto > comentarios = comentarioService.getComentariosByPublicacionId(publicacionId);
	// 2. Iterar sobre cada DTO en la lista y añadirle enlaces
	for(ComentarioResponseDto comentarioDto: comentarios) {
		Long comentarioId = comentarioDto.getId();
		// Añadir un enlace "self" a cada comentario individual (GET /api/comentarios/{comentarioId})
		Link selfLink = linkTo(methodOn(ComentarioController.class).getComentarioById(comentarioId)).withSelfRel();
		comentarioDto.add(selfLink);
		// Añadir un enlace a la publicación padre (GET /api/publicaciones/{publicacionId})
		Link publicacionLink = linkTo(methodOn(PublicacionController.class).getPublicacionById(publicacionId)).withRel("publicacion");
		comentarioDto.add(publicacionLink);
	}
	// Nota: Para colecciones (listas), también se pueden añadir enlaces a la colección misma (ej. link a "crear comentario").
	// Esto se haría envolviendo la lista en CollectionModel. Por ahora, añadimos enlaces solo a los ítems individuales.
	// 3. Retornar la lista modificada dentro de ResponseEntity
	return ResponseEntity.ok(comentarios);
}
    // Endpoint GET plano: Obtener un comentario por su propio ID
    // GET /api/comentarios/{id}
    @GetMapping("/api/comentarios/{id}")
    public ResponseEntity<ComentarioResponseDto> getComentarioById(@PathVariable Long id) {
        ComentarioResponseDto comentario = comentarioService.getComentarioById(id);
        return ResponseEntity.ok(comentario); // Devuelve 200 OK
    }

    // Endpoint PUT plano: Actualizar un comentario por su propio ID
    // PUT /api/comentarios/{id}
    @PutMapping("/api/comentarios/{id}")
    public ComentarioResponseDto updateComentario(@PathVariable Long id,
            @Valid @RequestBody ComentarioRequestDto comentarioRequestDto) {
        comentarioRequestDto.setPublicacionId(id);
        return comentarioService.updateComentario(id, comentarioRequestDto);
    }

    // Endpoint DELETE plano: Eliminar un comentario por su propio ID
    // DELETE /api/comentarios/{id}
    @DeleteMapping("/api/comentarios/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 No Content
    public void deleteComentario(@PathVariable Long id) {
        comentarioService.deleteComentario(id);
    }
}