package clases;

import java.util.ArrayList;

public abstract class MapaMemoria {

    public static ArrayList<ArrayList<ArrayList<Integer>>> mapaTotal = new ArrayList<>();

    public static void anadirMapa(ArrayList<ArrayList<Integer>> mapaEsteTiempo){
        mapaTotal.add(mapaEsteTiempo);
    }

    public static void inicializeMap(){
        mapaTotal.clear();
    }

    public static ArrayList<ArrayList<ArrayList<Integer>>> getMapaTotal() {
        return mapaTotal;
    }
}
