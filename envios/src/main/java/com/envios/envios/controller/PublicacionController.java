package com.envios.envios.controller;

import com.envios.envios.dto.PublicacionRequestDto;
import com.envios.envios.dto.PublicacionResponseDto;
import com.envios.envios.dto.PromedioCalificacionDto;
import com.envios.envios.service.PublicacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones") // Prefijo para todas las URLs de este controlador
public class PublicacionController {

    private final PublicacionService publicacionService;

    public PublicacionController(PublicacionService publicacionService) {
        this.publicacionService = publicacionService;
    }

    // Endpoint POST para crear una nueva publicacion
    @PostMapping // Mapea a solicitudes POST /api/publicaciones
    @ResponseStatus(HttpStatus.CREATED) // Indica que si todo va bien, debe devolver 201 Created
    public PublicacionResponseDto createPublicacion(@Valid @RequestBody PublicacionRequestDto publicacionRequestDto) {
        return publicacionService.createPublicacion(publicacionRequestDto);
    }

    // Endpoint GET para obtener todas las publicaciones
    @GetMapping // Mapea a solicitudes GET /api/publicaciones
    public List<PublicacionResponseDto> getAllPublicaciones() {
        return publicacionService.getAllPublicaciones();
    }

    // Endpoint GET para obtener una publicacion por su ID
    @GetMapping("/{id}") // Mapea a solicitudes GET /api/publicaciones/{id}
    public ResponseEntity<PublicacionResponseDto> getPublicacionById(@PathVariable Long id) {
        PublicacionResponseDto publicacion = publicacionService.getPublicacionById(id);
        return ResponseEntity.ok(publicacion);
    }

    // Endpoint PUT para actualizar una publicacion por su ID
    @PutMapping("/{id}") // Mapea a solicitudes PUT /api/publicaciones/{id}
    public PublicacionResponseDto updatePublicacion(@PathVariable Long id,
            @Valid @RequestBody PublicacionRequestDto publicacionRequestDto) {
        return publicacionService.updatePublicacion(id, publicacionRequestDto);
    }

    // Endpoint DELETE para eliminar una publicacion por su ID
    @DeleteMapping("/{id}") // Mapea a solicitudes DELETE /api/publicaciones/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT) // Indica que si todo va bien, debe devolver 204 No Content
    public void deletePublicacion(@PathVariable Long id) {
        publicacionService.deletePublicacion(id);
    }

    // Endpoint GET para obtener el promedio de calificacion de una publicacion
    @GetMapping("/{id}/promedio-calificacion") // Mapea a solicitudes GET /api/publicaciones/{id}/promedio-calificacion
    public PromedioCalificacionDto getPromedioCalificacionPublicacion(@PathVariable Long id) {
        return publicacionService.getPromedioCalificacion(id);
    }
}