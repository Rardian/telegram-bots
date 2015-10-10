package de.rardian.telegram.bot.castle.commands;

import java.util.Arrays;
import java.util.Collection;

import de.rardian.telegram.bot.castle.commands.actions.CastleStatusAction;
import de.rardian.telegram.bot.castle.commands.actions.SetInhabitantToWorkAction;
import de.rardian.telegram.bot.castle.facilities.CastleFacility;
import de.rardian.telegram.bot.command.Command;
import de.rardian.telegram.bot.command.action.Action;

public class InhabitantProduceCommand implements Command {

	@Override
	public Collection<String> getCommandStrings() {
		return Arrays.asList("/prod");
	}

	@Override
	public String getDescription() {
		return "Produziere Güter für die Burg";
	}

	@Override
	public Collection<Action> executeWithParams(String params) {
		return Arrays.asList(new SetInhabitantToWorkAction(CastleFacility.CATEGORY.PRODUCING), new CastleStatusAction());
	}

}
