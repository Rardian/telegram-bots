package de.rardian.telegram.bot.command.action;

import de.rardian.telegram.bot.model.User;

public interface UserAware {
	public void setUser(User user);
}
