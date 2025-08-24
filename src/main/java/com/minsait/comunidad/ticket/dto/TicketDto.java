package com.minsait.comunidad.ticket.dto;
import java.time.LocalDateTime;

import com.minsait.comunidad.ticket.enums.Estado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



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

    public TicketDto() {
    }

    public TicketDto(String id, String codigo, long orden, String comentario, String descripcion, String solicitante, String usuarioGenerador, Estado estado, LocalDateTime fechaCreacion) {
        this.id = id;
        this.codigo = codigo;
        this.orden = orden;
        this.comentario = comentario;
        this.descripcion = descripcion;
        this.solicitante = solicitante;
        this.usuarioGenerador = usuarioGenerador;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public long getOrden() {
        return orden;
    }

    public void setOrden(long orden) {
        this.orden = orden;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getUsuarioGenerador() {
        return usuarioGenerador;
    }

    public void setUsuarioGenerador(String usuarioGenerador) {
        this.usuarioGenerador = usuarioGenerador;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
