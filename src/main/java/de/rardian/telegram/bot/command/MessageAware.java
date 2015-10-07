package de.rardian.telegram.bot.command;

import de.rardian.telegram.bot.model.Message;

public interface MessageAware {
	public void setMessage(Message message);
}
