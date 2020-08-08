package clases;

import java.util.ArrayList;
import java.util.Comparator;

public class Prioridad extends Planificador{

    public Prioridad(Memoria memoria) {
        super(memoria);
    }

    //                          Operations

    public void planificar(){
        
        //  Obtengo y agrego los procesos que ingresan a la cola de listos en este instante.
        ArrayList<Proceso> nuevosListos;

        nuevosListos = this.nuevosListos();
        for(Proceso proceso : nuevosListos){
            proceso.setEstado("Listo");
            this.colaListos.add(proceso);
        }

        //  Si no tengo ningun proceso para ejecutar, selecciono el que tiene mas prioridad, lo pongo como actual.
        if(this.procesoActual==null && colaListos.isEmpty()==false) {
            //  Ordeno por prioridad
            this.colaListos.sort(Comparator.comparing(clases.Proceso::getPrioridad));

            this.procesoActual = this.colaListos.get(0);
            this.colaListos.remove(0);
            this.procesoActual.setEstado("Ejecutando");
        }

        this.obtenerBloqueadoActual();

        this.agregarGantt();

        this.ejecutarInstrucciones();

        this.comprobarFinalizados();

    }
    

}

