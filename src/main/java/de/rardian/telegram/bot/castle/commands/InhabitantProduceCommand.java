package de.rardian.telegram.bot.castle.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import de.rardian.telegram.bot.castle.commands.actions.MoveInhabitantToProductionAction;
import de.rardian.telegram.bot.command.Action;
import de.rardian.telegram.bot.command.Command;

public class InhabitantProduceCommand implements Command {

	@Override
	public Collection<String> getCommandStrings() {
		return Arrays.asList("prod", "produzieren", "produce");
	}

	@Override
	public String getDescription() {
		return "Produziere Güter für die Burg";
	}

	@Override
	public Collection<Action> executeWithParams(String params) {
		Collection<Action> actions = new ArrayList<>();

		actions.add(new MoveInhabitantToProductionAction());

		return actions;
	}

}
