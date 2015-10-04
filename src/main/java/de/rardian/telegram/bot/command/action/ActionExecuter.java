package de.rardian.telegram.bot.command.action;

import de.rardian.telegram.bot.model.Message;

public class ActionExecuter {

	private ActionInitializer actionInitializer;

	public void execute(ResultAction action) {
		actionInitializer.injectActionDependencies(action, null);
		action.execute();
	}

	public void execute(Action action, Message message) {
		actionInitializer.injectActionDependencies(action, message);
		action.execute();
	}

	public ActionExecuter withInitializer(ActionInitializer actionInitializer) {
		this.actionInitializer = actionInitializer;
		return this;
	}

}
