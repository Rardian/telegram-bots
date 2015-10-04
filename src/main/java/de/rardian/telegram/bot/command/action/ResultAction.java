package de.rardian.telegram.bot.command.action;

import de.rardian.telegram.bot.model.User;

/** User-based Action used in CastleFacilities. */
public abstract class ResultAction implements Action {
	protected User user;

	public ResultAction(User user) {
		this.user = user;
	}

	@Override
	public abstract void execute();
}
