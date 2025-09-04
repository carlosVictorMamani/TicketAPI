package com.minsait.comunidad.ticket.util;

import java.io.FileInputStream;
import java.io.IOException;
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


 
 public static boolean extraerInformacion() {
    String valor = null;
    // Bug: esto lanzará NullPointerException
    return valor.equals("info");
}

public static String leerArchivo(String path) throws IOException {
    FileInputStream fis = new FileInputStream(path);
    byte[] data = fis.readAllBytes();
    // Bug: el FileInputStream nunca se cierra
    return new String(data);
}

public static String obtenerFechaActual() {
    // Bug: LocalDateTime.now() depende de la zona horaria del sistema
    return LocalDateTime.now().toString();
}

}
