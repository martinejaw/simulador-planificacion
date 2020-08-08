package GUI;

import clases.Proceso;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class CargarTabla {

    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene scene) {
        CargarTabla.scene = scene;
    }

    private static Scene scene;

    public static boolean cargado;

    @FXML
    private ChoiceBox<String> tablaChoiceBox;

    @FXML
    private Button cancelarButton;

    @FXML
    private Button cargarButton;

    @FXML
    public void initialize() throws IOException {
        cargarChoiceBox();
        cargarButton.setOnAction( event -> {
            try {
                importarTabla();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        cancelarButton.setOnAction( event -> cancelar());
    }

    @FXML
    private void cancelar(){
        Scene scene = cancelarButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        stage.close();
        cargado=false;
    }

    @FXML
    private void importarTabla() throws IOException {
        List<Proceso> procesosAImportar = Home.sqliteDB.procesosDeTabla(Integer.parseInt(tablaChoiceBox.getValue()));
        TablaProcesos.procesosList.addAll(procesosAImportar);
        Scene scene = cargarButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        stage.close();
        cargado=true;
    }

    @FXML
    public void cargarChoiceBox() throws IOException {
        List<String> stringList = Home.sqliteDB.selectTablas();
        ObservableList<String> itemsTablaChoiceBox = FXCollections.observableArrayList(stringList);
        tablaChoiceBox.setItems(itemsTablaChoiceBox);
    }


}
