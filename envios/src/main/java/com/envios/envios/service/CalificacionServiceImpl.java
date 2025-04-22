package com.envios.envios.service;

import com.envios.envios.Calificacion;
import com.envios.envios.Publicacion;
import com.envios.envios.dto.CalificacionRequestDto;
import com.envios.envios.dto.CalificacionResponseDto;
import com.envios.envios.errors.ResourceNotFoundException;
import com.envios.envios.repository.CalificacionRepository;
import com.envios.envios.repository.PublicacionRepository;
//import com.envios.envios.service.CalificacionService; // Implementar interfaz

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Para manejo transaccional

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalificacionServiceImpl implements CalificacionService {

    private final CalificacionRepository calificacionRepository;
    private final PublicacionRepository publicacionRepository; //

    public CalificacionServiceImpl(CalificacionRepository calificacionRepository,
            PublicacionRepository publicacionRepository) {
        this.calificacionRepository = calificacionRepository;
        this.publicacionRepository = publicacionRepository;
    }

    // --- Métodos Helper para mapear DTOs a Entidades ---
    private Calificacion mapDtoToEntity(CalificacionRequestDto dto) {
        Calificacion calificacion = new Calificacion();
        calificacion.setValor(dto.getValor());
        calificacion.setAutor(dto.getAutor()); // Autor de la calificación
        return calificacion;
    }

    private CalificacionResponseDto mapEntityToDto(Calificacion entity) {
        CalificacionResponseDto dto = new CalificacionResponseDto();
        dto.setId(entity.getId());
        dto.setValor(entity.getValor());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setAutor(entity.getAutor());
        dto.setPublicacionId(entity.getPublicacion().getId()); // Incluir el ID de la Publicacion padre
        return dto;
    }
    // --- Fin de Métodos Helper ---

    @Override
    @Transactional
    public CalificacionResponseDto createCalificacion(CalificacionRequestDto calificacionRequestDto) {
        // 1. Verificar que la Publicacion padre exista
        Publicacion publicacion = publicacionRepository.findById(calificacionRequestDto.getPublicacionId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Publicacion no encontrada con id: " + calificacionRequestDto.getPublicacionId()));

        // 2. Mapear DTO a Entidad y establecer relaciones/campos
        Calificacion calificacion = mapDtoToEntity(calificacionRequestDto);
        calificacion.setFechaCreacion(LocalDateTime.now());
        calificacion.setPublicacion(publicacion); // Asociar la calificación a la publicación

        // 3. Guardar la calificación
        Calificacion savedCalificacion = calificacionRepository.save(calificacion);

        // 4. Mapear entidad guardada a DTO de respuesta
        return mapEntityToDto(savedCalificacion);
    }

    @Override
    // @Transactional(readOnly = true)
    public List<CalificacionResponseDto> getCalificacionesByPublicacionId(Long publicacionId) {
        // Opcional: Verificar si la Publicacion padre existe
        if (!publicacionRepository.existsById(publicacionId)) {
            throw new ResourceNotFoundException("Publicacion no encontrada con id: " + publicacionId);
        }

        // Usar el método personalizado del repositorio
        List<Calificacion> calificaciones = calificacionRepository.findByPublicacionId(publicacionId);

        // Mapear lista de entidades a lista de DTOs
        return calificaciones.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    // @Transactional(readOnly = true)
    public CalificacionResponseDto getCalificacionById(Long id) {
        Calificacion calificacion = calificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calificacion no encontrada con id: " + id));

        return mapEntityToDto(calificacion);
    }

    @Override
    @Transactional
    public void deleteCalificacion(Long id) {
        // Verificar existencia antes de eliminar
        if (!calificacionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Calificacion no encontrada con id: " + id);
        }
        calificacionRepository.deleteById(id);

    }
}