package de.rardian.telegram.bot.castle.commands;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.rardian.telegram.bot.command.Command;

public class CommandInitializer {

	private HashMap<String, Command> commands = new HashMap<>();

	public CommandInitializer() {
		initCommands();
	}

	public Map<String, Command> getCommandSet() {
		return commands;
	}

	private void initCommands() {
		Collection<Command> commandsToInitialize = Arrays.asList(//
				new CastleStatusCommand(), //
				new InhabitantProduceCommand(), //
				new HelpCommand());

		for (Command command : commandsToInitialize) {
			for (String commandString : command.getCommandStrings()) {
				commands.put(commandString, command);
			}
		}
	}
}
