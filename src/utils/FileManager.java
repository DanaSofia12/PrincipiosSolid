package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private final String filePath;

    public FileManager(String filePath) {
        this.filePath = filePath;
        crearArchivoSiNoExiste();
    }

    private void crearArchivoSiNoExiste() {
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo: " + e.getMessage());
        }
    }

    public List<String> leerLineas() {
        List<String> lineas = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
        } finally {
            // AQUÍ ESTÁ EL REQUERIMIENTO DEL 'FINALLY'
            // Garantiza que el archivo se cierre incluso si ocurre un error
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                System.err.println("Error al cerrar el lector: " + ex.getMessage());
            }
        }
        return lineas;
    }

    public void guardarLineas(List<String> lineas) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(filePath));
            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error escribiendo en el archivo: " + e.getMessage());
        } finally {
            // Bloque finally para asegurar que se libere el recurso
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException ex) {
                System.err.println("Error al cerrar el escritor: " + ex.getMessage());
            }
        }
    }
}