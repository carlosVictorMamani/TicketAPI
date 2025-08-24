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

import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TicketServicesImplTest {

    @Mock
    private TicketRepository repository;

    @Mock
    private TicketMapper mapper;

    @InjectMocks
    private TicketServicesImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new TicketServicesImpl(repository, mapper);
    }

    @Test
    void testFindAll() {
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());
        List<TicketDto> dtos = Arrays.asList(new TicketDto(), new TicketDto());
        when(repository.findAll()).thenReturn(tickets);
        when(mapper.toListDto(tickets)).thenReturn(dtos);

        List<TicketDto> result = service.findAll();
        assertEquals(2, result.size());
        verify(repository).findAll();
        verify(mapper).toListDto(tickets);
    }

    @Test
    void testFindByCodigo() {
        Ticket ticket = new Ticket();
        TicketDto dto = new TicketDto();
        when(repository.findByCodigo("ABC")).thenReturn(Optional.of(ticket));
        when(mapper.toDto(ticket)).thenReturn(dto);

        Optional<TicketDto> result = service.findByCodigo("ABC");
        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
    }

    @Test
    void testSaveReturnsNull() {
        TicketDto dto = new TicketDto();
        assertNull(service.save(dto));
    }

    @Test
    void testUpdate() {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setComentario("comentario");
        ticketDto.setSolicitante("solicitante");
        ticketDto.setEstado(Estado.LIBRE);

        TicketDto elementoTicket = new TicketDto();
        elementoTicket.setComentario("old");
        elementoTicket.setSolicitante("old");
        elementoTicket.setEstado(Estado.OCUPADO);

        Ticket ticketEntity = new Ticket();
        TicketDto updatedDto = new TicketDto();

        when(mapper.toEntity(elementoTicket)).thenReturn(ticketEntity);
        when(repository.save(ticketEntity)).thenReturn(ticketEntity);
        when(mapper.toDto(ticketEntity)).thenReturn(updatedDto);

        TicketDto result = service.update(ticketDto, elementoTicket);
        assertEquals(updatedDto, result);
        assertEquals("comentario", elementoTicket.getComentario());
        assertEquals("solicitante", elementoTicket.getSolicitante());
        assertEquals(Estado.LIBRE, elementoTicket.getEstado());
    }

    @Test
    void testUpdateStatusAll() {
        Ticket t1 = new Ticket();
        t1.setEstado(Estado.OCUPADO);
        Ticket t2 = new Ticket();
        t2.setEstado(Estado.LIBRE);

        List<Ticket> allTickets = Arrays.asList(t1, t2);
        when(repository.findAll()).thenReturn(allTickets);
        when(repository.save(t1)).thenReturn(t1);

        List<Ticket> updatedTickets = Collections.singletonList(t1);
        List<TicketDto> dtos = Arrays.asList(new TicketDto());
        when(mapper.toListDto(updatedTickets)).thenReturn(dtos);

        List<TicketDto> result = service.updateStatusAll();
        assertEquals(dtos, result);
        assertEquals(Estado.LIBRE, t1.getEstado());
    }

    @Test
    void testDeleteByCodigo() {
        Ticket ticket = new Ticket();
        when(repository.findByCodigo("XYZ")).thenReturn(Optional.of(ticket));
        service.deleteByCodigo("XYZ");
        verify(repository).delete(ticket);
    }

    @Test
    void testGenerateTicket() {
        TicketDto dto = new TicketDto();
        dto.setUsuarioGenerador("user");
        Ticket ticketEntity = new Ticket();
        Ticket savedTicket = new Ticket();
        savedTicket.setCodigo("CODE");
        savedTicket.setEstado(Estado.LIBRE);
        savedTicket.setUsuarioGenerador("user");
        savedTicket.setOrden(1L);
        savedTicket.setFechaCreacion(LocalDateTime.now());

        when(mapper.toEntity(dto)).thenReturn(ticketEntity);
        when(repository.save(any(Ticket.class))).thenReturn(savedTicket);
        when(mapper.toDto(savedTicket)).thenReturn(new TicketDto());

        TicketDto result = service.generateTicket(dto);
        assertNotNull(result);
        verify(repository).save(any(Ticket.class));
    }

    @Test
    void testGetTicketToNow() {
        Ticket t1 = new Ticket();
        t1.setFechaCreacion(LocalDateTime.now());
        Ticket t2 = new Ticket();
        t2.setFechaCreacion(LocalDateTime.now().minusDays(1));
        List<Ticket> allTickets = Arrays.asList(t1, t2);

        when(repository.findAll()).thenReturn(allTickets);
        List<Ticket> todayTickets = Collections.singletonList(t1);
        List<TicketDto> dtos = Arrays.asList(new TicketDto());
        when(mapper.toListDto(todayTickets)).thenReturn(dtos);

        List<TicketDto> result = service.getTicketToNow();
        assertEquals(dtos, result);
    }
}