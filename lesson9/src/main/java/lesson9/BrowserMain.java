package lesson9;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.beans.property.*;
import javafx.scene.web.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BrowserMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(layout());

        stage.setScene(scene);
        stage.setTitle("WebBrowser");
        stage.show();
    }

    private WebEngine engine;
    private StringProperty urlProperty;

    private VBox layout() {
        VBox vbox = new VBox();

        WebView webView = new WebView();
        engine  = webView.getEngine();

        vbox.getChildren().addAll(locationBar(), webView);

        return vbox;
    }

    private HBox locationBar() {
        HBox hbox = new HBox();

        Button back        = button("lesson9/back.png");
        Button forward     = button("lesson9/forward.png");
        Button reload      = button("lesson9/reload.png");
        TextField location = new TextField();
        location.setPrefColumnCount(56);
        location.textProperty().bindBidirectional(urlProperty());

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                backAction();
            }
        });
        forward.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                forwardAction();
            }
        });
        reload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                reloadAction();
            }
        });

        location.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openUrl();
            }
        });

        hbox.getChildren().addAll(back, forward, reload, location);

        return hbox;
    }

    // ==---------- PROPERTIES ----------==

    public String getUrl()              { return urlProperty().get(); }
    public void setUrl(String url)      { urlProperty().setValue(url); }
    public StringProperty urlProperty() {
        if (urlProperty == null) {
            urlProperty = new SimpleStringProperty(this, "urlProperty", "");
        }
        return urlProperty;
    }

    // ==---------- ACTIONS ----------==

    private void openUrl() {
        String url = getUrl();
        if (url.indexOf("://") < 0) url = "http://" + url;
        if (engine.getLocation().equals(url)) return;
        engine.load(url);
    }

    private void backAction() {
        engine.getHistory().go(-1);
        setUrl(getUrlFromHistory());
    }

    private void forwardAction() {
        engine.getHistory().go(1);
        setUrl(getUrlFromHistory());
    }

    private void reloadAction() {
        engine.reload();
    }

    // ==---------- HELPERS ----------==

    private String getUrlFromHistory() {
        return engine.getHistory().getEntries().get(engine.getHistory().getCurrentIndex()).getUrl();
    }

    private Button button(String imageResource) {
        Button button = new Button();
        Image image = new Image(Thread.currentThread().getContextClassLoader().getResource(imageResource).toString());
        button.graphicProperty().set(new ImageView(image));
        return button;
    }
}
