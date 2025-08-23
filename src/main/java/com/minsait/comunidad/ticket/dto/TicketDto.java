package com.minsait.comunidad.ticket.dto;
import com.minsait.comunidad.ticket.enums.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {
    private String id;
    private String codigo;
    private Estado status;
}
