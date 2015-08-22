package de.rardian.telegram.bot.castle.commands;

import java.util.Arrays;
import java.util.Collection;

import de.rardian.telegram.bot.castle.commands.actions.PrintHelpAction;
import de.rardian.telegram.bot.command.Action;
import de.rardian.telegram.bot.command.Command;

public class HelpCommand implements Command {

	@Override
	public Collection<Action> executeWithParams(String params) {
		return Arrays.asList(new PrintHelpAction());
	}

	@Override
	public Collection<String> getCommandStrings() {
		return Arrays.asList("help", "hilfe");
	}

	@Override
	public String getDescription() {
		return "Diese Übersicht";
	}

}
