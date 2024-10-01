package createkill;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessManager {

    private Process notepadProcess;
    private Process cmdProcess;

    // Método para abrir el Bloc de notas (notepad.exe)
    public void startNotepad() {
        try {
            notepadProcess = new ProcessBuilder("notepad.exe").start();
            System.out.println("Notepad abierto.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para finalizar el proceso de notepad
    public void killNotepad() {
        try {
            List<String> pids = getProcessIds("notepad.exe");
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

    // Método para abrir CMD y hacer un ping al 8.8.8.8
public void startCMDPing() {
    try {
          // El parámetro "/K" mantiene abierta la ventana del CMD después de ejecutar el comando
        cmdProcess = new ProcessBuilder("cmd.exe", "/c", "start", "cmd.exe", "/K", "ping 8.8.8.8").start();
        System.out.println("CMD con ping iniciado.");
    } catch (Exception e) {
        System.out.println("Error al intentar abrir el CMD: " + e.getMessage());
        e.printStackTrace();
    }
}




    // Método para finalizar el proceso de CMD
public void killCMD() {
    try {
        // Usamos taskkill para matar el proceso cmd.exe que se abrió
        new ProcessBuilder("cmd.exe", "/c", "taskkill /IM cmd.exe /F").start();
        System.out.println("CMD cerrado usando taskkill.");
    } catch (Exception e) {
        System.out.println("Error al intentar cerrar el CMD con taskkill: " + e.getMessage());
        e.printStackTrace();
    }
}

    // Obtener los PIDs de los procesos con el nombre dado
    private List<String> getProcessIds(String processName) throws Exception {
        List<String> pids = new ArrayList<>();
        ProcessBuilder builder = new ProcessBuilder("tasklist.exe", "/fo", "csv", "/nh");
        Process process = builder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.toLowerCase().contains(processName.toLowerCase())) {
                String[] fields = line.split("\",\"");
                if (fields.length > 1) {
                    String pid = fields[1].replaceAll("\"", "");
                    pids.add(pid);
                }
            }
        }
        return pids;
    }

    // Terminar un proceso basado en su PID
    private void terminateProcess(String pid) throws Exception {
        new ProcessBuilder("taskkill", "/PID", pid, "/F").start();
    }
}
