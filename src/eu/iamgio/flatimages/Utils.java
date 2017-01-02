package eu.iamgio.flatimages;

import eu.iamgio.libfx.api.JavaFX;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Gio on 20/12/2016.
 */
public class Utils
{
    //Current image
    static BufferedImage bufferedImage;
    static File imageFile;

    /**
     * Refreshes the image
     * @param file Image file
     */
    @SuppressWarnings("unchecked")
    public static void setImage(File file)
    {
        try
        {
            imageFile = file;

            //Loads a buffered image from the file
            bufferedImage = ImageIO.read(file);
            //Sets all the nodes visible
            JavaFX.getRoot().getChildrenUnmodifiable().forEach(n -> n.setVisible(true));

            //Sets the preview
            ((ImageView) JavaFX.fromId("preview_img")).setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            //Sets the filepath
            ((TextField) JavaFX.fromId("filepath_field")).setText(file.getAbsolutePath());
            //Sets the width in the field
            ((TextField) JavaFX.fromId("width_field")).setText(
                    bufferedImage.getWidth() + bufferedImage.getWidth()/2 + "");
            //Sets the height in the field
            ((TextField) JavaFX.fromId("height_field")).setText(
                    bufferedImage.getHeight() + bufferedImage.getHeight()/2 + "");
            //Sets the background type value in the box
            ComboBox box = (ComboBox) JavaFX.fromId("bgtype_combobox");
            box.getItems().addAll("Fill", "Circle");
            box.getSelectionModel().select(0);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Sets a red border
     * @param node Node
     */
    public static void addError(Node node)
    {
        ObservableList<String> styleClass = node.getStyleClass();
        if(!styleClass.contains("error"))
            styleClass.add("error");
    }

    /**
     * Removes the red border
     * @param node Node
     */
    public static void removeError(Node node)
    {
        ObservableList<String> styleClass = node.getStyleClass();
        if(styleClass.contains("error"))
            styleClass.remove("error");
    }
}
