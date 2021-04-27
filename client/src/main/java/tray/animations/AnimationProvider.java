/*
 * Decompiled with CFR 0.145.
 */
package tray.animations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tray.animations.TrayAnimation;

public class AnimationProvider {
    private List<TrayAnimation> animationsList = new ArrayList<TrayAnimation>();

    public AnimationProvider(TrayAnimation ... animations) {
        Collections.addAll(this.animationsList, animations);
    }

    public void addAll(TrayAnimation ... animations) {
        Collections.addAll(this.animationsList, animations);
    }

    public TrayAnimation get(int index) {
        return this.animationsList.get(index);
    }

    public TrayAnimation findFirstWhere(Predicate<? super TrayAnimation> predicate) {
        return this.animationsList.stream().filter(predicate).findFirst().orElse(null);
    }

    public List<TrayAnimation> where(Predicate<? super TrayAnimation> predicate) {
        return this.animationsList.stream().filter(predicate).collect(Collectors.toList());
    }
}

