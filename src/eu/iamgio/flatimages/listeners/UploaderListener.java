package eu.iamgio.flatimages.listeners;

import eu.iamgio.customevents.api.EventHandler;
import eu.iamgio.customevents.api.Listener;
import eu.iamgio.flatimages.Utils;
import eu.iamgio.libfx.api.JavaFX;
import eu.iamgio.libfx.api.events.MousePressEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Created by Gio on 20/12/2016.
 */
public class UploaderListener implements Listener
{
    @EventHandler
    public void click(MousePressEvent e)
    {
        if(e.getMouseEvent().getTarget() instanceof Button || e.getMouseEvent().getTarget() instanceof Text)
        {
            Button button = JavaFX.fromNode(((Node) e.getMouseEvent().getTarget()), Button.class);
            if(button == null) return;

            if(button.getId().equals("upload_button"))
            {
                //Loads the file from the chooser
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
                fileChooser.getExtensionFilters().addAll(extFilterPNG);
                File file = fileChooser.showOpenDialog(null);

                if(file != null) Utils.setImage(file);
            }
        }
    }
}
