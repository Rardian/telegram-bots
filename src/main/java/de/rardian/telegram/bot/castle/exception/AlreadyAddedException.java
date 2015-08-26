package de.rardian.telegram.bot.castle.exception;

public class AlreadyAddedException extends Exception {

	private static final long serialVersionUID = -7851373103510190124L;

	public AlreadyAddedException() {
		super();
	}

	public AlreadyAddedException(String message) {
		super(message);
	}
}
