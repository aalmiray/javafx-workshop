package lesson6;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EventsMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(layout());

        stage.setScene(scene);
        stage.setTitle("Events");
        stage.show();
    }

    private VBox layout() {
        final Button button1 = new Button("Handled by Handler");
        final Button button2 = new Button("Handled by Filter and Handler");
        final Button button3 = new Button("Handled by Filter and consumed");

        final EventHandler<ActionEvent> actionHandler = new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                System.out.println("Event handled by EventHandler\n");
            }
        };

       final EventHandler<ActionEvent> actionFilter= new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                System.out.println("Event handled by EventFilter");
                if (button3.equals(actionEvent.getSource())) {
                    System.out.println("Event consumed and won't reach EventHandler");
                    actionEvent.consume();
                }
            }
        };

        button1.addEventHandler(ActionEvent.ACTION, actionHandler);

        button2.addEventFilter(ActionEvent.ACTION, actionFilter);
        button2.addEventHandler(ActionEvent.ACTION, actionHandler);

        button3.addEventFilter(ActionEvent.ACTION, actionFilter);
        button3.addEventHandler(ActionEvent.ACTION, actionHandler);

        button1.setPrefWidth(300);
        button2.setPrefWidth(300);
        button3.setPrefWidth(300);

        VBox pane = new VBox();
        pane.setSpacing(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.getChildren().addAll(button1, button2, button3);
        return pane;
    }
}
