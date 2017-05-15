package eu.iamgio.flatimages.generator;

import eu.iamgio.flatimages.Components;
import eu.iamgio.flatimages.Validate;
import eu.iamgio.flatimages.fileloaders.Uploader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Gio on 13/05/2017.
 */
public class ImageGenerator {

    private int width, height, length;
    private Color color;
    private boolean fading;
    private float opacity;

    BufferedImage image,
                  bg,
                  shadow,
                  built;

    public boolean error = false;

    public ImageGenerator() {
        TextField[] fields = new TextField[] {(TextField) Components.imageSize[1], (TextField) Components.imageSize[2], (TextField) Components.shadowLength[1]};
        for(TextField field : fields) {
            Validate.removeError(field);
            if(!Validate.isNumber(field)) {
                error = true;
                Validate.addError(field);
            }
        }

        if(error) return;

        this.width = Integer.parseInt(fields[0].getText());
        this.height = Integer.parseInt(fields[1].getText());
        this.length = Integer.parseInt(fields[2].getText());
        javafx.scene.paint.Color color = ((ColorPicker) Components.backgroundColors[1]).getValue();
        this.color = new Color((float) color.getRed(), (float) color.getGreen(), (float) color.getBlue());
        this.fading = ((CheckBox) Components.fading[1]).isSelected();
        this.opacity = (float) ((Slider) Components.shadowOpacity[1]).getValue() / 100;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    int getShadowLength() {
        return length;
    }

    Color getColor() {
        return color;
    }

    public boolean fades() {
        return fading;
    }

    public float getOpacity() {
        return opacity;
    }

    public ImageGenerator addImageComponent(ImageComponent component) {
        BufferedImage image = component.generate();

        if(built == null)
            built = image;
        else {
            Graphics2D graphics2D = built.createGraphics();
            graphics2D.drawImage(image, 0, 0, null);
        }
        return this;
    }

    public BufferedImage getImage() {
        return built;
    }

    public File build() {
        String name = Uploader.getFile().getAbsolutePath().replace(".png", "_flat.png");
        try {
            ImageIO.write(built, "png", new File(name));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return new File(name);
    }

    final BufferedImage EMPTY_IMAGE() {
        return new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
    }

    public static ImageGenerator auto() {
        ImageGenerator generator = new ImageGenerator();
        if(!generator.error) {
            generator.addImageComponent(new Background(generator));
            generator.addImageComponent(new Image(generator));
            generator.addImageComponent(new Shadow(generator));
            generator.addImageComponent(new Image(generator));
        }
        return generator;
    }
}
