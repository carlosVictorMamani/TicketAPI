package com.minsait.comunidad.ticket.services;

import com.minsait.comunidad.ticket.domain.Ticket;
import com.minsait.comunidad.ticket.dto.TicketDto;
import com.minsait.comunidad.ticket.enums.Estado;
import com.minsait.comunidad.ticket.mapper.TicketMapper;
import com.minsait.comunidad.ticket.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class TicketServicesImplTest {

    @Mock
    private TicketRepository repository;

    @InjectMocks
    private TicketServicesImpl service;

    @Mock
    private TicketMapper mapper;

    @BeforeEach
    void setUp() {
        repository = mock(TicketRepository.class);
        mapper = mock(TicketMapper.class);
        service = new TicketServicesImpl(repository, mapper);
    }

    @Test
    void testFindAll() {
        List<Ticket> tickets = List.of(new Ticket());
        List<TicketDto> dtos = List.of(new TicketDto());
        when(repository.findAll()).thenReturn(tickets);
        when(mapper.toListDto(tickets)).thenReturn(dtos);

        List<TicketDto> result = service.findAll();
        assertEquals(dtos, result);
        verify(repository).findAll();
        verify(mapper).toListDto(tickets);
    }

    @Test
    void testFindByCodigo() {
        Ticket ticket = new Ticket();
        TicketDto dto = new TicketDto();
        when(repository.findByCodigo("code")).thenReturn(Optional.of(ticket));
        when(mapper.toDto(ticket)).thenReturn(dto);

        Optional<TicketDto> result = service.findByCodigo("code");
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
        TicketDto input = new TicketDto();
        input.setComentario("comentario");
        input.setSolicitante("solicitante");
        input.setEstado(Estado.LIBRE);

        TicketDto elemento = new TicketDto();
        elemento.setComentario("old");
        elemento.setSolicitante("old");
        elemento.setEstado(Estado.OCUPADO);

        Ticket entity = new Ticket();
        Ticket savedEntity = new Ticket();
        TicketDto expectedDto = new TicketDto();

        when(mapper.toEntity(elemento)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(expectedDto);

        TicketDto result = service.update(input, elemento);

        assertEquals(expectedDto, result);
        assertEquals("comentario", elemento.getComentario());
        assertEquals("solicitante", elemento.getSolicitante());
        assertEquals(Estado.LIBRE, elemento.getEstado());
    }

    @Test
    void testUpdateStatusAll() {
        Ticket t1 = new Ticket();
        t1.setEstado(Estado.OCUPADO);
        Ticket t2 = new Ticket();
        t2.setEstado(Estado.LIBRE);

        when(repository.findAll()).thenReturn(List.of(t1, t2));
        when(repository.save(t1)).thenReturn(t1);
        List<TicketDto> dtos = List.of(new TicketDto());
        when(mapper.toListDto(anyList())).thenReturn(dtos);

        List<TicketDto> result = service.updateStatusAll();
        assertEquals(dtos, result);
        assertEquals(Estado.LIBRE, t1.getEstado());
    }

    @Test
    void testDeleteByCodigo() {
        Ticket ticket = new Ticket();
        when(repository.findByCodigo("code")).thenReturn(Optional.of(ticket));
        service.deleteByCodigo("code");
        verify(repository).delete(ticket);
    }

    @Test
    void testGenerateTicket() {
        TicketDto dto = new TicketDto();
        dto.setUsuarioGenerador("user");
        Ticket entity = new Ticket();
        Ticket savedEntity = new Ticket();
        TicketDto expectedDto = new TicketDto();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(any(Ticket.class))).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(expectedDto);

        TicketDto result = service.generateTicket(dto);

        assertEquals(expectedDto, result);
        assertEquals(Estado.LIBRE, entity.getEstado());
        assertEquals("user", entity.getUsuarioGenerador());
        assertNotNull(entity.getCodigo());
        assertNotNull(entity.getFechaCreacion());
    }

    @Test
    void testGetTicketToNow() {
        Ticket t1 = new Ticket();
        t1.setFechaCreacion(LocalDateTime.now());
        Ticket t2 = new Ticket();
        t2.setFechaCreacion(LocalDateTime.now().minusDays(1));
        when(repository.findAll()).thenReturn(List.of(t1, t2));
        List<TicketDto> dtos = List.of(new TicketDto());
        when(mapper.toListDto(anyList())).thenReturn(dtos);

        List<TicketDto> result = service.getTicketToNow();
        assertEquals(dtos, result);
    }
}