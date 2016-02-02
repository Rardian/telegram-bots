package de.rardian.telegram.bot.manage;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.Validate;

import de.rardian.telegram.bot.model.User;
import de.rardian.telegram.bot.model.UserRepository;

public class UserManager {
	private Collection<User> knownUsers = new ArrayList<>();
	private UserRepository userRepository;

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	private boolean collectionContainsUser(Collection<User> collection, User user) {
		return collectionContains(collection, user, new UserByIdPredicate(user));
	}

	public static <O> boolean collectionContains(Collection<O> collection, O user, Predicate<O> predicate) {
		return CollectionUtils.exists(collection, predicate);
	}

	public boolean isUserKnown(final User user) {
		Validate.notNull(user);
		boolean resultRep = userRepository.exists(Long.valueOf(user.getId()));
		boolean resultCol = collectionContainsUser(knownUsers, user);
		System.out.println("Repo=" + resultRep + ", Coll=" + resultCol);
		return resultRep;
		//		return collectionContainsUser(knownUsers, user);
	}

	public void registerUser(User user) {
		Validate.notNull(user);
		knownUsers.add(user);
		User storedUser = userRepository.save(user);

		String logName = (storedUser.getUserName() == null ? storedUser.getFirstName() : storedUser.getUserName());
		System.out.println("User '" + logName + "' registered");
	}
}
