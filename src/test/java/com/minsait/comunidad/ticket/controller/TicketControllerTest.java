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
import java.util.Optional;

@WebMvcTest(TicketController.class)
public class TicketControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketServices service;

@Test
void testList_ReturnsAllTickets() throws Exception {
    TicketDto ticket1 = new TicketDto();
    TicketDto ticket2 = new TicketDto();
    List<TicketDto> tickets = Arrays.asList(ticket1, ticket2);

    when(service.findAll()).thenReturn(tickets);

    mockMvc.perform(get("/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
}

@Test
void testFindById_ReturnsTicket_WhenFound() throws Exception {
    String codigo = "123";
    TicketDto ticket = new TicketDto();
    ticket.setCodigo(codigo);

    when(service.findByCodigo(codigo)).thenReturn(Optional.of(ticket));

    mockMvc.perform(get("/" + codigo)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.codigo", is(codigo)));
}

@Test
void testFindById_ReturnsNotFound_WhenNotFound() throws Exception {
    String codigo = "notfound";
    when(service.findByCodigo(codigo)).thenReturn(Optional.empty());

    mockMvc.perform(get("/" + codigo)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
}

/*@Test
void testFindById_ReturnsBadRequest_WhenCodigoIsEmpty() throws Exception {
    mockMvc.perform(get("/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
}*/

@Test
void testGenerate_ReturnsCreatedTicket() throws Exception {
    TicketDto ticket = new TicketDto();
    ticket.setCodigo("456");

    when(service.generateTicket(ticket)).thenReturn(ticket);

    mockMvc.perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"codigo\":\"456\"}")
    )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.codigo", is("456")));
}

@Test
void testUpdate_ReturnsUpdatedTicket_WhenFound() throws Exception {
    String codigo = "789";
    TicketDto existing = new TicketDto();
    existing.setCodigo(codigo);
    TicketDto updated = new TicketDto();
    updated.setCodigo(codigo);

    when(service.findByCodigo(codigo)).thenReturn(Optional.of(existing));
    when(service.update(updated, existing)).thenReturn(updated);

    mockMvc.perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/" + codigo)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"codigo\":\"789\"}")
    )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.codigo", is(codigo)));
}

@Test
void testUpdate_ReturnsNotFound_WhenTicketNotFound() throws Exception {
    String codigo = "notfound";
    TicketDto ticket = new TicketDto();
    ticket.setCodigo(codigo);

    when(service.findByCodigo(codigo)).thenReturn(Optional.empty());

    mockMvc.perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/" + codigo)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"codigo\":\"notfound\"}")
    )
            .andExpect(status().isNotFound());
}

@Test
void testUpdateStatusAll_ReturnsOk() throws Exception {
    List<TicketDto> updatedTickets = Arrays.asList(new TicketDto(), new TicketDto());
    when(service.updateStatusAll()).thenReturn(updatedTickets);

    mockMvc.perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/status")
                    .contentType(MediaType.APPLICATION_JSON)
    )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
}

@Test
void testDelete_ReturnsNoContent_WhenFound() throws Exception {
    String codigo = "del";
    TicketDto ticket = new TicketDto();
    ticket.setCodigo(codigo);

    when(service.findByCodigo(codigo)).thenReturn(Optional.of(ticket));

    mockMvc.perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/" + codigo)
                    .contentType(MediaType.APPLICATION_JSON)
    )
            .andExpect(status().isNoContent());
}

@Test
void testDelete_ReturnsNotFound_WhenNotFound() throws Exception {
    String codigo = "notfound";
    when(service.findByCodigo(codigo)).thenReturn(Optional.empty());

    mockMvc.perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/" + codigo)
                    .contentType(MediaType.APPLICATION_JSON)
    )
            .andExpect(status().isNotFound());
}

@Test
void testExportTodayTickets_ReturnsOk() throws Exception {
    TicketDto ticket1 = new TicketDto();
    ticket1.setCodigo("t1");
    TicketDto ticket2 = new TicketDto();
    ticket2.setCodigo("t2");
    List<TicketDto> tickets = Arrays.asList(ticket1, ticket2);

    when(service.getTicketToNow()).thenReturn(tickets);

    mockMvc.perform(get("/export-today")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.header().exists("Content-Disposition"));
}
}