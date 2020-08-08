package GUI;

import clases.Instruccion;
import clases.Proceso;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.util.*;

public class TablaProcesos {

    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene scene) {
        TablaProcesos.scene = scene;
    }

    private static Scene scene;

    public static boolean conMultiNivel;

    public static ObservableList<Proceso> procesosList = FXCollections.observableArrayList();

    public static boolean getConMultiNivel() {
        return conMultiNivel;
    }

    public static Proceso procesoSeleccionado;

    @FXML
    private Button cargarTablaButton;

    @FXML
    private Button guardarTablaButton;

    @FXML
    public TableView<Proceso> tablaProcesos;

    @FXML
    private Button volverButton;

    @FXML
    private Button agregarProcesoButton;

    @FXML
    private Button editProcesoButton;

    @FXML
    private Button borrarProcesoButton;

    @FXML
    private Button iniciarSimulacionButton;

    @FXML
    private Button limpiarButton;

    @FXML
    private void initialize() {
        inicializarTabla();
        seleccionLineaTabla();

        editProcesoButton.setDisable(true);
        borrarProcesoButton.setDisable(true);

        iniciarSimulacionButton.setStyle("-fx-background-color: gold ;");
        agregarProcesoButton.setStyle("-fx-background-color: lightgreen;");
        editProcesoButton.setStyle("-fx-background-color: deepskyblue;");
        borrarProcesoButton.setStyle("-fx-background-color: crimson;");
    }

    private void seleccionLineaTabla() {
        tablaProcesos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

            if (newSelection != null) {
                editProcesoButton.setDisable(false);
                borrarProcesoButton.setDisable(false);
                procesoSeleccionado = newSelection;
            } else {
                editProcesoButton.setDisable(true);
                borrarProcesoButton.setDisable(true);
            }
        });
    }

    private void inicializarTabla() {
        TableColumn<Proceso, String> id = new TableColumn<>("ID");
        id.setMaxWidth(40);
        TableColumn<Proceso, String> tiempoArribo = new TableColumn<Proceso, String>("TA");
        tiempoArribo.setMaxWidth(50);
        TableColumn<Proceso, String> tamaño = new TableColumn<>("TAMAÑO");
        tamaño.setMaxWidth(100);
        TableColumn<Proceso, String> prioridad = new TableColumn<>("PRIORIDAD");
        prioridad.setMaxWidth(100);
        TableColumn<Proceso, String> cpu = new TableColumn<>("CPU1");
        TableColumn<Proceso, String> es1 = new TableColumn<Proceso, String>("E/S1");
        TableColumn<Proceso, String> cpu1 = new TableColumn<>("CPU2");
        TableColumn<Proceso, String> es2 = new TableColumn<>("E/S2");
        TableColumn<Proceso, String> cpu2 = new TableColumn<Proceso, String>("CPU3");

        tablaProcesos.getColumns().addAll(id, tiempoArribo, tamaño, prioridad, cpu, es1, cpu1, es2,cpu2);

        ObservableList<Proceso> data = procesosList;

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tiempoArribo.setCellValueFactory(new PropertyValueFactory<>("tiempoCreacion"));
        tamaño.setCellValueFactory(new PropertyValueFactory<>("tamano"));
        prioridad.setCellValueFactory(new PropertyValueFactory<>("prioridad"));
        cpu.setCellValueFactory(new PropertyValueFactory<>("cpu0"));
        es1.setCellValueFactory(new PropertyValueFactory<>("es1"));
        cpu1.setCellValueFactory(new PropertyValueFactory<>("cpu1"));
        es2.setCellValueFactory(new PropertyValueFactory<>("es2"));
        cpu2.setCellValueFactory(new PropertyValueFactory<>("cpu2"));

        //tablaProcesos.setItems(data);

        /**
         * reorder elements
         */
        //Comparator<Proceso> comparator = Comparator.comparingInt(Proceso::getId);
        //FXCollections.sort(data,comparator);
        SortedList<Proceso> sortedData = new SortedList<>(data);
        // this ensures the sortedData is sorted according to the sort columns in the table:
        sortedData.comparatorProperty().bind(tablaProcesos.comparatorProperty());
        tablaProcesos.setItems(data);

        // programmatically set a sort column:
        tablaProcesos.getSortOrder().addAll(id);
    }
    @FXML
    private Stage cargarTabla() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("CargarTablaController.fxml")));

        Stage stage = new Stage();
        stage.setTitle("Cargar Tabla");
        stage.setScene(new Scene(root, 360, 200));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        iniciarSimulacionButton.setDisable(false);

        return stage;
    }

    @FXML
    private void guardarTabla() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("GuardarTablaController.fxml")));

        Stage stage = new Stage();
        stage.setTitle("Guardar Tabla");
        stage.setScene(new Scene(root, 350, 220));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void limpiarTabla() throws IOException {
        procesosList.clear();
        Proceso.count = 0;
        FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("TablaController.fxml")));
    }

    @FXML
    private void agregarProceso() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AgregarProcesoController.fxml")));

        Stage stage = new Stage();
        stage.setTitle("Agregar Proceso");
        stage.setScene(new Scene(root, 386, 513));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("TablaController.fxml")));
    }

    @FXML
    private void editarProceso() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("EditarProcesoController.fxml")));

        Stage stage = new Stage();
        stage.setTitle("Editar Proceso");
        stage.setScene(new Scene(root, 386, 513));
        stage.show();

        FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("TablaController.fxml")));
        tablaProcesos.refresh();
    }

    @FXML
    private void borrarProceso() throws IOException {
        if (procesoSeleccionado != null) {
            procesosList.remove(procesoSeleccionado);
            Proceso.decrementarCount();
        }
        FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("TablaController.fxml")));
    }

    @FXML
    public void backToHome() throws IOException {
        Scene scene = volverButton.getScene();


        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        stage.setTitle("Simulador");
        if (conMultiNivel) {
            stage.setScene(HomeMultiNivel.getScene());
        } else {
            stage.setScene(Home.getScene());
        }
        stage.show();
    }

    @FXML
    public void startSimulacion() throws IOException {

        FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("GanttController.fxml")));
        FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("MapaMemoria.fxml")));
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("SimulacionOpcionesController.fxml")));

        Scene scene = volverButton.getScene();
        TablaProcesos.scene = scene;

        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        stage.setTitle("Simulacion");

        if (Gantt.getScene() == null) {
            stage.setScene(new Scene(root, 356, 411));
            stage.centerOnScreen();
        } else {
            stage.setScene(Gantt.getScene());
        }
        stage.show();
    }

    public static void setProcesosList(ObservableList<Proceso> procesosList) {
        TablaProcesos.procesosList = procesosList;
    }

    public static ObservableList<Proceso> getProcesosList() {
        return procesosList;
    }

}
