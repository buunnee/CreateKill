package createkill;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

public class CreateKillController {

    private ProcessManager processManager = new ProcessManager();

    @FXML
    private void openNotepad(ActionEvent event) {
        processManager.startNotepad();
    }

    @FXML
    private void killNotepad(ActionEvent event) {
        processManager.killNotepad();
    }
}
