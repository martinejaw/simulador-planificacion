package clases;

import java.util.concurrent.atomic.AtomicInteger;

public class Particion {

    private int id;

    private int tamano;

    private int direccionInicio;

    private Proceso proceso;

    private static final AtomicInteger count = new AtomicInteger(0);

    public Particion() {
        this.id = count.incrementAndGet();
    }

    public static void initializeCount(){
        count.set(0);
    }
    
    public int getTamano() {
        return this.tamano;
    }

    public void setTamano(Integer tamano) {
        this.tamano = tamano;
    }

    public int getDireccionInicio() {
        return this.direccionInicio;
    }

    public void setDireccionInicio(Integer direccionInicio) {
        this.direccionInicio = direccionInicio;
    }
    
    public Proceso getProceso() {
        return this.proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //                          Operations

    public boolean estaLibre() {
        if(this.proceso==null){
            return true;
        }else{
            return false;
        }
    }
}
