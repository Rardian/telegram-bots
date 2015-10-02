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

			int indexForParamStart = (text.contains(" ") ? text.indexOf(" ") : text.length());
			String command = text.substring(0, indexForParamStart);
			String params = text.substring(indexForParamStart);
			//			System.out.println("Kommando='" + command + "', Parameter='" + params + "'");

			Command commandToExecute = commands.get(command);
			if (commandToExecute == null) {
				actions.add(new CommandUnknownAction());
			} else {
				Collection<Action> resultActions = commandToExecute.executeWithParams(params);
				actions.addAll(resultActions);
			}
		} else {
			actions.add(new CommandUnknownAction());
		}
		return actions;
	}
}
