package eu.iamgio.flatimages.generator;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Gio on 14/05/2017.
 */
public class Background extends ImageComponent {

    public Background(ImageGenerator generator) {
        super(generator);
    }

    @Override
    public BufferedImage generate() {
        BufferedImage image = generator.EMPTY_IMAGE();
        Graphics2D graphics2D = image.createGraphics();
        graphics2D.setPaint(generator.getColor());
        graphics2D.fillRect(0, 0, generator.getWidth(), generator.getHeight());

        generator.bg = image;
        return image;
    }
}
