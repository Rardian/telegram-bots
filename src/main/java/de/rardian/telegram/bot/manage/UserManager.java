package de.rardian.telegram.bot.manage;

import java.util.Collection;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import de.rardian.telegram.bot.castle.commands.actions.UserMovesInAction;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.command.action.Action;
import de.rardian.telegram.bot.command.action.DoNothingAction;
import de.rardian.telegram.bot.model.InhabitantRepository;
import de.rardian.telegram.bot.model.User;
import de.rardian.telegram.bot.model.UserRepository;

@Service
public class UserManager {
	//	private Collection<User> knownUsers = new ArrayList<>();

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private InhabitantRepository inhabitantRepository;

	// public void setUserRepository(UserRepository userRepository) {
	// this.userRepository = userRepository;
	// }
	//
	// public void setInhabitantRepository(InhabitantRepository
	// inhabitantRepository) {
	// this.inhabitantRepository = inhabitantRepository;
	// }
	/*
		private boolean collectionContainsUser(Collection<User> collection, User user) {
			return collectionContains(collection, user, new UserByIdPredicate(user));
		}
	*/
	/*
	public static <O> boolean collectionContains(Collection<O> collection, O user, Predicate<O> predicate) {
		return CollectionUtils.exists(collection, predicate);
	}
	*/

	public Collection<Inhabitant> getInhabitants() {
		return Lists.newArrayList(inhabitantRepository.findAll());
	}

	public long countInhabitants() {
		return inhabitantRepository.count();
	}

	public boolean isUserKnown(final User user) {
		Validate.notNull(user);
		long userCount = userRepository.count();
		boolean resultRep = userRepository.exists(Long.valueOf(user.getId()));
		//	boolean resultCol = collectionContainsUser(knownUsers, user);
		System.out.println("userKnown=" + resultRep + ", userCount=" + userCount);
		return resultRep;
		//		return collectionContainsUser(knownUsers, user);
	}

	@Transactional
	public Action registerUser(User user) {
		Validate.notNull(user);
		//		knownUsers.add(user);
		//		User storedUser = userRepository.save(user);

		//		String logName = (storedUser.getUserName() == null ? storedUser.getFirstName() : storedUser.getUserName());
		//		System.out.println("User '" + logName + "' registered");

		if (isUserKnown(user)) {
			System.out.println("registerUser: Nutzer bekannt");
			return new DoNothingAction();
		} else {
			Inhabitant newInhabitant = new Inhabitant();
			user = userRepository.save(user);
			newInhabitant.setUser(user);
			inhabitantRepository.save(newInhabitant);
			System.out.println("registerUser: " + user + ", Inhabitant=" + newInhabitant.getName() + " ("
					+ newInhabitant.getId() + ")");

			return new UserMovesInAction();
		}

	}

	public Inhabitant getInhabitantByUser(User user) {
		return inhabitantRepository.findByUser(user);
	}

	public User getUserByInhabitant(Inhabitant inhabitant) {
		return inhabitantRepository.findOne(inhabitant.getId()).getUser();
		// return userRepository.findByInhabitant(inhabitant).get();
	}

}
