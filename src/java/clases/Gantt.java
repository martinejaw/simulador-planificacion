package clases;

import java.util.ArrayList;

public abstract class Gantt {
    public static ArrayList<Integer> listaCPU1 = new ArrayList<>();
    public static ArrayList<Integer> listaCPU2 = new ArrayList<>();
    public static ArrayList<Integer> listaCPU3 = new ArrayList<>();

    public static ArrayList<Integer> listaBloqueado = new ArrayList<>();

    public static int getProceso(int i){
        return listaCPU1.get(i);
    }

    public static void agregarCPU1(int id){ listaCPU1.add(id); }
    public static void agregarCPU2(int id){
        listaCPU2.add(id);
    }
    public static void agregarCPU3(int id){
        listaCPU3.add(id);
    }

    public static void agregarBloqueado(int id){
        listaBloqueado.add(id);
    }

    public static ArrayList<Integer> getListaCPU1() {
        return listaCPU1;
    }

    public static ArrayList<Integer> getListaCPU2() {
        return listaCPU2;
    }

    public static ArrayList<Integer> getListaCPU3() {
        return listaCPU3;
    }

    public static ArrayList<Integer> getListaBloqueado() {
        return listaBloqueado;
    }
}