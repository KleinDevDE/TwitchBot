package de.kleindev.twitchbot.external.twitch.events;

public class ExceptionEvent extends Event {

	boolean printStackTrace = true;
	Exception ex;
	
	public ExceptionEvent(Exception ex) {
		this.ex = ex;
	}
	
	public void setPrintStackTrace(boolean printStackTrace) {
		this.printStackTrace = printStackTrace;
	}
	
	public boolean isPrintStackTrace() {
		return printStackTrace;
	}
	
	public Exception getException() {
		return ex;
	}
	
}
