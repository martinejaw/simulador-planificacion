package clases;

import java.util.ArrayList;
import java.util.Comparator;

public class Fija extends Memoria {
    
    private ArrayList<Particion> listaParticiones = new ArrayList<>();

    protected String fit;

    public Fija(String fit) {
        this.fit = fit;
        if(fit!="FirstFit"){
            this.listaParticiones.sort(Comparator.comparing(Particion::getTamano));
        }
    }
    
    public void crearParticion(int tamanoParticion){
        Particion particionNueva = new Particion();
        particionNueva.setTamano(tamanoParticion);

        //  Busco la ultima particion y su direccion final.
        Particion ultimaParticion = null;
        boolean flag = false;

        for(Particion particion : this.listaParticiones){
            ultimaParticion = particion;
            flag=true;
        }
        if(flag==true){
            particionNueva.setDireccionInicio(ultimaParticion.getDireccionInicio()+ultimaParticion.getTamano());
        } else{
            particionNueva.setDireccionInicio(0);
        }

        this.listaParticiones.add(particionNueva);

        if(tamanoParticion>tamano){
            tamano = tamanoParticion;
        }
    }

    public void alojarProceso(Proceso proceso, Particion particion){    /////////////////revisar
        int direc = particion.getDireccionInicio();
        proceso.setDireccionInicio(direc);
        particion.setProceso(proceso);
        this.listaAuxiliar.remove(proceso);
        this.nuevosAsignados.add(proceso);
    }

    public void ejecutarFit(){
        this.comprobarFinalizados();

        this.listaAuxiliar = new ArrayList<>(this.colaEntrada);

        this.nuevosAsignados = new ArrayList<>();

        this.fit(); //si es best fit, la lista se ordena una vez que se instancia

        this.colaEntrada = this.listaAuxiliar;

        MapaMemoria.anadirMapa(this.mapaMemoriaEsteMomento());

    }

    public void fit(){
        int tamanoProceso;
        int tamanoParticion;
        boolean flag;

        for(Proceso proceso : this.colaEntrada){
            flag = false;
            tamanoProceso = proceso.getTamano();

            for(Particion particion : this.listaParticiones){
                if(particion.estaLibre()){
                    tamanoParticion = particion.getTamano();
                    if (flag==false && tamanoParticion>=tamanoProceso){
                        this.alojarProceso(proceso,particion);
                        flag = true;
                    }
                }
            }
        }
    }
    
    public void comprobarFinalizados(){
        Proceso proceso;

        for(Particion particion : this.listaParticiones){
            if(particion.estaLibre()==false) {
                proceso = particion.getProceso();
                if (proceso.finalizo()) {
                    particion.setProceso(null);
                }
            }
        }
    }

    public ArrayList<ArrayList<Integer>> mapaMemoriaEsteMomento(){
        ArrayList<ArrayList<Integer>> mapa = new ArrayList<>();
        for (Particion particion : this.listaParticiones) {
            ArrayList<Integer> unaParticion = new ArrayList<>();
            unaParticion.add(particion.getId());
            Proceso proceso = particion.getProceso();
            if (proceso != null) {
                unaParticion.add(proceso.getId());
            }else{
                unaParticion.add(-1);
            }
            unaParticion.add(particion.getTamano());
            mapa.add(unaParticion);
        }
        return mapa;
    }
}
