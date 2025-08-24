package com.minsait.comunidad.ticket.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.minsait.comunidad.ticket.dto.TicketDto;
import com.minsait.comunidad.ticket.services.TicketServices;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(TicketController.class)
public class TicketControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketServices service;

    // Aquí puedes agregar tus métodos de prueba
    @Test
    public void testlist() throws Exception  {
       
    }
    @Test
    public void testListReturnsOkAndTickets() throws Exception {
        TicketDto ticket1 = new TicketDto();
        ticket1.setCodigo("123");
        TicketDto ticket2 = new TicketDto();
        ticket2.setCodigo("456");
        List<TicketDto> tickets = Arrays.asList(ticket1, ticket2);

        when(service.findAll()).thenReturn(tickets);

        mockMvc.perform(get("/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].codigo", is("123")))
                .andExpect(jsonPath("$[1].codigo", is("456")));
    }
}
