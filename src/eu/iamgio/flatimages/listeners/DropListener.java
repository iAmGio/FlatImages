package eu.iamgio.flatimages.listeners;

import eu.iamgio.flatimages.FlatImages;
import eu.iamgio.flatimages.Utils;
import javafx.scene.Scene;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.io.File;

/**
 * Created by Gio on 20/12/2016.
 */
public class DropListener
{
    /**
     * Event called on drag'n'drop
     */
    public void registerDrop(Scene scene)
    {
        scene.setOnDragOver(e ->
        {
            Dragboard db = e.getDragboard();
            if(!FlatImages.process)
                if(db.hasFiles())
                    e.acceptTransferModes(TransferMode.COPY);
                else
                    e.consume();
        });

        scene.setOnDragDropped(e ->
        {
            Dragboard db = e.getDragboard();
            boolean success = false;
            if(db.hasFiles())
            {
                success = true;
                File file = db.getFiles().get(0);
                if(file.getName().endsWith(".png"))
                    Utils.setImage(file);
            }

            e.setDropCompleted(success);
            e.consume();
        });
    }
}
