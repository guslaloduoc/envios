package com.envios.envios;

import com.envios.envios.Publicacion;
import com.envios.envios.dto.PublicacionRequestDto;
import com.envios.envios.dto.PublicacionResponseDto;
import com.envios.envios.errors.ResourceNotFoundException;
import com.envios.envios.repository.CalificacionRepository;
import com.envios.envios.repository.PublicacionRepository;
import com.envios.envios.service.PublicacionServiceImpl;

import org.junit.jupiter.api.BeforeEach; // Para @BeforeEach (equivalente a @Before en JUnit 4)
import org.junit.jupiter.api.AfterEach; // Para @AfterEach (equivalente a @After en JUnit 4)
import org.junit.jupiter.api.Test; // Para @Test
import org.junit.jupiter.api.extension.ExtendWith; // Para integrar Mockito con JUnit 5

import org.mockito.InjectMocks; // Inyecta los mocks en la clase a probar
import org.mockito.Mock; // Crea un objeto mock
import org.mockito.junit.jupiter.MockitoExtension; // Habilita Mockito en JUnit 5

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*; // Para aserciones como assertEquals, assertThrows
import static org.mockito.Mockito.*; // Para when, verify, any

@ExtendWith(MockitoExtension.class) // Esta anotación es clave para que Mockito funcione con JUnit 5
public class PublicacionServiceImplTest {

    // Creamos un mock del repositorio de Publicacion

    @Mock
    private PublicacionRepository publicacionRepository;

    // Creamos un mock del repositorio de Calificacion
    // Aunque no lo usemos en estos tests, el constructor de ServiceImpl lo requiere

    @Mock
    private CalificacionRepository calificacionRepository;

    // Inyectamos los mocks creados arriba en la instancia de PublicacionServiceImpl

    @InjectMocks
    private PublicacionServiceImpl publicacionService;

    // --- Anotaciones Solicitadas y su Propósito ---

    @BeforeEach // Se ejecuta antes de CADA método de test (@Test)
    void setUp() {
        // En este caso, @ExtendWith(MockitoExtension.class) hace la inicialización
        // automática. Pero si no la usaras, aquí podrías tener:
        // MockitoAnnotations.openMocks(this);
        System.out.println("--- Ejecutando test ---");
    }

    @AfterEach // Se ejecuta después de CADA método de test (@Test)
    void tearDown() {
        // Se usa para limpiar recursos si fuera necesario.
        // En tests unitarios con mocks, raramente se necesita.
        System.out.println("--- Test finalizado ---");
    }

    // --- Tests Unitarios Solicitados (2) ---

    @Test // Marca este método como una prueba unitaria
    void testGetPublicacionById_Existente() {
        // Arrange (Preparar): Configurar el entorno para el test

        // 1. Creamos una entidad Publicacion de ejemplo que simulará la BD
        Long publicacionId = 1L;
        Publicacion publicacionMock = new Publicacion();
        publicacionMock.setId(publicacionId);
        publicacionMock.setTitulo("Título de Prueba");
        publicacionMock.setContenido("Contenido de Prueba");
        publicacionMock.setFechaCreacion(LocalDateTime.now());
        publicacionMock.setAutor("Autor de Prueba");
        // 2. Configuramos el mock del repositorio: Cuando se llame findById con
        // cualquier Long,// devuelve un Optional conteniendo nuestra entidad de
        // ejemplo.
        when(publicacionRepository.findById(anyLong())).thenReturn(Optional.of(publicacionMock));

        // Act (Actuar): Llamar al método que queremos probar
        PublicacionResponseDto resultadoDto = publicacionService.getPublicacionById(publicacionId);

        // Assert (Verificar): Comprobar que el resultado es el esperado

        // 1. Verificamos que el método findById del repositorio mockeado fue llamado
        // exactamente una vez
        // con el ID correcto.
        verify(publicacionRepository, times(1)).findById(publicacionId);

        // 2. Verificamos que el DTO de respuesta no es nulo.
        assertNotNull(resultadoDto, "El DTO de respuesta no debería ser nulo");

        // 3. Verificamos que los datos del DTO de respuesta coinciden con los de
        // nuestra entidad de ejemplo
        assertEquals(publicacionId, resultadoDto.getId(), "El ID debe coincidir");
        assertEquals("Título de Prueba", resultadoDto.getTitulo(), "El título debe coincidir");
        assertEquals("Contenido de Prueba", resultadoDto.getContenido(), "El contenido debe coincidir");
        // Puedes añadir más aserciones para otros campos si lo deseas
    }

    @Test // Marca este método como una prueba unitari
    void testCreatePublicacion_Exitoso() {
        // Arrange (Preparar): Configurar el entorno para el test

        // 1. Creamos un DTO de Request de ejemplo que el servicio recibirá
        PublicacionRequestDto requestDto = new PublicacionRequestDto();
        requestDto.setTitulo("Nuevo Título");
        requestDto.setContenido("Nuevo Contenido");
        requestDto.setAutor("Nuevo Autor");

        // 2. Creamos una entidad Publicacion de ejemplo que simulará ser guardada y
        // devuelta por la BD
        // (esta ya tendrá un ID asignado y la fecha de creación)
        Long savedPublicacionId = 2L;
        Publicacion savedPublicacionMock = new Publicacion();
        savedPublicacionMock.setId(savedPublicacionId);
        savedPublicacionMock.setTitulo(requestDto.getTitulo()); // Debe coincidir con el DTO
        savedPublicacionMock.setContenido(requestDto.getContenido()); // Debe coincidir con el DTO
        savedPublicacionMock.setAutor(requestDto.getAutor()); // Debe coincidir con el DTO
        savedPublicacionMock.setFechaCreacion(LocalDateTime.now()); // Simula que la BD/JPA la setea

        // 3. Configuramos el mock del repositorio: Cuando se llame save con CUALQUIER
        // instancia de Publicacion,
        // devuelve nuestra entidad "guardada" de ejemplo.
        when(publicacionRepository.save(any(Publicacion.class))).thenReturn(savedPublicacionMock);

        // Act (Actuar): Llamar al método que queremos probar
        PublicacionResponseDto resultadoDto = publicacionService.createPublicacion(requestDto);

        // Assert (Verificar): Comprobar que el resultado es el esperado

        // 1. Verificamos que el método save del repositorio mockeado fue llamado
        // exactamente una vez
        // con una instancia de Publicacion.
        verify(publicacionRepository, times(1)).save(any(Publicacion.class));

        // 2. Verificamos que el DTO de respuesta no es nulo.
        assertNotNull(resultadoDto, "El DTO de respuesta no debería ser nulo");

        // 3. Verificamos que el DTO de respuesta contenga el ID que simulamos que la BD
        // le asignó
        assertEquals(savedPublicacionId, resultadoDto.getId(), "El DTO de respuesta debe contener el ID generado");

        // 4. Verificamos que los datos del DTO de respuesta coinciden con los que
        // enviamos en el request
        assertEquals(requestDto.getTitulo(), resultadoDto.getTitulo(),
                "El título en el DTO de respuesta debe coincidir");
        assertEquals(requestDto.getContenido(), resultadoDto.getContenido(),
                "El contenido en el DTO de respuesta debe coincidir");
        assertEquals(requestDto.getAutor(), resultadoDto.getAutor(), "El autor en el DTO de respuesta debe coincidir");
        assertNotNull(resultadoDto.getFechaCreacion(), "La fecha de creación no debería ser nula"); // La fecha la setea
                                                                                                    // el servicio/BD
    }

}