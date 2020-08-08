package GUI;

import clases.Proceso;
import clases.TablaProcesos;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Objects;


public class EstadisticaProceso {

    public static Scene getScene() {
        return scene;
    }

    public static Scene scene;

    @FXML
    private Label valorLabel1;

    @FXML
    private Label valorLabel2;

    @FXML
    private Label valorLabel3;

    @FXML
    public void initialize() throws IOException {
        if (TablaProcesos.tablaProcesos!=null){
            valorLabel1.setText(String.valueOf(TablaProcesos.tablaProcesos.tiempoRetornoPromedio()));
            valorLabel2.setText(String.valueOf(TablaProcesos.tablaProcesos.tiempoEsperaPromedio()));
            valorLabel3.setText(String.valueOf(TablaProcesos.tablaProcesos.tiempoRespuestaPromedio()));
        } else {
            valorLabel1.setText("0");
            valorLabel2.setText("0");
            valorLabel3.setText("0");
        }





    }

}
