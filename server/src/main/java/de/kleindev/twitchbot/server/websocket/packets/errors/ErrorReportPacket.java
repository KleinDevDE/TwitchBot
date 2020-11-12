package de.kleindev.twitchbot.server.websocket.packets.errors;

import de.kleindev.twitchbot.server.websocket.Packet;

public class ErrorReportPacket extends Packet {
    private Exception exception;
    private String operatingSystem;
    private String cpu;
    private String ram;
    private String[] lastLogs;


    public Exception getException() {
        return exception;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public String getCpu() {
        return cpu;
    }

    public String getRam() {
        return ram;
    }

    public String[] getLastLogs() {
        return lastLogs;
    }
}
