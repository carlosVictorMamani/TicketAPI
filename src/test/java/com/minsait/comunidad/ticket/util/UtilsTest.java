package com.minsait.comunidad.ticket.util;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UtilsTest {

    /*@Test
     void testPrivateConstructor() throws Exception {
          Constructor<Utils> constructor = Utils.class.getDeclaredConstructor();
          constructor.setAccessible(true);
          Exception exception = assertThrows(IllegalStateException.class, constructor::newInstance);
          assertEquals("Utility class", exception.getMessage());
     }*/

     @Test
     void testFormatLocalDateTimeToDate() {
          LocalDateTime dateTime = LocalDateTime.of(2023, 8, 24, 15, 30);
          String formatted = Utils.formatLocalDateTimeToDate(dateTime);
          assertEquals("2023-08-24", formatted);
     }

     @Test
     void testGenerateCodigo() {
          String codigo = Utils.generateCodigo();
          assertNotNull(codigo);
          assertTrue(codigo.startsWith("TICKET"));
          assertEquals(18, codigo.length()); // "TICKET" (6) + 12 chars from UUID
     }

}