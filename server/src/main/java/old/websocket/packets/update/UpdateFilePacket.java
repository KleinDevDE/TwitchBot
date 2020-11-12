package old.websocket.packets.update;

import old.websocket.packets.Packet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class UpdateFilePacket extends Packet {
    private String fileContent;

    public UpdateFilePacket(File updateFile){
        try {
            fileContent = new String(Base64.getEncoder().encode(Files.readAllBytes(updateFile.toPath())));
        } catch (IOException e) {
            Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
        }
    }

    public String getFileContent() {
        return fileContent;
    }

    public void writeToFile(File file){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            for(byte b : Base64.getDecoder().decode(fileContent)){
                fileOutputStream.write(b);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
        }
    }
}
