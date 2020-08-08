package clases;

public abstract class Instante {
    public static int instante = 0;

    public static void incrementarInstante(){
        instante++;
    }

    public static void inicializarInstante(){
        instante = 0;
    }

}
