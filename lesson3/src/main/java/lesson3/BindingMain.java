package lesson3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BindingMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(layout());

        stage.setScene(scene);
        stage.setTitle("Binding");
        stage.show();
    }

    private Pane layout() {
        final Label lbl1 = new Label("Two-way binding");
        final Label lbl2 = new Label("One-way binding");
        final TextField tf1 = new TextField();
        final TextField tf2 = new TextField();
        final TextField tf3 = new TextField();
        final TextField tf4 = new TextField();

        tf2.textProperty().bindBidirectional(tf1.textProperty());
        tf4.textProperty().bind(tf3.textProperty());

        final GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(5);

        GridPane.setColumnSpan(lbl1, 2);
        pane.add(lbl1, 0, 0);
        pane.add(tf1, 0, 1);
        pane.add(tf2, 1, 1);
        GridPane.setColumnSpan(lbl2, 2);
        pane.add(lbl2, 0, 2);
        pane.add(tf3, 0, 3);
        pane.add(tf4, 1, 3);
        return pane;
    }
}
