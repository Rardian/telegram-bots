package de.rardian.telegram.bot.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.rardian.telegram.bot.manage.Message;
import de.rardian.telegram.bot.rardian.commands.Test1Command;
import de.rardian.telegram.bot.rardian.commands.Test2Command;

public class CommandParser {
	private Map<String, Command> commands;

	public CommandParser() {
		initCommands();
	}

	public Collection<Action> parse(Message message) {
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

	private void initCommands() {
		commands = new HashMap<String, Command>();

		commands.put("test1", new Test1Command());
		commands.put("test2", new Test2Command());

	}

}
