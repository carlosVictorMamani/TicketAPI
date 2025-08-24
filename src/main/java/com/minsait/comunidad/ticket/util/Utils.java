package com.minsait.comunidad.ticket.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    
    public static String formatLocalDateTimeToDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTime.format(formatter);
    }

    public static String generateCodigo() {
        // Genera un código único usando UUID y un prefijo estándar
        String prefix = "TICKET";
        String uuid = java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
        return prefix + uuid;
    }

}
