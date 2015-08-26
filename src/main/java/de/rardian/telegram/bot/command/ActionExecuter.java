package de.rardian.telegram.bot.command;

import de.rardian.telegram.bot.manage.Message;

public class ActionExecuter {

	private ActionInitializer actionInitializer;

	public void execute(Action action, Message message) {
		actionInitializer.injectActionDependencies(action, message);
		action.execute();
	}

	public ActionExecuter withInitializer(ActionInitializer actionInitializer) {
		this.actionInitializer = actionInitializer;
		return this;
	}

}
