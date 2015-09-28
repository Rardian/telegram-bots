package de.rardian.telegram.bot.castle.commands;

import java.util.Arrays;
import java.util.Collection;

import de.rardian.telegram.bot.castle.commands.actions.CharacterStatusAction;
import de.rardian.telegram.bot.command.Action;
import de.rardian.telegram.bot.command.Command;

public class CharacterStatusCommand implements Command {

	@Override
	public Collection<String> getCommandStrings() {
		return Arrays.asList("/char");
	}

	@Override
	public String getDescription() {
		return "Zeigt den Status deines Burgbewohners";
	}

	@Override
	public Collection<Action> executeWithParams(String params) {
		return Arrays.asList(new CharacterStatusAction());
	}

}
