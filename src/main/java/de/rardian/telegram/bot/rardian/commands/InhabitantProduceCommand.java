package de.rardian.telegram.bot.rardian.commands;

import java.util.ArrayList;
import java.util.Collection;

import de.rardian.telegram.bot.command.Action;
import de.rardian.telegram.bot.command.Command;
import de.rardian.telegram.bot.rardian.commands.actions.MoveInhabitantToProductionAction;

public class InhabitantProduceCommand implements Command {

	@Override
	public Collection<Action> executeWithParams(String params) {
		Collection<Action> actions = new ArrayList<>();

		actions.add(new MoveInhabitantToProductionAction());

		return actions;
	}

}
