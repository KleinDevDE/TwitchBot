package de.kleindev.twitchbot.fxmlcontrollers.debug;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import de.kleindev.twitchbot.helpers.ExceptionHelper;
import de.kleindev.twitchbot.websocket.WebSocketManager;
import de.kleindev.twitchbot.websocket.packets.auth.AuthPacket;
import de.kleindev.twitchbot.websocket.packets.auth.AuthType;
import de.kleindev.twitchbot.websocket.packets.auth.TwoFADataPacket;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.NonNull;

public class Debug_2FA {
    private static TwoFADataPacket twoFADataPacket;
    private static Stage stage;
    public ImageView qr_code;
    public Label strToken;
    public TextField strCode;
    public Label strURL;

    {
        System.out.println("static");
        stage.setOnShowing(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("onShowing");
                qr_code.setImage(generateQR("otpauth://totp/"+twoFADataPacket.getName()+"?secret="+twoFADataPacket.getSecret(), 250, 250));
                strToken.setText(twoFADataPacket.getSecret());
            }
        });
    }

    /**
     * This will show this scene
     * @param replaceActuallyStage
     */
    @NonNull
    public static void show(boolean replaceActuallyStage, TwoFADataPacket twoFADataPacket){
        if (twoFADataPacket == null){
            System.err.println("twoFADataPacket is null");
            new Alert(Alert.AlertType.ERROR, "Could not open Debug_2FA cause TwoFADataPacket is NULL!").show();
            return;
        }
        try {
            stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(Debug_2FA.class.getClassLoader().getResource("fxml/debug/Debug_2FA.fxml"))));
            Debug_2FA.twoFADataPacket = twoFADataPacket;
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionHelper.handle(e);
        }
        stage.show();
    }

    public void sendToken(MouseEvent mouseEvent) {
        new AuthPacket(AuthType.TWOFA_TOKEN, new String[]{"1532", "KleinDev", strToken.getText()}).send("auth");
    }

    public Image generateQR(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height);

            WritableImage writableImage = new WritableImage(width, height);
            PixelWriter pixelWriter = writableImage.getPixelWriter();

            System.out.println("Writing pixels...");
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    pixelWriter.setColor(x, y, bitMatrix.get(x, y) ?
                            Color.BLACK : Color.TRANSPARENT);
                }
            }
            System.out.println("Done!");
            return writableImage;

        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendRequest(MouseEvent mouseEvent) {
        WebSocketManager.getClient("auth").send(new AuthPacket(AuthType.GENERATE_TWOFA, new String[]{"1532", "KleinDev"}).getSendableString());
    }
}
