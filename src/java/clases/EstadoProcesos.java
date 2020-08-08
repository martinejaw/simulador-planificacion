package clases;

import java.util.ArrayList;

public class EstadoProcesos {

    public static ArrayList<ArrayList<Integer>> mapaProcesos = new ArrayList<>();
    public static ArrayList<ArrayList<String>> mapaEstados = new ArrayList<>();

    public static void anadirProcesos(ArrayList<Integer> mapaEsteTiempo){
        mapaProcesos.add(mapaEsteTiempo);
    }

    public static void anadirEstados(ArrayList<String> mapaEsteTiempo){
        mapaEstados.add(mapaEsteTiempo);
    }

    public static void clearProcesos(){
        mapaProcesos = new ArrayList<>();
    }

    public static void clearEstados(){
        mapaEstados = new ArrayList<>();
    }

    public static ArrayList<ArrayList<Integer>> getMapaProcesos() {
        return mapaProcesos;
    }
    public static ArrayList<ArrayList<String>> getMapaEstados() {
        return mapaEstados;
    }
}
