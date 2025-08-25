package com.minsait.comunidad.ticket.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.minsait.comunidad.ticket.enums.Estado;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto implements Serializable {
    
    private String id;
    @Nullable
    private String codigo;
    private long orden;
    @Nullable
    private String comentario;
    @Nullable
    private String descripcion;
    private String solicitante;
    private String usuarioGenerador;
    private Estado estado;
    private LocalDateTime fechaCreacion;

}
