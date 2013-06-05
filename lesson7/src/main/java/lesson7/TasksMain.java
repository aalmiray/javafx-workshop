package lesson7;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TasksMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(layout());

        stage.setScene(scene);
        stage.setTitle("Tasks");
        stage.show();
    }

    private VBox layout() {
        final ProgressBar progressBar = new ProgressBar();
        progressBar.setMinWidth(250);
        final Button startButton = new Button("Start");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                Task task = createCounterTask();
                progressBar.progressProperty().bind(task.progressProperty());
                startButton.disableProperty().bind(task.runningProperty());
                task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override public void handle(WorkerStateEvent workerStateEvent) {
                        progressBar.progressProperty().unbind();
                        progressBar.setProgress(-1);
                    }
                });
                new Thread(task).start();
            }
        });

        VBox pane = new VBox();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setSpacing(10);
        pane.getChildren().addAll(progressBar, startButton);
        return pane;
    }

    private Task createCounterTask() {
        return new Task<Void>() {
            @Override public Void call() {
                final int max = 200_000_000;
                for (int i = 1 ; i < max ; i++) {
                    if (isCancelled()) {
                        break;
                    }
                    updateProgress(i, max);
                }
                return null;
            }
        };
    }
}
