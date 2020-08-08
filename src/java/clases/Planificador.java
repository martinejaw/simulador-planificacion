package clases;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Planificador{

    public Planificador(Memoria memoria) {
        this.memoria = memoria;
        this.nroCola = 0;
    }

    public static boolean seEjecutoCPU;
    public static boolean seEjecutoES;


    protected Proceso procesoActual;

    protected Proceso bloqueadoActual;

    protected int nroCola;

    protected ArrayList<Proceso> colaListos = new ArrayList<Proceso>();
    
    protected ArrayList<Proceso> colaBloqueados = new ArrayList<Proceso>();

    protected Memoria memoria;

    public Proceso getProcesoActual() {
        return this.procesoActual;
    }
    public Proceso getBloqueadoActual() {
        return this.bloqueadoActual;
    }
    public ArrayList<Proceso> getColaListos() {
        return this.colaListos;
    }
    public ArrayList<Proceso> getColaBloqueados() {
        return this.colaBloqueados;
    }
    public Memoria getMemoria() {
        return this.memoria;
    }

    public void setProcesoActual(Proceso procesoActual) {
        this.procesoActual = procesoActual;
    }
    public void setBloqueadoActual(Proceso bloqueadoActual) {
        this.bloqueadoActual =bloqueadoActual;
    }
    public void setColaListos(ArrayList<Proceso> colaListos) {
        this.colaListos = colaListos;
    }
    public void setColaBloqueados(ArrayList<Proceso> colaBloqueados) {
        this.colaBloqueados = colaBloqueados;
    }
    public void setMemoria(Memoria memoria) {
        this.memoria = memoria;
    }

    public int getNroCola() {
        return nroCola;
    }

    public void setNroCola(int nroCola) {
        this.nroCola = nroCola;
    }

    //                          Operations

    abstract void planificar();
    

    //  Si el proceso actual o el bloqueado actual finalizaron, setea el respectivo atributo a Nulo.
    //  Tambien comprueba si el proceso actual cambio a Instrucciones E/S, si es asi, lo pasa a la cola de bloqueados.
    public void comprobarFinalizados() {

        for(Proceso bloqueado : colaBloqueados){
            bloqueado.tiempoBloqueadoInc();
        }

        if(this.bloqueadoActual!=null){
            this.bloqueadoActual.tiempoBloqueadoInc();
            if(this.bloqueadoActual.finalizo()){
                this.bloqueadoActual.setEstado("Finalizado");
                this.bloqueadoActual = null;
            }
        }

        if(this.procesoActual !=null){
            if(this.procesoActual.finalizo()){
                this.procesoActual.setEstado("Finalizado");
                this.procesoActual = null;
            }else if(!this.procesoActual.esDeCPU()){
                this.colaBloqueados.add(procesoActual);
                this.procesoActual.setEstado("Bloqueado");
                this.procesoActual = null;
            }
        }
    } 


    //  Devuelve los procesos que entraron a la cola de listos en el instante actual.
    public ArrayList<Proceso> nuevosListos() {

        ArrayList<Proceso> procesosNuevos = memoria.getNuevosAsignados();

        if(this.nroCola!=0){
            ArrayList<Proceso> nuevosListos = new ArrayList<>();
            for (Proceso proceso: procesosNuevos) {
                if(proceso.getPrioridad()==this.nroCola){
                    nuevosListos.add(proceso);
                }
            }

            for(Proceso proceso : nuevosListos){
                proceso.setTiempoArriboColaListos(Instante.instante);
            }

            if(this.bloqueadoActual!=null && this.bloqueadoActual.esDeCPU()==true){
                nuevosListos.add(bloqueadoActual);
                this.bloqueadoActual = null;
            }

            return nuevosListos;

        }else{
            for(Proceso procesoNuevo : procesosNuevos){
                procesoNuevo.setTiempoArriboColaListos(Instante.instante);
            }
            if(this.bloqueadoActual!=null && this.bloqueadoActual.esDeCPU()==true){
                procesosNuevos.add(bloqueadoActual);
                this.bloqueadoActual = null;
            }
            return procesosNuevos;
        }
    }

    public static void nuevoCiclo(){
        seEjecutoES = false;
        seEjecutoCPU = false;
    }

    public void agregarGantt(){
        if(seEjecutoCPU==false){
            if(this.procesoActual !=null){
                int id = procesoActual.getId();
                switch (nroCola) {
                    case 0:     Gantt.agregarCPU1(id);
                        break;
                    case 1:     Gantt.agregarCPU1(id);
                        break;
                    case 2:     Gantt.agregarCPU2(id);
                        break;
                    case 3:     Gantt.agregarCPU3(id);
                        break;
                }
            }else{
                switch (nroCola) {
                    case 0:     Gantt.agregarCPU1(-1);
                        break;
                    case 1:     Gantt.agregarCPU1(-1);
                        break;
                    case 2:     Gantt.agregarCPU2(-1);
                        break;
                    case 3:     Gantt.agregarCPU3(-1);
                        break;
                }
            }
        }else{
            switch (nroCola) {
                case 0:     Gantt.agregarCPU1(-1);
                    break;
                case 1:     Gantt.agregarCPU1(-1);
                    break;
                case 2:     Gantt.agregarCPU2(-1);
                    break;
                case 3:     Gantt.agregarCPU3(-1);
                    break;
            }
        }

        if(seEjecutoES==false) {
            if (this.bloqueadoActual != null) {
                Gantt.agregarBloqueado(bloqueadoActual.getId());
            } else {
                if(nroCola==0 || nroCola==3) {
                    Gantt.agregarBloqueado(-1);
                }
            }
        }
    }

    protected void ejecutarInstrucciones(){
        //  Si tengo un proceso listo para rafaga de cpu y uno para rafaga de E/S, lo ejecuto.
        if(procesoActual!=null & seEjecutoCPU==false){
            this.procesoActual.ejecutar();
            seEjecutoCPU = true;
        }

        if(bloqueadoActual!=null & seEjecutoES==false){
            this.bloqueadoActual.ejecutar();
            seEjecutoES = true;
        }
    }

    protected void obtenerBloqueadoActual(){
        //  Si no tengo ningun proceso utilizando la E/S, selecciono el primero de la cola de bloqueados, lo pongo como actual,
        //  y lo elimino de la misma.
        if(this.bloqueadoActual==null){
            if(this.colaBloqueados.isEmpty()==false){
                this.bloqueadoActual = this.colaBloqueados.get(0);
                this.colaBloqueados.remove(0);
                this.bloqueadoActual.setEstado("E/S");
            }
        }
    }

}
