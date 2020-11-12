package de.kleindev.twitchbot.client.fxmlcontrollers.debug;

import de.kleindev.twitchbot.client.helpers.ExceptionHelper;
import de.kleindev.twitchbot.client.websocket.WebSocketManager;
import de.kleindev.twitchbot.client.websocket.packets.auth.AuthPacket;
import de.kleindev.twitchbot.client.websocket.packets.auth.AuthType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.NonNull;

import java.io.IOException;
import java.util.regex.Pattern;

public class Debug_Login {
    private static Stage stage = new Stage();

    public TextField username;
    public TextField password;
    public TextField email;
    public TextField twitch_username;
    public TextField twitch_token;

    private final Pattern PATTERN_EMAIL = Pattern.compile("^(.+)@(.+)\\.(.+)$");

    /**
     * Alias for "show(false)"
     */
    public static void show(){
        show(false);
    }

    /**
     * This will show this scene
     * @param replaceActuallyStage
     */
    @NonNull
    public static void show(boolean replaceActuallyStage){
        try {
            stage.setScene(new Scene(FXMLLoader.load(Debug_Login.class.getClassLoader().getResource("fxml/debug/Debug_Login.fxml"))));
        } catch (IOException e) {
            ExceptionHelper.handle(e);
        }
        stage.show();
    }

    public void onResetClick(MouseEvent mouseEvent) {
        username.setText("");
        password.setText("");
        email.setText("");
        twitch_username.setText("");
        twitch_token.setText("");

        username.getStyleClass().remove("error");
        password.getStyleClass().remove("error");
        email.getStyleClass().remove("error");
        twitch_username.getStyleClass().remove("error");
        twitch_token.getStyleClass().remove("error");
    }

    public void onLoginClick(MouseEvent mouseEvent) {
        if (username.getText().isEmpty()){
            username.getStyleClass().add("error");
            return;
        }
        if (password.getText().isEmpty()){
            password.getStyleClass().add("error");
            return;
        }
        AuthPacket authPacket = new AuthPacket(AuthType.LOGIN, new String[]{null, username.getText(), password.getText()});
        WebSocketManager.getClient("auth").send(authPacket.getSendableString());
    }

    public void onRegisterClick(MouseEvent mouseEvent) {
        if (username.getText().isEmpty()){
            username.getStyleClass().add("error");
            return;
        }
        if (password.getText().isEmpty()){
            password.getStyleClass().add("error");
            return;
        }
        if (email.getText().isEmpty()){
            email.getStyleClass().add("error");
            return;
        }
        if (twitch_token.getText().isEmpty()){
            twitch_token.getStyleClass().add("error");
            return;
        }
        if (twitch_username.getText().isEmpty()){
            twitch_username.getStyleClass().add("error");
            return;
        }
        AuthPacket authPacket = new AuthPacket(AuthType.REGISTER, new String[]{null, username.getText(), password.getText(), email.getText(), twitch_username.getText(), twitch_token.getText()});
        WebSocketManager.getClient("login").send(authPacket.getSendableString());
    }

    public void validateUsername(KeyEvent keyEvent) {
        if (username.getText().length() > 30 || username.getText().length() < 5){
            username.getStyleClass().add("error");
            //TODO Tooltip
        } else username.getStyleClass().remove("error");
    }

    public void validatePassword(KeyEvent keyEvent) {
        if (password.getText().length() > 30 || password.getText().length() < 8){
            password.getStyleClass().add("error");
            //TODO Tooltip
        } else password.getStyleClass().remove("error");
    }

    public void validateEmail(KeyEvent keyEvent) {
        if (!PATTERN_EMAIL.matcher(email.getText()).matches()){
            email.getStyleClass().add("error");
        } else email.getStyleClass().remove("error");
    }

    public void validateTwitchUsername(KeyEvent keyEvent) {
        if (twitch_username.getText().length() > 30 || twitch_username.getText().length() < 3){
            twitch_username.getStyleClass().add("error");
            //TODO Tooltip
        } else twitch_username.getStyleClass().remove("error");
    }

    public void validateTwitchToken(KeyEvent keyEvent) {
        if (twitch_token.getText().length() > 50 || twitch_token.getText().length() < 10){
            twitch_token.getStyleClass().add("error");
            //TODO Tooltip
        } else twitch_token.getStyleClass().remove("error");
    }
}
