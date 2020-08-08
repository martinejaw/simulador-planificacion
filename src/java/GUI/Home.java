package GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Objects;

public class Home  {

    public static Home home = new Home();

    public static Home getHome(){
        return home;
    }

    public static Scene scene;

    public static Scene getScene() {
        return scene;
    }

    public static SqliteDB sqliteDB;

    public static String memoria;

    public static boolean conColasMultiSelected;

    public static boolean prioridadSelected;

    public static String algoritmoPlanificacionMem;

    public static String algoritmoPlanificacionChoiceboxValue;

    @FXML
    private VBox vboxContainer;

    @FXML
    private Button cargaDeMemyPlan;

    @FXML
    private Button guardarMemYPlan;

    @FXML
    private RadioButton sinColasMultiRadioButton;

    @FXML
    private RadioButton conColasMultiRadioButton;

    @FXML
    private ChoiceBox<String> algoritmoPlanificacionChoicebox;

    @FXML
    private Label quantumLabel;

    @FXML
    private ChoiceBox<String> quantumChoicebox;

    @FXML
    private ChoiceBox<String> tipoDeParticionChoicebox;

    @FXML
    private ChoiceBox<String> algoritmoPlanificacionMemoriaChoiceBox;

    @FXML
    private Label cantidadParticionesLabel;

    @FXML
    private Spinner<Integer> cantidadParticionesSpinner;

    @FXML
    private Label tamañoParaSOLabel;

    @FXML
    private Spinner<Integer> tamañoParaSOSpinner;

    @FXML
    private Label tamañoMemoriaLabel;

    @FXML
    private TextField tamañoMemoriaInput;

    @FXML
    private Label porcentajeSOLabel;

    @FXML
    private TextField porcentajeSOInput;

    @FXML
    private TextField particionUno;

    @FXML
    private TextField particionDos;

    @FXML
    private TextField particionTres;

    @FXML
    private TextField particionCuatro;

    @FXML
    private TextField particionCinco;

    @FXML
    private TextField particionSeis;

    @FXML
    private Button siguiente;


    private ObservableList<String> algoritmosPlanificacionItems = FXCollections.observableArrayList("Round Robin", "FCFS", "SRTF", "SJF","Prioridad");

    private ObservableList<String> quantumItems = FXCollections.observableArrayList("1","2","3","4","5");

    private ObservableList<String> tipoDeParticionItems = FXCollections.observableArrayList("FIJA","VARIABLE");

    private ObservableList<String> algoritmoPlanificacionMemFija = FXCollections.observableArrayList("First-Fit","Best-Fit");

    private ObservableList<String> algoritmoPlanificacionMemVariable = FXCollections.observableArrayList("First-Fit","Worst-Fit");


    @FXML
    private void initialize(){
        instanciarDB();
        setInputRestrictions();
        sinColasMultiRadioButton.setSelected(true);


        algoritmoPlanificacionChoicebox.setValue("FCFS");
        algoritmoPlanificacionChoicebox.setItems(algoritmosPlanificacionItems);
        algoritmoPlanificacionChoicebox.setOnAction( event -> displayDropdownAlgoritmoPlanificacion());

        quantumChoicebox.setItems(quantumItems);
        quantumLabel.setVisible(false);
        quantumChoicebox.setVisible(false);

        tipoDeParticionChoicebox.setItems(tipoDeParticionItems);
        tipoDeParticionChoicebox.setValue("FIJA");
        tipoDeParticionChoicebox.setOnAction( event -> displayDropdownTipoParticion());

        algoritmoPlanificacionMemoriaChoiceBox.setItems(algoritmoPlanificacionMemFija);
        algoritmoPlanificacionMemoriaChoiceBox.setValue("First-Fit");


        SpinnerValueFactory<Integer> valueFactorycant = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 6, 1);

        cantidadParticionesSpinner.setValueFactory(valueFactorycant);
        cantidadParticionesSpinner.setEditable(true);
        cantidadParticionesSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!"".equals(newValue)) {
                displayCantidadParticiones();
            }
        });

        particionUno.setDisable(false);
        particionUno.setText("10");
        particionDos.setDisable(true);
        particionTres.setDisable(true);
        particionCuatro.setDisable(true);
        particionCinco.setDisable(true);
        particionSeis.setDisable(true);


        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 50, 5);

        tamañoParaSOSpinner.setValueFactory(valueFactory);
        tamañoParaSOSpinner.setEditable(true);

    }

    private void instanciarDB() {
        sqliteDB = new SqliteDB();
        sqliteDB.conectar();
        sqliteDB.crearTablas();
        sqliteDB.insertarEjemplos();
    }


    @FXML
    private void displayDropdownTipoParticion(){
        String tipoOption = tipoDeParticionChoicebox.getValue();
        if (tipoOption.equals("FIJA")){
            //guardar memoria fija seleccionada
            memoria = "FIJA";

            algoritmoPlanificacionMemoriaChoiceBox.setItems(algoritmoPlanificacionMemFija);
            algoritmoPlanificacionMemoriaChoiceBox.setValue("First-Fit");
            cantidadParticionesSpinner.setDisable(false);

            tamañoMemoriaLabel.setVisible(false);
            tamañoMemoriaInput.setVisible(false);
            porcentajeSOLabel.setVisible(false);
            porcentajeSOInput.setVisible(false);

            cantidadParticionesLabel.setVisible(true);
            cantidadParticionesSpinner.setVisible(true);
            tamañoParaSOLabel.setVisible(true);
            tamañoParaSOSpinner.setVisible(true);

            displayCantidadParticiones();
        } else {
            //guardar memoria variable seleccionada
            memoria = "VARIABLE";

            algoritmoPlanificacionMemoriaChoiceBox.setItems(algoritmoPlanificacionMemVariable);
            algoritmoPlanificacionMemoriaChoiceBox.setValue("First-Fit");

            tamañoMemoriaLabel.setVisible(true);
            tamañoMemoriaInput.setVisible(true);
            porcentajeSOLabel.setVisible(true);
            porcentajeSOInput.setVisible(true);

            cantidadParticionesLabel.setVisible(false);
            cantidadParticionesSpinner.setVisible(false);
            tamañoParaSOLabel.setVisible(false);
            tamañoParaSOSpinner.setVisible(false);

            particionUno.setDisable(true);
            particionDos.setDisable(true);
            particionTres.setDisable(true);
            particionCuatro.setDisable(true);
            particionCinco.setDisable(true);
            particionSeis.setDisable(true);
        }
    }

    @FXML
    private void displayDropdownAlgoritmoPlanificacion(){
        String algoritmoOption = algoritmoPlanificacionChoicebox.getValue();
        if (algoritmoOption.equals("Round Robin")){
            quantumLabel.setVisible(true);
            quantumChoicebox.setVisible(true);
        } else {
            quantumLabel.setVisible(false);
            quantumChoicebox.setVisible(false);
        }

        //Cambia a falso static attribute si no se elije "Prioridad"
        prioridadSelected= algoritmoOption.equals("Prioridad");
    }

    @FXML
    private void displayCantidadParticiones(){
        Integer cantidad = cantidadParticionesSpinner.getValue();
        switch (cantidad){
            case 1:
                particionUno.setDisable(false);
                particionDos.setDisable(true);
                particionTres.setDisable(true);
                particionCuatro.setDisable(true);
                particionCinco.setDisable(true);
                particionSeis.setDisable(true);
                break;
            case 2:
                particionUno.setDisable(false);
                particionDos.setDisable(false);
                if (particionDos.getText().equals("")){
                    particionDos.setText("10");
                }
                particionTres.setDisable(true);
                particionCuatro.setDisable(true);
                particionCinco.setDisable(true);
                particionSeis.setDisable(true);
                break;
            case 3:
                particionUno.setDisable(false);
                particionDos.setDisable(false);
                if (particionDos.getText().equals("")){
                    particionDos.setText("10");
                }
                particionTres.setDisable(false);
                if (particionTres.getText().equals("")){
                    particionTres.setText("10");
                }
                particionCuatro.setDisable(true);
                particionCinco.setDisable(true);
                particionSeis.setDisable(true);
                break;
            case 4:
                particionUno.setDisable(false);
                particionDos.setDisable(false);
                if (particionDos.getText().equals("")){
                    particionDos.setText("10");
                }
                particionTres.setDisable(false);
                if (particionTres.getText().equals("")){
                    particionTres.setText("10");
                }
                particionCuatro.setDisable(false);
                if (particionCuatro.getText().equals("")){
                    particionCuatro.setText("10");
                }
                particionCinco.setDisable(true);
                particionSeis.setDisable(true);
                break;
            case 5:
                particionUno.setDisable(false);
                particionDos.setDisable(false);
                if (particionDos.getText().equals("")){
                    particionDos.setText("10");
                }
                particionTres.setDisable(false);
                if (particionTres.getText().equals("")){
                    particionTres.setText("10");
                }
                particionCuatro.setDisable(false);
                if (particionCuatro.getText().equals("")){
                    particionCuatro.setText("10");
                }
                particionCinco.setDisable(false);
                if (particionCinco.getText().equals("")){
                    particionCinco.setText("10");
                }
                particionSeis.setDisable(true);
                break;
            case 6:
                particionUno.setDisable(false);
                if (particionDos.getText().equals("")){
                    particionDos.setText("10");
                }
                particionTres.setDisable(false);
                if (particionTres.getText().equals("")){
                    particionTres.setText("10");
                }
                particionCuatro.setDisable(false);
                if (particionCuatro.getText().equals("")){
                    particionCuatro.setText("10");
                }
                particionCinco.setDisable(false);
                if (particionCinco.getText().equals("")){
                    particionCinco.setText("10");
                }
                particionSeis.setDisable(false);
                if (particionSeis.getText().equals("")){
                    particionSeis.setText("10");
                }
                break;
                default:
                    break;

        }
    }

    private void setInputRestrictions() {
        tamañoMemoriaInput.setText("100");
        // force the field to be numeric only
        tamañoMemoriaInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tamañoMemoriaInput.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        tamañoMemoriaInput.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                if (Integer.parseInt(tamañoMemoriaInput.getText()) < 100) {
                    tamañoMemoriaInput.setText("100");
                }
                if (Integer.parseInt(tamañoMemoriaInput.getText()) > 1000) {
                    tamañoMemoriaInput.setText("1000");
                }
            }
        });

        porcentajeSOInput.setText("5");
        porcentajeSOInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                porcentajeSOInput.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        porcentajeSOInput.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                if (Integer.parseInt(porcentajeSOInput.getText()) < 5) {
                    porcentajeSOInput.setText("5");
                }
                if (Integer.parseInt(porcentajeSOInput.getText()) > 50) {
                    porcentajeSOInput.setText("50");
                }
            }
        });

        tamañoParaSOSpinner.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                if (tamañoParaSOSpinner.getValue() < 5) {
                    tamañoParaSOSpinner.getValueFactory().setValue(5);
                }
                if (tamañoParaSOSpinner.getValue() > 50) {
                    tamañoParaSOSpinner.getValueFactory().setValue(50);
                }
            }
        });

        particionUno.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                particionUno.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        particionDos.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                particionDos.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        particionTres.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                particionTres.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        particionCuatro.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                particionCuatro.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        particionCinco.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                particionCinco.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        particionSeis.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                particionSeis.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        particionUno.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                if (Integer.parseInt(particionUno.getText()) < 10) {
                    particionUno.setText("10");
                }
                if (Integer.parseInt(particionUno.getText()) > 50) {
                    particionUno.setText("50");
                }
            }
        });

        particionDos.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                if (Integer.parseInt(particionDos.getText()) < 10) {
                    particionDos.setText("10");
                }
                if (Integer.parseInt(particionDos.getText()) > 50) {
                    particionDos.setText("50");
                }
            }
        });

        particionTres.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                if (Integer.parseInt(particionTres.getText()) < 10) {
                    particionTres.setText("10");
                }
                if (Integer.parseInt(particionTres.getText()) > 50) {
                    particionTres.setText("50");
                }
            }
        });

        particionCuatro.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                if (Integer.parseInt(particionCuatro.getText()) < 10) {
                    particionCuatro.setText("10");
                }
                if (Integer.parseInt(particionCuatro.getText()) > 50) {
                    particionCuatro.setText("50");
                }
            }
        });

        particionCinco.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                if (Integer.parseInt(particionCinco.getText()) < 10) {
                    particionCinco.setText("10");
                }
                if (Integer.parseInt(particionCinco.getText()) > 50) {
                    particionCinco.setText("50");
                }
            }
        });

        particionSeis.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                if (Integer.parseInt(particionSeis.getText()) < 10) {
                    particionSeis.setText("10");
                }
                if (Integer.parseInt(particionSeis.getText()) > 50) {
                    particionSeis.setText("50");
                }
            }
        });

        cantidadParticionesSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue){
                commitEditorText(cantidadParticionesSpinner);
                displayCantidadParticiones();
            }
        });

        tamañoParaSOSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue){
                commitEditorText(tamañoParaSOSpinner);
            }
        });
    }

    /**
     * c&p from Spinner
     */
    private <T> void commitEditorText(Spinner<T> spinner) {
        if (!spinner.isEditable()) return;
        String text = spinner.getEditor().getText();
        SpinnerValueFactory<T> valueFactory = spinner.getValueFactory();
        if (valueFactory != null) {
            StringConverter<T> converter = valueFactory.getConverter();
            if (converter != null) {
                T value = converter.fromString(text);
                valueFactory.setValue(value);
            }
        }
    }

    @FXML
    private void chooseSinColas(){
        sinColasMultiRadioButton.setSelected(true);
    }

    @FXML
    private void chooseConColas() throws IOException {
        conColasMultiSelected=true;

        conColasMultiRadioButton.setSelected(false);
        sinColasMultiRadioButton.setSelected(true);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("HomeControllerMultiNivel.fxml")));

        Scene scene = siguiente.getScene();
        Home.scene = scene;
        Window window = scene.getWindow();
        Stage stage = (Stage) window;

        if (HomeMultiNivel.getScene()==null){
            stage.setScene(new Scene(root,700,650));
        } else {
            stage.setScene(HomeMultiNivel.getScene());
        }
        stage.show();
    }

    @FXML
    public void goToProcessTable() throws IOException {
        //Seteando Input Values
        home.setSinColasMultiRadioButton(sinColasMultiRadioButton);
        home.setConColasMultiRadioButton(sinColasMultiRadioButton);
        home.setTamañoMemoriaInput(tamañoMemoriaInput);
        home.setPorcentajeSOInput(porcentajeSOInput);
        home.setTipoDeParticionChoicebox(tipoDeParticionChoicebox);
        home.setCantidadParticionesSpinner(cantidadParticionesSpinner);
        home.setTamañoParaSOSpinner(tamañoParaSOSpinner);
        home.setQuantumChoicebox(quantumChoicebox);
        if (particionUno.getText()!=null){
            home.setParticionUno(particionUno);
        }
        if (particionDos.getText()!=null) {
            home.setParticionDos(particionDos);
        }
        if (particionTres.getText()!=null) {
            home.setParticionTres(particionTres);
        }
        if (particionCuatro.getText()!=null) {
            home.setParticionCuatro(particionCuatro);
        }
        if (particionCinco.getText()!=null) {
            home.setParticionCinco(particionCinco);
        }
        if (particionSeis.getText()!=null) {
            home.setParticionSeis(particionSeis);
        }

        algoritmoPlanificacionMem = tipoDeParticionChoicebox.getValue();
        algoritmoPlanificacionChoiceboxValue = algoritmoPlanificacionChoicebox.getValue();


        //Pasando a Otra ventana
        TablaProcesos.conMultiNivel = false;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("TablaController.fxml")));

        Scene scene = siguiente.getScene();
        Home.scene = scene;
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        stage.setTitle("Tabla de Procesos");

        if (TablaProcesos.getScene()==null){
            stage.setScene(new Scene(root,759,500));
        } else {
            stage.setScene(TablaProcesos.getScene());
        }
        stage.show();
    }

    public VBox getVboxContainer() {
        return vboxContainer;
    }

    public Button getCargaDeMemyPlan() {
        return cargaDeMemyPlan;
    }

    public Button getGuardarMemYPlan() {
        return guardarMemYPlan;
    }

    public RadioButton getSinColasMultiRadioButton() {
        return sinColasMultiRadioButton;
    }

    public RadioButton getConColasMultiRadioButton() {
        return conColasMultiRadioButton;
    }

    public ChoiceBox<String> getAlgoritmoPlanificacionChoicebox() {
        return algoritmoPlanificacionChoicebox;
    }

    public Label getQuantumLabel() {
        return quantumLabel;
    }

    public ChoiceBox<String> getQuantumChoicebox() {
        return quantumChoicebox;
    }

    public ChoiceBox<String> getTipoDeParticionChoicebox() {
        return tipoDeParticionChoicebox;
    }

    public ChoiceBox<String> getAlgoritmoPlanificacionMemoriaChoiceBox() {
        return algoritmoPlanificacionMemoriaChoiceBox;
    }

    public Label getCantidadParticionesLabel() {
        return cantidadParticionesLabel;
    }

    public Spinner<Integer> getCantidadParticionesSpinner() {
        return cantidadParticionesSpinner;
    }

    public Label getTamañoParaSOLabel() {
        return tamañoParaSOLabel;
    }

    public Spinner<Integer> getTamañoParaSOSpinner() {
        return tamañoParaSOSpinner;
    }

    public Label getTamañoMemoriaLabel() {
        return tamañoMemoriaLabel;
    }

    public TextField getTamañoMemoriaInput() {
        return tamañoMemoriaInput;
    }

    public Label getPorcentajeSOLabel() {
        return porcentajeSOLabel;
    }

    public TextField getPorcentajeSOInput() {
        return porcentajeSOInput;
    }

    public TextField getParticionUno() {
        return particionUno;
    }

    public TextField getParticionDos() {
        return particionDos;
    }

    public TextField getParticionTres() {
        return particionTres;
    }

    public TextField getParticionCuatro() {
        return particionCuatro;
    }

    public TextField getParticionCinco() {
        return particionCinco;
    }

    public TextField getParticionSeis() {
        return particionSeis;
    }

    public Button getSiguiente() {
        return siguiente;
    }

    public ObservableList<String> getAlgoritmosPlanificacionItems() {
        return algoritmosPlanificacionItems;
    }

    public ObservableList<String> getQuantumItems() {
        return quantumItems;
    }

    public ObservableList<String> getTipoDeParticionItems() {
        return tipoDeParticionItems;
    }

    public ObservableList<String> getAlgoritmoPlanificacionMemFija() {
        return algoritmoPlanificacionMemFija;
    }

    public ObservableList<String> getAlgoritmoPlanificacionMemVariable() {
        return algoritmoPlanificacionMemVariable;
    }

    public void setVboxContainer(VBox vboxContainer) {
        this.vboxContainer = vboxContainer;
    }

    public void setCargaDeMemyPlan(Button cargaDeMemyPlan) {
        this.cargaDeMemyPlan = cargaDeMemyPlan;
    }

    public void setGuardarMemYPlan(Button guardarMemYPlan) {
        this.guardarMemYPlan = guardarMemYPlan;
    }

    public void setSinColasMultiRadioButton(RadioButton sinColasMultiRadioButton) {
        this.sinColasMultiRadioButton = sinColasMultiRadioButton;
    }

    public void setConColasMultiRadioButton(RadioButton conColasMultiRadioButton) {
        this.conColasMultiRadioButton = conColasMultiRadioButton;
    }

    public void setAlgoritmoPlanificacionChoicebox(ChoiceBox<String> algoritmoPlanificacionChoicebox) {
        this.algoritmoPlanificacionChoicebox = algoritmoPlanificacionChoicebox;
    }

    public void setQuantumLabel(Label quantumLabel) {
        this.quantumLabel = quantumLabel;
    }

    public void setQuantumChoicebox(ChoiceBox<String> quantumChoicebox) {
        this.quantumChoicebox = quantumChoicebox;
    }

    public void setTipoDeParticionChoicebox(ChoiceBox<String> tipoDeParticionChoicebox) {
        this.tipoDeParticionChoicebox = tipoDeParticionChoicebox;
    }

    public void setAlgoritmoPlanificacionMemoriaChoiceBox(ChoiceBox<String> algoritmoPlanificacionMemoriaChoiceBox) {
        this.algoritmoPlanificacionMemoriaChoiceBox = algoritmoPlanificacionMemoriaChoiceBox;
    }

    public void setCantidadParticionesLabel(Label cantidadParticionesLabel) {
        this.cantidadParticionesLabel = cantidadParticionesLabel;
    }

    public void setCantidadParticionesSpinner(Spinner<Integer> cantidadParticionesSpinner) {
        this.cantidadParticionesSpinner = cantidadParticionesSpinner;
    }

    public void setTamañoParaSOLabel(Label tamañoParaSOLabel) {
        this.tamañoParaSOLabel = tamañoParaSOLabel;
    }

    public void setTamañoParaSOSpinner(Spinner<Integer> tamañoParaSOSpinner) {
        this.tamañoParaSOSpinner = tamañoParaSOSpinner;
    }

    public void setTamañoMemoriaLabel(Label tamañoMemoriaLabel) {
        this.tamañoMemoriaLabel = tamañoMemoriaLabel;
    }

    public void setTamañoMemoriaInput(TextField tamañoMemoriaInput) {
        this.tamañoMemoriaInput = tamañoMemoriaInput;
    }

    public void setPorcentajeSOLabel(Label porcentajeSOLabel) {
        this.porcentajeSOLabel = porcentajeSOLabel;
    }

    public void setPorcentajeSOInput(TextField porcentajeSOInput) {
        this.porcentajeSOInput = porcentajeSOInput;
    }

    public void setParticionUno(TextField particionUno) {
        this.particionUno = particionUno;
    }

    public void setParticionDos(TextField particionDos) {
        this.particionDos = particionDos;
    }

    public void setParticionTres(TextField particionTres) {
        this.particionTres = particionTres;
    }

    public void setParticionCuatro(TextField particionCuatro) {
        this.particionCuatro = particionCuatro;
    }

    public void setParticionCinco(TextField particionCinco) {
        this.particionCinco = particionCinco;
    }

    public void setParticionSeis(TextField particionSeis) {
        this.particionSeis = particionSeis;
    }

    public void setSiguiente(Button siguiente) {
        this.siguiente = siguiente;
    }

    public void setAlgoritmosPlanificacionItems(ObservableList<String> algoritmosPlanificacionItems) {
        this.algoritmosPlanificacionItems = algoritmosPlanificacionItems;
    }

    public void setQuantumItems(ObservableList<String> quantumItems) {
        this.quantumItems = quantumItems;
    }

    public void setTipoDeParticionItems(ObservableList<String> tipoDeParticionItems) {
        this.tipoDeParticionItems = tipoDeParticionItems;
    }

    public void setAlgoritmoPlanificacionMemFija(ObservableList<String> algoritmoPlanificacionMemFija) {
        this.algoritmoPlanificacionMemFija = algoritmoPlanificacionMemFija;
    }

    public void setAlgoritmoPlanificacionMemVariable(ObservableList<String> algoritmoPlanificacionMemVariable) {
        this.algoritmoPlanificacionMemVariable = algoritmoPlanificacionMemVariable;
    }
}
