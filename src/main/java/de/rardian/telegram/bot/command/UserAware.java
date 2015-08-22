package de.rardian.telegram.bot.command;

import de.rardian.telegram.bot.model.User;

public interface UserAware {
	public void setUser(User user);
}
