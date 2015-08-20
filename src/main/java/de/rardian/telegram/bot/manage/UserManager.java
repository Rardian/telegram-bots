package de.rardian.telegram.bot.manage;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.Validate;

import de.rardian.telegram.bot.model.User;

public class UserManager {
	private Collection<User> knownUsers = new ArrayList<>();

	public boolean isUserKnown(final User user) {
		return CollectionUtils.exists(knownUsers, new Predicate<User>() {
			@Override
			public boolean evaluate(User object) {
				return user.getId() == object.getId();
			}
		});
	}

	public void registerUser(User user) {
		Validate.notNull(user);
		knownUsers.add(user);
		System.out.println("User '" + user.getUserName() + "' registered");
	}

}
