package de.rardian.telegram.bot.castle.commands;

import java.util.Arrays;
import java.util.Collection;

import de.rardian.telegram.bot.command.Command;
import de.rardian.telegram.bot.command.action.Action;

public class Test1Command implements Command {
	public static final Action TEST_ACTION = new Action() {

		@Override
		public void execute() {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public Collection<String> getCommandStrings() {
		return Arrays.asList("command1");
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Action> executeWithParams(String params) {
		return Arrays.asList(TEST_ACTION);
	}

}
