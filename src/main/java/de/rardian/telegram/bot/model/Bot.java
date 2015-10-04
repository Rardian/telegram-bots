package de.rardian.telegram.bot.model;

import java.util.Collection;

import de.rardian.telegram.bot.command.action.ResultAction;

public interface Bot {

	public String getId();

	public void processMessage(Message message);

	public String getCommandOverview();

	public void executeResultActions(Collection<ResultAction> actions);
}
