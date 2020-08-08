package clases;

import java.util.ArrayList;
import java.util.Comparator;

public class SRTF extends Planificador{

    public SRTF(Memoria memoria) {
        super(memoria);
    }

    public void planificar(){

        ArrayList<Proceso> nuevosListos = this.nuevosListos();

        if(this.procesoActual==null){
            for(Proceso proceso : nuevosListos){
                proceso.setEstado("Listo");
                this.colaListos.add(proceso);
            }

            if(this.colaListos.isEmpty()==false) {
                this.colaListos.sort(Comparator.comparing(Proceso::tiempoRemanente));

                Proceso procesoMinimo = this.colaListos.get(0);
                procesoMinimo.setEstado("Ejecutando");
                this.procesoActual = procesoMinimo;
                this.colaListos.remove(0);
            }
        }else if (nuevosListos.isEmpty() == false) {

            nuevosListos.sort(Comparator.comparing(Proceso::tiempoRemanente));
            Proceso procesoMinimo = nuevosListos.get(0);

            if(procesoMinimo.tiempoRemanente()<procesoActual.tiempoRemanente()) {
                this.procesoActual.setEstado("Listo");
                this.colaListos.add(procesoActual);

                procesoMinimo.setEstado("Ejecutando");
                this.procesoActual = procesoMinimo;
                nuevosListos.remove(0);
            }

            for (Proceso proceso : nuevosListos) {
                proceso.setEstado("Listo");
                this.colaListos.add(proceso);
            }
        }

        this.obtenerBloqueadoActual();

        this.agregarGantt();

        this.ejecutarInstrucciones();

        this.comprobarFinalizados();
    }
}

