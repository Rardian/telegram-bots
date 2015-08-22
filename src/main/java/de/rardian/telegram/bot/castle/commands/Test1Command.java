package de.rardian.telegram.bot.castle.commands;

import java.util.Arrays;
import java.util.Collection;

import de.rardian.telegram.bot.command.Action;
import de.rardian.telegram.bot.command.Command;

public class Test1Command implements Command {
	public static final Action TEST_ACTION = new Action() {

		@Override
		public void execute() {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public Collection<Action> executeWithParams(String params) {
		return Arrays.asList(TEST_ACTION);
	}

}
