package utils;

import exceptions.HttpException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorLogger {
    private static final String LOG_FILE = "error_log.txt";

    public static void log(HttpException e) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            pw.println("=== ERROR [" + e.getStatusCode() + "] ===");
            pw.println("Fecha y Hora : " + now.format(formatter));
            pw.println("Detalle      : " + e.getDetail());
            
            if (e.getErrorData() != null) {
                pw.println("Objeto DTO   : " + e.getErrorData().toString());
            } else {
                pw.println("Objeto DTO   : N/A");
            }
            pw.println("--------------------------------------------------");
        } catch (IOException ex) {
            System.err.println("Fallo crítico: No se pudo escribir en el log de errores.");
        }
    }
}