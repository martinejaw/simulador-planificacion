package clases;

import GUI.TablaProcesos;

import java.util.ArrayList;

public class Proceso {

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    private int instruccionActual = 1;

    private String estado;

    private int tiempoCreacion = 0;

    private int tiempoArriboColaListos;

    private int tiempoFinalizacion=-1;

    private int tiempoPrimerRespuesta = -1;

    public static int count = 0;

    private int tamano;

    private int direccionInicio;

    private ArrayList<Instruccion> instrucciones = new ArrayList<>();

    private int prioridad;

    private int tiempoBloqueado=0;

    private int cpu0;

    private int es1;

    private int cpu1;

    private int es2;

    private int cpu2;

    public int getCpu0() {
        return cpu0;
    }

    public void setCpu0(int cpu0) {
        this.cpu0 = cpu0;
    }

    public int getEs1() {
        return es1;
    }

    public void setEs1(int es1) {
        this.es1 = es1;
    }

    public int getCpu1() {
        return cpu1;
    }

    public void setCpu1(int cpu1) {
        this.cpu1 = cpu1;
    }

    public int getEs2() {
        return es2;
    }

    public void setEs2(int es2) {
        this.es2 = es2;
    }

    public int getCpu2() {
        return cpu2;
    }

    public void setCpu2(int cpu2) {
        this.cpu2 = cpu2;
    }

    public Proceso() {
        if (!TablaProcesos.procesosList.isEmpty()){
            count=1;
            for (Proceso proceso : TablaProcesos.procesosList) {
                if (count == proceso.getId()){
                    count++;
                } else {
                    for (Proceso proceso1 : TablaProcesos.procesosList) {
                        if (proceso1.getId()==count){
                            count++;
                        }
                    }
                }
            }
        } else {
            count++;
        }
        this.prioridad = 3;
        this.estado = "NoCreado";
        this.id = count;
    }

    public void reiniciarProceso(){
        //this.tiempoArriboColaListos = 0;
        this.instruccionActual=1;
        //this.tiempoCreacion = 0;
        this.tiempoFinalizacion=-1;
        this.tiempoPrimerRespuesta = -1;
        this.tiempoBloqueado=0;
    }

    public int getTiempoCreacion() {
        return tiempoCreacion;
    }

    public void setTiempoCreacion(int tiempoCreacion) {
        this.tiempoCreacion = tiempoCreacion;
    }

    public void setTiempoArriboColaListos(int tiempoArriboColaListos) {
        this.tiempoArriboColaListos = tiempoArriboColaListos;
    }

    public int getTiempoFinalizacion() {
        return tiempoFinalizacion;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getId() {
        return this.id;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public void ejecutar() {
        this.instruccionActual = this.instruccionActual + 1;
        if(this.tiempoPrimerRespuesta==-1){
            this.tiempoPrimerRespuesta = Instante.instante;
        }
    }

    public static void incrementarCount(){
        count++;
    }

    public static void decrementarCount(){
        count--;
    }

    public int tiempoRemanente() {
        int cantRafaga = 0;
        int iteracion = this.instruccionActual;
        if(this.estado=="Finalizado"){
            return 0;
        }
        String tipoInstruccion = (this.instrucciones.get(this.instruccionActual-1)).getTipo();
        int cantInstrucciones = this.instrucciones.size();

        while(iteracion<=cantInstrucciones && this.instrucciones.get(iteracion-1).getTipo()==tipoInstruccion){
            cantRafaga++;
            iteracion++;
        }
        return cantRafaga;
    }

    public boolean finalizo() {
        if(this.instruccionActual>this.instrucciones.size()){
            if(this.tiempoFinalizacion==-1){
                this.tiempoFinalizacion = Instante.instante;
            }
            return true;
        }else{
            return false;
        }
    }

    public boolean esDeCPU() {
        if(this.instrucciones.isEmpty()==false && this.instrucciones.get(this.instruccionActual-1).getTipo()=="CPU"){
            return true;
        }else{
            return false;
        }
    }

    public void anadirInstrucciones(int cantidad,String tipo) {
        for(int i=0; i<cantidad; i++){
            this.instrucciones.add(new Instruccion(tipo));
        }
    }

    public void clearInstrucciones(){
        this.instrucciones.clear();
    }

    public int tiempoRetorno(){
        return tiempoFinalizacion - tiempoArriboColaListos - tiempoBloqueado;
    }

    public int tiempoRespuesta(){
        return tiempoPrimerRespuesta - tiempoCreacion;
    }

    public int tiempoEspera(){
        //return this.tiempoRetorno() - this.cantDeCPU() + 1;
        return tiempoFinalizacion - tiempoCreacion - instrucciones.size() +1;
    }

    public void tiempoBloqueadoInc() {
        this.tiempoBloqueado++;
    }

    public int cantDeCPU(){
        int cont = 0;
        for(Instruccion instruccion : instrucciones) {
            if (instruccion.getTipo() == "CPU") {
                cont++;
            }
        }
        return cont;
    }
}
