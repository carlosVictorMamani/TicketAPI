package com.minsait.comunidad.ticket.dto;
import java.time.LocalDateTime;

import com.minsait.comunidad.ticket.enums.Estado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto implements java.io.Serializable {
    
    private String id;
    private String codigo;
    private long orden;
    private String comentario;
    private String descripcion;
    private String solicitante;
    private String usuarioGenerador;
    private Estado estado;
    private LocalDateTime fechaCreacion;

}
