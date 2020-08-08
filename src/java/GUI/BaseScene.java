package GUI;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

abstract public class BaseScene {

    private VBox layout;
    private Scene scene;

    BaseScene(Stage stage, int width, int height) {
        this.layout = new VBox(20);
        List<Object> elements = initializeElements(stage);

        for (Object element: elements) {
            this.layout.getChildren().add((Node) element);
        }
        this.scene = new Scene(layout, width,height);
    }


    public VBox getLayout() {
        return layout;
    }

    public void setVbox(VBox layout) {
        this.layout = layout;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    protected abstract List<Object> initializeElements(Stage stage);
}
