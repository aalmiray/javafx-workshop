package lesson8;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.geometry.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ObservableMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(layout());

        stage.setScene(scene);
        stage.setTitle("ObservableLists");
        stage.show();
    }

    private TabPane layout() {
        final TabPane tabPane = new TabPane();
        tabPane.getTabs().add(demo1Tab());
        tabPane.getTabs().add(demo2Tab());
        return tabPane;
    }

    private Tab demo1Tab() {
        final GridPane grid = new GridPane();
        grid.setPadding(new Insets(5));
        grid.setHgap(10);
        grid.setVgap(10);

        Label titleLeft = new Label("Left");
        GridPane.setHalignment(titleLeft, HPos.CENTER);
        grid.add(titleLeft, 0, 0);

        Label titleRight = new Label("Right");
        grid.add(titleRight, 2, 0);
        GridPane.setHalignment(titleRight, HPos.CENTER);

        // Setup left list
        final ObservableList<String> leftList = FXCollections.observableArrayList("Switzerland", "Germany", "Sweden", "Austria");
        final ListView<String> leftListView = new ListView<>(leftList);
        leftListView.setPrefWidth(150);
        leftListView.setPrefHeight(150);
        grid.add(leftListView, 0, 1);

        // Setup right list
        final ObservableList<String> rightList = FXCollections.observableArrayList();
        final ListView<String> rightListView = new ListView<>(rightList);
        rightListView.setPrefWidth(150);
        rightListView.setPrefHeight(150);
        grid.add(rightListView, 2, 1);

        // Setup buttons
        Button moveToRightButton = new Button(">");
        moveToRightButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Get the selected item from the list view
                String item = leftListView.getSelectionModel().getSelectedItem();

                if (item != null) {
                    // Clear the selection in the left list view
                    leftListView.getSelectionModel().clearSelection();

                    // Remove the selected item from the left list view
                    leftList.remove(item);

                    // Add the selected item to the right list view
                    rightList.add(item);
                }
            }
        });

        Button moveToLeftButton = new Button("<");
        moveToLeftButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                // Get the selected item from the list view
                String item = rightListView.getSelectionModel().getSelectedItem();

                if (item != null) {
                    // Clear the selection in the right list view
                    rightListView.getSelectionModel().clearSelection();

                    // Remove the selected item from the right list view
                    rightList.remove(item);

                    // Add the selected item to the left list view
                    leftList.add(item);
                }
            }
        });

        VBox vbox = new VBox(5);
        vbox.getChildren().addAll(moveToRightButton, moveToLeftButton);

        grid.add(vbox, 1, 1);
        GridPane.setConstraints(vbox, 1, 1, 1, 2, HPos.CENTER, VPos.CENTER);

        Tab tab = new Tab("Demo1");
        tab.setContent(grid);
        tab.setClosable(false);
        return tab;
    }

    private Tab demo2Tab() {
        final List<String> list = new ArrayList<>();
        list.add("Gerrit");
        list.add("Dierk");
        list.add("Andres");

        // Update an ObservableList from a background thread
        final ObservableList<String> observableList = FXCollections.observableList(list);
        observableList.addListener(new ListChangeListener() {
            @Override public void onChanged(ListChangeListener.Change change) {
                while (change.next()) {
                    System.out.println("Was added ?      " + change.wasAdded());
                    System.out.println("Was removed ?    " + change.wasRemoved());
                    System.out.println("Was replaced ?   " + change.wasReplaced());
                    System.out.println("Was permutated ? " + change.wasPermutated());
                }
            }
        });

        // Insert a new entry to the ObservableList from a background thread
        final Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override public void run() {
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        observableList.add("Sandra");
                    }
                });
                timer.cancel();
            }
        }, 5000);

        StackPane pane = new StackPane();
        ListView listView = new ListView(observableList);
        pane.getChildren().add(listView);

        Tab tab = new Tab("Demo2");
        tab.setContent(pane);
        tab.setClosable(false);
        return tab;
    }
}
