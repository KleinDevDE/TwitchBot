package old.packets.update;

import old.packets.Packet;

import java.io.File;

public class UpdateFilePacket extends Packet {
    private String fileContent;

    public UpdateFilePacket(File updateFile){
        //TODO write content of tile to string
    }

    public String getFileContent() {
        return fileContent;
    }

    public void writeToFile(File file){

    }
}
