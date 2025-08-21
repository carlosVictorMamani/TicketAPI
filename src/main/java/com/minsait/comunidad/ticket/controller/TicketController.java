package com.minsait.comunidad.ticket.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import com.minsait.comunidad.ticket.dto.TicketDto;
import com.minsait.comunidad.ticket.services.TicketServices;

@RestController
public class TicketController {
    
    final private TicketServices ticketServices;

    public TicketController(TicketServices ticketServices) {
        this.ticketServices = ticketServices;
    }

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(ticketServices.findAll());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> findById(@PathVariable String codigo) {
        
        if(codigo == null || codigo.isEmpty()) {
            return ResponseEntity.badRequest().body("ID cannot be null or empty");
        }
        System.out.println("Searching for ticket with codigo: " + codigo);
        Optional<TicketDto> ticketOptional = ticketServices.findByCodigo(codigo);
        
        if(ticketOptional.isPresent()) {
             System.out.println(">>>>>>>>>>>>>>>>>" + ticketOptional.get());
            return ResponseEntity.ok(ticketOptional.get());
        }
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<TicketDto> create(@RequestBody TicketDto ticket) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketServices.save(ticket));
    }

}
