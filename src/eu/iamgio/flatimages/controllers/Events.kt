package eu.iamgio.flatimages.controllers

import eu.iamgio.flatimages.Components
import eu.iamgio.flatimages.FlatImages
import eu.iamgio.flatimages.fileloaders.Uploader
import eu.iamgio.flatimages.generator.Background
import eu.iamgio.flatimages.generator.Image
import eu.iamgio.flatimages.generator.ImageGenerator
import eu.iamgio.flatimages.generator.Shadow
import eu.iamgio.flatimages.messages.Message
import eu.iamgio.flatimages.messages.MessageAnnouncer
import eu.iamgio.libfx.api.JavaFX
import javafx.embed.swing.SwingFXUtils
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.util.Duration

/**
 * Created by Gio on 12/05/2017.
 */
class Events {

    fun initButtonEffects() {
        val scale = 7
        JavaFX.getRoot().childrenUnmodifiable.forEach { n ->
            if(n is Button) {
                n.setOnMousePressed {
                    n.prefWidth -= scale
                    n.prefHeight -= scale
                    n.translateX += scale/2
                    n.translateY += scale/2
                }
                n.setOnMouseReleased {
                    n.prefWidth += scale
                    n.prefHeight += scale
                    n.translateX -= scale/2
                    n.translateY -= scale/2
                }
            }
        }
    }

    fun registerKeys() {
        FlatImages.scene.setOnKeyReleased { e ->
            when(e.code) {
                KeyCode.ESCAPE -> {
                    if (Components.image.image != null)
                        Uploader.unload()
                }
                KeyCode.K -> {
                    FlatImages.live = FlatImages.live xor true
                    val status: String = if(FlatImages.live) "ON" else "OFF. Press F5 to update the preview"
                    MessageAnnouncer(Message("Live preview: $status. Press K to toggle again"), Duration.seconds(2.0))
                            .announce()
                }
                KeyCode.F5 -> {
                    val generator: ImageGenerator = ImageGenerator.auto()
                    if(!generator.error) {
                        Components.image.image = SwingFXUtils.toFXImage(generator.image, null)
                    }
                }
            }
        }
    }

    fun bind() {
        class Binder {
            fun bind() {
                if(FlatImages.live) {
                    val generator: ImageGenerator = ImageGenerator.auto()
                    if(!generator.error) {
                        Components.image.image = SwingFXUtils.toFXImage(generator.image, null)
                    }
                }
            }
        }

        for(node in Components.getAll()) {
            val binder: Binder = Binder()
            when(node) {
                is TextField -> node.textProperty().addListener { p -> binder.bind() }
                is CheckBox -> node.selectedProperty().addListener { p -> binder.bind() }
                is Slider -> node.valueProperty().addListener { p -> binder.bind() }
                is ColorPicker -> node.valueProperty().addListener { p -> binder.bind() }
            }
        }
    }
}