package clases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Variable extends Memoria {

    private ArrayList<clases.Hueco> listaHuecos = new ArrayList<>();

    private String fit;

    private ArrayList<Proceso> listaAlojados = new ArrayList<>();


    public Variable(String fit, int tamano) {
        this.tamano = tamano;
        this.listaHuecos.add(new Hueco(tamano,0));
        this.fit = fit;
    }

    public void setListaHuecos(ArrayList<Hueco> listaHuecos) {
        this.listaHuecos = listaHuecos;
    }
    
    public void compactar() {
        //Limpio los huecos y creo uno nuevo con todo el tamano de la memoria
        this.listaHuecos = new ArrayList<>();
        clases.Hueco huecoTotal = new Hueco();
        huecoTotal.setDireccionInicio(0);
        huecoTotal.setTamano(this.tamano);
        listaHuecos.add(huecoTotal);
        
        //Alojo los procesos uno por uno otra vez (y van a quedar en espacios contiguos)
        ArrayList<Proceso> aux = new ArrayList<>(listaAlojados);
        listaAlojados = new ArrayList<>();

        for(Proceso proceso : aux){
            this.alojarProceso(proceso,huecoTotal);
        }
    }
    
    public void juntarHuecos() {
        int posFinalAnt;
        int tamanoHuecoAnt;
        clases.Hueco huecoAnt;

        int tamanoHuecoAct;
        int posInicialAct;
        clases.Hueco huecoAct;

        ArrayList<Hueco> listaJuntada = new ArrayList<>();
        int j = 0;

        this.listaHuecos.sort(Comparator.comparing(clases.Hueco::getDireccionInicio));

        for(int i=0; i<this.listaHuecos.size(); i++){
            
            huecoAct = this.listaHuecos.get(i);
            posInicialAct = huecoAct.getDireccionInicio();
            tamanoHuecoAct = huecoAct.getTamano();
            
            
            if(listaJuntada.isEmpty()==false){
                huecoAnt = listaJuntada.get(j);
                tamanoHuecoAnt = huecoAnt.getTamano();
                posFinalAnt = huecoAnt.getDireccionInicio()+tamanoHuecoAnt;

                if(posFinalAnt == posInicialAct){
                    huecoAnt.setTamano(tamanoHuecoAnt+tamanoHuecoAct);
                }else{
                    listaJuntada.add(huecoAct);
                    j++;
                }
            }else{
                listaJuntada.add(huecoAct);
            }  
        }
        this.setListaHuecos(listaJuntada);
    }
    
    public void alojarProceso(Proceso proceso, clases.Hueco hueco){
        int direc = hueco.getDireccionInicio();
        proceso.setDireccionInicio(direc);
        this.listaAlojados.add(proceso);
        this.listaAuxiliar.remove(proceso);
        int tamanoHueco = hueco.getTamano();
        int tamanoProceso = proceso.getTamano();
        if(tamanoHueco-tamanoProceso == 0){
            this.listaHuecos.remove(hueco);
        }else{
            hueco.setTamano(tamanoHueco-tamanoProceso);
            hueco.setDireccionInicio(direc+tamanoProceso);
        }
    }
    

    public void firstFit(){
        for(Proceso proceso : this.colaEntrada){
            int sumaTamanos = 0;
            boolean flag = false;
            int tamanoHueco;
            int tamanoProceso = proceso.getTamano();
            ArrayList<Hueco> listaAuxiliar2 = new ArrayList<>(this.listaHuecos);

            for(Hueco hueco : listaAuxiliar2){
                tamanoHueco = hueco.getTamano();
                if (flag==false && tamanoHueco>=tamanoProceso){
                    alojarProceso(proceso,hueco);
                    this.nuevosAsignados.add(proceso);
                    flag = true;
                }
                sumaTamanos = sumaTamanos + tamanoHueco;
            }

            if (flag==false){
                if (sumaTamanos>=tamanoProceso){
                    this.compactar();
                    for(Hueco hueco : listaAuxiliar2){
                        tamanoHueco = hueco.getTamano();
                        if (flag==false && tamanoHueco>=tamanoProceso){
                            alojarProceso(proceso,hueco);
                            this.nuevosAsignados.add(proceso);
                            flag = true;
                        }
                    }
                }
            }
        }
    }

    public void worstFit(){
        int tamanoProceso;

        for(Proceso proceso : this.colaEntrada){
            tamanoProceso = proceso.getTamano();

            if(this.listaHuecos!=null){
                this.listaHuecos.sort(Comparator.comparing(Hueco::getTamano));
                Hueco huecoMinimo = this.listaHuecos.get(0);
                if(huecoMinimo.getTamano()>=tamanoProceso){
                    this.alojarProceso(proceso,huecoMinimo);
                    this.nuevosAsignados.add(proceso);
                }else if(tamanoHuecos()>=tamanoProceso){
                    compactar();
                    huecoMinimo = this.listaHuecos.get(0);
                    this.alojarProceso(proceso,huecoMinimo);
                    this.nuevosAsignados.add(proceso);
                }
            }
        }
    }


    public void comprobarFinalizados(){
        Hueco huecoNuevo;
        int tamano;
        int direcIni;

        this.listaAuxiliar = new ArrayList<>(this.listaAlojados);

        for(Proceso proceso : this.listaAlojados){
            if(proceso.finalizo()){
                huecoNuevo = new Hueco();
                direcIni = proceso.getDireccionInicio();
                tamano = proceso.getTamano();
                huecoNuevo.setTamano(tamano);
                huecoNuevo.setDireccionInicio(direcIni);
                this.listaHuecos.add(huecoNuevo);
                this.listaAuxiliar.remove(proceso);
            }
        }

        this.listaAlojados = this.listaAuxiliar;
    }

    public void ejecutarFit(){
        this.comprobarFinalizados();
        this.juntarHuecos();

        this.listaAuxiliar = new ArrayList<>(this.colaEntrada);

        this.nuevosAsignados = new ArrayList<>();

        if(this.fit=="FirstFit"){
            this.firstFit();
        }else{
            this.worstFit();
        }

        this.colaEntrada = this.listaAuxiliar;

        MapaMemoria.anadirMapa(this.mapaMemoriaEsteMomento());
    }


    public ArrayList<ArrayList<Integer>> mapaMemoriaEsteMomento(){
        ArrayList<ArrayList<Integer>> mapa = new ArrayList<>();
        for (Proceso particion : this.listaAlojados) {
            ArrayList<Integer> unaParticion = new ArrayList<>();
            unaParticion.add(particion.getId());
            unaParticion.add(particion.getDireccionInicio());
            unaParticion.add(particion.getTamano());
            mapa.add(unaParticion);
        }
        for (Hueco particion : this.listaHuecos){
            ArrayList<Integer> unaParticion = new ArrayList<>();
            unaParticion.add(-1);
            unaParticion.add(particion.getDireccionInicio());
            unaParticion.add(particion.getTamano());
            mapa.add(unaParticion);
        }
        Collections.sort(mapa, new Comparator<ArrayList<Integer>>(){
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2){
                if(o1.get(1) == o2.get(1))
                    return 0;
                return o1.get(1) < o2.get(1) ? -1 : 1;
            }
        });
        return mapa;
    }

    private int tamanoHuecos(){
        int suma = 0;
        for(Hueco hueco:this.listaHuecos){
            suma += hueco.getTamano();
        }
        return suma;
    }

}
