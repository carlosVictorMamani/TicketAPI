package com.minsait.comunidad.ticket.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import com.minsait.comunidad.ticket.dto.TicketDto;
import com.minsait.comunidad.ticket.services.TicketServices;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class TicketController {
    
    private TicketServices ticketServices;

    
    public TicketController(TicketServices ticketServices) {
        this.ticketServices = ticketServices;
    }

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(ticketServices.findAll());
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<?> delete(@PathVariable String codigo) {
        Optional<TicketDto> existingTicket = ticketServices.findByCodigo(codigo);
        if(existingTicket.isPresent()) {
            ticketServices.deleteByCodigo(existingTicket.get().getCodigo());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> findById(@PathVariable String codigo) {
        
        if(codigo == null || codigo.isEmpty()) {
            return ResponseEntity.badRequest().body("ID cannot be null or empty");
        }
       
        Optional<TicketDto> ticketOptional = ticketServices.findByCodigo(codigo);
        
        if(ticketOptional.isPresent()) {
            return ResponseEntity.ok(ticketOptional.get());
        }
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<TicketDto> generate(@RequestBody TicketDto ticket) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketServices.generateTicket(ticket));
    }

    

    @PutMapping("/{codigo}")
    public ResponseEntity<?> update(@PathVariable String codigo, @RequestBody TicketDto ticket) {
       
        Optional<TicketDto> existingTicket = ticketServices.findByCodigo(codigo);
        if(existingTicket.isPresent()) {
            return ResponseEntity.ok(ticketServices.update(ticket, existingTicket.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/status")
    public ResponseEntity<?> updateStatusAll() {
        return ResponseEntity.ok(ticketServices.updateStatusAll());
    }   

    @GetMapping("/export-today")
    public ResponseEntity<?> exportTodayTickets() {
        try {
            List<TicketDto> ticketsHoy =ticketServices.getTicketToNow();
            String filePath = "tickets_" + java.time.LocalDate.now() + ".txt";
            java.nio.file.Path path = java.nio.file.Paths.get(filePath);
            try (java.io.BufferedWriter writer = java.nio.file.Files.newBufferedWriter(path)) {
                for (TicketDto ticket : ticketsHoy) {
                    writer.write(ticket.toString());
                    writer.newLine();
                }
            }
            org.springframework.core.io.Resource resource = new org.springframework.core.io.FileSystemResource(filePath);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + filePath)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al exportar los tickets: " + e.getMessage());
        }
    }

}
