package lesson4;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LayoutMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(layout());

        stage.setScene(scene);
        stage.setTitle("Layouts");
        stage.show();
    }

    private TabPane layout() {
        final TabPane tabPane = new TabPane();
        tabPane.getTabs().add(borderPaneTab());
        tabPane.getTabs().add(flowPaneTab());
        tabPane.getTabs().add(tilePaneTab());
        tabPane.getTabs().add(gridPaneTab());
        tabPane.getTabs().add(anchorPaneTab());
        tabPane.getTabs().add(hboxTab());
        tabPane.getTabs().add(vboxTab());
        return tabPane;
    }

    private Tab borderPaneTab() {
        BorderPane pane = new BorderPane();
        pane.setTop(node("Top", "200, 0, 0, 0.5").withPrefHeight(50));
        pane.setLeft(node("Left", "200, 200, 0, 0.5").withPrefWidth(100));
        pane.setCenter(node("Center", "0, 200, 0, 0.5").withPrefSize(300, 300));
        pane.setRight(node("Right", "0, 200, 200, 0.5").withPrefWidth(100));
        pane.setBottom(node("Bottom", "0, 0, 200, 0.5").withPrefHeight(50));

        Tab tab = new Tab("BorderPane");
        tab.setContent(pane);
        tab.setClosable(false);
        return tab;
    }

    private Tab flowPaneTab() {
        FlowPane pane = new FlowPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPrefWrapLength(70);
        pane.getChildren().addAll(
            node("First", "200, 0, 0, 0.5").withPrefSize(50, 100),
            node("Second", "200, 200, 0, 0.5").withPrefSize(50, 100),
            node("Third", "0, 200, 0, 0.5").withPrefSize(100, 100),
            node("Fourth", "0, 200, 200, 0.5").withPrefSize(100, 100),
            node("Fifth", "0, 0, 200, 0.5").withPrefSize(100, 100)
        );

        Tab tab = new Tab("FlowPane");
        tab.setContent(pane);
        tab.setClosable(false);
        return tab;
    }

    private Tab tilePaneTab() {
        TilePane pane = new TilePane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPrefColumns(3);
        pane.getChildren().addAll(
            node("First", "200, 0, 0, 0.5").withPrefSize(50, 100),
            node("Second", "200, 200, 0, 0.5").withPrefSize(50, 100),
            node("Third", "0, 200, 0, 0.5").withPrefSize(100, 100),
            node("Fourth", "0, 200, 200, 0.5").withPrefSize(100, 100),
            node("Fifth", "0, 0, 200, 0.5").withPrefSize(100, 100)
        );

        Tab tab = new Tab("TilePane");
        tab.setContent(pane);
        tab.setClosable(false);
        return tab;
    }

    private Tab gridPaneTab() {
        Node fifth = node("Fifth", "0, 0, 200, 0.5").withPrefSize(100, 100);
        Node sixth = node("Sixth", "200, 0, 200, 0.5").withPrefSize(100, 100);

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.add(node("First", "200, 0, 0, 0.5").withPrefSize(100, 100),    0, 0);
        pane.add(node("Second", "200, 200, 0, 0.5").withPrefSize(100, 100), 1, 0);
        pane.add(node("Third", "0, 200, 0, 0.5").withPrefSize(100, 100),    0, 1);
        pane.add(node("Fourth", "0, 200, 200, 0.5").withPrefSize(100, 100), 1, 1);
        pane.add(fifth,                                                     2, 0);
        GridPane.setRowSpan(fifth, 2);
        pane.add(sixth,                                                     0, 2);
        GridPane.setColumnSpan(sixth, 3);

        Tab tab = new Tab("GridPane");
        tab.setContent(pane);
        tab.setClosable(false);
        return tab;
    }

    private Tab anchorPaneTab() {
        StackPane top    = node("Top", "200, 0, 0, 0.5").withPrefSize(500, 50);
        StackPane right  = node("Right", "0, 200, 0, 0.5").withPrefSize(50, 300);
        StackPane bottom = node("Bottom", "0, 0, 200, 0.5").withPrefSize(100, 50);
        bottom.getChildren().add(new Button("Exit"));

        AnchorPane pane = new AnchorPane();
        pane.getChildren().addAll(top, right, bottom);

        AnchorPane.setTopAnchor(top, 10.0);
        AnchorPane.setLeftAnchor(top, 10.0);
        AnchorPane.setRightAnchor(top, 10.0);

        AnchorPane.setTopAnchor(right, 70.0);
        AnchorPane.setRightAnchor(right, 10.0);
        AnchorPane.setBottomAnchor(right, 70.0);

        AnchorPane.setRightAnchor(bottom, 10.0);
        AnchorPane.setBottomAnchor(bottom, 10.0);

        Tab tab = new Tab("AnchorPane");
        tab.setContent(pane);
        tab.setClosable(false);
        return tab;
    }

    private Tab hboxTab() {
        HBox pane = new HBox();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setSpacing(10);
        pane.getChildren().addAll(
            node("First", "200, 0, 0, 0.5").withPrefSize(100, 100),
            node("Second", "200, 200, 0, 0.5").withPrefSize(100, 100),
            node("Third", "0, 200, 0, 0.5").withPrefSize(100, 100),
            node("Fourth", "0, 200, 200, 0.5").withPrefSize(100, 100),
            node("Fifth", "0, 0, 200, 0.5").withPrefSize(100, 100)
        );

        Tab tab = new Tab("HBox");
        tab.setContent(pane);
        tab.setClosable(false);
        return tab;
    }

    private Tab vboxTab() {
        VBox pane = new VBox();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setSpacing(10);
        pane.getChildren().addAll(
            node("First", "200, 0, 0, 0.5").withPrefSize(100, 100),
            node("Second", "200, 200, 0, 0.5").withPrefSize(100, 100),
            node("Third", "0, 200, 0, 0.5").withPrefSize(100, 100),
            node("Fourth", "0, 200, 200, 0.5").withPrefSize(100, 100),
            node("Fifth", "0, 0, 200, 0.5").withPrefSize(100, 100)
        );

        Tab tab = new Tab("VBox");
        tab.setContent(pane);
        tab.setClosable(false);
        return tab;
    }

    // ==--------------------------------------------------------------------==

    private FluentStackPane node(String text, String color) {
        FluentStackPane pane = new FluentStackPane();
        pane.getChildren().add(new Label(text));
        pane.setStyle("-fx-background-color: rgba("+ color +");");
        return pane;
    }

    private static class FluentStackPane extends StackPane {
        public FluentStackPane withPrefHeight(double h) {
            setPrefHeight(h); return this;
        }

        public FluentStackPane withPrefWidth(double w) {
            setPrefWidth(w); return this;
        }

        public FluentStackPane withPrefSize(double w, double h) {
            setPrefSize(w, h); return this;
        }
    }
}
