package eu.iamgio.flatimages.listeners;

import eu.iamgio.customevents.api.EventHandler;
import eu.iamgio.customevents.api.Listener;
import eu.iamgio.flatimages.FlatImage;
import eu.iamgio.flatimages.FlatImages;
import eu.iamgio.flatimages.Utils;
import eu.iamgio.libfx.api.JavaFX;
import eu.iamgio.libfx.api.events.mouse.MousePressEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Created by Gio on 20/12/2016.
 */
public class CreateListener implements Listener
{
    @EventHandler
    public void click(MousePressEvent e)
    {
        if(e.getMouseEvent().getTarget() instanceof Button || e.getMouseEvent().getTarget() instanceof Text)
        {
            Button button = JavaFX.fromNode(((Node) e.getMouseEvent().getTarget()), Button.class);
            if(button == null) return;

            if(button.getId().equals("create_button"))
            {
                //Gets width, height and length (not as numbers)
                TextField widthField = (TextField) JavaFX.fromId("width_field");
                TextField heightField = (TextField) JavaFX.fromId("height_field");
                TextField lengthField = (TextField) JavaFX.fromId("shadow_field");

                //Removes eventual error borders
                Utils.removeError(widthField);
                Utils.removeError(heightField);
                Utils.removeError(lengthField);

                boolean canBuild = true;

                //Checks the validity of the width
                try
                {
                    int width = Integer.parseInt(widthField.getText());
                    if(width <= 0) throw new Exception();
                }
                catch(Exception ex)
                {
                    Utils.addError(widthField);
                    canBuild = false;
                }

                //Same for the height
                try
                {
                    int height = Integer.parseInt(heightField.getText());
                    if(height <= 0) throw new Exception();
                }
                catch(Exception ex)
                {
                    Utils.addError(heightField);
                    canBuild = false;
                }

                //Same for the length
                try
                {
                    int length = Integer.parseInt(lengthField.getText());
                    if(length < 0) throw new Exception();
                }
                catch(Exception ex)
                {
                    Utils.addError(lengthField);
                    canBuild = false;
                }

                if(canBuild)
                {
                    FlatImages.process = true;
                    new FlatImage().setBackground().setImage().applyShadow().build();
                }
            }
        }
    }
}
