package de.rardian.telegram.bot.castle.commands;

import java.util.Arrays;
import java.util.Collection;

import de.rardian.telegram.bot.castle.commands.actions.CastleStatusAction;
import de.rardian.telegram.bot.command.Command;
import de.rardian.telegram.bot.command.action.Action;

public class CastleStatusCommand implements Command {

	@Override
	public Collection<String> getCommandStrings() {
		return Arrays.asList("/burg");
	}

	@Override
	public String getDescription() {
		return "Zeigt den Status der Burg";
	}

	@Override
	public Collection<Action> executeWithParams(String params) {
		return Arrays.asList(new CastleStatusAction());
	}

}
