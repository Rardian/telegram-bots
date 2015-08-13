package de.rardian.telegram.bot.model;

import de.rardian.telegram.bot.manage.Message;

public interface Bot {

	public String getId();

	public void processMessage(Message message);
}
