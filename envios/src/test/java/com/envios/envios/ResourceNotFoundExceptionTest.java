package com.envios.envios.errors;

import org.junit.jupiter.api.Test; // Para @Test
import static org.junit.jupiter.api.Assertions.*; // Para aserciones como assertEquals, assertThrows

public class ResourceNotFoundExceptionTest {

@Test // Marca este método como una prueba unitaria
void testConstructorWithMessage() {
// Arrange (Preparar)
String mensajeEsperado = "El recurso no fue encontrado con el ID especificado";

// Act (Actuar)
// Creamos una instancia de la excepción usando el constructor con mensaje
ResourceNotFoundException excepcion = new ResourceNotFoundException(mensajeEsperado);

// Assert (Verificar)
// Verificamos que el mensaje de la excepción sea el esperado
assertEquals(mensajeEsperado, excepcion.getMessage(), "El mensaje de la excepción debe coincidir");
// Verificamos que la causa sea nula (ya que usamos el constructor sin causa)
assertNull(excepcion.getCause(), "La causa de la excepción debe ser nula");
}

@Test // Marca este método como una prueba unitaria
void testConstructorWithMessageAndCause() {
// Arrange (Preparar)
String mensajeEsperado = "Error al procesar el recurso";
Throwable causaEsperada = new RuntimeException("Error subyacente");

// Act (Actuar)
// Creamos una instancia de la excepción usando el constructor con mensaje y causa
ResourceNotFoundException excepcion = new ResourceNotFoundException(mensajeEsperado, causaEsperada);

// Assert (Verificar)
// Verificamos que el mensaje de la excepción sea el esperado
assertEquals(mensajeEsperado, excepcion.getMessage(), "El mensaje de la excepción debe coincidir");
// Verificamos que la causa de la excepción sea la esperada
assertEquals(causaEsperada, excepcion.getCause(), "La causa de la excepción debe coincidir");
}

}