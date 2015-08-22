package de.rardian.telegram.bot.rardian.commands;

import java.util.Arrays;
import java.util.Collection;

import de.rardian.telegram.bot.command.Action;
import de.rardian.telegram.bot.command.Command;
import de.rardian.telegram.bot.rardian.commands.actions.CastleStatusAction;

public class CastleStatusCommand implements Command {

	@Override
	public Collection<Action> executeWithParams(String params) {
		return Arrays.asList(new CastleStatusAction());
	}

}
