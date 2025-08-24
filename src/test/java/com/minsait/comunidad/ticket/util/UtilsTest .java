package com.minsait.comunidad.ticket.util;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void testFormatLocalDateTimeToDate() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 6, 10, 15, 30, 45);
        String formattedDate = Utils.formatLocalDateTimeToDate(dateTime);
        assertEquals("2024-06-10", formattedDate);
    }

    @Test
    void testGenerateCodigoFormat() {
        String codigo = Utils.generateCodigo();
        assertTrue(codigo.startsWith("TICKET"));
        assertEquals(18, codigo.length());
    }

    @Test
    void testGenerateCodigoUniqueness() {
        String codigo1 = Utils.generateCodigo();
        String codigo2 = Utils.generateCodigo();
        assertNotEquals(codigo1, codigo2);
    }
}