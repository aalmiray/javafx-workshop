package lesson2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Lesson2Controller {
    @FXML private TextField name;

    public void sayHello() {
        String nameText = name.getText();

        // simple check for empty String - replace with
        // your implementation of choice
        if ("".equals(nameText)) {
            System.out.println("Howdy stranger!");
        } else {
            System.out.println("Hello " + nameText);
        }
    }
}