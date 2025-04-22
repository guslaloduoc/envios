package com.envios.envios.controller; // En la carpeta controller

import com.envios.envios.dto.CalificacionRequestDto; // Importar DTOs
import com.envios.envios.dto.CalificacionResponseDto;
import com.envios.envios.service.CalificacionService; // Importar Servicio

import jakarta.validation.Valid; // Importar para validación
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// No ponemos @RequestMapping a nivel de clase
public class CalificacionController {

    private final CalificacionService calificacionService;

    public CalificacionController(CalificacionService calificacionService) {
        this.calificacionService = calificacionService;
    }

    // Endpoint POST anidado: Crear una calificación para una Publicacion específica
    // POST /api/publicaciones/{publicacionId}/calificaciones
    @PostMapping("/api/publicaciones/{publicacionId}/calificaciones")
    @ResponseStatus(HttpStatus.CREATED) // 201 Created
    public CalificacionResponseDto createCalificacion(
            @PathVariable Long publicacionId,
            @Valid @RequestBody CalificacionRequestDto calificacionRequestDto) {

        // Nos aseguramos que el DTO tenga el ID de la publicación del path
        calificacionRequestDto.setPublicacionId(publicacionId);

        return calificacionService.createCalificacion(calificacionRequestDto);
    }

    // Endpoint GET anidado: Obtener todas las calificaciones de una Publicacion
    // específica
    // GET /api/publicaciones/{publicacionId}/calificaciones
    @GetMapping("/api/publicaciones/{publicacionId}/calificaciones")
    public List<CalificacionResponseDto> getCalificacionesByPublicacionId(@PathVariable Long publicacionId) {
        return calificacionService.getCalificacionesByPublicacionId(publicacionId);
    }

    // Endpoint GET plano: Obtener una calificación por su propio ID
    // GET /api/calificaciones/{id}
    @GetMapping("/api/calificaciones/{id}")
    public ResponseEntity<CalificacionResponseDto> getCalificacionById(@PathVariable Long id) {
        CalificacionResponseDto calificacion = calificacionService.getCalificacionById(id);
        return ResponseEntity.ok(calificacion); // Devuelve 200 OK
    }

    // Endpoint DELETE plano: Eliminar una calificación por su propio ID
    // DELETE /api/calificaciones/{id}
    @DeleteMapping("/api/calificaciones/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 No Content
    public void deleteCalificacion(@PathVariable Long id) {
        calificacionService.deleteCalificacion(id);
    }

    // Nota: No incluimos un endpoint PUT/Update para Calificaciones por
    // simplicidad,
    // ya que a menudo se manejan con Delete y Create.
}