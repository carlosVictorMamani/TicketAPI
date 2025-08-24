package com.minsait.comunidad.ticket.services;

import java.util.List;
import java.util.Optional;
import com.minsait.comunidad.ticket.domain.Ticket;
import com.minsait.comunidad.ticket.dto.TicketDto;
import com.minsait.comunidad.ticket.enums.Estado;
import com.minsait.comunidad.ticket.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;  
import com.minsait.comunidad.ticket.mapper.TicketMapper;

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
    /*for (int i = 102; i <= 140; i++) {
        TicketDto ticket2 = new TicketDto();
        ticket2.setCodigo("P" + i);
        ticket2.setStatus(Estado.LIBRE);
        this.repository.save(mapper.toEntity(ticket2));
    }

        return null;
    */
        //return mapper.toDto(this.repository.save(mapper.toEntity(ticket)));

    @Override
    @Transactional(readOnly = true)
    public TicketDto update(TicketDto ticket , TicketDto elementoTicket) {
        
        System.out.println("Estado recibidoss : " + elementoTicket.getEstado());

        System.out.println("Estado ==> : " + Estado.values());
        
        if(ticket.getCodigo() != "null" && !ticket.getCodigo().isEmpty()) {     
            elementoTicket.setDescripcion(ticket.getDescripcion());
        }

        elementoTicket.setComentario(ticket.getComentario());
        elementoTicket.setSolicitante(ticket.getSolicitante());
        elementoTicket.setUsuarioGenerador("USR001");
        elementoTicket.setEstado(ticket.getEstado());
         System.out.println("Estado FINALIZADO : " + elementoTicket.getEstado());
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
        throw new UnsupportedOperationException("Unimplemented method 'generateTicket'");
    }

}
