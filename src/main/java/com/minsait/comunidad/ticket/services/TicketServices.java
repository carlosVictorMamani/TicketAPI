package com.minsait.comunidad.ticket.services;

import java.util.List;
import java.util.Optional;
import com.minsait.comunidad.ticket.dto.TicketDto;

public interface TicketServices {
    
    Optional<TicketDto> findByCodigo(String codigo);
    
    TicketDto save(TicketDto ticket);
    
    List<TicketDto> findAll();

    TicketDto update(TicketDto ticket , TicketDto elemntoTicket);

    List<TicketDto> updateStatusAll();

    void deleteByCodigo(String codigo);
}
