package com.envios.envios.controller;

import com.envios.envios.dto.ComentarioRequestDto;
import com.envios.envios.dto.ComentarioResponseDto;
import com.envios.envios.service.ComentarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    @GetMapping("/api/publicaciones/{publicacionId}/comentarios")
    public List<ComentarioResponseDto> getComentariosByPublicacionId(@PathVariable Long publicacionId) {
        return comentarioService.getComentariosByPublicacionId(publicacionId);
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