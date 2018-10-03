package eu.iamgio.flatimages.messages;

import eu.iamgio.flatimages.Components;
import eu.iamgio.libfx.api.animations.Animation;
import eu.iamgio.libfx.api.animations.AnimationHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Created by Gio on 13/05/2017.
 */
public class MessageAnnouncer {

    private Message message;
    private Duration waitTime;

    public MessageAnnouncer(Message message, Duration waitTime) {
        this.message = message;
        this.waitTime = waitTime;
    }

    public void announce() {
        Node[] messageNodes = Components.messages;
        ((Label) messageNodes[1]).setText(message.getText());
        if(messageNodes[0].getTranslateY() == 0)
            playAnimation(false, () -> wait(() -> playAnimation(true, () -> {})));
    }

    private void playAnimation(boolean reverse, AnimationHandler handler) {
        byte rev = (byte) (reverse ? -1 : 1);

        for(Node node : Components.messages) {
            if(!node.isVisible())
                node.setVisible(true);
            Animation moveY = new Animation(Animation.Type.MOVEMENT_Y, node.getTranslateY()+120*rev, Duration.seconds(0.15), false);
            moveY.play(node);
            moveY.setOnAnimationCompleted(handler);
        }
    }

    private void wait(AnimationHandler handler) {
        Animation waitAnimation = new Animation(Animation.Type.OPACITY, 1, waitTime, false);
        waitAnimation.play(Components.messages[0]);
        waitAnimation.setOnAnimationCompleted(handler);
    }
}
