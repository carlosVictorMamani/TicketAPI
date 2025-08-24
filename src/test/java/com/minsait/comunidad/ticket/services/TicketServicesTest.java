package com.minsait.comunidad.ticket.services;

import com.minsait.comunidad.ticket.domain.Ticket;
import com.minsait.comunidad.ticket.dto.TicketDto;
import com.minsait.comunidad.ticket.enums.Estado;
import com.minsait.comunidad.ticket.repository.TicketRepository;
import static org.mockito.BDDMockito.given;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class TicketServicesTest {
    
    @Mock
    private TicketRepository repository;

    @InjectMocks
    private TicketServices service;

    @Test
    public void testFindAllReturnsTickets() {
     
     Ticket ticket1 = new Ticket();
        ticket1.setCodigo("TICKET123456");
        ticket1.setDescripcion("First Ticket");
        ticket1.setSolicitante("Alice");
        



     Ticket ticket2 = new Ticket();
        ticket2.setCodigo("TICKET654321");
        ticket2.setDescripcion("Second Ticket");
        ticket2.setSolicitante("Bob");
     
     given(repository.findAll()).willReturn(Arrays.asList(ticket1, ticket2));
        
     List<TicketDto> tickets = service.findAll();

     //assertEquals(tickets).isnotNull();
     //assertEquals(tickets.size()).isEqualTo(2);
     verify(repository).findAll();

    }  

    
}
