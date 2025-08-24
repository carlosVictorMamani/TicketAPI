package com.minsait.comunidad.ticket.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.minsait.comunidad.ticket.domain.Ticket;
import com.minsait.comunidad.ticket.dto.TicketDto;
import com.minsait.comunidad.ticket.enums.Estado;
import com.minsait.comunidad.ticket.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;  
import com.minsait.comunidad.ticket.mapper.TicketMapper;
import com.minsait.comunidad.ticket.util.Utils;

@Service
public class TicketServicesImpl implements TicketServices {
    
    final private TicketRepository repository;
    final private TicketMapper mapper;

    public TicketServicesImpl(TicketRepository repository, TicketMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketDto> findAll() {
       return mapper.toListDto(repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TicketDto> findByCodigo(String codigo) {
        return repository.findByCodigo(codigo).map(mapper::toDto);
    }
    
    @Override
    @Transactional
    public TicketDto save(TicketDto ticket) {
        return null;
    }
    
    @Override
    @Transactional(readOnly = true)
    public TicketDto update(TicketDto ticket , TicketDto elementoTicket) {
        elementoTicket.setComentario(ticket.getComentario());
        elementoTicket.setSolicitante(ticket.getSolicitante());
        elementoTicket.setEstado(ticket.getEstado());
        Ticket ticketEntity = this.repository.save(mapper.toEntity(elementoTicket));
        return mapper.toDto(ticketEntity);                       
    } 

    @Override
    @Transactional(readOnly = true)
    public List<TicketDto> updateStatusAll() {
        List<Ticket> updatedTickets = repository.findAll().stream()
            .filter(t -> t.getEstado() == Estado.OCUPADO)
            .map(t -> {
                t.setEstado(Estado.LIBRE);
                return repository.save(t);
            })
            .toList();
        return mapper.toListDto(updatedTickets);
    }
        
    @Override
    @Transactional
    public void deleteByCodigo(String codigo) {
        repository.findByCodigo(codigo).ifPresent(ticket -> repository.delete(ticket));
    }

    @Override
    @Transactional
    public TicketDto generateTicket(TicketDto ticket) {
        Ticket newTicket = mapper.toEntity(ticket);
        newTicket.setCodigo(Utils.generateCodigo());
        newTicket.setEstado(Estado.LIBRE);
        newTicket.setUsuarioGenerador(ticket.getUsuarioGenerador()); 
        newTicket.setOrden(getNextOrdenForToday()); 
        newTicket.setFechaCreacion(java.time.LocalDateTime.now());
        return mapper.toDto(repository.save(newTicket));
    
    }
    
    private long getNextOrdenForToday() {
        
        long count = repository.findAll().stream()
            .filter(ticket -> ticket.getFechaCreacion() != null &&
            Utils.formatLocalDateTimeToDate(ticket.getFechaCreacion())
                .equals(Utils.formatLocalDateTimeToDate(java.time.LocalDateTime.now())))
            .count();
        return count +1 ;
    }

    @Override
    public List<TicketDto> getTicketToNow() {
        List<Ticket> ticketsHoy = repository.findAll().stream()
            .filter(ticket -> ticket.getFechaCreacion() != null &&
                ticket.getFechaCreacion().toLocalDate().equals(java.time.LocalDate.now()))
            .toList();
        return  mapper.toListDto(ticketsHoy);
    }

}
