package de.rardian.telegram.bot.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import de.rardian.telegram.bot.manage.Message;

public class CommandParser {
	private Map<String, Command> commands;

	public CommandParser withCommands(Map<String, Command> commands) {
		this.commands = commands;
		return this;
	}

	public Collection<Action> parse(Message message) {
		Validate.notNull(commands, "command set must not be null");

		String text = message.getText();
		Collection<Action> actions = new ArrayList<Action>();

		if (text.startsWith("/")) {

			String command = text.substring(1, text.indexOf(" "));
			String params = text.substring(text.indexOf(" "));
			System.out.println("Kommando='" + command + "', Parameter='" + params + "'");

			Command commandToExecute = commands.get(command);
			Collection<Action> resultActions = commandToExecute.executeWithParams(params);
			actions.addAll(resultActions);
		}
		return actions;
	}
}
