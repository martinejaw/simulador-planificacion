package clases;

import java.util.ArrayList;
import java.util.Comparator;

public class SJF extends Planificador {

    public SJF(Memoria memoria) {
        super(memoria);
    }

//                          Operations 

    public void planificar(){

        //  Obtengo y agrego los procesos que ingresan a la cola de listos en este instante.
        ArrayList<Proceso> nuevosListos = this.nuevosListos();

        for(Proceso proceso : nuevosListos){
            proceso.setEstado("Listo");
            this.colaListos.add(proceso);
        }

        // Si no tengo un proceso actual asignado, y la cola de listos no esta vacia,
        // obtengo el proceso de menor tiempo remanente y lo asigno al actual.
        if(this.procesoActual==null && this.colaListos.isEmpty()==false) {
            this.colaListos.sort(Comparator.comparing(Proceso::tiempoRemanente));

            Proceso procesoMinimo = this.colaListos.get(0);
            procesoMinimo.setEstado("Ejecutando");
            this.procesoActual = procesoMinimo;
            this.colaListos.remove(0);
        }

        this.obtenerBloqueadoActual();

        this.agregarGantt();

        this.ejecutarInstrucciones();

        this.comprobarFinalizados();

    }
}

