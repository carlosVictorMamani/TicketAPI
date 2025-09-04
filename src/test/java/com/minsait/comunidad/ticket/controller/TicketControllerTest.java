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
import static org.mockito.ArgumentMatchers.anyString;
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
    void exportTodayTickets_returnsFile() throws Exception {
        TicketDto ticket = new TicketDto();
        Mockito.when(service.getTicketToNow()).thenReturn(Collections.singletonList(ticket));

        mockMvc.perform(get("/export-today"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", org.hamcrest.Matchers.containsString("attachment")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void list_returnsAllTickets() throws Exception {
        TicketDto ticket1 = new TicketDto();
        TicketDto ticket2 = new TicketDto();
        when(service.findAll()).thenReturn(Arrays.asList(ticket1, ticket2));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void delete_existingTicket_returnsOk() throws Exception {
        TicketDto ticket = new TicketDto();
        ticket.setCodigo("TICKET32");
        when(service.findByCodigo("TICKET32")).thenReturn(Optional.of(ticket));
        doNothing().when(service).deleteByCodigo("TICKET32");

        mockMvc.perform(delete("/TICKET32"))
                .andExpect(status().isOk());
    }

    @Test
    void delete_nonExistingTicket_returnsNotFound() throws Exception {
        when(service.findByCodigo("TICKET32")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/TICKET32"))
                .andExpect(status().isNotFound());
    }

   

    @Test
    void generate_createsTicket_returnsCreated() throws Exception {
        TicketDto ticket = new TicketDto();
        ticket.setCodigo("new");
        when(service.generateTicket(any(TicketDto.class))).thenReturn(ticket);

        mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"codigo\":\"new\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codigo", is("new")));
    }

    @Test
    void update_nonExistingTicket_returnsNotFound() throws Exception {
        when(service.findByCodigo("nope")).thenReturn(Optional.empty());

        mockMvc.perform(put("/nope")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"codigo\":\"nope\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateStatusAll_returnsOk() throws Exception {
        when(service.updateStatusAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(put("/status"))
                .andExpect(status().isOk());
    }

    @Test
    void update_existingTicket_returnsOk() throws Exception {
        TicketDto existing = new TicketDto();
        existing.setCodigo("TICKET99");
        TicketDto updated = new TicketDto();
        updated.setCodigo("TICKET99");
        when(service.findByCodigo("TICKET99")).thenReturn(Optional.of(existing));
        when(service.update(any(TicketDto.class), any(TicketDto.class))).thenReturn(updated);

        mockMvc.perform(put("/TICKET99")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"codigo\":\"TICKET99\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo", is("TICKET99")));
    }

    @Test
    void findByCodigo_existingTicket_returnsOk() throws Exception {
        TicketDto ticket = new TicketDto();
        ticket.setCodigo("TICKET1");
        when(service.findByCodigo("TICKET1")).thenReturn(Optional.of(ticket));

        mockMvc.perform(get("/codigo/TICKET1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo", is("TICKET1")));
    }

    @Test
    void findByCodigo_nonExistingTicket_returnsNotFound() throws Exception {
        when(service.findByCodigo("TICKET1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/codigo/TICKET1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findSolicitante_existingTicket_returnsOk() throws Exception {
        TicketDto ticket = new TicketDto();
        ticket.setCodigo("TICKET2");
        when(service.findBySolicitante("solicitante1")).thenReturn(Optional.of(ticket));

        mockMvc.perform(get("/solicitante/solicitante1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo", is("TICKET2")));
    }

    @Test
    void findSolicitante_nonExistingTicket_returnsNotFound() throws Exception {
        when(service.findBySolicitante("solicitante1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/solicitante/solicitante1"))
                .andExpect(status().isNotFound());
    }

}