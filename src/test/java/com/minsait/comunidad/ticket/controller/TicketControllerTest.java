package com.minsait.comunidad.ticket.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.minsait.comunidad.ticket.dto.TicketDto;
import com.minsait.comunidad.ticket.enums.Estado;
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
    void testList() throws Exception {
        TicketDto ticket1 = new TicketDto();
        TicketDto ticket2 = new TicketDto();
        when(service.findAll()).thenReturn(Arrays.asList(ticket1, ticket2));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
        }

   
    @Test
    void delete_existingTicket_returnsOk() throws Exception {
        String codigo = "DEL123";
        TicketDto ticket = new TicketDto();
        ticket.setCodigo(codigo);

        when(service.findByCodigo(codigo)).thenReturn(Optional.of(ticket));
        doNothing().when(service).deleteByCodigo(codigo);

        mockMvc.perform(delete("/" + codigo))
                .andExpect(status().isOk());
    }

    @Test
    void delete_nonExistingTicket_returnsNotFound() throws Exception {
        String codigo = "T23";
        when(service.findByCodigo(codigo)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/" + codigo))
                .andExpect(status().isNotFound());
    }

    

    @Test
    void update_existingTicket_returnsOk() throws Exception {
        String codigo = "UPD123";
        TicketDto existingTicket = new TicketDto();
        existingTicket.setCodigo(codigo);

        TicketDto updatedTicket = new TicketDto();
        updatedTicket.setCodigo(codigo);
        updatedTicket.setComentario("Updated comment");

        when(service.findByCodigo(codigo)).thenReturn(Optional.of(existingTicket));
        when(service.update(Mockito.any(TicketDto.class), Mockito.eq(existingTicket))).thenReturn(updatedTicket);

        mockMvc.perform(put("/" + codigo)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"codigo\":\"UPD123\",\"comentario\":\"Updated comment\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo", is(codigo)))
                .andExpect(jsonPath("$.comentario", is("Updated comment")));
    }

    @Test
    void update_nonExistingTicket_returnsNotFound() throws Exception {
        String codigo = "NOEXIST";
        TicketDto ticket = new TicketDto();
        ticket.setCodigo(codigo);

        when(service.findByCodigo(codigo)).thenReturn(Optional.empty());

        mockMvc.perform(put("/" + codigo)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"codigo\":\"NOEXIST\"}"))
                .andExpect(status().isNotFound());
    }

       @Test
        void findById_existingTicket_returnsOk() throws Exception {
            
              
            String codigo = "EXIST123";
            TicketDto ticket = new TicketDto();
            ticket.setCodigo(codigo);

            when(service.findByCodigo(codigo)).thenReturn(Optional.of(ticket));

            mockMvc.perform(get("/" + codigo))
                    .andExpect(status().isOk());
        }

        @Test
    void testGenerateTicket_success() throws Exception {
        String codigo = "EXIST123";
         TicketDto ticket = new TicketDto();
            ticket.setCodigo(codigo);
        // Mock the service behavior to return the mock DTO
        when(service.generateTicket(any(TicketDto.class)))
            .thenReturn(ticket);

        // Perform the POST request
        mockMvc.perform(post("/") // Replace /your-api-path with your actual endpoint path
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"codigo\":\"EXIST123\"}")) // Adjust JSON as per TicketDto structure
                .andExpect(status().isCreated()) // Expect HTTP 201 CREATED
                .andExpect(jsonPath("$.id").value(ticket.getId()));
                // Add more jsonPath assertions for other fields
    }


        @Test
        void findById_nonExistingTicket_returnsNotFound() throws Exception {
            String codigo = "NOTEXIST";
            when(service.findByCodigo(codigo)).thenReturn(Optional.empty());

            mockMvc.perform(get("/" + codigo))
                    .andExpect(status().isNotFound());
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
        TicketDto ticket = new TicketDto(); // configura el ticket si es necesario
        Mockito.when(service.getTicketToNow()).thenReturn(Collections.singletonList(ticket));

        mockMvc.perform(get("/export-today"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", org.hamcrest.Matchers.containsString("attachment")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }




}