package de.rardian.telegram.bot.castle.commands;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.command.Command;

public class CommandInitializer {

	private HashMap<String, Command> commands = new HashMap<>();

	public CommandInitializer(Castle castle) {
		initCommands(castle);
	}

	public Map<String, Command> getCommandSet() {
		return commands;
	}

	private void initCommands(Castle castle) {
		Collection<Command> commandsToInitialize = Arrays.asList(//
				new BuildCommand(), //
				new CastleStatusCommand(), //
				new CharacterStatusCommand(), //
				new InhabitantProduceCommand(), //
				new HelpCommand(), //
				new ScoutCommand(), //
				new ShoutCommand(castle), //
				new SetNameCommand());

		for (Command command : commandsToInitialize) {
			for (String commandString : command.getCommandStrings()) {
				commands.put(commandString, command);
			}
		}
	}
}
