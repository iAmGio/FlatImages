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
import javafx.stage.Stage;

/**
 * Created by Gio on 19/12/2016.
 *
 * Main class
 */
public class FlatImages extends Application
{
    //Actual version
    static final String VERSION = "2.0.1";

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
        JavaFX.getEventManager().registerEvents(new DropListener());

        //Shows the scene
        SimpleStage simpleStage = new SimpleStage(primaryStage);
        simpleStage.show(scene, "Flat v" + VERSION, false);
        simpleStage.setIcon(getClass(), "assets/icon.png");

        stage = primaryStage;
    }

    public static void main(String...args)
    {
        launch(args);
    }
}