package eu.iamgio.flatimages.animations

import eu.iamgio.flatimages.Components
import eu.iamgio.libfx.api.animations.Animation
import javafx.util.Duration

/**
 * Created by Gio on 12/05/2017.
 */
class BarAnimation {

    fun play(reverse: Boolean) {
        val rev: Byte = if(reverse) -1 else 1

        Components.bar.forEach { n ->
            val animation: Animation = Animation(Animation.Type.MOVEMENT_Y, n.translateY + 150*rev, Duration.seconds(0.15), false)
            animation.play(n)
        }
    }
}