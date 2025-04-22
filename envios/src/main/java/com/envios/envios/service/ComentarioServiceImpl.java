package com.envios.envios.service;

import com.envios.envios.Comentario;
import com.envios.envios.Publicacion;
import com.envios.envios.dto.ComentarioRequestDto;
import com.envios.envios.dto.ComentarioResponseDto;
import com.envios.envios.errors.ResourceNotFoundException;
import com.envios.envios.repository.ComentarioRepository;
import com.envios.envios.repository.PublicacionRepository;
//import com.envios.envios.service.ComentarioService; // Implementar interfaz

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Para manejo transaccional

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final PublicacionRepository publicacionRepository;

    public ComentarioServiceImpl(ComentarioRepository comentarioRepository,
            PublicacionRepository publicacionRepository) {
        this.comentarioRepository = comentarioRepository;
        this.publicacionRepository = publicacionRepository;
    }

    // --- Métodos Helper para mapear DTOs a Entidades ---
    private Comentario mapDtoToEntity(ComentarioRequestDto dto) {
        Comentario comentario = new Comentario();
        comentario.setContenido(dto.getContenido());
        comentario.setAutor(dto.getAutor());
        // La Publicacion y fechaCreacion se establecen en el método del servicio
        return comentario;
    }

    private ComentarioResponseDto mapEntityToDto(Comentario entity) {
        ComentarioResponseDto dto = new ComentarioResponseDto();
        dto.setId(entity.getId());
        dto.setContenido(entity.getContenido());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setAutor(entity.getAutor());
        dto.setPublicacionId(entity.getPublicacion().getId());
        return dto;
    }
    // --- Fin de Métodos Helper ---

    @Override
    @Transactional
    public ComentarioResponseDto createComentario(ComentarioRequestDto comentarioRequestDto) {
        // 1. Verificar que la Publicacion padre exista
        Publicacion publicacion = publicacionRepository.findById(comentarioRequestDto.getPublicacionId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Publicacion no encontrada con id: " + comentarioRequestDto.getPublicacionId()));

        // 2. Mapear DTO a Entidad y establecer relaciones/campos
        Comentario comentario = mapDtoToEntity(comentarioRequestDto);
        comentario.setFechaCreacion(LocalDateTime.now());
        comentario.setPublicacion(publicacion); // Asociar el comentario a la publicación encontrada

        // 3. Guardar el comentario
        Comentario savedComentario = comentarioRepository.save(comentario);

        // 4. Mapear entidad guardada a DTO de respuesta
        return mapEntityToDto(savedComentario);
    }

    @Override
    // @Transactional(readOnly = true)
    public List<ComentarioResponseDto> getComentariosByPublicacionId(Long publicacionId) {
        if (!publicacionRepository.existsById(publicacionId)) {
            throw new ResourceNotFoundException("Publicacion no encontrada con id: " + publicacionId);
        }

        // Usar el método personalizado del repositorio
        List<Comentario> comentarios = comentarioRepository.findByPublicacionId(publicacionId);

        // Mapear lista de entidades a lista de DTOs
        return comentarios.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    // @Transactional(readOnly = true)
    public ComentarioResponseDto getComentarioById(Long id) {
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario no encontrado con id: " + id));

        return mapEntityToDto(comentario);
    }

    @Override
    @Transactional
    public ComentarioResponseDto updateComentario(Long id, ComentarioRequestDto comentarioRequestDto) {
        // 1. Buscar el comentario existente
        Comentario existingComentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario no encontrado con id: " + id));

        if (comentarioRequestDto.getPublicacionId() != null
                && !existingComentario.getPublicacion().getId().equals(comentarioRequestDto.getPublicacionId())) {
        }

        // 3. Actualizar campos
        existingComentario.setContenido(comentarioRequestDto.getContenido());
        if (comentarioRequestDto.getAutor() != null) { // Actualizar autor solo si viene en el DTO
            existingComentario.setAutor(comentarioRequestDto.getAutor());
        }

        // 4. Guardar el comentario actualizado
        Comentario updatedComentario = comentarioRepository.save(existingComentario);

        // 5. Mapear a DTO de respuesta
        return mapEntityToDto(updatedComentario);
    }

    @Override
    @Transactional
    public void deleteComentario(Long id) {

        if (!comentarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comentario no encontrado con id: " + id);
        }
        comentarioRepository.deleteById(id);
    }
}