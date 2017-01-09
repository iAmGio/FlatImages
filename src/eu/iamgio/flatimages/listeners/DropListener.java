package eu.iamgio.flatimages.listeners;

import eu.iamgio.customevents.api.EventHandler;
import eu.iamgio.customevents.api.Listener;
import eu.iamgio.flatimages.FlatImages;
import eu.iamgio.flatimages.Utils;
import eu.iamgio.libfx.api.events.FileDropEvent;
import javafx.scene.input.Dragboard;

import java.io.File;

/**
 * Created by Gio on 20/12/2016.
 */
public class DropListener implements Listener {

    @EventHandler
    public void onFileDrop(FileDropEvent e) {
        if(FlatImages.process) return;

        Dragboard db = e.getDragboard();
        if(e.success()) {
            File file = db.getFiles().get(0);
            if(file.getName().endsWith(".png")) Utils.setImage(file);
        }
    }
}
