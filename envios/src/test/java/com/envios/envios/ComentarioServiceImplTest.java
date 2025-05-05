package com.envios.envios.service;

import com.envios.envios.Comentario;
import com.envios.envios.Publicacion;
import com.envios.envios.dto.ComentarioRequestDto;
import com.envios.envios.dto.ComentarioResponseDto;
import com.envios.envios.errors.ResourceNotFoundException;
import com.envios.envios.repository.ComentarioRepository;
import com.envios.envios.repository.PublicacionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor; // Necesario para capturar argumentos pasados a mocks
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays; // Para crear listas rápidas
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComentarioServiceImplTest {

// Creamos mocks para los repositorios de los que depende el servicio
@Mock
private ComentarioRepository comentarioRepository;

@Mock
private PublicacionRepository publicacionRepository;

// Inyectamos los mocks en la instancia del servicio que queremos probar
 @InjectMocks
private ComentarioServiceImpl comentarioService;

// Puedes usar BeforeEach si necesitas configurar algo antes de cada test,
// aunque con MockitoExtension, a menudo no es estrictamente necesario
@BeforeEach
void setUp() {
System.out.println("--- Ejecutando test de ComentarioServiceImpl ---");
}

@AfterEach
void tearDown() {
System.out.println("--- Test de ComentarioServiceImpl finalizado ---");
}

// --- Tests Unitarios para ComentarioServiceImpl ---

@Test
void testCreateComentario_Success() {
// Arrange (Preparar)

// Datos de entrada para el servicio
Long publicacionId = 1L;
ComentarioRequestDto requestDto = new ComentarioRequestDto();
requestDto.setContenido("Contenido del comentario");
requestDto.setAutor("Autor del comentario");
requestDto.setPublicacionId(publicacionId); // ID de la publicación a la que pertenece

// Simular la publicación existente en el repositorio
Publicacion publicacionMock = new Publicacion();
publicacionMock.setId(publicacionId);
// Puedes añadir otros campos si son relevantes para la creación del comentario

// Simular el comentario que sería devuelto por el repositorio después de guardar
Long comentarioId = 10L;
Comentario savedComentarioMock = new Comentario();
savedComentarioMock.setId(comentarioId);
savedComentarioMock.setContenido(requestDto.getContenido());
savedComentarioMock.setAutor(requestDto.getAutor());
savedComentarioMock.setFechaCreacion(LocalDateTime.now()); // Simular que el servicio/BD la setea
savedComentarioMock.setPublicacion(publicacionMock);

// Configurar mocks
// Cuando se busque la publicación por su ID, devolver la publicación mockeada
when(publicacionRepository.findById(publicacionId)).thenReturn(Optional.of(publicacionMock));

// Cuando se guarde cualquier comentario, devolver el comentario mockeado "guardado"
when(comentarioRepository.save(any(Comentario.class))).thenReturn(savedComentarioMock);

//A***************rgumentCaptor para verificar que el comentario guardado tenga los datos correctos
ArgumentCaptor<Comentario> comentarioCaptor = ArgumentCaptor.forClass(Comentario.class);
// Act (Actuar)

// Llamar al método del servicio que queremos probar
ComentarioResponseDto resultadoDto = comentarioService.createComentario(requestDto);

// Assert (Verificar)

 // 1. Verificar que se llamó a findById en publicacionRepository con el ID correcto
verify(publicacionRepository, times(1)).findById(publicacionId);

// 2. Verificar que se llamó a save en comentarioRepository exactamente una vez
verify(comentarioRepository, times(1)).save(comentarioCaptor.capture());

// 3. Verificar que el comentario que se intentó guardar tenga los datos correctos
Comentario comentarioPasadoASave = comentarioCaptor.getValue();
assertEquals(requestDto.getContenido(), comentarioPasadoASave.getContenido(), "El contenido del comentario guardado debe coincidir con el del request");
assertEquals(requestDto.getAutor(), comentarioPasadoASave.getAutor(), "El autor del comentario guardado debe coincidir con el del request");
assertNotNull(comentarioPasadoASave.getFechaCreacion(), "La fecha de creación debe haber sido establecida");
assertNotNull(comentarioPasadoASave.getPublicacion(), "El comentario guardado debe estar asociado a una publicación");
assertEquals(publicacionId, comentarioPasadoASave.getPublicacion().getId(), "El comentario guardado debe estar asociado a la publicación correcta");

// 4. Verificar que el DTO de respuesta sea correcto (mapeado desde el comentario guardado)
assertNotNull(resultadoDto, "El DTO de respuesta no debería ser nulo");
assertEquals(comentarioId, resultadoDto.getId(), "El ID del DTO de respuesta debe ser el del comentario guardado");
assertEquals(requestDto.getContenido(), resultadoDto.getContenido(), "El contenido del DTO de respuesta debe coincidir");
assertEquals(requestDto.getAutor(), resultadoDto.getAutor(), "El autor del DTO de respuesta debe coincidir");
assertNotNull(resultadoDto.getFechaCreacion(), "El DTO de respuesta debe tener fecha de creación");
assertEquals(publicacionId, resultadoDto.getPublicacionId(), "El DTO de respuesta debe contener el ID de la publicación");
}
 @Test
void testGetComentariosByPublicacionId_Success() {
// Arrange (Preparar)

// Datos de entrada
Long publicacionId = 1L;

// Simular que la publicación existe
Publicacion publicacionMock = new Publicacion();
publicacionMock.setId(publicacionId);

// Simular la lista de comentarios devuelta por el repositorio
Comentario comentario1 = new Comentario();
comentario1.setId(10L);
comentario1.setContenido("Comentario 1");
comentario1.setAutor("Autor 1");
comentario1.setFechaCreacion(LocalDateTime.now().minusDays(1));
comentario1.setPublicacion(publicacionMock); // Asociar al mock de publicación

Comentario comentario2 = new Comentario();
comentario2.setId(11L);
comentario2.setContenido("Comentario 2");
comentario2.setAutor("Autor 2");
comentario2.setFechaCreacion(LocalDateTime.now());
comentario2.setPublicacion(publicacionMock); // Asociar al mock de publicación

List<Comentario> comentariosMock = Arrays.asList(comentario1, comentario2);

// Configurar mocks
// Cuando se verifique si la publicación existe, devolver true
when(publicacionRepository.existsById(publicacionId)).thenReturn(true);

// Cuando se busquen comentarios por publicacionId, devolver la lista mockeada
when(comentarioRepository.findByPublicacionId(publicacionId)).thenReturn(comentariosMock);

// Act (Actuar)

// Llamar al método del servicio
List<ComentarioResponseDto> resultadoDtos = comentarioService.getComentariosByPublicacionId(publicacionId);

// Assert (Verificar)

// 1. Verificar que se llamó a existsById en publicacionRepository
verify(publicacionRepository, times(1)).existsById(publicacionId);

// 2. Verificar que se llamó a findByPublicacionId en comentarioRepository con el ID correcto
verify(comentarioRepository, times(1)).findByPublicacionId(publicacionId);

// 3. Verificar que la lista de DTOs de respuesta no sea nula y tenga el tamaño correcto
assertNotNull(resultadoDtos, "La lista de DTOs de respuesta no debería ser nula");
assertEquals(2, resultadoDtos.size(), "La lista de DTOs de respuesta debe contener 2 elementos");

// 4. Verificar que los datos en los DTOs de respuesta sean correctos (puedes verificar solo algunos campos o todos)
assertEquals(comentario1.getId(), resultadoDtos.get(0).getId(), "El ID del primer DTO debe coincidir");
assertEquals(comentario2.getContenido(), resultadoDtos.get(1).getContenido(), "El contenido del segundo DTO debe coincidir");
assertEquals(publicacionId, resultadoDtos.get(0).getPublicacionId(), "El primer DTO debe tener el ID de publicación correcto");
assertEquals(publicacionId, resultadoDtos.get(1).getPublicacionId(), "El segundo DTO debe tener el ID de publicación correcto");
}

    // --- Puedes añadir más tests aquí para cubrir otros escenarios, como: ---
    // - createComentario cuando la Publicacion NO existe (debe lanzar ResourceNotFoundException)
    // - getComentariosByPublicacionId cuando la Publicacion NO existe (debe lanzar ResourceNotFoundException)
    // - getComentarioById exitoso
    // - getComentarioById cuando NO existe (debe lanzar ResourceNotFoundException)
    // - updateComentario exitoso
    // - deleteComentario exitoso
    // - deleteComentario cuando NO existe (debe lanzar ResourceNotFoundException)
}