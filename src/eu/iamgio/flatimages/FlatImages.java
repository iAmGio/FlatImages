package eu.iamgio.flatimages;

import eu.iamgio.flatimages.listeners.CreateListener;
import eu.iamgio.flatimages.listeners.DropListener;
import eu.iamgio.flatimages.listeners.UploaderListener;
import eu.iamgio.libfx.api.CSS;
import eu.iamgio.libfx.api.FXML;
import eu.iamgio.libfx.api.JavaFX;
import eu.iamgio.libfx.api.elements.SimpleStage;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Created by Gio on 19/12/2016.
 *
 * Main class
 */
public class FlatImages extends Application
{
    //Actual version
    static final String VERSION = "2.0.0";

    //Image is processing
    public static boolean process = false;

    //Stage
    static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        //Loads the main root from the FXML
        Parent root = FXML.load(getClass(), "scenes/MainScene.fxml");
        Scene scene = new Scene(root, 950, 600);
        //Applies the style
        CSS.load(getClass(), scene, "stylesheets/styles.css");

        //Starts events like mouse click
        JavaFX.startDefaultEvents(scene);

        //Registers the events
        JavaFX.getEventManager().registerEvents(new UploaderListener());
        JavaFX.getEventManager().registerEvents(new CreateListener());
        new DropListener().registerDrop(scene);

        //Shows the scene
        new SimpleStage(primaryStage).show(scene, "Flat v" + VERSION, false);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("assets/icon.png")));

        stage = primaryStage;
    }

    public static void main(String...args)
    {
        launch(args);
    }
}