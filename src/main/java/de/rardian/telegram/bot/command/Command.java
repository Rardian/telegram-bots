package de.rardian.telegram.bot.command;

import java.util.Collection;

import de.rardian.telegram.bot.command.action.Action;

public interface Command {

	public Collection<Action> executeWithParams(String params);

	public Collection<String> getCommandStrings();

	public String getDescription();

}
