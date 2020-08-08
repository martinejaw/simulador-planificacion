package clases;

import java.util.ArrayList;

public class FCFS extends Planificador{

    public FCFS(Memoria memoria) {
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

        //  Si no tengo ningun proceso para ejecutar, selecciono el primero de la cola de listos, lo pongo como actual, y lo elimino de la misma.
        if(this.procesoActual==null && colaListos.isEmpty()==false){
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

