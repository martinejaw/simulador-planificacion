package GUI;

import clases.*;
import clases.TablaProcesos;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class Gantt {


    //public static GanttChart<Number,String> ganttChart;

    @FXML
    private StackedBarChart<Number, String> stackedBarChartObj;

    public static Group group;

    public static ScrollPane scrollPane;

    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene scene) {
        Gantt.scene = scene;
    }

    private static Scene scene;

    @FXML
    private void initialize() {
        inizializar();
        createChart();

        ScrollPane scrollPaneCreated = new ScrollPane();
        scrollPaneCreated.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPaneCreated.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        //scrollPaneCreated.hbarPolicyProperty();
        //scrollPaneCreated.fitToWidthProperty().setValue(true);
        scrollPaneCreated.setContent(group);

        scrollPane = scrollPaneCreated;

    }

    private void inizializar() {
        for (Proceso proceso : GUI.TablaProcesos.procesosList) {
            proceso.setEstado("Pendiente");
        }

        clases.Gantt.listaCPU1.clear();
        clases.Gantt.listaCPU2.clear();
        clases.Gantt.listaCPU3.clear();
        clases.Gantt.listaBloqueado.clear();
        Instante.inicializarInstante();
        EstadoProcesos.clearProcesos();
        EstadoProcesos.clearEstados();

        //Reinicializar estadistica de procesos y mapamemoria
        clases.TablaProcesos.tablaProcesos = null;
        MapaMemoria.inicializeMap();
        Particion.initializeCount();

        //Añade los procesos a la clase Procesos de Clases Para La Simulacion
        clases.TablaProcesos tablaProcesos = new clases.TablaProcesos();

        for (Proceso proceso : GUI.TablaProcesos.procesosList) {
            proceso.clearInstrucciones();
            proceso.reiniciarProceso();
            proceso.anadirInstrucciones(proceso.getCpu0(), "CPU");
            proceso.anadirInstrucciones(proceso.getEs1(), "ES");
            proceso.anadirInstrucciones(proceso.getCpu1(), "CPU");
            proceso.anadirInstrucciones(proceso.getEs2(), "ES");
            proceso.anadirInstrucciones(proceso.getCpu2(),"CPU");
            tablaProcesos.anadirProceso(proceso);
        }

        clases.TablaProcesos.tablaProcesos = tablaProcesos;
    }

    private void    createChart() {

        if (Home.conColasMultiSelected){
            flujoConColasMultiNivel();
        } else {
            flujoSinColasMultiNivel();
        }

        //Setting the data to bar chart
        //Creating a Group object
        group = new Group(stackedBarChartObj);
    }

    private void flujoSinColasMultiNivel() {
        //Defining the axes
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList
                ("E/S", "CPU")));

        NumberAxis yAxis = new NumberAxis(0, 50, 1);
        yAxis.setLabel("DIAGRAMA DE GANTT");
        yAxis.setMinWidth(100);
        yAxis.setMaxWidth(100);

        //Creating the Bar chart
        stackedBarChartObj = new StackedBarChart<>(yAxis, xAxis);
        stackedBarChartObj.setTitle("SIMULACION");
        stackedBarChartObj.setMinWidth(1200);
        stackedBarChartObj.setMaxWidth(6000);
        stackedBarChartObj.setLayoutX(40);
        stackedBarChartObj.setCategoryGap(100);
        stackedBarChartObj.setLegendVisible(false);

        //instanciando clases/variable
        Fija memoriaFija = null;
        Variable memoriaVariable = null;
        boolean variable;
        clases.TablaProcesos tablaProcesos = TablaProcesos.tablaProcesos;
        String homeOrMultiChoiceBoxValue;
        String homeOrMultiAlgoritmoMemoriaValue;
        int tamaño = 0;
        int porcentaje;
        int totalResta = 0;

        //Setear variables para usar de tamaño
        homeOrMultiChoiceBoxValue = Home.getHome().getTipoDeParticionChoicebox().getValue();
        homeOrMultiAlgoritmoMemoriaValue = Home.algoritmoPlanificacionChoiceboxValue;
        if (homeOrMultiChoiceBoxValue.equals("VARIABLE")) {
            tamaño = Integer.parseInt(Home.getHome().getTamañoMemoriaInput().getText());
            porcentaje = Integer.parseInt(Home.getHome().getPorcentajeSOInput().getText());
            totalResta = (int)(Math.ceil(tamaño/100.0f)*porcentaje);
        }

        //SELECCION MEMORIA VARIABLE O FIJA
        if (homeOrMultiChoiceBoxValue.equals("VARIABLE")) { //variable
            int total = tamaño - totalResta;

            if (homeOrMultiAlgoritmoMemoriaValue.equals("Worst-Fit")) {
                memoriaVariable = new Variable("WorstFit", total);
            } else {
                memoriaVariable = new Variable("FirstFit", total);
            }

            variable = true;
        } else {    //fija
            if (homeOrMultiAlgoritmoMemoriaValue.equals("First-Fit")) {
                memoriaFija = new Fija("FirstFit");
            } else {
                memoriaFija = new Fija("BestFit");
            }

            //setear particiones
            if (!Home.getHome().getParticionUno().getText().equals("")) {
                int particion1 = Integer.parseInt(Home.getHome().getParticionUno().getText());
                memoriaFija.crearParticion(particion1);
            }
            if (!Home.getHome().getParticionDos().getText().equals("")) {
                int particion2 = Integer.parseInt(Home.getHome().getParticionDos().getText());
                memoriaFija.crearParticion(particion2);
            }
            if (!Home.getHome().getParticionTres().getText().equals("")) {
                int particion3 = Integer.parseInt(Home.getHome().getParticionTres().getText());
                memoriaFija.crearParticion(particion3);
            }
            if (!Home.getHome().getParticionCuatro().getText().equals("")) {
                int particion4 = Integer.parseInt(Home.getHome().getParticionCuatro().getText());
                memoriaFija.crearParticion(particion4);
            }
            if (!Home.getHome().getParticionCinco().getText().equals("")) {
                int particion5 = Integer.parseInt(Home.getHome().getParticionCinco().getText());
                memoriaFija.crearParticion(particion5);
            }
            if (!Home.getHome().getParticionSeis().getText().equals("")) {
                int particion6 = Integer.parseInt(Home.getHome().getParticionSeis().getText());
                memoriaFija.crearParticion(particion6);
            }
            variable = false;
        }

        //Crear simulador
        Simulador simulador = null;
        simulador = generarSimuladorSinColasMultiNivel(variable, memoriaFija, memoriaVariable, tablaProcesos, simulador);
        simulador.iniciar();

        //Obtener listas CPU-ES
        ArrayList ganttCPUList = clases.Gantt.getListaCPU1();
        ArrayList ganttESList = clases.Gantt.getListaBloqueado();

        int highSize = Math.max(ganttCPUList.size(),ganttESList.size());
        yAxis.setUpperBound(highSize+10);

        for (Object proceso: ganttCPUList) {
            XYChart.Series series = new XYChart.Series<>();
            series.setName(proceso.toString());

            final XYChart.Data<Number, String> data = new XYChart.Data<>(1, "CPU");
            data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    setNodeStyle(data, proceso.toString());
                    if (proceso.toString().equals("-1")){
                        displayLabelForData(data,"-");
                    }else {
                        displayLabelForData(data, proceso.toString());
                    }
                }
            });

            series.getData().add(data);

            stackedBarChartObj.getData().add(series);
        }

        for (Object proceso: ganttESList) {

            XYChart.Series series = new XYChart.Series<>();
            series.setName(proceso.toString());

            final XYChart.Data<Number, String> data = new XYChart.Data<>(1, "E/S");
            data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    setNodeStyle(data, proceso.toString());

                    if (proceso.toString().equals("-1")){
                        displayLabelForData(data,"-");
                    }else {
                        displayLabelForData(data, proceso.toString());
                    }
                }
            });

            series.getData().add(data);

            stackedBarChartObj.getData().add(series);
        }
    }

    private void flujoConColasMultiNivel() {
        //Defining the axes
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList
                ("E/S", "CPU3", "CPU2", "CPU1")));
        xAxis.setTickLength(10);

        NumberAxis yAxis = new NumberAxis(0, 50, 1);
        yAxis.setLabel("DIAGRAMA DE GANTT");
        yAxis.setMinWidth(100);
        yAxis.setMaxWidth(100);
        yAxis.setMinorTickLength(10);


        //Creating the Bar chart
        stackedBarChartObj = new StackedBarChart<>(yAxis, xAxis);
        stackedBarChartObj.setTitle("SIMULACION");
        stackedBarChartObj.setMinWidth(4000);
        stackedBarChartObj.setMaxWidth(6000);
        stackedBarChartObj.setLayoutX(40);
        stackedBarChartObj.setLayoutY(40);
        stackedBarChartObj.setCategoryGap(30);
        stackedBarChartObj.setLegendVisible(false);;


        //instanciando clases/variables
        Fija memoriaFija = null;
        Variable memoriaVariable = null;
        boolean variable;
        clases.TablaProcesos tablaProcesos = TablaProcesos.tablaProcesos;


        //SELECCION MEMORIA VARIABLE O FIJA
        if (HomeMultiNivel.tipoParticionValue.equals("VARIABLE")) { //variable
            int tamaño = Integer.parseInt(HomeMultiNivel.tamañoMemoriaInputValue);
            int porcentaje = HomeMultiNivel.tamañoSOValue;
            int totalResta = (int)(Math.ceil(tamaño/100.0f)*porcentaje);
            int total = tamaño - totalResta;

            if (HomeMultiNivel.algoritmoPlanificacionMemoria.equals("Worst-Fit")) {
                memoriaVariable = new Variable("WorstFit", total);
            } else {
                memoriaVariable = new Variable("FirstFit", total);
            }

            variable = true;
        } else {    //fija
            if (HomeMultiNivel.algoritmoPlanificacionMemoria.equals("First-Fit")) {
                memoriaFija = new Fija("FirstFit");
            } else {
                memoriaFija = new Fija("BestFit");
            }

            //setear particiones
            if (!HomeMultiNivel.getHomeMultiNivel().getParticionUno().getText().equals("")) {
                int particion1 = Integer.parseInt(HomeMultiNivel.getHomeMultiNivel().getParticionUno().getText());
                memoriaFija.crearParticion(particion1);
            }
            if (!HomeMultiNivel.getHomeMultiNivel().getParticionDos().getText().equals("")) {
                int particion2 = Integer.parseInt(HomeMultiNivel.getHomeMultiNivel().getParticionDos().getText());
                memoriaFija.crearParticion(particion2);
            }
            if (!HomeMultiNivel.getHomeMultiNivel().getParticionTres().getText().equals("")) {
                int particion3 = Integer.parseInt(HomeMultiNivel.getHomeMultiNivel().getParticionTres().getText());
                memoriaFija.crearParticion(particion3);
            }
            if (!HomeMultiNivel.getHomeMultiNivel().getParticionCuatro().getText().equals("")) {
                int particion4 = Integer.parseInt(HomeMultiNivel.getHomeMultiNivel().getParticionCuatro().getText());
                memoriaFija.crearParticion(particion4);
            }
            if (!HomeMultiNivel.getHomeMultiNivel().getParticionCinco().getText().equals("")) {
                int particion5 = Integer.parseInt(HomeMultiNivel.getHomeMultiNivel().getParticionCinco().getText());
                memoriaFija.crearParticion(particion5);
            }
            if (!HomeMultiNivel.getHomeMultiNivel().getParticionSeis().getText().equals("")) {
                int particion6 = Integer.parseInt(HomeMultiNivel.getHomeMultiNivel().getParticionSeis().getText());
                memoriaFija.crearParticion(particion6);
            }

            variable = false;
        }

        //crear simulador
        Simulador simulador = generarSimuladorColasMultiNivel(variable, memoriaFija, memoriaVariable, tablaProcesos);
        simulador.iniciar();

        //Obtener listas CPU1-CPU2-CPU3
        ArrayList ganttCPUList1 = clases.Gantt.getListaCPU1();
        ArrayList ganttESList = clases.Gantt.getListaBloqueado();
        ArrayList ganttCPUList2 = clases.Gantt.getListaCPU2();
        ArrayList ganttCPUList3 = clases.Gantt.getListaCPU3();

        int highSize = Math.max(ganttCPUList1.size(), Math.max(ganttCPUList2.size(), Math.max(ganttCPUList3.size(), ganttESList.size())));
        yAxis.setUpperBound(highSize+10);

        stackedBarChartObj.setMinWidth(highSize*100);

        for (Object proceso: ganttCPUList1) {
            XYChart.Series series = new XYChart.Series<>();
            series.setName(proceso.toString());

            final XYChart.Data<Number, String> data = new XYChart.Data<>(1, "CPU1");

            data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    setNodeStyle(data, proceso.toString());
                    if (proceso.toString().equals("-1")){
                        displayLabelForData(data,"-");
                    }else {
                        displayLabelForData(data, proceso.toString());
                    }
                }
            });

            series.getData().add(data);

            stackedBarChartObj.getData().add(series);
        }

        for (Object proceso: ganttCPUList2) {
            XYChart.Series series = new XYChart.Series<>();
            series.setName(proceso.toString());

            final XYChart.Data<Number, String> data = new XYChart.Data<>(1, "CPU2");

            data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    setNodeStyle(data, proceso.toString());
                    if (proceso.toString().equals("-1")){
                        displayLabelForData(data,"-");
                    }else {
                        displayLabelForData(data, proceso.toString());
                    }
                }
            });

            series.getData().add(data);

            stackedBarChartObj.getData().add(series);
        }


        for (Object proceso: ganttCPUList3) {
            XYChart.Series series = new XYChart.Series<>();
            series.setName(proceso.toString());

            final XYChart.Data<Number, String> data = new XYChart.Data<>(1, "CPU3");

            data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    setNodeStyle(data, proceso.toString());
                    if (proceso.toString().equals("-1")){
                        displayLabelForData(data,"-");
                    }else {
                        displayLabelForData(data, proceso.toString());
                    }
                }
            });

            series.getData().add(data);

            stackedBarChartObj.getData().add(series);
        }

        for (Object proceso: ganttESList) {

        XYChart.Series series = new XYChart.Series<>();
        series.setName(proceso.toString());

        final XYChart.Data<Number, String> data = new XYChart.Data<>(1, "E/S");
        data.nodeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setNodeStyle(data, proceso.toString());

                if (proceso.toString().equals("-1")){
                    displayLabelForData(data,"-");
                }else {
                    displayLabelForData(data, proceso.toString());
                }
            }
        });

        series.getData().add(data);

        stackedBarChartObj.getData().add(series);
        }

    }

    private Planificador setPlanificador(Variable memoriaVariable, String algoritmoPlanificacion, String quantumValueString) {
        switch (algoritmoPlanificacion) {
            case "FCFS":
                return new FCFS(memoriaVariable);
            case "SJF":
                return new SJF(memoriaVariable);
            case "Round Robin":
                int quantum = 0;
                if (!quantumValueString.equals("")){
                    quantum = Integer.parseInt(quantumValueString);
                }
                return new RoundRobin(memoriaVariable, quantum);
            case "Prioridad":
                return new Prioridad(memoriaVariable);
            case "SRTF":
               return new SRTF(memoriaVariable);
            default:
                break;
        }
        return null;
    }

    private Planificador setPlanificador(Fija memoriaFija, String algoritmoPlanificacion, String quantumValueString) {
        switch (algoritmoPlanificacion) {
            case "FCFS":
                return new FCFS(memoriaFija);
            case "SJF":
                return new SJF(memoriaFija);
            case "Round Robin":
                int quantum = 0;
                if (!quantumValueString.equals("")){
                    quantum = Integer.parseInt(quantumValueString);
                }
                return new RoundRobin(memoriaFija, quantum);
            case "Prioridad":
                return new Prioridad(memoriaFija);
            case "SRTF":
                return new SRTF(memoriaFija);
            default:
                break;
        }
        return null;
    }

    private Simulador generarSimuladorColasMultiNivel(boolean variable, Fija memoriaFija, Variable memoriaVariable, TablaProcesos tablaProcesos) {
        Simulador simulador;
        Planificador planificador1;
        Planificador planificador2;
        Planificador planificador3;

        if (variable){
            String algoritmoPlanificacion1 = HomeMultiNivel.algoritmoPlanificacionChoiceBox1Value;
            String quantum1 = HomeMultiNivel.quantum1Value;
            planificador1 = setPlanificador(memoriaVariable, algoritmoPlanificacion1, quantum1);

            String algoritmoPlanificacion2 = HomeMultiNivel.algoritmoPlanificacionChoiceBox2Value;
            String quantum2 = HomeMultiNivel.quantum2Value;
            planificador2 = setPlanificador(memoriaVariable, algoritmoPlanificacion2, quantum2);

            String algoritmoPlanificacion3 = HomeMultiNivel.algoritmoPlanificacionChoiceBox3Value;
            String quantum3 = HomeMultiNivel.quantum3Value;
            planificador3 = setPlanificador(memoriaVariable, algoritmoPlanificacion3, quantum3);

            assert planificador1 != null;
            assert planificador2 != null;
            assert planificador3 != null;
            ColasMultinivel colasMultinivel = new ColasMultinivel(planificador1, planificador2, planificador3);
            simulador = new Simulador(memoriaVariable, colasMultinivel, tablaProcesos);
        } else {
            String algoritmoPlanificacion1 = HomeMultiNivel.algoritmoPlanificacionChoiceBox1Value;
            String quantum1 = HomeMultiNivel.quantum1Value;
            planificador1 = setPlanificador(memoriaFija, algoritmoPlanificacion1, quantum1);

            String algoritmoPlanificacion2 = HomeMultiNivel.algoritmoPlanificacionChoiceBox2Value;
            String quantum2 = HomeMultiNivel.quantum2Value;
            planificador2 = setPlanificador(memoriaFija, algoritmoPlanificacion2, quantum2);

            String algoritmoPlanificacion3 = HomeMultiNivel.algoritmoPlanificacionChoiceBox3Value;
            String quantum3 = HomeMultiNivel.quantum3Value;
            planificador3 = setPlanificador(memoriaFija, algoritmoPlanificacion3, quantum3);

            assert planificador1 != null;
            assert planificador2 != null;
            assert planificador3 != null;
            ColasMultinivel colasMultinivel = new ColasMultinivel(planificador1, planificador2, planificador3);
            simulador = new Simulador(memoriaFija, colasMultinivel, tablaProcesos);
        }
        return simulador;
    }

    private Simulador generarSimuladorSinColasMultiNivel(boolean variable, Fija memoriaFija, Variable memoriaVariable, TablaProcesos tablaProcesos, Simulador simulador) {
        if (variable) {
            switch (Home.algoritmoPlanificacionChoiceboxValue) {
                case "FCFS":
                    FCFS planificadorFCFS = new FCFS(memoriaVariable);
                    simulador = new Simulador(memoriaVariable, planificadorFCFS, tablaProcesos);
                    break;
                case "SJF":
                    SJF planificadorSJF = new SJF(memoriaVariable);
                    simulador = new Simulador(memoriaVariable, planificadorSJF, tablaProcesos);
                    break;
                case "Round Robin":
                    int quantum = 0;
                    if (!Home.home.getQuantumChoicebox().getValue().equals("")){
                        quantum = Integer.parseInt(Home.getHome().getQuantumChoicebox().getValue());
                    }
                    RoundRobin planificadorRoundRobin = new RoundRobin(memoriaVariable, quantum);
                    simulador = new Simulador(memoriaVariable, planificadorRoundRobin, tablaProcesos);
                    break;
                case "Prioridad":
                    Prioridad planificadorPrioridad = new Prioridad(memoriaVariable);
                    simulador = new Simulador(memoriaVariable, planificadorPrioridad, tablaProcesos);
                    break;
                case "SRTF":
                    SRTF planificadorSRTF = new SRTF(memoriaVariable);
                    simulador = new Simulador(memoriaVariable, planificadorSRTF, tablaProcesos);
                    break;
                default:
                    break;
            }
        } else {
            switch (Home.algoritmoPlanificacionChoiceboxValue) {
                case "FCFS":
                    FCFS planificadorFCFS = new FCFS(memoriaFija);
                    simulador = new Simulador(memoriaFija, planificadorFCFS, tablaProcesos);
                    break;
                case "SJF":
                    SJF planificadorSJF = new SJF(memoriaFija);
                    simulador = new Simulador(memoriaFija, planificadorSJF, tablaProcesos);
                    break;
                case "Round Robin":
                    int quantum = Integer.parseInt(Home.getHome().getQuantumChoicebox().getValue());
                    RoundRobin planificadorRoundRobin = new RoundRobin(memoriaFija, quantum);
                    simulador = new Simulador(memoriaFija, planificadorRoundRobin, tablaProcesos);
                    break;
                case "Prioridad":
                    Prioridad planificadorPrioridad = new Prioridad(memoriaFija);
                    simulador = new Simulador(memoriaFija, planificadorPrioridad, tablaProcesos);
                    break;
                case "SRTF":
                    SRTF planificadorSRTF = new SRTF(memoriaFija);
                    simulador = new Simulador(memoriaFija, planificadorSRTF, tablaProcesos);
                    break;
                default:
                    break;
            }
        }
        return simulador;
    }

    /** Change color of bar if value of i is <5 then red, if >5 then green if i>8 then blue */
        private void setNodeStyle (XYChart.Data < Number, String > data, String processValue){
            Node node = data.getNode();
            switch (Integer.parseInt(processValue)) {
                case 1:
                    node.setStyle("-fx-bar-fill: red; ");
                    break;
                case 2:
                    node.setStyle("-fx-bar-fill: cyan; ");
                    break;
                case 3:
                    node.setStyle("-fx-bar-fill: yellow;");
                    break;
                case 4:
                    node.setStyle("-fx-bar-fill: green;");
                    break;
                case 5:
                    node.setStyle("-fx-bar-fill: orange;");
                    break;
                case 6:
                    node.setStyle("-fx-bar-fill: turquoise;");
                    break;
                case 7:
                    node.setStyle("-fx-bar-fill: lime;");
                    break;
                case 8:
                    node.setStyle("-fx-bar-fill: violet;");
                    break;
                case 9:
                    node.setStyle("-fx-bar-fill: gold;");
                    break;
                case 10:
                    node.setStyle("-fx-bar-fill: burlywood;");
                    break;
                case 11:
                    node.setStyle("-fx-bar-fill: brown;");
                    break;
                case 12:
                    node.setStyle("-fx-bar-fill: blue;");
                    break;
                case 13:
                    node.setStyle("-fx-bar-fill: magenta;");
                    break;
                case 14:
                    node.setStyle("-fx-bar-fill: lightyellow;");
                    break;
                case 15:
                    node.setStyle("-fx-bar-fill: darkmagenta;");
                    break;
                default:
                    node.setStyle("-fx-bar-fill: grey;");
                    break;
            }
        }

    /** places a text label with a bar's value above a bar node for a given XYChart.Data */
    private void displayLabelForData(XYChart.Data<Number, String> data, String procesoId) {
        final Node node = data.getNode();
        final Text dataText = new Text(procesoId);
        node.parentProperty().addListener((ov, oldParent, parent) -> {
            Group parentGroup = (Group) parent;
            parentGroup.getChildren().add(dataText);
        });

        node.boundsInParentProperty().addListener((ov, oldBounds, bounds) -> {
            dataText.setLayoutX(
                    Math.round(
                            bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2
                    )
            );
            dataText.setLayoutY(
                    Math.round(
                            bounds.getMinY() - dataText.prefHeight(-1) * (-1.5)
                    )
            );
        });
    }
}