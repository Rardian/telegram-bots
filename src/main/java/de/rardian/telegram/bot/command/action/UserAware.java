package de.rardian.telegram.bot.command.action;

import de.rardian.telegram.bot.model.User;

/** For Actions that need a User object. */
public interface UserAware {
	public void setUser(User user);
}
