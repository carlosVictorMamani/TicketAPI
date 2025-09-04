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
	@Column(name = "codigo",  nullable = true)
    private String codigo;
    @Column(name = "orden",  nullable = true)
    private long orden;
    @Column(name = "comentario",  nullable = true)
    private String comentario;
    @Column(name = "descripcion",  nullable = true)
    private String descripcion;
    @Column(name = "descripcion",  nullable = true)
    private String solicitante;
    @Column(name = "usuarioGenerador",  nullable = true)
    private String usuarioGenerador;
    @Column(name = "estado",  nullable = true)
    private Estado estado;
    @Column(name = "fechaCreacion",  nullable = true)
    private LocalDateTime fechaCreacion;

}
