package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.Objects;

public class HomeMultiNivel {

    public static HomeMultiNivel getHomeMultiNivel() {
        return homeMultiNivel;
    }

    public static HomeMultiNivel homeMultiNivel = new HomeMultiNivel();

    public static Scene scene;

    public static Scene getScene() {
        return scene;
    }

    public static String algoritmoPlanificacionChoiceBox1Value;

    public static String algoritmoPlanificacionChoiceBox2Value;

    public static String algoritmoPlanificacionChoiceBox3Value;

    public static String quantum1Value;

    public static String quantum2Value;

    public static String quantum3Value;

    public static String tipoParticionValue;

    public static String algoritmoPlanificacionMemoria;

    public static String algoritmoPlanificacionMem;

    public static String tamañoMemoriaInputValue;

    public static int tamañoSOValue;

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
    private ChoiceBox<String> algoritmoPlanificacionChoicebox1;

    @FXML
    private ChoiceBox<String> algoritmoPlanificacionChoicebox2;

    @FXML
    private ChoiceBox<String> algoritmoPlanificacionChoicebox3;

    @FXML
    private Label quantumLabel1;

    @FXML
    private ChoiceBox<String> quantumChoicebox1;

    @FXML
    private Label quantumLabel2;

    @FXML
    private ChoiceBox<String> quantumChoicebox2;

    @FXML
    private Label quantumLabel3;

    @FXML
    private ChoiceBox<String> quantumChoicebox3;

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


    private ObservableList<String> algoritmosPlanificacionItems = FXCollections.observableArrayList("Round Robin", "FCFS", "SRTF", "SJF");

    private ObservableList<String> algoritmosPlanificacionItemsSinFCFS = FXCollections.observableArrayList("Round Robin", "SRTF", "SJF");

    private ObservableList<String> quantumItems = FXCollections.observableArrayList("1","2","3","4","5");

    private ObservableList<String> tipoDeParticionItems = FXCollections.observableArrayList("FIJA","VARIABLE");

    private ObservableList<String> algoritmoPlanificacionMemFija = FXCollections.observableArrayList("First-Fit","Best-Fit");

    private ObservableList<String> algoritmoPlanificacionMemVariable = FXCollections.observableArrayList("First-Fit","Worst-Fit");


    @FXML
    private void initialize(){
        setInputRestrictions();
        conColasMultiRadioButton.setSelected(true);

        algoritmoPlanificacionChoicebox1.setValue("SRTF");
        algoritmoPlanificacionChoicebox1.setItems(algoritmosPlanificacionItemsSinFCFS);
        algoritmoPlanificacionChoicebox1.setOnAction( event -> displayDropdownAlgoritmoPlanificacion1());
        quantumChoicebox1.setItems(quantumItems);
        quantumLabel1.setVisible(false);
        quantumChoicebox1.setVisible(false);

        algoritmoPlanificacionChoicebox2.setValue("FCFS");
        algoritmoPlanificacionChoicebox2.setItems(algoritmosPlanificacionItems);
        algoritmoPlanificacionChoicebox2.setOnAction( event -> displayDropdownAlgoritmoPlanificacion2());
        quantumChoicebox2.setItems(quantumItems);
        quantumLabel2.setVisible(false);
        quantumChoicebox2.setVisible(false);


        algoritmoPlanificacionChoicebox3.setValue("FCFS");
        algoritmoPlanificacionChoicebox3.setItems(algoritmosPlanificacionItems);
        algoritmoPlanificacionChoicebox3.setOnAction( event -> displayDropdownAlgoritmoPlanificacion3());
        quantumChoicebox3.setItems(quantumItems);
        quantumLabel3.setVisible(false);
        quantumChoicebox3.setVisible(false);

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
    private void displayDropdownTipoParticion(){
        String tipoOption = tipoDeParticionChoicebox.getValue();
        if (tipoOption.equals("FIJA")){
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
    private void displayDropdownAlgoritmoPlanificacion1(){
        String algoritmoOption = algoritmoPlanificacionChoicebox1.getValue();
        if (algoritmoOption.equals("Round Robin")){
            quantumLabel1.setVisible(true);
            quantumChoicebox1.setVisible(true);
        } else {
            quantumLabel1.setVisible(false);
            quantumChoicebox1.setVisible(false);
        }
    }

    @FXML
    private void displayDropdownAlgoritmoPlanificacion2(){
        String algoritmoOption = algoritmoPlanificacionChoicebox2.getValue();
        if (algoritmoOption.equals("Round Robin")){
            quantumLabel2.setVisible(true);
            quantumChoicebox2.setVisible(true);
        } else {
            quantumLabel2.setVisible(false);
            quantumChoicebox2.setVisible(false);
        }
    }

    @FXML
    private void displayDropdownAlgoritmoPlanificacion3(){
        String algoritmoOption = algoritmoPlanificacionChoicebox3.getValue();
        if (algoritmoOption.equals("Round Robin")){
            quantumLabel3.setVisible(true);
            quantumChoicebox3.setVisible(true);
        } else {
            quantumLabel3.setVisible(false);
            quantumChoicebox3.setVisible(false);
        }
    }

    @FXML
    private void chooseColas(){
        if (sinColasMultiRadioButton.isSelected()) {
            conColasMultiRadioButton.setSelected(false);
            sinColasMultiRadioButton.setSelected(true);
        }
        if (conColasMultiRadioButton.isSelected()) {
            conColasMultiRadioButton.setSelected(true);
            sinColasMultiRadioButton.setSelected(false);
        }
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

    @FXML
    private void chooseConColas(){
        conColasMultiRadioButton.setSelected(true);
    }

    @FXML
    private void chooseSinColas() throws IOException {
        Home.conColasMultiSelected=false;

        conColasMultiRadioButton.setSelected(true);
        sinColasMultiRadioButton.setSelected(false);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("HomeController.fxml")));

        Scene scene = siguiente.getScene();
        HomeMultiNivel.scene = scene;
        Window window = scene.getWindow();
        Stage stage = (Stage) window;

        if (Home.getScene()==null){
            stage.setScene(new Scene(root,700,500));
        } else {
            stage.setScene(Home.getScene());
        }
        stage.show();
    }

    @FXML
    public void goToProcessTable() throws IOException {
        algoritmoPlanificacionChoiceBox1Value = algoritmoPlanificacionChoicebox1.getValue();
        algoritmoPlanificacionChoiceBox2Value = algoritmoPlanificacionChoicebox2.getValue();
        algoritmoPlanificacionChoiceBox3Value = algoritmoPlanificacionChoicebox3.getValue();

        homeMultiNivel.setParticionUno(particionUno);
        homeMultiNivel.setParticionDos(particionDos);
        homeMultiNivel.setParticionTres(particionTres);
        homeMultiNivel.setParticionCuatro(particionCuatro);
        homeMultiNivel.setParticionCinco(particionCinco);
        homeMultiNivel.setParticionSeis(particionSeis);

        quantum1Value = quantumChoicebox1.getValue();
        quantum2Value = quantumChoicebox2.getValue();
        quantum3Value = quantumChoicebox3.getValue();

        tipoParticionValue =tipoDeParticionChoicebox.getValue();
        algoritmoPlanificacionMemoria = algoritmoPlanificacionMemoriaChoiceBox.getValue();
        algoritmoPlanificacionMem = tipoDeParticionChoicebox.getValue();

        tamañoMemoriaInputValue = tamañoMemoriaInput.getText();
        if (tipoDeParticionChoicebox.getValue().equals("VARIABLE")){
            tamañoSOValue = Integer.parseInt(porcentajeSOInput.getText());
        } else {
            tamañoSOValue = tamañoParaSOSpinner.getValue();
        }

        //Pasando de tabla
        TablaProcesos.conMultiNivel = true;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("TablaController.fxml")));

        Scene scene = siguiente.getScene();
        HomeMultiNivel.scene = scene;
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

    public void setVboxContainer(VBox vboxContainer) {
        this.vboxContainer = vboxContainer;
    }

    public Button getCargaDeMemyPlan() {
        return cargaDeMemyPlan;
    }

    public void setCargaDeMemyPlan(Button cargaDeMemyPlan) {
        this.cargaDeMemyPlan = cargaDeMemyPlan;
    }

    public Button getGuardarMemYPlan() {
        return guardarMemYPlan;
    }

    public void setGuardarMemYPlan(Button guardarMemYPlan) {
        this.guardarMemYPlan = guardarMemYPlan;
    }

    public RadioButton getSinColasMultiRadioButton() {
        return sinColasMultiRadioButton;
    }

    public void setSinColasMultiRadioButton(RadioButton sinColasMultiRadioButton) {
        this.sinColasMultiRadioButton = sinColasMultiRadioButton;
    }

    public RadioButton getConColasMultiRadioButton() {
        return conColasMultiRadioButton;
    }

    public void setConColasMultiRadioButton(RadioButton conColasMultiRadioButton) {
        this.conColasMultiRadioButton = conColasMultiRadioButton;
    }

    public ChoiceBox<String> getAlgoritmoPlanificacionChoicebox1() {
        return algoritmoPlanificacionChoicebox1;
    }

    public void setAlgoritmoPlanificacionChoicebox1(ChoiceBox<String> algoritmoPlanificacionChoicebox1) {
        this.algoritmoPlanificacionChoicebox1 = algoritmoPlanificacionChoicebox1;
    }

    public ChoiceBox<String> getAlgoritmoPlanificacionChoicebox2() {
        return algoritmoPlanificacionChoicebox2;
    }

    public void setAlgoritmoPlanificacionChoicebox2(ChoiceBox<String> algoritmoPlanificacionChoicebox2) {
        this.algoritmoPlanificacionChoicebox2 = algoritmoPlanificacionChoicebox2;
    }

    public ChoiceBox<String> getAlgoritmoPlanificacionChoicebox3() {
        return algoritmoPlanificacionChoicebox3;
    }

    public void setAlgoritmoPlanificacionChoicebox3(ChoiceBox<String> algoritmoPlanificacionChoicebox3) {
        this.algoritmoPlanificacionChoicebox3 = algoritmoPlanificacionChoicebox3;
    }

    public Label getQuantumLabel1() {
        return quantumLabel1;
    }

    public void setQuantumLabel1(Label quantumLabel1) {
        this.quantumLabel1 = quantumLabel1;
    }

    public ChoiceBox<String> getQuantumChoicebox1() {
        return quantumChoicebox1;
    }

    public void setQuantumChoicebox1(ChoiceBox<String> quantumChoicebox1) {
        this.quantumChoicebox1 = quantumChoicebox1;
    }

    public Label getQuantumLabel2() {
        return quantumLabel2;
    }

    public void setQuantumLabel2(Label quantumLabel2) {
        this.quantumLabel2 = quantumLabel2;
    }

    public ChoiceBox<String> getQuantumChoicebox2() {
        return quantumChoicebox2;
    }

    public void setQuantumChoicebox2(ChoiceBox<String> quantumChoicebox2) {
        this.quantumChoicebox2 = quantumChoicebox2;
    }

    public Label getQuantumLabel3() {
        return quantumLabel3;
    }

    public void setQuantumLabel3(Label quantumLabel3) {
        this.quantumLabel3 = quantumLabel3;
    }

    public ChoiceBox<String> getQuantumChoicebox3() {
        return quantumChoicebox3;
    }

    public void setQuantumChoicebox3(ChoiceBox<String> quantumChoicebox3) {
        this.quantumChoicebox3 = quantumChoicebox3;
    }

    public ChoiceBox<String> getTipoDeParticionChoicebox() {
        return tipoDeParticionChoicebox;
    }

    public void setTipoDeParticionChoicebox(ChoiceBox<String> tipoDeParticionChoicebox) {
        this.tipoDeParticionChoicebox = tipoDeParticionChoicebox;
    }

    public ChoiceBox<String> getAlgoritmoPlanificacionMemoriaChoiceBox() {
        return algoritmoPlanificacionMemoriaChoiceBox;
    }

    public void setAlgoritmoPlanificacionMemoriaChoiceBox(ChoiceBox<String> algoritmoPlanificacionMemoriaChoiceBox) {
        this.algoritmoPlanificacionMemoriaChoiceBox = algoritmoPlanificacionMemoriaChoiceBox;
    }

    public Label getCantidadParticionesLabel() {
        return cantidadParticionesLabel;
    }

    public void setCantidadParticionesLabel(Label cantidadParticionesLabel) {
        this.cantidadParticionesLabel = cantidadParticionesLabel;
    }

    public Spinner<Integer> getCantidadParticionesSpinner() {
        return cantidadParticionesSpinner;
    }

    public void setCantidadParticionesSpinner(Spinner<Integer> cantidadParticionesSpinner) {
        this.cantidadParticionesSpinner = cantidadParticionesSpinner;
    }

    public Label getTamañoParaSOLabel() {
        return tamañoParaSOLabel;
    }

    public void setTamañoParaSOLabel(Label tamañoParaSOLabel) {
        this.tamañoParaSOLabel = tamañoParaSOLabel;
    }

    public Spinner<Integer> getTamañoParaSOSpinner() {
        return tamañoParaSOSpinner;
    }

    public void setTamañoParaSOSpinner(Spinner<Integer> tamañoParaSOSpinner) {
        this.tamañoParaSOSpinner = tamañoParaSOSpinner;
    }

    public Label getTamañoMemoriaLabel() {
        return tamañoMemoriaLabel;
    }

    public void setTamañoMemoriaLabel(Label tamañoMemoriaLabel) {
        this.tamañoMemoriaLabel = tamañoMemoriaLabel;
    }

    public TextField getTamañoMemoriaInput() {
        return tamañoMemoriaInput;
    }

    public void setTamañoMemoriaInput(TextField tamañoMemoriaInput) {
        this.tamañoMemoriaInput = tamañoMemoriaInput;
    }

    public Label getPorcentajeSOLabel() {
        return porcentajeSOLabel;
    }

    public void setPorcentajeSOLabel(Label porcentajeSOLabel) {
        this.porcentajeSOLabel = porcentajeSOLabel;
    }

    public TextField getPorcentajeSOInput() {
        return porcentajeSOInput;
    }

    public void setPorcentajeSOInput(TextField porcentajeSOInput) {
        this.porcentajeSOInput = porcentajeSOInput;
    }

    public TextField getParticionUno() {
        return particionUno;
    }

    public void setParticionUno(TextField particionUno) {
        this.particionUno = particionUno;
    }

    public TextField getParticionDos() {
        return particionDos;
    }

    public void setParticionDos(TextField particionDos) {
        this.particionDos = particionDos;
    }

    public TextField getParticionTres() {
        return particionTres;
    }

    public void setParticionTres(TextField particionTres) {
        this.particionTres = particionTres;
    }

    public TextField getParticionCuatro() {
        return particionCuatro;
    }

    public void setParticionCuatro(TextField particionCuatro) {
        this.particionCuatro = particionCuatro;
    }

    public TextField getParticionCinco() {
        return particionCinco;
    }

    public void setParticionCinco(TextField particionCinco) {
        this.particionCinco = particionCinco;
    }

    public TextField getParticionSeis() {
        return particionSeis;
    }

    public void setParticionSeis(TextField particionSeis) {
        this.particionSeis = particionSeis;
    }

    public Button getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Button siguiente) {
        this.siguiente = siguiente;
    }

    public ObservableList<String> getAlgoritmosPlanificacionItems() {
        return algoritmosPlanificacionItems;
    }

    public void setAlgoritmosPlanificacionItems(ObservableList<String> algoritmosPlanificacionItems) {
        this.algoritmosPlanificacionItems = algoritmosPlanificacionItems;
    }

    public ObservableList<String> getAlgoritmosPlanificacionItemsSinFCFS() {
        return algoritmosPlanificacionItemsSinFCFS;
    }

    public void setAlgoritmosPlanificacionItemsSinFCFS(ObservableList<String> algoritmosPlanificacionItemsSinFCFS) {
        this.algoritmosPlanificacionItemsSinFCFS = algoritmosPlanificacionItemsSinFCFS;
    }

    public ObservableList<String> getQuantumItems() {
        return quantumItems;
    }

    public void setQuantumItems(ObservableList<String> quantumItems) {
        this.quantumItems = quantumItems;
    }

    public ObservableList<String> getTipoDeParticionItems() {
        return tipoDeParticionItems;
    }

    public void setTipoDeParticionItems(ObservableList<String> tipoDeParticionItems) {
        this.tipoDeParticionItems = tipoDeParticionItems;
    }

    public ObservableList<String> getAlgoritmoPlanificacionMemFija() {
        return algoritmoPlanificacionMemFija;
    }

    public void setAlgoritmoPlanificacionMemFija(ObservableList<String> algoritmoPlanificacionMemFija) {
        this.algoritmoPlanificacionMemFija = algoritmoPlanificacionMemFija;
    }

    public ObservableList<String> getAlgoritmoPlanificacionMemVariable() {
        return algoritmoPlanificacionMemVariable;
    }

    public void setAlgoritmoPlanificacionMemVariable(ObservableList<String> algoritmoPlanificacionMemVariable) {
        this.algoritmoPlanificacionMemVariable = algoritmoPlanificacionMemVariable;
    }
}
