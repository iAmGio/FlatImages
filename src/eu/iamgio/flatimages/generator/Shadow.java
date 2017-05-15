package eu.iamgio.flatimages.generator;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Gio on 14/05/2017.
 */
public class Shadow extends ImageComponent {

    public Shadow(ImageGenerator generator) {
        super(generator);
    }

    @Override
    public BufferedImage generate() {
        BufferedImage shadow = generator.EMPTY_IMAGE();
        BufferedImage image = generator.image;

        int w = generator.getWidth(), h = generator.getHeight();

        float alpha = generator.getOpacity();

        for(int y = 1; y < h; y++) {
            for(int x = 1; x < w; x++) {
                if(image.getRGB(x - 1, y - 1) != 0 || shadow.getRGB(x - 1, y - 1) != 0) {
                    Color color = new Color(0F, 0F, 0F, alpha >= 0 ? alpha : 0);
                    shadow.setRGB(x, y, color.getRGB());
                    if(generator.fades()) {
                        alpha -= 0.1 / (w + h) / generator.getShadowLength();
                    }
                }
            }
        }

        generator.shadow = shadow;
        return shadow;
    }
}
