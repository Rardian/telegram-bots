package de.rardian.telegram.bot.castle.commands;

import java.util.Arrays;
import java.util.Collection;

import de.rardian.telegram.bot.castle.commands.actions.MoveInhabitantToBuildingAction;
import de.rardian.telegram.bot.command.Action;
import de.rardian.telegram.bot.command.Command;

public class BuildCommand implements Command {

	@Override
	public Collection<Action> executeWithParams(String params) {
		return Arrays.asList(new MoveInhabitantToBuildingAction());
	}

	@Override
	public Collection<String> getCommandStrings() {
		return Arrays.asList("/bau");
	}

	@Override
	public String getDescription() {
		return "Erweitert das Lager der Burg";
	}

}
