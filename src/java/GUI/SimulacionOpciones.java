package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Objects;

public class SimulacionOpciones {

    private Stage stageGantt;

    private Stage stageMemoria;

    private Stage stageEstadistica;

    private static Scene scene;

    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene scene) {
        SimulacionOpciones.scene = scene;
    }

    @FXML
    private Button ganttButton;

    @FXML
    private Button mapaMemoriaButton;

    @FXML
    private Button estadisticaProcesosButton;

    @FXML
    private Button atrasButton;

    @FXML
    private void initialize() {
    }

    public void openGanttWindow() throws IOException {

        if (stageGantt == null){
            stageGantt = new Stage();
            stageGantt.setTitle("Diagrama de Gantt");
            stageGantt.setScene(new Scene(Gantt.scrollPane, 800, 400));
        }
        stageGantt.show();
        stageGantt.centerOnScreen();
    }

    public void openMapaMemoriaWindow() throws IOException {

        if (stageMemoria == null){
            stageMemoria = new Stage();
            stageMemoria.setTitle("Mapa de Memoria");
            stageMemoria.setScene(new Scene(MapaMemoriaGUI.scrollPane, 800, 400));
        }
        stageMemoria.show();
        stageMemoria.centerOnScreen();
    }

    public void openEstadisticaProcesos() throws IOException {
        if (stageEstadistica== null){
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("EstadisticaProcesos.fxml")));
            stageEstadistica = new Stage();
            stageEstadistica.setTitle("Estadisticas de Procesos");
            stageEstadistica.setScene(new Scene(root, 600, 400));

        }
        stageEstadistica.show();
        stageEstadistica.centerOnScreen();
    }

    public void atras(){
        Scene scene = atrasButton.getScene();

        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        stage.setTitle("Tabla Proceso");
        stage.setScene(TablaProcesos.getScene());
        stage.show();
        stage.centerOnScreen();
    }

}
