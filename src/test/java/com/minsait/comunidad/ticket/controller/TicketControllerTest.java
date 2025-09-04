package com.minsait.comunidad.ticket.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.minsait.comunidad.ticket.dto.TicketDto;
import com.minsait.comunidad.ticket.mapper.TicketMapper;
import com.minsait.comunidad.ticket.services.TicketServices;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;  
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketServices service;

    @MockBean
    private TicketMapper ticketMapper;
   
    @Test
    void PruebaListaOk () throws Exception {
        TicketDto ticket1 = new TicketDto();
        ticket1.setCodigo("TICKET1");
        TicketDto ticket2 = new TicketDto();
        ticket2.setCodigo("TICKET2");
        List<TicketDto> tickets = Arrays.asList(ticket1, ticket2);

        when(service.findAll()).thenReturn(tickets);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].codigo", is("TICKET1")))
                .andExpect(jsonPath("$[1].codigo", is("TICKET2")));

    }

    @Test
    void testFindByCodigo_success() throws Exception {
        String codigo = "TICKET23";
        TicketDto ticket = new TicketDto();
        ticket.setCodigo(codigo);
        
        when(service.findByCodigo(codigo)).thenReturn(Optional.of(ticket));
        
        mockMvc.perform(get("/codigo/" + codigo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value("TICKET23"));
    }


     @Test
    void testFindBySolicitante_success() throws Exception {
        String solicitante = "Solicitante1";
        TicketDto ticket = new TicketDto();
        ticket.setSolicitante(solicitante);
        
        when(service.findBySolicitante(solicitante)).thenReturn(Optional.of(ticket));
        
        mockMvc.perform(get("/solicitante/" + solicitante))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.solicitante").value("Solicitante1"));
    }


    @Test
    void testGenerateTicket_success() throws Exception {
        String codigo = "EXIST123";
         TicketDto ticket = new TicketDto();
            ticket.setCodigo(codigo);
        
        when(service.generateTicket(any(TicketDto.class)))
            .thenReturn(ticket);
			
        mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"codigo\":\"EXIST123\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(ticket.getId()));
    }


    @Test
    void updateStatusAll_returnsUpdatedTickets() throws Exception {
        TicketDto ticket1 = new TicketDto();
        TicketDto ticket2 = new TicketDto();
        List<TicketDto> updatedTickets = Arrays.asList(ticket1, ticket2);

        when(service.updateStatusAll()).thenReturn(updatedTickets);

        mockMvc.perform(put("/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
    
    @Test
    void exportTodayTickets_returnsFile() throws Exception {
        TicketDto ticket = new TicketDto();
        Mockito.when(service.getTicketToNow()).thenReturn(Collections.singletonList(ticket));

        mockMvc.perform(get("/export-today"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", org.hamcrest.Matchers.containsString("attachment")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}