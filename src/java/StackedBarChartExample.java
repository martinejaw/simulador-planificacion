import java.util.Arrays;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.stage.Stage;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

public class StackedBarChartExample extends Application {
    @Override
    public void start(Stage stage) {
        //Defining the axes
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList
                ("Africa", "America", "Asia", "Europe", "Oceania")));

        xAxis.setLabel("category");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Population (In millions)");

        //Creating the Bar chart
        StackedBarChart<Number, String> stackedBarChart =
                new StackedBarChart<>(yAxis, xAxis);
        stackedBarChart.setTitle("Historic World Population by Region");

        //Prepare XYChart.Series objects by setting data
        XYChart.Series<Number,String> series1 = new XYChart.Series<>();
        series1.setName("1800");
        series1.getData().add(new XYChart.Data<>( 107, "Africa"));
        series1.getData().add(new XYChart.Data<>( 31,"America"));
        series1.getData().add(new XYChart.Data<>(635,"Asia" ));
        series1.getData().add(new XYChart.Data<>(251,"Europe" ));
        series1.getData().add(new XYChart.Data<>(2,"Oceania" ));

        XYChart.Series<Number,String> series2 = new XYChart.Series<>();
        series2.setName("2000");
        series2.getData().add(new XYChart.Data<>( 130, "Africa"));
        series2.getData().add(new XYChart.Data<>( 50,"America"));
        series2.getData().add(new XYChart.Data<>(700,"Asia" ));
        series2.getData().add(new XYChart.Data<>(400,"Europe" ));
        series2.getData().add(new XYChart.Data<>(7,"Oceania" ));


        //Setting the data to bar chart
        stackedBarChart.getData().addAll(series1, series2);
        //Creating a Group object
        Group root = new Group(stackedBarChart);

        //Creating a scene object
        Scene scene = new Scene(root, 600, 400);

        //Setting title to the Stage
        stage.setTitle("stackedBarChart");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}