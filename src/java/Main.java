import clases.*;

import javax.swing.*;
import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {


        Proceso p1 = new Proceso();
        p1.setTamano(50);
        Proceso p2 = new Proceso();
        p2.setTamano(40);
        Proceso p3 = new Proceso();
        p3.setTamano(20);

        p1.anadirInstrucciones(1, "CPU");
        p1.anadirInstrucciones(8, "E/S");
        p2.anadirInstrucciones(3, "CPU");
        p3.anadirInstrucciones(5, "CPU");

        TablaProcesos tabla = new TablaProcesos();

        tabla.anadirProceso(p1);
        tabla.anadirProceso(p2);
        tabla.anadirProceso(p3);

        p3.setPrioridad(1);


        Variable memoria = new Variable("FirstFit", 150);
/*
        Fija memoriaFija = new Fija("FirstFit");
        memoriaFija.crearParticion(90);
        memoriaFija.crearParticion(80);
        memoriaFija.crearParticion(5);
 */

        //SJF planificador = new SJF(memoria);
        FCFS planificador = new FCFS(memoria);
 /*
        for (int i = 0; i < 29; i++) {
            memoria.agregarProcesos(tabla.procesosEsteInstante());
            memoria.ejecutarFit();
            planificador.planificar();
            Instante.incrementarInstante();
        }
       */
        Simulador simulador = new Simulador(memoria,planificador,tabla);

        simulador.iniciar();

        int instanteUltimo = Instante.instante;
        int tiempoRetorno1 = p1.tiempoRetorno();
        int tiempoRetorno2 = p2.tiempoRetorno();
        int tiempoRetorno3 = p3.tiempoRetorno();

        int tiempoRespuesta1 = p1.tiempoRespuesta();
        int tiempoRespuesta2 = p2.tiempoRespuesta();
        int tiempoRespuesta3 = p3.tiempoRespuesta();

        int tiempoEspera1 = p1.tiempoEspera();
        int tiempoEspera2 = p2.tiempoEspera();
        int tiempoEspera3 = p3.tiempoEspera();

        double esperaProm = tabla.tiempoEsperaPromedio();
        double respuestaProm = tabla.tiempoRespuestaPromedio();
        double retornoProm = tabla.tiempoRetornoPromedio();
    }

}
