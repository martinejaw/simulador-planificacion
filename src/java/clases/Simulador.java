package clases;

import java.util.ArrayList;

public class Simulador{

    private Memoria memoria;

    private Planificador planificador;

    private TablaProcesos tablaProcesos;

    private ColasMultinivel colasMultinivel;

    public Simulador(Memoria memoria, Planificador planificador, TablaProcesos tablaProcesos) {
        this.memoria = memoria;
        this.planificador = planificador;
        this.tablaProcesos = tablaProcesos;
    }

    public Simulador(Memoria memoria, ColasMultinivel colasMultinivel, TablaProcesos tablaProcesos) {
        this.memoria = memoria;
        this.colasMultinivel = colasMultinivel;
        this.tablaProcesos = tablaProcesos;
    }

    public void iniciar(){

        tablaProcesos.inicializar();
        int cont = 0;
        while(tablaProcesos.quedanProcesos()){


            Planificador.nuevoCiclo();

            //  Obtengo los procesos de este instante y los agrego a la memoria.
            ArrayList<Proceso> procesosInstante;
            procesosInstante = tablaProcesos.procesosEsteInstante();
            memoria.agregarProcesos(procesosInstante,tablaProcesos);

            //  Ejecuto el Fit correspondiente
            memoria.ejecutarFit();

            //  El planificador se ejecuta.
            if(colasMultinivel==null){
                planificador.planificar();
            }else{
                colasMultinivel.planificar();
            }

            tablaProcesos.guardarEstadoProcesos();
            
            //  Incremento el instante.
            Instante.incrementarInstante();
            System.out.println(cont++);
        }
    }
}

