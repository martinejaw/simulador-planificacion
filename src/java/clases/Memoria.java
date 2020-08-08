package clases;

import java.util.ArrayList;

public abstract class Memoria {

    protected int tamano;

    protected ArrayList<Proceso> colaEntrada = new ArrayList<>();

    protected ArrayList<Proceso> nuevosAsignados = new ArrayList<>();

    protected ArrayList<Proceso> listaAuxiliar;

    public void setTamano(Integer tamano) {
        this.tamano = tamano;
    }
    
    public ArrayList<Proceso> getNuevosAsignados() {
        return this.nuevosAsignados;
    }
    
    public void agregarProcesos(ArrayList<Proceso> procesosParaAgregar, TablaProcesos tabla) {
        for(Proceso proceso : procesosParaAgregar){
            if(proceso.getTamano()<=tamano) {
                proceso.setEstado("ColaEntrada");
                this.colaEntrada.add(proceso);
            }else{
                tabla.quitarProceso(proceso);
            }
        }
    } 

    abstract void comprobarFinalizados();

    abstract void ejecutarFit();

}
