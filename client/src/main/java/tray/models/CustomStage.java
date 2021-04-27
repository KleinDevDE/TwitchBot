/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.SimpleDoubleProperty
 *  javafx.geometry.Rectangle2D
 *  javafx.scene.layout.AnchorPane
 *  javafx.stage.Screen
 *  javafx.stage.Stage
 *  javafx.stage.StageStyle
 */
package tray.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tray.models.Location;

public class CustomStage
extends Stage {
    private final Location bottomRight;
    private SimpleDoubleProperty xLocationProperty = new SimpleDoubleProperty(){

        public void set(double newValue) {
            CustomStage.this.setX(newValue);
        }

        public double get() {
            return CustomStage.this.getX();
        }
    };
    private SimpleDoubleProperty yLocationProperty = new SimpleDoubleProperty(){

        public void set(double newValue) {
            CustomStage.this.setY(newValue);
        }

        public double get() {
            return CustomStage.this.getY();
        }
    };

    public CustomStage(AnchorPane ap, StageStyle style) {
        this.initStyle(style);
        this.setSize(ap.getPrefWidth(), ap.getPrefHeight());
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double x = screenBounds.getMinX() + screenBounds.getWidth() - ap.getPrefWidth() - 2.0;
        double y = screenBounds.getMinY() + screenBounds.getHeight() - ap.getPrefHeight() - 2.0;
        this.bottomRight = new Location(x, y);
    }

    public Location getBottomRight() {
        return this.bottomRight;
    }

    public void setSize(double width, double height) {
        this.setWidth(width);
        this.setHeight(height);
    }

    public Location getOffScreenBounds() {
        Location loc = this.getBottomRight();
        return new Location(loc.getX() + this.getWidth(), loc.getY());
    }

    public void setLocation(Location loc) {
        this.setX(loc.getX());
        this.setY(loc.getY());
    }

    public SimpleDoubleProperty xLocationProperty() {
        return this.xLocationProperty;
    }

    public SimpleDoubleProperty yLocationProperty() {
        return this.yLocationProperty;
    }

}

