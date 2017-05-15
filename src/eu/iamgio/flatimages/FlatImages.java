package eu.iamgio.flatimages;

import eu.iamgio.flatimages.animations.BarAnimation;
import eu.iamgio.flatimages.controllers.Events;
import eu.iamgio.flatimages.fileloaders.FileDropListener;
import eu.iamgio.libfx.api.FXML;
import eu.iamgio.libfx.api.JavaFX;
import eu.iamgio.libfx.api.elements.SimpleStage;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Gio on 08/05/2017.
 */
public class FlatImages extends Application {

    public static final String VERSION = "3.0.0";

    public static Stage stage;
    public static Scene scene;

    public static boolean live = true;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        FontLoader loader = new FontLoader();
        /*
        * Lemon/Milk: http://www.dafont.com/it/lemon-milk.font
        */
        loader.load("LemonMilk.otf");
        setup();
        Components.init();

        Events event = new Events();
        event.initButtonEffects();
        event.registerKeys();

        new BarAnimation().play(false);
    }

    public static void setup() {
        Parent root = FXML.load(FlatImages.class, "scenes/MainScene.fxml");
        scene = new Scene(root, 915, 650);

        JavaFX.startDefaultEvents(scene);
        JavaFX.getEventManager().registerEvents(new FileDropListener());

        SimpleStage stage = new SimpleStage(FlatImages.stage);
        stage.setIcon(FlatImages.class, "assets/icon.png");
        stage.show(scene, "FlatImages v" + VERSION, false);
    }

    public static void main(String...args) {
        launch(args);
    }
}
