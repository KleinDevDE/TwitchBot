/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.scene.control.Label
 *  javafx.scene.image.Image
 *  javafx.scene.image.ImageView
 *  javafx.scene.input.MouseEvent
 *  javafx.scene.layout.AnchorPane
 *  javafx.scene.paint.Paint
 *  javafx.scene.shape.Rectangle
 *  javafx.stage.StageStyle
 *  javafx.util.Duration
 */
package tray.notification;

import java.io.IOException;
import java.net.URL;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tray.animations.AnimationProvider;
import tray.animations.AnimationType;
import tray.animations.FadeAnimation;
import tray.animations.PopupAnimation;
import tray.animations.SlideAnimation;
import tray.animations.TrayAnimation;
import tray.models.CustomStage;
import tray.models.Location;
import tray.notification.NotificationType;

public final class TrayNotification {
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblMessage;
    @FXML
    private Label lblClose;
    @FXML
    private ImageView imageIcon;
    @FXML
    private Rectangle rectangleColor;
    @FXML
    private AnchorPane rootNode;
    private CustomStage stage;
    private NotificationType notificationType;
    private AnimationType animationType;
    private EventHandler<ActionEvent> onDismissedCallBack;
    private EventHandler<ActionEvent> onShownCallback;
    private TrayAnimation animator;
    private AnimationProvider animationProvider;

    public TrayNotification(String title, String body, Image img, Paint rectangleFill) {
        this.initTrayNotification(title, body, NotificationType.CUSTOM);
        this.setImage(img);
        this.setRectangleFill(rectangleFill);
    }

    public TrayNotification(String title, String body, NotificationType notificationType) {
        this.initTrayNotification(title, body, notificationType);
    }

    public TrayNotification() {
        this.initTrayNotification("", "", NotificationType.CUSTOM);
    }

    private void initTrayNotification(String title, String message, NotificationType type) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/tray/views/TrayNotification.fxml"));
            fxmlLoader.setController((Object)this);
            fxmlLoader.load();
            this.initStage();
            this.initAnimations();
            this.setTray(title, message, type);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initAnimations() {
        this.animationProvider = new AnimationProvider(new FadeAnimation(this.stage), new SlideAnimation(this.stage), new PopupAnimation(this.stage));
        this.setAnimationType(AnimationType.SLIDE);
    }

    private void initStage() {
        this.stage = new CustomStage(this.rootNode, StageStyle.UNDECORATED);
        this.stage.setScene(new Scene((Parent)this.rootNode));
        this.stage.setAlwaysOnTop(true);
        this.stage.setLocation(this.stage.getBottomRight());
        this.lblClose.setOnMouseClicked(e -> this.dismiss());
    }

    public void setNotificationType(NotificationType nType) {
        this.notificationType = nType;
        URL imageLocation = null;
        String paintHex = null;
        switch (nType) {
            case INFORMATION: {
                imageLocation = this.getClass().getResource("/tray/resources/info.png");
                paintHex = "#2C54AB";
                break;
            }
            case NOTICE: {
                imageLocation = this.getClass().getResource("/tray/resources/notice.png");
                paintHex = "#8D9695";
                break;
            }
            case SUCCESS: {
                imageLocation = this.getClass().getResource("/tray/resources/success.png");
                paintHex = "#009961";
                break;
            }
            case WARNING: {
                imageLocation = this.getClass().getResource("/tray/resources/warning.png");
                paintHex = "#E23E0A";
                break;
            }
            case ERROR: {
                imageLocation = this.getClass().getResource("/tray/resources/error.png");
                paintHex = "#CC0033";
                break;
            }
            case CUSTOM: {
                return;
            }
        }
        this.setRectangleFill(Paint.valueOf(paintHex));
        this.setImage(new Image(imageLocation.toString()));
        this.setTrayIcon(this.imageIcon.getImage());
    }

    public NotificationType getNotificationType() {
        return this.notificationType;
    }

    public void setTray(String title, String message, NotificationType type) {
        this.setTitle(title);
        this.setMessage(message);
        this.setNotificationType(type);
    }

    public void setTray(String title, String message, Image img, Paint rectangleFill, AnimationType animType) {
        this.setTitle(title);
        this.setMessage(message);
        this.setImage(img);
        this.setRectangleFill(rectangleFill);
        this.setAnimationType(animType);
    }

    public boolean isTrayShowing() {
        return this.animator.isShowing();
    }

    public void showAndDismiss(Duration dismissDelay) {
        if (this.isTrayShowing()) {
            this.dismiss();
        } else {
            this.stage.show();
            this.onShown();
            this.animator.playSequential(dismissDelay);
        }
        this.onDismissed();
    }

    public void showAndWait() {
        if (!this.isTrayShowing()) {
            this.stage.show();
            this.animator.playShowAnimation();
            this.onShown();
        }
    }

    public void dismiss() {
        if (this.isTrayShowing()) {
            this.animator.playDismissAnimation();
            this.onDismissed();
        }
    }

    private void onShown() {
        if (this.onShownCallback != null) {
            this.onShownCallback.handle(new ActionEvent());
        }
    }

    private void onDismissed() {
        if (this.onDismissedCallBack != null) {
            this.onDismissedCallBack.handle(new ActionEvent());
        }
    }

    public void setOnDismiss(EventHandler<ActionEvent> event) {
        this.onDismissedCallBack = event;
    }

    public void setOnShown(EventHandler<ActionEvent> event) {
        this.onShownCallback = event;
    }

    public void setTrayIcon(Image img) {
        this.stage.getIcons().clear();
        this.stage.getIcons().add(img);
    }

    public Image getTrayIcon() {
        return (Image)this.stage.getIcons().get(0);
    }

    public void setTitle(String txt) {
        this.lblTitle.setText(txt);
    }

    public String getTitle() {
        return this.lblTitle.getText();
    }

    public void setMessage(String txt) {
        this.lblMessage.setText(txt);
    }

    public String getMessage() {
        return this.lblMessage.getText();
    }

    public void setImage(Image img) {
        this.imageIcon.setImage(img);
        this.setTrayIcon(img);
    }

    public Image getImage() {
        return this.imageIcon.getImage();
    }

    public void setRectangleFill(Paint value) {
        this.rectangleColor.setFill(value);
    }

    public Paint getRectangleFill() {
        return this.rectangleColor.getFill();
    }

    public void setAnimationType(AnimationType type) {
        this.animator = this.animationProvider.findFirstWhere(a -> a.getAnimationType() == type);
        this.animationType = type;
    }

    public AnimationType getAnimationType() {
        return this.animationType;
    }

}

