package de.rardian.telegram.bot.manage;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;

import de.rardian.telegram.bot.model.User;

public class UserManager {
	private Collection<User> knownUsers = new ArrayList<>();

	public static boolean collectionContainsUser(Collection<User> collection, User user) {
		return CollectionUtils.exists(collection, new UserByIdPredicate(user));
	}

	public boolean isUserKnown(final User user) {
		return collectionContainsUser(knownUsers, user);
	}

	public void registerUser(User user) {
		Validate.notNull(user);
		knownUsers.add(user);
		System.out.println("User '" + user.getUserName() + "' registered");
	}
}
