package eu.iamgio.flatimages;

import eu.iamgio.libfx.api.JavaFX;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Gio on 10/05/2017.
 */
public final class Components {

    private Components(){}

    public static ImageView image;
    
    public static Node[] bar,
                         upload,
                         imageSize,
                         backgroundColors,
                         shadowLength,
                         fading,
                         shadowOpacity,
                         create,
                         messages;

    public static Node[][] editors;

    private static Node[] all;
    private static HashMap<Node, Double> y = new HashMap<>();

    public static void init() {
        bar = fromIds("upsect", "title", "version");

        upload = fromIds("upload_btn", "path_field");
        imageSize = fromIds("imagesize_txt", "width_field", "height_field");
        backgroundColors = fromIds("background_txt", "colorpicker");
        shadowLength = fromIds("shadowlength_txt", "shadowlength_field");
        fading = fromIds("fading_txt", "fading_checkbtn");
        shadowOpacity = fromIds("shadowopacity_txt", "shadowopacity_slider");
        create = fromIds("create_btn");
        messages = fromIds("message_bg", "message_text");
        
        image = ((ImageView) JavaFX.fromId("img"));

        editors = new Node[][]{imageSize, backgroundColors, shadowLength, fading, shadowOpacity, create};
    }

    private static Node[] fromIds(String...ids) {
        List<Node> nodes = new ArrayList<>();
        for(String id : ids) {
            nodes.add(JavaFX.fromId(id));
        }
        return nodes.toArray(new Node[nodes.size()]);
    }

    public static Node[] getAll() {
        if(all != null)
            return all;
        List<Node> nodes = new ArrayList<>();
        for(Node[] section : editors) {
            Collections.addAll(nodes, section);
        }
        all = nodes.toArray(new Node[nodes.size()]);
        return all;
    }
}
