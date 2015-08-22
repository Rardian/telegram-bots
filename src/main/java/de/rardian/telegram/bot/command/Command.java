package de.rardian.telegram.bot.command;

import java.util.Collection;

public interface Command {

	public Collection<Action> executeWithParams(String params);

	public Collection<String> getCommandStrings();

	public String getDescription();

}
