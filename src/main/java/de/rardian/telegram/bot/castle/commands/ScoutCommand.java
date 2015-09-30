package de.rardian.telegram.bot.castle.commands;

import java.util.Arrays;
import java.util.Collection;

import de.rardian.telegram.bot.castle.commands.actions.SetInhabitantToWorkAction;
import de.rardian.telegram.bot.castle.facilities.CastleFacilityCategories;
import de.rardian.telegram.bot.command.Action;
import de.rardian.telegram.bot.command.Command;

public class ScoutCommand implements Command {

	@Override
	public Collection<Action> executeWithParams(String params) {
		return Arrays.asList(new SetInhabitantToWorkAction(CastleFacilityCategories.SCOUTING));
	}

	@Override
	public Collection<String> getCommandStrings() {
		return Arrays.asList("/scout");
	}

	@Override
	public String getDescription() {
		return "Erkunde das Umland";
	}

}