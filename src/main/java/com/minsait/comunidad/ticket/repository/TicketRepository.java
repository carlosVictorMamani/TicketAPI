package com.minsait.comunidad.ticket.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.minsait.comunidad.ticket.domain.Ticket;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

import org.bson.types.ObjectId;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, ObjectId> {

    Optional<Ticket> findByCodigo(String codigo);

    Optional<Ticket> findBySolicitante(String solicitante);

    long countByFechaCreacion(Date fechaCreacion);
}
