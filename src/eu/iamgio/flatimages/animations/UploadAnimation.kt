package eu.iamgio.flatimages.animations

import eu.iamgio.flatimages.Components
import eu.iamgio.libfx.api.animations.Animation
import eu.iamgio.libfx.api.animations.AnimationHandler
import javafx.scene.Node
import javafx.util.Duration

/**
 * Created by Gio on 11/05/2017.
 */
class UploadAnimation {

    private var index: Int = 0

    inner class UIBuilderAnimation {

        fun play(unload: Boolean, handler: AnimationHandler) {
            val editors: Array<Array<Node>> = Components.editors
            val duration: Duration = Duration.seconds(0.20)

            val fade: Animation = fadeAnim(unload, duration)

            if(!unload) {
                if(index < editors.size) {
                    for(i in 0..editors[index].size - 1) {
                        val node: Node = editors[index][i]

                        val moveY: Animation = moveYAnim(node, unload, duration)
                        node.isVisible = true
                        fade.play(node)
                        moveY.play(node)
                        if(i == editors[index].size - 1) {
                            moveY.setOnAnimationCompleted {
                                if(!editors[0][0].isVisible) {
                                    for(n in Components.getAll()) {
                                        n.isVisible = false
                                    }
                                }
                                handler.run()
                                index++
                                play(unload, handler)
                            }
                        }
                    }
                }
            }
            else {
                for(node in Components.getAll()) {
                    fade.play(node)
                    fade.setOnAnimationCompleted {
                        handler.run()
                        node.isVisible = false
                        node.translateY += 20
                    }
                }
            }
        }

        private fun fadeAnim(unload: Boolean, duration: Duration): Animation {
            return Animation(Animation.Type.OPACITY, if(!unload) 1.0 else 0.0, duration, false)
        }

        private fun moveYAnim(n: Node, unload: Boolean, duration: Duration): Animation {
            return Animation(Animation.Type.MOVEMENT_Y, n.translateY - 20*if(unload) -1 else 1, duration, false)
        }
    }

    inner class UploadNodesAnimation {
        fun play(reverse: Boolean, handler: AnimationHandler) {
            val rev: Byte = if (reverse) -1 else 1

            val duration: Duration = Duration.seconds(0.35)
            var animation: Animation = Animation(null, 0.0, null, false)

            for (n in Components.upload) {
                animation = Animation(Animation.Type.MOVEMENT_Y, n.translateY - 75 * rev, duration, false)
                animation.play(n)
            }

            animation.setOnAnimationCompleted { handler.run() }
        }
    }
}