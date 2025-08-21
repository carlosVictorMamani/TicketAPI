package com.minsait.comunidad.ticket.services;

import java.util.List;
import java.util.Optional;
import com.minsait.comunidad.ticket.dto.TicketDto;

public interface TicketServices {
    
    List<TicketDto> findAll();
    Optional<TicketDto> findByCodigo(String codigo);
    TicketDto save(TicketDto ticket);
}
