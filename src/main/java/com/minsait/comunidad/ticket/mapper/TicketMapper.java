package com.minsait.comunidad.ticket.mapper;

import java.util.List;
import org.mapstruct.Mapper;

import com.minsait.comunidad.ticket.domain.Ticket;
import com.minsait.comunidad.ticket.dto.TicketDto;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    
    @Mapping(target = "id", expression = "java(entity.getId() != null ? entity.getId().toString() : null)")
    TicketDto toDto(Ticket entity);
    
    @Mapping(target = "id", expression = "java(dto.getId() != null ? new org.bson.types.ObjectId(dto.getId()) : null)")
    Ticket toEntity(TicketDto dto);
    
    List<TicketDto> toListDto(List<Ticket> entities);
    List<Ticket> toListEntity(List<TicketDto> dtos);

}


