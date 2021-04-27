package de.kleindev.twitchbot.websocket.exceptions;

public class InvalidWebSocketEndpointClassException extends Exception{
    public InvalidWebSocketEndpointClassException(String msg){
        super(msg);
    }
}
