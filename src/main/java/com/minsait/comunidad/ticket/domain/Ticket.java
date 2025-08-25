package com.minsait.comunidad.ticket.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.minsait.comunidad.ticket.enums.Estado;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Ticket")
public class Ticket implements Serializable {
    
    @Id
    private ObjectId id;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "orden")
    private long orden;
    @Column(name = "comentario")
    private String comentario;
    @Column(name = "descripcion")
    private String descripcion;
   @Column(name = "solicitante")
    private String solicitante;
   @Column(name = "usuarioGenerador")
    private String usuarioGenerador;
   @Column(name = "estado")
    private Estado estado;
   @Column(name = "fechaCreacion")
    private LocalDateTime fechaCreacion;

}
