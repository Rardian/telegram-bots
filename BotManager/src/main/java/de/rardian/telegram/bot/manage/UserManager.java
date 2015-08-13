package de.rardian.telegram.bot.manage;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.Validate;

import de.rardian.telegram.bot.model.User;

public class UserManager {
	private Collection<User> knownUsers = new ArrayList<User>();

	public boolean isUserKnown(User user) {
		return knownUsers.contains(user);
	}

	public void registerUser(User user) {
		Validate.notNull(user);
		knownUsers.add(user);
	}

}
