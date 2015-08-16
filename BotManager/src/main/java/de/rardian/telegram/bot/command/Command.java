package de.rardian.telegram.bot.command;

import java.util.Collection;

public interface Command {

	Collection<Action> executeWithParams(String params);

}
