package eu.iamgio.flatimages;

import eu.iamgio.libfx.api.CSS;
import eu.iamgio.libfx.api.FXML;
import eu.iamgio.libfx.api.JavaFX;
import eu.iamgio.libfx.api.animations.Animation;
import eu.iamgio.libfx.api.elements.SimpleStage;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FlatImage
{
    private Parent root;
    private BufferedImage img;
    private int width;
    private int height;
    private Color color;
    private double length;

    private BufferedImage bg;
    private BufferedImage image;
    private BufferedImage built;

    public FlatImage()
    {
        //Selected color
        javafx.scene.paint.Color color = ((ColorPicker) JavaFX.fromId("color_picker")).getValue();

        this.root = JavaFX.getRoot();
        this.img = Utils.bufferedImage;
        this.width = Integer.parseInt(((TextField) JavaFX.fromId("width_field")).getText());
        this.height = Integer.parseInt(((TextField) JavaFX.fromId("height_field")).getText());
        this.color = new Color(
                (float) color.getRed(), (float) color.getGreen(), (float) color.getBlue());
        this.length = Integer.parseInt(((TextField) JavaFX.fromId("shadow_field")).getText());
    }

    /**
     * Sets the background
     * @return this
     */
    public FlatImage setBackground()
    {
        //Creates a new image
        BufferedImage bg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bg.createGraphics();

        //Paints it
        graphics.setPaint(color);
        graphics.fillRect(0, 0, width, height);

        this.bg = bg;

        return this;
    }

    /**
     * Draws the image
     * @return this
     */
    public FlatImage setImage()
    {
        //Creates a new blank image
        BufferedImage flat = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D graphics = flat.createGraphics();

        //Draws the uploaded one
        graphics.drawImage(
                img, flat.getWidth() / 20, flat.getHeight() / 20, img.getWidth(), img.getHeight(),
                null);

        this.image = flat;

        return this;
    }

    /**
     * Applies the shadow
     * @return this
     */
    public FlatImage applyShadow()
    {
        BufferedImage image = this.image;

        //Gets the opacity level from the slider
        float alpha = (float) ((Slider) JavaFX.fromId("opacity_slider")).getValue() / 100;

        double endX = width, endY = height;

        for(int y = 1; y < height; y++)
            for(int x = 1; x < width; x++)
                //Now we can select all the pixels
                if(image.getRGB(x - 1, y - 1) != 0 && image.getRGB(x, y) == 0) //Checks if the high left pixel is colored
                {
                    if(((CheckBox) JavaFX.fromId("shading_checkbox")).isSelected())
                        //Decreases the opacity
                        alpha -= 1 / (endX + endY) / length;

                    Color shadowColor = new Color(0, 0, 0, alpha >= 0 ? alpha : 0);
                    //Refreshes the RGB
                    image.setRGB(x, y, shadowColor.getRGB());
                }

        this.image = image;

        return this;
    }

    /**
     * Builds all and creates the file
     */
    public void build()
    {
        //Loads the background
        BufferedImage finalImage = bg;
        //Draws the final image
        finalImage.createGraphics().drawImage(
                image, width / 20, height / 20, image.getWidth(), image.getHeight(),
                null);

        File imageFile = Utils.imageFile;

        //Creates the new filename
        String fileName = imageFile.getParent() + File.separator +
                imageFile.getName().replace(" ", "_").replace(".png", "") + "_flat.png";
        try
        {
            //Writes and saves the image
            ImageIO.write(finalImage, "png", new File(fileName));
            //Sets the final scene
            setFinalScene(fileName);
        }
        catch(IOException e)
        {
            Text errorTitle = new Text("\n Oops!");
            errorTitle.setFont(javafx.scene.text.Font.font("Segoe UI", 20));
            errorTitle.setFill(javafx.scene.paint.Paint.valueOf("ff0000"));

            Text error = new Text("\n \n \n Something went wrong. Please check the file name and the directory name.");
            error.setFont(javafx.scene.text.Font.font("Segoe UI", 15));
            error.setFill(javafx.scene.paint.Paint.valueOf("ff0000"));

            ((Pane) root).setBackground(new Background(
                    new BackgroundFill(javafx.scene.paint.Paint.valueOf("fff"), null, null)));
            ((Pane) root).getChildren().addAll(errorTitle, error);
        }
    }

    /**
     * Loads the final scene
     * @param fileName File name
     */
    private void setFinalScene(String fileName)
    {
        //Loads the new scene
        Parent root = FXML.load(FlatImages.class, "scenes/FinalScene.fxml");
        Scene scene = new Scene(root, 950, 600);
        CSS.load(FlatImages.class, scene, "stylesheets/styles.css");
        new SimpleStage(FlatImages.stage).show(scene, "Flat v" + FlatImages.VERSION + " - Completed!", false);

        //Plays the animation
        Rectangle target = ((Rectangle) JavaFX.fromId("animation_rectangle"));
        new Animation(Animation.Type.MOVEMENT_Y, -620, Duration.seconds(2), false).play(target);

        //Creates the image
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(FlatImages.class.getResourceAsStream("assets/done.png")));
        imageView.setTranslateX(330);
        imageView.setTranslateY(100);

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setTranslateY(330);
        box.setMinWidth(950);

        //Creates the text
        Text text = new Text("Image saved to " + fileName);
        text.setFont(javafx.scene.text.Font.font("Segoe UI Light"));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(javafx.scene.paint.Paint.valueOf("FFF"));
        box.getChildren().add(text);

        ((Pane) root).getChildren().addAll(imageView, box);
    }
}
