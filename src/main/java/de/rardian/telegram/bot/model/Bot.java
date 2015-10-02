package de.rardian.telegram.bot.model;

public interface Bot {

	public String getId();

	public void processMessage(Message message);

	public String getCommandOverview();
}
