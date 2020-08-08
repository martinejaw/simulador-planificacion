import GUI.SqliteDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Starter extends Application{


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("HomeController.fxml"));

        stage.setTitle("Simulador");
        stage.setScene(new Scene(root, 700, 500));
        stage.show();
    }

}



/*
        ArrayList<Proceso> procesosList = new ArrayList<>();

        Proceso p1 = new Proceso();
        p1.setTamano(50);
        Proceso p2 = new Proceso();
        p2.setTamano(40);
        Proceso p3 = new Proceso();
        p3.setTamano(20);

        p1.anadirInstrucciones(8, "CPU");
        p2.anadirInstrucciones(8, "CPU");
        p3.anadirInstrucciones(8, "CPU");

        procesosList.add(p1);
        procesosList.add(p2);
        procesosList.add(p3);

        p3.setPrioridad(1);


        Variable memoria = new Variable("FirstFit", 150);

        memoria.agregarProcesos(procesosList);

        Fija memoriaFija = new Fija("FirstFit");
        memoriaFija.crearParticion(90);
        memoriaFija.crearParticion(80);
        memoriaFija.crearParticion(5);

        memoriaFija.agregarProcesos(procesosList);


        Prioridad planificador = new Prioridad(memoria);

        for (int i = 0; i < 16; i++) {
            memoria.ejecutarFit();
            planificador.planificar();
        }
        */