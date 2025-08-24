package com.minsait.comunidad.ticket.dto;
import java.time.LocalDateTime;

import com.minsait.comunidad.ticket.enums.Estado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDto implements java.io.Serializable {
    
    private static final long serialVersionUID = 42L;

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
