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
public class TicketDto {
    
    private String id;
    @Nullable
    private String codigo;
    @Nullable
    private long orden;
    @Nullable
    private String comentario;
    @Nullable
    private String descripcion;
    @Nullable
    private String solicitante;
    @Nullable
    private String usuarioGenerador;
    @Nullable
    private Estado estado;
    @Nullable
    private LocalDateTime fechaCreacion;

}
