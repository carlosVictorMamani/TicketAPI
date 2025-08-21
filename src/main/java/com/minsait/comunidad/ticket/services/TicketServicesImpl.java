package com.minsait.comunidad.ticket.services;

import java.util.List;
import java.util.Optional;
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
       // mapper.toListDto(repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TicketDto> findByCodigo(String codigo) {
        return repository.findByCodigo(codigo).map(mapper::toDto);
    }
    
    @Override
    @Transactional
    public TicketDto save(TicketDto ticket) {

    for (int i = 102; i <= 140; i++) {
        TicketDto ticket2 = new TicketDto();
        ticket2.setCodigo("P" + i);
        ticket2.setStatus(Estado.LIBRE);
        this.repository.save(mapper.toEntity(ticket2));
    }

        return null;

        //return mapper.toDto(this.repository.save(mapper.toEntity(ticket)));
    }

  /*   public Optional<TicketDto> findById(String id) {
        return  repository.findById(new ObjectId(id)).map(mapper::toDto);
    } */
    
}
