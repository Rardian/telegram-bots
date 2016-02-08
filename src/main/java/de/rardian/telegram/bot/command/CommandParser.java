package de.rardian.telegram.bot.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import de.rardian.telegram.bot.command.action.Action;
import de.rardian.telegram.bot.command.action.CommandUnknownAction;
import de.rardian.telegram.bot.model.Message;

public class CommandParser {
	private Map<String, Command> commands;

	public CommandParser withCommands(Map<String, Command> commands) {
		this.commands = commands;
		return this;
	}

	public Collection<Action> parse(Message message) {
		Validate.notNull(commands, "command set must not be null");

		String text = message.getText();
		Collection<Action> actions = new ArrayList<>();

		if (text.startsWith("/")) {
			final boolean hasParams = text.contains(" ");

			int indexForCommandEnd = (hasParams ? text.indexOf(" ") : text.length());
			String command = text.substring(0, indexForCommandEnd);

			int indexForParamStart = (hasParams ? text.indexOf(" ") + 1 : text.length());
			String params = text.substring(indexForParamStart);
			//			System.out.println("Kommando='" + command + "', Parameter='" + params + "'");

			command = command.toLowerCase();

			Command commandToExecute = commands.get(command);
			if (commandToExecute == null) {
				actions.add(new CommandUnknownAction());
			} else {
				if (commandToExecute instanceof MessageAware) {
					((MessageAware) commandToExecute).setMessage(message);
				}
				Collection<Action> resultActions = commandToExecute.executeWithParams(params);
				actions.addAll(resultActions);
			}
		} else {
			actions.add(new CommandUnknownAction());
		}
		return actions;
	}
}
