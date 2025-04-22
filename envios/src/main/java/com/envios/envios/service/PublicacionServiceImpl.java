package com.envios.envios.service;

import com.envios.envios.Publicacion;
import com.envios.envios.dto.PromedioCalificacionDto;
import com.envios.envios.dto.PublicacionRequestDto;
import com.envios.envios.dto.PublicacionResponseDto;
import com.envios.envios.errors.ResourceNotFoundException;
import com.envios.envios.repository.CalificacionRepository;
import com.envios.envios.repository.PublicacionRepository;
//import com.envios.envios.service.PublicacionService; // Importar la interfaz del Servicio

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime; // Para la fecha de creación
import java.util.List; // Para listas de publicaciones
import java.util.stream.Collectors; // Para mapear listas

@Service
public class PublicacionServiceImpl implements PublicacionService {

    // Inyectamos los repositorios necesarios para interactuar con la base de datos
    private final PublicacionRepository publicacionRepository;
    private final CalificacionRepository calificacionRepository; // Lo necesitamos para calcular el promedio

    // Constructor para inyección de dependencias (Spring inyecta automáticamente)
    public PublicacionServiceImpl(PublicacionRepository publicacionRepository,
            CalificacionRepository calificacionRepository) {
        this.publicacionRepository = publicacionRepository;
        this.calificacionRepository = calificacionRepository;
    }

    private Publicacion mapDtoToEntity(PublicacionRequestDto dto) {
        Publicacion publicacion = new Publicacion();
        // Mapeamos los campos que vienen del DTO de request
        publicacion.setTitulo(dto.getTitulo());
        publicacion.setContenido(dto.getContenido());
        publicacion.setAutor(dto.getAutor());
        return publicacion;
    }

    private PublicacionResponseDto mapEntityToDto(Publicacion entity) {
        PublicacionResponseDto dto = new PublicacionResponseDto();
        // Mapeamos los campos que queremos exponer en el DTO de respuesta
        dto.setId(entity.getId()); // Incluimos el ID generado
        dto.setTitulo(entity.getTitulo());
        dto.setContenido(entity.getContenido());
        dto.setFechaCreacion(entity.getFechaCreacion()); // Incluimos la fecha de creación
        dto.setAutor(entity.getAutor());
        return dto;
    }
    // --- Fin de Métodos Helper ---

    @Override
    @Transactional

    public PublicacionResponseDto createPublicacion(PublicacionRequestDto publicacionRequestDto) {
        Publicacion publicacion = mapDtoToEntity(publicacionRequestDto);

        publicacion.setFechaCreacion(LocalDateTime.now()); // Establece la fecha y hora actuales

        Publicacion savedPublicacion = publicacionRepository.save(publicacion);

        return mapEntityToDto(savedPublicacion);
    }

    @Override
    public List<PublicacionResponseDto> getAllPublicaciones() {

        List<Publicacion> publicaciones = publicacionRepository.findAll();
        return publicaciones.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override // Implementa el método de la interfaz PublicacionService
    // @Transactional(readOnly = true)
    public PublicacionResponseDto getPublicacionById(Long id) {

        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion no encontrada con id: " + id));
        return mapEntityToDto(publicacion);
    }

    @Override
    @Transactional
    public PublicacionResponseDto updatePublicacion(Long id, PublicacionRequestDto publicacionRequestDto) {

        Publicacion existingPublicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion no encontrada con id: " + id));

        existingPublicacion.setTitulo(publicacionRequestDto.getTitulo());
        existingPublicacion.setContenido(publicacionRequestDto.getContenido());
        existingPublicacion.setAutor(publicacionRequestDto.getAutor());

        Publicacion updatedPublicacion = publicacionRepository.save(existingPublicacion);

        return mapEntityToDto(updatedPublicacion);
    }

    @Override
    @Transactional
    public void deletePublicacion(Long id) {

        if (!publicacionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Publicacion no encontrada con id: " + id);
        }
        // 2. Usar el repositorio para eliminar la entidad por su ID.
        publicacionRepository.deleteById(id);

    }

    @Override
    public PromedioCalificacionDto getPromedioCalificacion(Long id) {

        if (!publicacionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Publicacion no encontrada con id: " + id);
        }

        Double promedio = calificacionRepository.findAverageValorByPublicacionId(id);

        Double finalPromedio = (promedio != null) ? promedio : 0.0;
        return new PromedioCalificacionDto(finalPromedio);
    }
}