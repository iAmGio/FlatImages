package eu.iamgio.flatimages.generator;

import java.awt.image.BufferedImage;

/**
 * Created by Gio on 13/05/2017.
 */
public abstract class ImageComponent {

    protected ImageGenerator generator;

    public ImageComponent(ImageGenerator generator) {
        this.generator = generator;
    }

    abstract public BufferedImage generate();
}
