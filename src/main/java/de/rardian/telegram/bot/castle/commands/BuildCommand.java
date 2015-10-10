package de.rardian.telegram.bot.castle.commands;

import java.util.Arrays;
import java.util.Collection;

import de.rardian.telegram.bot.castle.commands.actions.CastleStatusAction;
import de.rardian.telegram.bot.castle.commands.actions.SetInhabitantToWorkAction;
import de.rardian.telegram.bot.castle.facilities.CastleFacility;
import de.rardian.telegram.bot.command.Command;
import de.rardian.telegram.bot.command.action.Action;

public class BuildCommand implements Command {

	@Override
	public Collection<Action> executeWithParams(String params) {
		return Arrays.asList(new SetInhabitantToWorkAction(CastleFacility.CATEGORY.BUILDING), new CastleStatusAction());
	}

	@Override
	public Collection<String> getCommandStrings() {
		return Arrays.asList("/bau");
	}

	@Override
	public String getDescription() {
		return "Erweitere das Ressourcenlager der Burg";
	}

}
