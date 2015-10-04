package de.rardian.telegram.bot.command.action;

import de.rardian.telegram.bot.communication.MessageSender;
import de.rardian.telegram.bot.model.User;

public class SendMessageToUserAction extends ResultAction implements SendsMessage {

	private MessageSender sender;
	private String message;

	public SendMessageToUserAction(User user, String message) {
		super(user);
		this.message = message;
	}

	@Override
	public void setMessageSender(MessageSender sender) {
		this.sender = sender;
	}

	@Override
	public void execute() {
		sender.sendMessage(user, message);
	}

}
