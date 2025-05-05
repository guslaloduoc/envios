package com.envios.envios.controller;
// Imports necesarios para HATEOAS
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
// Importa los métodos estáticos para facilitar la construcción de enlaces
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
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
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) // Indica que si todo va bien, debe devolver 201 Created
	public PublicacionResponseDto createPublicacion(@Valid @RequestBody PublicacionRequestDto publicacionRequestDto) {
		PublicacionResponseDto newPublicacionDto = publicacionService.createPublicacion(publicacionRequestDto);
		// Añadir HATEOAS self link al recurso recién creado
		newPublicacionDto.add(linkTo(methodOn(PublicacionController.class).getPublicacionById(newPublicacionDto.getId())).withSelfRel());
		return newPublicacionDto;
	}
	// Endpoint GET para obtener todas las publicaciones (con HATEOAS en cada elemento)
	@GetMapping
	public ResponseEntity < List < PublicacionResponseDto >> getAllPublicaciones() {
		List < PublicacionResponseDto > publicaciones = publicacionService.getAllPublicaciones();
		// Iterar sobre cada DTO en la lista y añadirle un enlace "self"
		for(PublicacionResponseDto publicacionDto: publicaciones) {
			Long publicacionId = publicacionDto.getId();
			publicacionDto.add(linkTo(methodOn(PublicacionController.class).getPublicacionById(publicacionId)).withSelfRel());
		}
		return ResponseEntity.ok(publicaciones);
	}
	// Endpoint GET para obtener una publicacion por su ID (con HATEOAS)
	@GetMapping("/{id}")
	public ResponseEntity < PublicacionResponseDto > getPublicacionById(@PathVariable Long id) {
		PublicacionResponseDto publicacionDto = publicacionService.getPublicacionById(id);
		// Añadir enlaces HATEOAS: self y comentarios
		Link selfLink = linkTo(methodOn(PublicacionController.class).getPublicacionById(id)).withSelfRel();
		Link comentariosLink = linkTo(methodOn(ComentarioController.class).getComentariosByPublicacionId(id)).withRel("comentarios");
		publicacionDto.add(selfLink, comentariosLink);
		return ResponseEntity.ok(publicacionDto);
	}
	// Endpoint PUT para actualizar una publicacion por su ID
	@PutMapping("/{id}")
	public PublicacionResponseDto updatePublicacion(@PathVariable Long id, @Valid @RequestBody PublicacionRequestDto publicacionRequestDto) {
		PublicacionResponseDto updatedPublicacionDto = publicacionService.updatePublicacion(id, publicacionRequestDto);
		// Añadir HATEOAS self link al recurso actualizado
		updatedPublicacionDto.add(linkTo(methodOn(PublicacionController.class).getPublicacionById(updatedPublicacionDto.getId())).withSelfRel());
		return updatedPublicacionDto;
	}
	// Endpoint DELETE para eliminar una publicacion por su ID
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) // Indica que si todo va bien, debe devolver 204 No Content
	public void deletePublicacion(@PathVariable Long id) {
		publicacionService.deletePublicacion(id);
		// 204 No Content típicamente no tiene cuerpo de respuesta, no se añaden enlaces aquí.
	}

}