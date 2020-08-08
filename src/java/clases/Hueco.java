package clases;

public class Hueco {
    
    private int tamano;

    private int direccionInicio;

    public Hueco() {
    }

    public Hueco(int tamano, int direccionInicio) {
        this.tamano = tamano;
        this.direccionInicio = direccionInicio;
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
    
}
