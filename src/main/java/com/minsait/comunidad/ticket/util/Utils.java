package com.minsait.comunidad.ticket.util;

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
        String prefix = "TICKET";
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(12);
       
        for (int i = 0; i < 12; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        return prefix + sb.toString();
    }

 public static String extraerInformacion() {
    return null;
 }  

}
