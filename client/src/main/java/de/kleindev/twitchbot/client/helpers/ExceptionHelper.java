package de.kleindev.twitchbot.client.helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ExceptionHelper implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        handle(t, e);
    }

    /**
     * Alias for "handle(Thread.currentThread(), e)"
     * @param e
     */
    public static void handle(Throwable e){
        handle(Thread.currentThread(), e);
    }

    public static void handle(Thread t, Throwable e){
        ButtonType sendReportButton = new ButtonType("Send report...", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE, sendReportButton);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.isPresent() && buttonType.get() == sendReportButton){
            //TODO Send report to server
        }
    }
}
