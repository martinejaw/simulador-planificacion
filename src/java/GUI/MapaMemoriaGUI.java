package GUI;

import clases.*;
import javafx.beans.binding.Bindings;
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
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;

public class MapaMemoriaGUI {

    public static Group group;

    public static ScrollPane scrollPane;

    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene scene) {
        MapaMemoriaGUI.scene = scene;
    }

    private static Scene scene;

    @FXML
    public void initialize() {
        createChart();

        ScrollPane scrollPaneCreated = new ScrollPane();
        scrollPaneCreated.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPaneCreated.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        //scrollPaneCreated.hbarPolicyProperty();
        //scrollPaneCreated.fitToWidthProperty().setValue(true);

        scrollPaneCreated.setContent(group);
        scrollPane = scrollPaneCreated;
    }

    private void createChart() {

        int max = 0;
        ArrayList<ArrayList<ArrayList<Integer>>> mapaMemoria = MapaMemoria.getMapaTotal();

        //Busca el maximo de procesos que va haber en un tiempo
        for (ArrayList<ArrayList<Integer>> tiempo : mapaMemoria) {
            if (tiempo.size() > max) {
                max = tiempo.size();
            }
        }
        ArrayList<String> tiempos = new ArrayList<>();
        for (int i = 1; i <= max; i++) {
            tiempos.add(String.valueOf(i));
        }
        Collections.reverse(tiempos);

        //Defining the axes
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.<String>observableArrayList(tiempos));

        NumberAxis yAxis = new NumberAxis(0, mapaMemoria.size() + 5, 1);
        yAxis.setLabel("MAPA DE MEMORIA");
        yAxis.setMinorTickLength(10);


        //Creating the Bar chart
        StackedBarChart stackedBarChartObj = new StackedBarChart<>(yAxis, xAxis);
        //stackedBarChartObj.getYAxis().setTickLabelsVisible(false);
        //stackedBarChartObj.getYAxis().setOpacity(0);

        stackedBarChartObj.setCategoryGap(30);
        stackedBarChartObj.setMinWidth(mapaMemoria.size() * 110);
        stackedBarChartObj.setMaxWidth(6000);
        stackedBarChartObj.setMinHeight(100 * max);
        stackedBarChartObj.setMaxHeight(200 * max);

        //stackedBarChartObj.setLayoutX(40);
        stackedBarChartObj.setLegendVisible(false);

        for (ArrayList<ArrayList<Integer>> tiempo : mapaMemoria) {
            for (int proceso = 0; proceso < max; proceso++) {
                XYChart.Series series = new XYChart.Series<>();
                series.setName("P");

                final XYChart.Data<Number, String> data = new XYChart.Data<>(1, String.valueOf(proceso + 1));
                int finalProceso = proceso;
                data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        try {
                            ArrayList<String> dataValue = buildDataValue(tiempo.get(finalProceso));
                            boolean isFija = isFija();
                            if (isFija) {
                                setNodeStyle(data, String.valueOf(tiempo.get(finalProceso).get(1)));
                            } else {
                                setNodeStyle(data, String.valueOf(tiempo.get(finalProceso).get(0)));
                            }
                            displayLabelForData(data, dataValue);
                        } catch (IndexOutOfBoundsException e) {
                            setNodeStyleEmpty(data);
                            displayLabelForData(data);
                        }
                    }
                });

                series.getData().add(data);
                stackedBarChartObj.getData().add(series);
            }
        }


        group = new Group(stackedBarChartObj);
    }

    private boolean isFija() {
        if (Home.conColasMultiSelected) {
            if (HomeMultiNivel.algoritmoPlanificacionMem.equals("FIJA")) {
                return true;
            } else {
                return false;
            }
        } else {
            if (Home.algoritmoPlanificacionMem.equals("FIJA")) {
                return true;
            } else {
                return false;
            }
        }
    }

    private ArrayList<String> buildDataValue(ArrayList<Integer> proceso) {
        ArrayList<String> stringValues = new ArrayList<>();
        if (Home.conColasMultiSelected) {
            if (HomeMultiNivel.algoritmoPlanificacionMem.equals("FIJA")) {
                stringValues.add("Particion " + proceso.get(0));
                if (proceso.get(1) == -1) {
                    stringValues.add("LIBRE");
                } else {
                    stringValues.add("Proceso " + proceso.get(1));
                }
                stringValues.add("Tamaño " + proceso.get(2));
            } else {
                if (proceso.get(0) == -1) {
                    stringValues.add("HUECO");
                } else {
                    stringValues.add("Proceso " + proceso.get(0));
                }
                stringValues.add("Inicio " + proceso.get(1));
                stringValues.add("Tamaño " + proceso.get(2));
            }
        } else {
            if (Home.algoritmoPlanificacionMem.equals("FIJA")) {
                stringValues.add("Particion " + proceso.get(0));
                if (proceso.get(1) == -1) {
                    stringValues.add("LIBRE");
                } else {
                    stringValues.add("Proceso " + proceso.get(1));
                }
                stringValues.add("Tamaño " + proceso.get(2));
            } else {
                if (proceso.get(0) == -1) {
                    stringValues.add("HUECO");
                } else {
                    stringValues.add("Proceso " + proceso.get(0));
                }
                stringValues.add("Inicio " + proceso.get(1));
                stringValues.add("Tamaño " + proceso.get(2));
            }
        }
        return stringValues;
    }

    /**
     * Change color of bar if value of i is <5 then red, if >5 then green if i>8 then blue
     */
    private void setNodeStyle(XYChart.Data<Number, String> data, String processValue) {
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

    private void setNodeStyleEmpty(XYChart.Data<Number, String> data) {
        Node node = data.getNode();
        node.setStyle("-fx-bar-fill: darkgrey; ");
    }

    /**
     * places a text label with a bar's value above a bar node for a given XYChart.Data
     */
    private void displayLabelForData(XYChart.Data<Number, String> data, ArrayList<String> stringValues) {
        final Node node = data.getNode();

        final Text dataText1 = new Text(stringValues.get(0));
        final Text dataText2 = new Text(stringValues.get(1));
        final Text dataText3 = new Text(stringValues.get(2));


        node.parentProperty().addListener((ov, oldParent, parent) -> {
            Group parentGroup = (Group) parent;
            parentGroup.getChildren().addAll(dataText1, dataText2, dataText3);
        });

        node.boundsInParentProperty().addListener((ov, oldBounds, bounds) -> {
            dataText1.setLayoutX(
                    Math.round(
                            bounds.getMinX() + bounds.getWidth() / 2 - dataText1.prefWidth(-1) / 2
                    )
            );
            dataText1.setLayoutY(

                    (bounds.getMinY() + bounds.getHeight() / 2) - 10

            );

            dataText2.setLayoutX(

                    Math.round(
                            bounds.getMinX() + bounds.getWidth() / 2 - dataText2.prefWidth(-1) / 2
                    )

            );
            dataText2.setLayoutY(

                    (bounds.getMinY() + bounds.getHeight() / 2) + 2

            );

            if (dataText3.toString().length()>=204){
                dataText3.setLayoutX(

                        (bounds.getMinX() + bounds.getWidth() / 2 - dataText1.prefWidth(-1) / 2) - 12

                );
            } else {
                dataText3.setLayoutX(

                        (bounds.getMinX() + bounds.getWidth() / 2 - dataText1.prefWidth(-1) / 2) - 2

                );
            }
            dataText3.setLayoutY(

                    (bounds.getMinY() + bounds.getHeight() / 2) + 14

            );
        });
    }

    private void displayLabelForData(XYChart.Data<Number, String> data) {
        final Node node = data.getNode();

        final Text dataText = new Text("");


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
