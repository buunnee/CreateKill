package createkill;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessManager {

    private Process notepadProcess;

    // Método para abrir el Bloc de notas (notepad.exe)
    public void startNotepad() {
        try {
            // Ejecutar notepad
            notepadProcess = new ProcessBuilder("notepad.exe").start();
            System.out.println("Notepad abierto.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para finalizar el proceso de notepad
    public void killNotepad() {
        try {
            // Obtenemos los PIDs de los procesos notepad.exe
            List<String> pids = getProcessIds("notepad.exe");  // Usamos específicamente "notepad.exe"

            if (pids.isEmpty()) {
                System.out.println("No se encontraron procesos de Notepad.");
            } else {
                for (String pid : pids) {
                    terminateProcess(pid);
                    System.out.println("Proceso con PID " + pid + " terminado.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Obtener los PIDs de los procesos con el nombre dado
    private List<String> getProcessIds(String processName) throws Exception {
        List<String> pids = new ArrayList<>();
        // Ejecutamos el comando tasklist para listar procesos
        ProcessBuilder builder = new ProcessBuilder("tasklist.exe", "/fo", "csv", "/nh");
        Process process = builder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            // Filtramos las líneas que contienen el nombre del proceso exacto
            if (line.toLowerCase().contains(processName.toLowerCase())) {
                String[] fields = line.split("\",\"");  // Es un CSV, por lo que dividimos por comillas
                if (fields.length > 1) {
                    String pid = fields[1].replaceAll("\"", "");  // El PID está en el segundo campo
                    pids.add(pid);
                }
            }
        }
        return pids;
    }

    // Terminar un proceso basado en su PID
    private void terminateProcess(String pid) throws Exception {
        // Ejecutamos el comando taskkill para finalizar el proceso
        new ProcessBuilder("taskkill", "/PID", pid, "/F").start();
    }
}
