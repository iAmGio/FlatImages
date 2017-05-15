package eu.iamgio.flatimages.generator;

import eu.iamgio.flatimages.fileloaders.Uploader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Gio on 14/05/2017.
 */
public class Image extends ImageComponent {

    public Image(ImageGenerator generator) {
        super(generator);
    }

    @Override
    public BufferedImage generate() {
        BufferedImage image = generator.EMPTY_IMAGE();
        Graphics2D graphics2D = image.createGraphics();
        graphics2D.drawImage(Uploader.getBufferedImage(), generator.getWidth()/50, generator.getHeight()/50, null);

        generator.image = image;
        return image;
    }
}
