package com.minsait.comunidad.ticket.util;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Utils {
    
  


  private Utils() {
    
  }

  public static String formatLocalDateTimeToDate(LocalDateTime dateTime) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return dateTime.format(formatter);
  }

    public static String generateCodigo() {
      Random random = new Random();
      
        return null;
    }

}
