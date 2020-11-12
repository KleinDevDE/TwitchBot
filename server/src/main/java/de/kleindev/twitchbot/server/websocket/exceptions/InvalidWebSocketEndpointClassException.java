package de.kleindev.twitchbot.server.websocket.exceptions;

public class InvalidWebSocketEndpointClassException extends Exception{
    public InvalidWebSocketEndpointClassException(String msg){
        super(msg);
    }
}
