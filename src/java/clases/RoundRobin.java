package clases;

import java.util.ArrayList;

public class RoundRobin extends Planificador{

    public RoundRobin(Memoria memoria, int quantum) {
        super(memoria);
        this.quantum = quantum;
    }

    protected int quantum;

    protected int quantumActual = 1;

    public void planificar(){
        ArrayList<Proceso> nuevosListos = this.nuevosListos();

        for(Proceso proceso : nuevosListos){
            proceso.setEstado("Listo");
            this.colaListos.add(proceso);
        }

        if(this.colaListos.isEmpty()==false){
            if(this.procesoActual==null){
                this.procesoActual = this.colaListos.get(0);
                this.colaListos.remove(0);
                this.procesoActual.setEstado("Ejecutando");
                quantumActual = 1;
            }else if (quantumActual>quantum) {
                this.colaListos.add(this.procesoActual);
                this.procesoActual.setEstado("Listo");

                this.procesoActual = this.colaListos.get(0);
                this.colaListos.remove(0);
                this.procesoActual.setEstado("Ejecutando");
                quantumActual = 1;  ///////////////
            }
        }

        this.obtenerBloqueadoActual();

        this.agregarGantt();

        this.ejecutarInstrucciones();

        this.comprobarFinalizados();
    }

    @Override
    protected void ejecutarInstrucciones(){
        //  Si tengo un proceso listo para rafaga de cpu y uno para rafaga de E/S, lo ejecuto.
        if(procesoActual!=null & seEjecutoCPU==false){
            this.procesoActual.ejecutar();
            seEjecutoCPU = true;
            quantumActual++;
        }

        if(bloqueadoActual!=null & seEjecutoES==false){
            this.bloqueadoActual.ejecutar();
            seEjecutoES = true;
        }
    }
}

