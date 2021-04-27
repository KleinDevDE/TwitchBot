/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  javafx.animation.Animation
 *  javafx.animation.KeyFrame
 *  javafx.animation.KeyValue
 *  javafx.animation.SequentialTransition
 *  javafx.animation.Timeline
 *  javafx.beans.property.DoubleProperty
 *  javafx.beans.property.SimpleDoubleProperty
 *  javafx.beans.value.WritableValue
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.util.Duration
 */
package tray.animations;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.WritableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.animations.TrayAnimation;
import tray.models.CustomStage;
import tray.models.Location;

public class PopupAnimation
implements TrayAnimation {
    private final Timeline showAnimation;
    private final Timeline dismissAnimation;
    private final SequentialTransition sq;
    private final CustomStage stage;
    private boolean trayIsShowing;

    public PopupAnimation(CustomStage s) {
        this.stage = s;
        this.showAnimation = this.setupShowAnimation();
        this.dismissAnimation = this.setupDismissAnimation();
        this.sq = new SequentialTransition(new Animation[]{this.setupShowAnimation(), this.setupDismissAnimation()});
    }

    private Timeline setupDismissAnimation() {
        Timeline tl = new Timeline();
        KeyValue kv1 = new KeyValue((WritableValue)this.stage.yLocationProperty(), (Object)(this.stage.getY() + this.stage.getWidth()));
        KeyFrame kf1 = new KeyFrame(Duration.millis((double)2000.0), new KeyValue[]{kv1});
        KeyValue kv2 = new KeyValue((WritableValue)this.stage.opacityProperty(), (Object)0.0);
        KeyFrame kf2 = new KeyFrame(Duration.millis((double)2000.0), new KeyValue[]{kv2});
        tl.getKeyFrames().addAll(new KeyFrame[]{kf1, kf2});
        tl.setOnFinished(e -> {
            this.trayIsShowing = false;
            this.stage.close();
            this.stage.setLocation(this.stage.getBottomRight());
        });
        return tl;
    }

    private Timeline setupShowAnimation() {
        Timeline tl = new Timeline();
        KeyValue kv1 = new KeyValue((WritableValue)this.stage.yLocationProperty(), (Object)(this.stage.getBottomRight().getY() + this.stage.getWidth()));
        KeyFrame kf1 = new KeyFrame(Duration.ZERO, new KeyValue[]{kv1});
        KeyValue kv2 = new KeyValue((WritableValue)this.stage.yLocationProperty(), (Object)this.stage.getBottomRight().getY());
        KeyFrame kf2 = new KeyFrame(Duration.millis((double)1000.0), new KeyValue[]{kv2});
        KeyValue kv3 = new KeyValue((WritableValue)this.stage.opacityProperty(), (Object)0.0);
        KeyFrame kf3 = new KeyFrame(Duration.ZERO, new KeyValue[]{kv3});
        KeyValue kv4 = new KeyValue((WritableValue)this.stage.opacityProperty(), (Object)1.0);
        KeyFrame kf4 = new KeyFrame(Duration.millis((double)2000.0), new KeyValue[]{kv4});
        tl.getKeyFrames().addAll(new KeyFrame[]{kf1, kf2, kf3, kf4});
        tl.setOnFinished(e -> {
            this.trayIsShowing = true;
        });
        return tl;
    }

    @Override
    public AnimationType getAnimationType() {
        return AnimationType.POPUP;
    }

    @Override
    public void playSequential(Duration dismissDelay) {
        ((Animation)this.sq.getChildren().get(1)).setDelay(dismissDelay);
        this.sq.play();
    }

    @Override
    public void playShowAnimation() {
        this.showAnimation.play();
    }

    @Override
    public void playDismissAnimation() {
        this.dismissAnimation.play();
    }

    @Override
    public boolean isShowing() {
        return this.trayIsShowing;
    }
}

