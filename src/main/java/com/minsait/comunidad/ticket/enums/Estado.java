package com.minsait.comunidad.ticket.enums;

import lombok.Data;


public enum Estado {
    
    NUEVO("nuevo"),
    ASIGNADO("Asignado"),
    RESUELTO("Resuelto"),
    BLOQUEADO("Bloqueado"),
    ATRASADO("Atrasado"),
    CERRADO("Cerrado");
    
    private final String descripcion;

    Estado(String descripcion) {
        this.descripcion = descripcion;
    }

}
