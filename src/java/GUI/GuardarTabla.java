package GUI;

import clases.Proceso;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

public class GuardarTabla {

    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene scene) {
        GuardarTabla.scene = scene;
    }

    private static Scene scene;

    @FXML
    private Button guardarButton;

    @FXML
    public void initialize(){
        guardar();
        guardarButton.setOnAction( event -> aceptar());
    }

    private void guardar() {
        Home.sqliteDB.nuevaTabla();
    }

    @FXML
    private void aceptar(){
        Scene scene = guardarButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        stage.close();
    }


}
