package eu.iamgio.flatimages;

import javafx.scene.Node;
import javafx.scene.control.TextField;

/**
 * Created by Gio on 14/05/2017.
 */
public class Validate {

    public static boolean isNumber(TextField textField) {
        try {
            int n = Integer.parseInt(textField.getText());
            return n > 0;
        }
        catch(Exception e) {
            return false;
        }
    }

    public static void addError(Node node) {
        if(!node.getStyleClass().contains("error")) {
            node.getStyleClass().add("error");
        }
    }

    public static void removeError(Node node) {
        if(node.getStyleClass().contains("error")) {
            node.getStyleClass().remove("error");
        }
    }
}
