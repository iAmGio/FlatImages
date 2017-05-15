package eu.iamgio.flatimages.fileloaders;

import eu.iamgio.flatimages.Components;
import eu.iamgio.flatimages.animations.BarAnimation;
import eu.iamgio.flatimages.animations.UploadAnimation;
import eu.iamgio.flatimages.controllers.Events;
import eu.iamgio.flatimages.messages.Message;
import eu.iamgio.flatimages.messages.MessageAnnouncer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static eu.iamgio.flatimages.Components.imageSize;
import static eu.iamgio.flatimages.Components.upload;

/**
 * Created by Gio on 12/05/2017.
 */
public class Uploader {

    private static BufferedImage bufferedImage;
    private static File file;

    public static void upload(File file) {
        Uploader.file = file;
        try {
            if(bufferedImage == null) {
                new UploadAnimation().new UploadNodesAnimation().play(false,
                        () -> new UploadAnimation().new UIBuilderAnimation().play(false, () -> {}));
                new BarAnimation().play(true);
            }
            for(Node node : Components.getAll()) {
                if(node.getTranslateY() == -20)
                    node.setTranslateY(0);
            }

            new MessageAnnouncer(new Message("Loaded " + file.getName()), Duration.seconds(1.5)).announce();

            bufferedImage = ImageIO.read(file);
            Components.image.setImage(SwingFXUtils.toFXImage(bufferedImage, null));

            updateNodes();
            new Events().bind();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void unload() {
        if(bufferedImage != null) {
            new UploadAnimation().new UIBuilderAnimation().play(true, () ->
                    new UploadAnimation().new UploadNodesAnimation().play(true, () -> {}));
            new BarAnimation().play(false);
        }

        new MessageAnnouncer(new Message("Unloaded"), Duration.seconds(1.5)).announce();
        ((TextField) Components.upload[1]).setText("");

        bufferedImage = null;
        Components.image.setImage(null);
    }

    private static void updateNodes() {
        ((TextField) upload[1]).setText(file.getAbsolutePath());

        TextField width = ((TextField) imageSize[1]), height = ((TextField) imageSize[2]);
        width.setPromptText(bufferedImage.getWidth() + ""); width.setText((int)(bufferedImage.getWidth()*1.5) + "");
        height.setPromptText(bufferedImage.getHeight() + ""); height.setText((int)(bufferedImage.getHeight()*1.5) + "");
    }

    public static BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public static File getFile() {
        return file;
    }
}
