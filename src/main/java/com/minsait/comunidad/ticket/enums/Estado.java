package com.minsait.comunidad.ticket.enums;

import lombok.Data;


public enum Estado {
    
    LIBRE("Libre"),
    OCUPADO("Ocupado"),
    EN_MANTENIMIENTO("En Mantenimiento"),
    BLOQUEADO("Bloqueado"),
    CERRADO("Cerrado");
    
    private final String descripcion;

    Estado(String descripcion) {
        this.descripcion = descripcion;
    }

}
