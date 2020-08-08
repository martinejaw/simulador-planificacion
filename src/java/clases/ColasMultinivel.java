package clases;

public class ColasMultinivel {

    private Planificador planificador1;
    private Planificador planificador2;
    private Planificador planificador3;

    public ColasMultinivel(Planificador planificador1, Planificador planificador2, Planificador planificador3) {
        this.planificador1 = planificador1;
        this.planificador2 = planificador2;
        this.planificador3 = planificador3;
        planificador1.setNroCola(1);
        planificador2.setNroCola(2);
        planificador3.setNroCola(3);
    }

    public void planificar(){
        this.planificador1.planificar();
        this.planificador2.planificar();
        this.planificador3.planificar();
    }

}
