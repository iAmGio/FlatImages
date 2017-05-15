package eu.iamgio.flatimages.fileloaders;

import eu.iamgio.customevents.api.EventHandler;
import eu.iamgio.customevents.api.Listener;
import eu.iamgio.flatimages.messages.Message;
import eu.iamgio.flatimages.messages.MessageAnnouncer;
import eu.iamgio.libfx.api.events.FileDropEvent;
import javafx.util.Duration;

/**
 * Created by Gio on 12/05/2017.
 */
public class FileDropListener implements Listener {

    @EventHandler
    @SuppressWarnings("unused")
    public void onDrop(FileDropEvent event) {
        if(!event.getDragboard().getFiles().get(0).getName().endsWith(".png")) {
            new MessageAnnouncer(new Message("Only PNG files are supported."), Duration.seconds(1.5)).announce();
            return;
        }
        if(event.success()) {
            Uploader.upload(event.getDragboard().getFiles().get(0));
        }
    }
}
