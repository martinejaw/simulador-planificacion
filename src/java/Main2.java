import clases.*;

import java.io.IOException;

public class Main2 {

    public static void main(String[] args) throws IOException {

        Proceso p1 = new Proceso();
        p1.setTamano(15);
        Proceso p2 = new Proceso();
        p2.setTamano(20);
        Proceso p3 = new Proceso();
        p3.setTamano(12);
        Proceso p4 = new Proceso();
        p4.setTamano(5);
        Proceso p5 = new Proceso();
        p5.setTamano(3);
        Proceso p6 = new Proceso();
        p6.setTamano(70);
        Proceso p7 = new Proceso();
        p7.setTamano(25);
        Proceso p8 = new Proceso();
        p8.setTamano(10);


        p1.anadirInstrucciones(5, "CPU");
        //p1.anadirInstrucciones(8, "E/S");
        p2.anadirInstrucciones(4, "CPU");
        p3.anadirInstrucciones(10, "CPU");
        p4.anadirInstrucciones(3,"CPU");
        p5.anadirInstrucciones(2,"CPU");
        p6.anadirInstrucciones(10,"CPU");
        p7.anadirInstrucciones(5,"CPU");
        p8.anadirInstrucciones(5,"CPU");


        TablaProcesos tabla = new TablaProcesos();
        p4.setTiempoCreacion(1);
        p5.setTiempoCreacion(2);
        p6.setTiempoCreacion(3);
        p7.setTiempoCreacion(4);
        p8.setTiempoCreacion(5);
        
        tabla.anadirProceso(p1);
        tabla.anadirProceso(p2);
        tabla.anadirProceso(p3);
        tabla.anadirProceso(p4);
        tabla.anadirProceso(p5);
        tabla.anadirProceso(p6);
        tabla.anadirProceso(p7);
        tabla.anadirProceso(p8);



        Variable memoria = new Variable("FirstFit", 98);



        Fija memoriaFija = new Fija("FirstFit");
        memoriaFija.crearParticion(6);
        memoriaFija.crearParticion(20);
        memoriaFija.crearParticion(70);


        SRTF planificador = new SRTF(memoriaFija);
        /*
        for (int i = 0; i < 44; i++) {
            memoria.agregarProcesos(tabla.procesosEsteInstante());
            memoria.ejecutarFit();
            planificador.planificar();
            Instante.incrementarInstante();
        }*/

        Simulador simulador = new Simulador(memoriaFija,planificador,tabla);

        simulador.iniciar();

        int instanteUltimo = Instante.instante;
        int tiempoRetorno1 = p1.tiempoRetorno();
        int tiempoRetorno2 = p2.tiempoRetorno();
        int tiempoRetorno3 = p3.tiempoRetorno();
        int tiempoRetorno4 = p4.tiempoRetorno();
        int tiempoRetorno5 = p5.tiempoRetorno();
        int tiempoRetorno6 = p6.tiempoRetorno();
        int tiempoRetorno7 = p7.tiempoRetorno();
        int tiempoRetorno8 = p8.tiempoRetorno();



        int tiempoRespuesta1 = p1.tiempoRespuesta();
        int tiempoRespuesta2 = p2.tiempoRespuesta();
        int tiempoRespuesta3 = p3.tiempoRespuesta();
        int tiempoRespuesta4 = p4.tiempoRespuesta();
        int tiempoRespuesta5 = p5.tiempoRespuesta();
        int tiempoRespuesta6 = p6.tiempoRespuesta();
        int tiempoRespuesta7 = p7.tiempoRespuesta();
        int tiempoRespuesta8 = p8.tiempoRespuesta();



        int tiempoEspera1 = p1.tiempoEspera();
        int tiempoEspera2 = p2.tiempoEspera();
        int tiempoEspera3 = p3.tiempoEspera();
        int tiempoEspera4 = p4.tiempoEspera();
        int tiempoEspera5 = p5.tiempoEspera();
        int tiempoEspera6 = p6.tiempoEspera();
        int tiempoEspera7 = p7.tiempoEspera();
        int tiempoEspera8 = p8.tiempoEspera();


        double esperaProm = tabla.tiempoEsperaPromedio();
        double respuestaProm = tabla.tiempoRespuestaPromedio();
        double retornoProm = tabla.tiempoRetornoPromedio();


        System.out.println("instante ultimo "+ instanteUltimo);
        System.out.println("TR1 "+ p1.getTiempoFinalizacion());
        System.out.println("TR2 "+ p2.getTiempoFinalizacion());
        System.out.println("TR3 "+ tiempoRetorno3);
        System.out.println("TR4 "+ tiempoRetorno4);
        System.out.println("TR5 "+ tiempoRetorno5);
        System.out.println("TR6 "+ tiempoRetorno6);
        System.out.println("TR7 "+ tiempoRetorno7);
        System.out.println("TR8 "+ p8.getTiempoFinalizacion());
        /* System.out.println("TRta1 "+ tiempoRespuesta1);
        System.out.println("TRta2 "+ tiempoRespuesta2);
        System.out.println("TRta3 "+ tiempoRespuesta3);
        System.out.println("TRta4 "+ tiempoRespuesta4);
        System.out.println("TRta5 "+ tiempoRespuesta5);
        System.out.println("TRta6 "+ tiempoRespuesta6);
        System.out.println("TRta7 "+ tiempoRespuesta7);
        System.out.println("TRta8 "+ tiempoRespuesta8);
        System.out.println("TE1 "+ tiempoEspera1);
        System.out.println("TE2 "+ tiempoEspera2);
        System.out.println("TE3 "+ tiempoEspera3);
        System.out.println("TE4 "+ tiempoEspera4);
        System.out.println("TE5 "+ tiempoEspera5);
        System.out.println("TE6 "+ tiempoEspera6);
        System.out.println("TE7 "+ tiempoEspera7);
        System.out.println("TE8 "+ tiempoEspera8);
        System.out.println("EP "+ esperaProm);
        System.out.println("RP "+ respuestaProm);
        System.out.println("RetP "+ retornoProm);*/


    }

}
