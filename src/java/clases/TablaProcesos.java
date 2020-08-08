package clases;

import java.util.ArrayList;
import java.util.Comparator;

public class TablaProcesos {

    private ArrayList<Proceso> listaProcesos = new ArrayList<>();

    public static TablaProcesos tablaProcesos;

    public void anadirProceso(Proceso proceso){
        listaProcesos.add(proceso);
    }
    public void quitarProceso(Proceso proceso) { listaProcesos.remove(proceso);}

    public ArrayList<Proceso> procesosEsteInstante(){
        ArrayList<Proceso> procesosInstante = new ArrayList<>();

        int instante = Instante.instante;
        /*int i = 0;
        while(listaProcesos.get(i).getTiempoCreacion()>instante){
            i++;
        }
        while()*/

        for(Proceso proceso : listaProcesos){
            if(proceso.getTiempoCreacion()==instante){
                procesosInstante.add(proceso);
            }
        }
        return procesosInstante;
    }

    public double tiempoRetornoPromedio(){
        double promedio = 0;
        for(Proceso proceso : listaProcesos){
            promedio += proceso.tiempoRetorno();
        }
        promedio /= listaProcesos.size();

        return Math.round(promedio * 100.0) / 100.0;
    }

    public double tiempoEsperaPromedio(){
        double promedio = 0;
        for(Proceso proceso : listaProcesos){
            promedio += proceso.tiempoEspera();
        }
        promedio /= listaProcesos.size();

        return Math.round(promedio * 100.0) / 100.0;
    }

    public double tiempoRespuestaPromedio(){
        double promedio = 0;
        for(Proceso proceso : listaProcesos){
            promedio += proceso.tiempoRespuesta();
        }
        promedio /= listaProcesos.size();

        return Math.round(promedio * 100.0) / 100.0;
    }

    public void inicializar(){
        this.listaProcesos.sort(Comparator.comparing(clases.Proceso::getTiempoCreacion));
    }

    public boolean quedanProcesos(){
        for(Proceso proceso : listaProcesos){
            if(proceso.getEstado()!="Finalizado"){
                return true;
            }
        }
        return false;
    }

    public void guardarEstadoProcesos(){
        ArrayList<Integer> procesosEnIstante = new ArrayList<>();
        ArrayList<String> estadosEnIstante = new ArrayList<>();

        for(Proceso proceso : listaProcesos){
            if(proceso.getEstado()!="NoCreado"){
                procesosEnIstante.add(proceso.getId());
                estadosEnIstante.add(proceso.getEstado());
            }
        }

        EstadoProcesos.anadirProcesos(procesosEnIstante);
        EstadoProcesos.anadirEstados(estadosEnIstante);
    }

}

