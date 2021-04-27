/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  javafx.animation.Animation
 *  javafx.animation.Interpolator
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
import javafx.animation.Interpolator;
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

public class SlideAnimation
implements TrayAnimation {
    private final Timeline showAnimation;
    private final Timeline dismissAnimation;
    private final SequentialTransition sq;
    private final CustomStage stage;
    private boolean trayIsShowing;

    public SlideAnimation(CustomStage customStage) {
        this.stage = customStage;
        this.showAnimation = this.setupShowAnimation();
        this.dismissAnimation = this.setupDismissAnimation();
        this.sq = new SequentialTransition(new Animation[]{this.setupShowAnimation(), this.setupDismissAnimation()});
    }

    private Timeline setupShowAnimation() {
        Timeline tl = new Timeline();
        double offScreenX = this.stage.getOffScreenBounds().getX();
        KeyValue kvX = new KeyValue((WritableValue)this.stage.xLocationProperty(), (Object)offScreenX);
        KeyFrame frame1 = new KeyFrame(Duration.ZERO, new KeyValue[]{kvX});
        Interpolator interpolator = Interpolator.TANGENT((Duration)Duration.millis((double)300.0), (double)50.0);
        KeyValue kvInter = new KeyValue((WritableValue)this.stage.xLocationProperty(), (Object)this.stage.getBottomRight().getX(), interpolator);
        KeyFrame frame2 = new KeyFrame(Duration.millis((double)1300.0), new KeyValue[]{kvInter});
        KeyValue kvOpacity = new KeyValue((WritableValue)this.stage.opacityProperty(), (Object)0.0);
        KeyFrame frame3 = new KeyFrame(Duration.ZERO, new KeyValue[]{kvOpacity});
        KeyValue kvOpacity2 = new KeyValue((WritableValue)this.stage.opacityProperty(), (Object)1.0);
        KeyFrame frame4 = new KeyFrame(Duration.millis((double)1000.0), new KeyValue[]{kvOpacity2});
        tl.getKeyFrames().addAll(new KeyFrame[]{frame1, frame2, frame3, frame4});
        tl.setOnFinished(e -> {
            this.trayIsShowing = true;
        });
        return tl;
    }

    private Timeline setupDismissAnimation() {
        Timeline tl = new Timeline();
        double offScreenX = this.stage.getOffScreenBounds().getX();
        Interpolator interpolator = Interpolator.TANGENT((Duration)Duration.millis((double)300.0), (double)50.0);
        double trayPadding = 3.0;
        KeyValue kvX = new KeyValue((WritableValue)this.stage.xLocationProperty(), (Object)(offScreenX + trayPadding), interpolator);
        KeyFrame frame1 = new KeyFrame(Duration.millis((double)1400.0), new KeyValue[]{kvX});
        KeyValue kvOpacity = new KeyValue((WritableValue)this.stage.opacityProperty(), (Object)0.4);
        KeyFrame frame2 = new KeyFrame(Duration.millis((double)2000.0), new KeyValue[]{kvOpacity});
        tl.getKeyFrames().addAll(new KeyFrame[]{frame1, frame2});
        tl.setOnFinished(e -> {
            this.trayIsShowing = false;
            this.stage.close();
            this.stage.setLocation(this.stage.getBottomRight());
        });
        return tl;
    }

    @Override
    public AnimationType getAnimationType() {
        return AnimationType.SLIDE;
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

