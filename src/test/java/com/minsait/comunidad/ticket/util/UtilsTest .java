package com.minsait.comunidad.ticket.util;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UtilsTest {

    @Test
    void testFormatLocalDateTimeToDate() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 10, 5, 14, 30);
        String formattedDate = Utils.formatLocalDateTimeToDate(dateTime);
        assertEquals("2023-10-05", formattedDate);
    }

    @Test
    void testGenerateCodigo() {
        String codigo1 = Utils.generateCodigo();
        String codigo2 = Utils.generateCodigo();

        assertTrue(codigo1.startsWith("TICKET"));
        assertTrue(codigo2.startsWith("TICKET"));
        assertNotEquals(codigo1, codigo2); // Ensure uniqueness
        assertEquals(18, codigo1.length()); // "TICKET" + 12 characters
        assertEquals(18, codigo2.length());
    }
}