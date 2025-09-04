package com.minsait.comunidad.ticket.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.minsait.comunidad.ticket.enums.Estado;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Ticket")
public class Ticket{
    
    @Id
    private ObjectId id;
    @Column
    private String codigo;
    @Column
    private long orden;
    @Column
    private String comentario;
    @Column
    private String descripcion;
    @Column 
    private String solicitante;
    @Column
    private String usuarioGenerador;
    @Column
    private Estado estado;
    @Column
    private LocalDateTime fechaCreacion;

}
