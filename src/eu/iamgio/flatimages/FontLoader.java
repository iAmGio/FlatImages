package eu.iamgio.flatimages;

import javafx.scene.text.Font;

/**
 * Created by Gio on 14/05/2017.
 */
class FontLoader {

    void load(String...fonts) {
        for(String font : fonts)
            Font.loadFont(FlatImages.class.getResource("assets/fonts/" + font).toExternalForm(), 20);
    }
}
