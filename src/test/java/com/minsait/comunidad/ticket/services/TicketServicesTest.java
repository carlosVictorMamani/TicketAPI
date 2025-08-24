package com.minsait.comunidad.ticket.services;

import com.minsait.comunidad.ticket.domain.Ticket;
import com.minsait.comunidad.ticket.dto.TicketDto;
import com.minsait.comunidad.ticket.enums.Estado;
import com.minsait.comunidad.ticket.repository.TicketRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServicesTest {
    
    @Mock
    private TicketRepository repository;

    @InjectMocks
    private TicketServices service;

    @Test
    void testfindAll() {
        // 1. Configuración del mock:
        // Crea una lista de productos de prueba que el mock va a devolver.
        Ticket ticket = new Ticket();
        ticket.setCodigo("TICKET123456");
        ticket.setOrden(1L);
        ticket.setComentario("Primer ticket"); 
        ticket.setDescripcion("Descripción del primer ticket");
        ticket.setSolicitante("Usuario1");
        ticket.setUsuarioGenerador("USR001");
        ticket.setEstado(Estado.LIBRE);
        Ticket ticket2 = new Ticket();
        ticket2.setCodigo("TICKET123457"); 
        ticket2.setOrden(2L);
        ticket2.setComentario("Segundo ticket");   
        ticket2.setDescripcion("Descripción del segundo ticket");
        ticket2.setSolicitante("Usuario2");
        ticket2.setUsuarioGenerador("USR002");
        ticket2.setEstado(Estado.OCUPADO);

        List<Ticket> mockProducts = Arrays.asList(ticket, ticket2);

        // Define el comportamiento del mock:
        // Cuando se llame a productRepository.findAll(), devuelve la lista mockProducts.
        when(repository.findAll()).thenReturn(mockProducts);

        // 2. Ejecución de la prueba:
        // Llama al método que estamos probando.
        List<TicketDto> result = service.findAll();

        // 3. Verificación de los resultados:
        // a) Verifica que el tamaño de la lista devuelta es el esperado.
        assertEquals(2, result.size(), "El tamaño de la lista debe ser 2.");

        // b) Verifica que el primer elemento es el producto esperado.
        //assertEquals("Laptop", result.get(0).getName(), "El nombre del primer producto debe ser Laptop.");
    }
}
