package de.rardian.telegram.bot.command.action;

import de.rardian.telegram.bot.model.Message;

public class ActionExecuter {

	private ActionInitializer actionInitializer;

	public void execute(ResultAction action) {
		try {
			actionInitializer.injectActionDependencies(action, null);
			action.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void execute(Action action, Message message) {
		try {
			actionInitializer.injectActionDependencies(action, message);
			action.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ActionExecuter withInitializer(ActionInitializer actionInitializer) {
		this.actionInitializer = actionInitializer;
		return this;
	}

}
