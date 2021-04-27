/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  javafx.util.Duration
 */
package tray.animations;

import javafx.util.Duration;

public interface TrayAnimation {
    public AnimationType getAnimationType();

    public void playSequential(Duration var1);

    public void playShowAnimation();

    public void playDismissAnimation();

    public boolean isShowing();
}

