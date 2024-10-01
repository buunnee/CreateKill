package createkill;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CreateKillController {

    private final ProcessManager processManager = new ProcessManager();

    @FXML
    public void handleOpenNotepad() {
        processManager.startNotepad();
    }

    @FXML
    public void handleCloseNotepad() {
        processManager.killNotepad();
    }

    // Abrir el CMD y ejecutar el comando ping
    @FXML
    public void handleOpenCMDPing() {
        processManager.startCMDPing();
    }

    // Cerrar el CMD que está ejecutando el ping
    @FXML
    public void handleCloseCMD() {
        processManager.killCMD();
    }
}
