package de.rardian.telegram.bot.manage;

import java.util.ArrayList;
import java.util.Collection;

import de.rardian.telegram.bot.model.User;

public class UserManager {
	private Collection<User> knownUsers = new ArrayList<User>();

	public boolean isUserKnown(User user) {
		return knownUsers.contains(user);
	}

	public void registerUser(User user) {
		knownUsers.add(user);
	}

}
