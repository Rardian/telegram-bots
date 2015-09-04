package de.rardian.telegram.bot.manage;

import de.rardian.telegram.bot.model.User;

/**
 * Predicate that checks User objects by their id. Works with Guava and Commons
 * Collections.
 */
public class UserByIdPredicate implements org.apache.commons.collections4.Predicate<User>, com.google.common.base.Predicate<User> {

	private User user;

	public UserByIdPredicate(User toCheckAgainst) {
		this.user = toCheckAgainst;
	}

	@Override
	public boolean apply(User input) {
		return checkInput(input);
	}

	@Override
	public boolean evaluate(User object) {
		return checkInput(object);
	}

	private boolean checkInput(User input) {
		return user.getId() == input.getId();
	}
}
