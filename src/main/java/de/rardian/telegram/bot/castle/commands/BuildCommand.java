package de.rardian.telegram.bot.castle.commands;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import de.rardian.telegram.bot.castle.commands.actions.BuildProjectAction;
import de.rardian.telegram.bot.castle.commands.actions.CastleStatusAction;
import de.rardian.telegram.bot.command.Command;
import de.rardian.telegram.bot.command.action.Action;

public class BuildCommand implements Command {

	@Override
	public Collection<Action> executeWithParams(String params) {
		//		return Arrays.asList(new SetInhabitantToWorkAction(CastleFacility.CATEGORY.BUILDING), new CastleStatusAction());
		String strippedParam = StringUtils.strip(params);

		return Arrays.asList(new BuildProjectAction().setProjectId(strippedParam), new CastleStatusAction());
	}

	@Override
	public Collection<String> getCommandStrings() {
		return Arrays.asList("/bau");
	}

	@Override
	public String getDescription() {
		return "Führe ein Bauprojekt durch oder beteilige dich daran";
	}

}
