package com.minsait.comunidad.ticket.services;

import com.minsait.comunidad.ticket.domain.Ticket;
import com.minsait.comunidad.ticket.dto.TicketDto;
import com.minsait.comunidad.ticket.enums.Estado;
import com.minsait.comunidad.ticket.mapper.TicketMapper;
import com.minsait.comunidad.ticket.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TicketServicesImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketServicesImpl ticketServices;

    @Test
    void testFindByCodigo_NotFound() {
        String codigo = "NONEXISTENT";

        when(ticketRepository.findByCodigo(codigo)).thenReturn(Optional.empty());

        Optional<TicketDto> result = ticketServices.findByCodigo(codigo);

        assert(result.isEmpty());
        verify(ticketRepository, times(1)).findByCodigo(codigo);
    }

}