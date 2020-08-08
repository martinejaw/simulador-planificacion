package GUI;


import clases.Proceso;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

public class AgregarProceso{

    @FXML
    private TextField tamañoProcesoInput;

    @FXML
    private TextField tiempoArriboInput;

    @FXML
    private Label prioridadLabel;

    @FXML
    private Spinner prioridadSpinner;

    @FXML
    private TextField cpu0Input;

    @FXML
    private TextField cpu1Input;

    @FXML
    private TextField cpu2Input;

    @FXML
    private TextField entSalInput1;

    @FXML
    private TextField entSalInput2;

    @FXML
    private RadioButton sinEntSalRadioButton;

    @FXML
    private RadioButton unaEntSalRadioButton;

    @FXML
    private RadioButton dosEntSalRadioButton;

    @FXML
    private Button agregarProcesoButton;


    public TextField getTamañoProcesoInput() {
        return tamañoProcesoInput;
    }

    public TextField getTiempoArriboInput() {
        return tiempoArriboInput;
    }

    public Spinner getPrioridadSpinner() {
        return prioridadSpinner;
    }

    public TextField getCpu0Input() {
        return cpu0Input;
    }

    public TextField getCpu1Input() {
        return cpu1Input;
    }

    public TextField getCpu2Input() {
        return cpu2Input;
    }

    public TextField getEntSalInput1() {
        return entSalInput1;
    }

    public TextField getEntSalInput2() {
        return entSalInput2;
    }

    @FXML
    private void initialize() {
        setInputRestrictions();
        tamañoProcesoInput.setPromptText("cantidad");
        tiempoArriboInput.setPromptText("tiempo");

        verifyPrioridadOrMultiNivel();

        SpinnerValueFactory<Integer> valueFactorycant = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, 1);

        prioridadSpinner.setValueFactory(valueFactorycant);
        prioridadSpinner.setEditable(true);

        cpu0Input.setPromptText("cpu 1");
        cpu1Input.setPromptText("cpu 2");
        cpu2Input.setPromptText("cpu 3");
        entSalInput1.setPromptText("e/s 1");
        entSalInput2.setPromptText("e/s 2");

        sinEntSalRadioButtonsChoose();
        radioButtonChoose();
        sinEntSalRadioButton.setOnAction(event -> {
            sinEntSalRadioButtonsChoose();
            radioButtonChoose();
        });

        unaEntSalRadioButton.setOnAction(event -> {
            unaEntSalRadioButtonChoose();
            radioButtonChoose();
        });
        dosEntSalRadioButton.setOnAction(event -> {
            dosEntSalRadioButtonChoose();
            radioButtonChoose();
        });
    }

    private void setInputRestrictions() {
        tamañoProcesoInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tamañoProcesoInput.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        tamañoProcesoInput.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue && !tamañoProcesoInput.getText().equals("")) {
                if (Integer.parseInt(tamañoProcesoInput.getText()) < 3) {
                    tamañoProcesoInput.setText("3");
                }
                if (Integer.parseInt(tamañoProcesoInput.getText()) > 50) {
                    tamañoProcesoInput.setText("50");
                }
            }
        });

        tiempoArriboInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tiempoArriboInput.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        tiempoArriboInput.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue && !tiempoArriboInput.getText().equals("")) {
                if (Integer.parseInt(tiempoArriboInput.getText()) < 0) {
                    tiempoArriboInput.setText("0");
                }
                if (Integer.parseInt(tiempoArriboInput.getText()) > 50) {
                    tiempoArriboInput.setText("50");
                }
            }
        });

        cpu0Input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                cpu0Input.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        cpu0Input.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue && !cpu0Input.getText().equals("")) {
                if (Integer.parseInt(cpu0Input.getText()) < 1) {
                    cpu0Input.setText("1");
                }
                if (Integer.parseInt(cpu0Input.getText()) > 20) {
                    cpu0Input.setText("20");
                }
            }
        });

        entSalInput1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                entSalInput1.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        entSalInput1.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue && !entSalInput1.getText().equals("")) {
                if (Integer.parseInt(entSalInput1.getText()) < 1) {
                    entSalInput1.setText("1");
                }
                if (Integer.parseInt(entSalInput1.getText()) > 20) {
                    entSalInput1.setText("20");
                }
            }
        });

        cpu1Input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                cpu1Input.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        cpu1Input.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue && !cpu1Input.getText().equals("")) {
                if (Integer.parseInt(cpu1Input.getText()) < 1) {
                    cpu1Input.setText("1");
                }
                if (Integer.parseInt(cpu1Input.getText()) > 20) {
                    cpu1Input.setText("20");
                }
            }
        });

        entSalInput2.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                entSalInput2.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        entSalInput2.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue && !entSalInput2.getText().equals("")) {
                if (Integer.parseInt(entSalInput2.getText()) < 1) {
                    entSalInput2.setText("1");
                }
                if (Integer.parseInt(entSalInput2.getText()) > 20) {
                    entSalInput2.setText("20");
                }
            }
        });

        cpu2Input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                cpu2Input.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        cpu2Input.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue && !cpu2Input.getText().equals("")) {
                if (Integer.parseInt(cpu2Input.getText()) < 1) {
                    cpu2Input.setText("1");
                }
                if (Integer.parseInt(cpu2Input.getText()) > 20) {
                    cpu2Input.setText("20");
                }
            }
        });

        prioridadSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue){
                commitEditorText(prioridadSpinner);
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
    private void radioButtonChoose(){
        if (sinEntSalRadioButton.isSelected()){
            cpu0Input.setDisable(false);
            entSalInput1.setDisable(true);
            cpu1Input.setDisable(true);
            entSalInput2.setDisable(true);
            cpu2Input.setDisable(true);
        } else {
            if (unaEntSalRadioButton.isSelected()){
                cpu0Input.setDisable(false);
                entSalInput1.setDisable(false);
                cpu1Input.setDisable(false);
                entSalInput2.setDisable(true);
                cpu2Input.setDisable(true);
            } else {
                if (dosEntSalRadioButton.isSelected()){
                    cpu0Input.setDisable(false);
                    entSalInput1.setDisable(false);
                    cpu1Input.setDisable(false);
                    entSalInput2.setDisable(false);
                    cpu2Input.setDisable(false);
                }

            }
        }
    }

    @FXML
    private void sinEntSalRadioButtonsChoose(){
        if (!sinEntSalRadioButton.isSelected()){
            sinEntSalRadioButton.setSelected(true);
        }
        unaEntSalRadioButton.setSelected(false);
        dosEntSalRadioButton.setSelected(false);
    }

    @FXML
    private  void unaEntSalRadioButtonChoose(){
        if (!unaEntSalRadioButton.isSelected()){
            unaEntSalRadioButton.setSelected(true);
        }
        sinEntSalRadioButton.setSelected(false);
        dosEntSalRadioButton.setSelected(false);
    }

    @FXML
    private  void dosEntSalRadioButtonChoose(){
        if (!dosEntSalRadioButton.isSelected()){
            dosEntSalRadioButton.setSelected(true);
        }
        sinEntSalRadioButton.setSelected(false);
        unaEntSalRadioButton.setSelected(false);
    }

    @FXML
    private void agregarProceso() throws IOException {

        Proceso proceso = new Proceso();
        proceso.setTamano(Integer.valueOf(this.tamañoProcesoInput.getText()));
        proceso.setTiempoCreacion(Integer.parseInt(this.tiempoArriboInput.getText()));
        if (!Home.prioridadSelected && !Home.conColasMultiSelected){
            proceso.setPrioridad(3);
        } else {
            proceso.setPrioridad( (int) this.prioridadSpinner.getValue());
        }

        //proceso.anadirInstrucciones(Integer.parseInt(this.cpu0Input.getText()), "CPU");
        proceso.setCpu0(Integer.parseInt(this.cpu0Input.getText()));
        if (!entSalInput1.isDisabled()){
            proceso.setEs1(Integer.parseInt(this.entSalInput1.getText()));
            //proceso.anadirInstrucciones(Integer.parseInt(this.entSalInput1.getText()), "E/S");
        }
        if (!cpu1Input.isDisabled()) {
            proceso.setCpu1(Integer.parseInt(this.cpu1Input.getText()));
            //proceso.anadirInstrucciones(Integer.parseInt(this.cpu1Input.getText()), "CPU");
        }
        if (!entSalInput2.isDisabled()) {
            proceso.setEs2(Integer.parseInt(this.entSalInput2.getText()));
            //proceso.anadirInstrucciones(Integer.parseInt(this.entSalInput2.getText()), "E/S");
        }
        if (!cpu2Input.isDisabled()) {
            proceso.setCpu2(Integer.parseInt(this.cpu2Input.getText()));
            //proceso.anadirInstrucciones(Integer.parseInt(this.cpu2Input.getText()), "CPU");
        }

        //Almacena el proceso en La Observable List para mostrarla en la UI

        ObservableList<Proceso> observableListToStore = TablaProcesos.getProcesosList();
        observableListToStore.add(proceso);
        TablaProcesos.setProcesosList(observableListToStore);

        FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("TablaController.fxml")));

        Scene scene = agregarProcesoButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        stage.close();

    }

    private void verifyPrioridadOrMultiNivel(){
        if (!Home.prioridadSelected && !Home.conColasMultiSelected){
            prioridadSpinner.setDisable(true);
        } else {
            prioridadLabel.setDisable(false);
        }
    }

}
