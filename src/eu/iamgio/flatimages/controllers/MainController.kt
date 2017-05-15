package eu.iamgio.flatimages.controllers

import eu.iamgio.flatimages.fileloaders.Uploader
import eu.iamgio.flatimages.generator.ImageGenerator
import eu.iamgio.flatimages.messages.Message
import eu.iamgio.flatimages.messages.MessageAnnouncer
import eu.iamgio.libfx.api.JavaFX
import eu.iamgio.libfx.api.animations.Animation
import eu.iamgio.libfx.api.filemanagers.FileChooser
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.input.MouseEvent
import javafx.scene.layout.VBox
import javafx.util.Duration
import java.io.File
import java.net.URL
import java.util.*

/**
 * Created by Gio on 09/05/2017.
 */
class MainController : Initializable {

    @FXML lateinit var uploadButton: Button
    @FXML lateinit var createButton: Button
    @FXML lateinit var strip: VBox

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        uploadButton.setOnMouseClicked {
            try {
                val file: File = FileChooser().choose("Image files (*.PNG)", "png")
                Uploader.upload(file)
            }
            catch(e: Exception) {}

            strip.addEventFilter(MouseEvent.MOUSE_ENTERED) {
                val animation: Animation = Animation(Animation.Type.OPACITY, 1.0, Duration.seconds(0.2), false)
                animation.play(JavaFX.fromId("contacts_vbox"))
            }
            strip.addEventFilter(MouseEvent.MOUSE_EXITED) {
                val animation: Animation = Animation(Animation.Type.OPACITY, 0.0, Duration.seconds(0.2), false)
                animation.play(JavaFX.fromId("contacts_vbox"))
            }
        }

        createButton.setOnMouseClicked {
            val generator: ImageGenerator = ImageGenerator.auto()
            if(!generator.error) {
                val out: File = generator.build()

                MessageAnnouncer(Message("Created file " + out.absolutePath), Duration.seconds(3.0)).announce()
            }
        }
    }
}