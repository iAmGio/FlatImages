package eu.iamgio.flatimages.listeners;

import eu.iamgio.customevents.api.EventHandler;
import eu.iamgio.customevents.api.Listener;
import eu.iamgio.flatimages.Utils;
import eu.iamgio.libfx.api.JavaFX;
import eu.iamgio.libfx.api.events.mouse.MousePressEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * Created by Gio on 09/01/2017.
 */
public class PreviewUpdaterListener implements Listener {

    @EventHandler
    public void click(MousePressEvent e) {
        if(e.getMouseEvent().getTarget() instanceof Button || e.getMouseEvent().getTarget() instanceof Text) {
            Button button = JavaFX.fromNode(((Node) e.getMouseEvent().getTarget()), Button.class);
            if(button == null) return;

            if(button.getId().equals("preview_button")) {
                Utils.setImage();
            }
        }
    }
}
