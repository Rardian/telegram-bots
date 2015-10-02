package de.rardian.telegram.bot.command.action;

import de.rardian.telegram.bot.communication.MessageSender;

public interface SendsMessage {
	public void setMessageSender(MessageSender sender);
}
