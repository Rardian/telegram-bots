package de.rardian.telegram.bot.castle.facilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Iterables;

import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Resources;
import de.rardian.telegram.bot.manage.UserByIdPredicate;
import de.rardian.telegram.bot.manage.UserManager;
import de.rardian.telegram.bot.model.User;

public abstract class BasicFacility implements CastleFacility {
	protected Castle castle;
	protected Resources resources;

	private Collection<User> members = Collections.synchronizedList(new ArrayList<>());

	public BasicFacility(Castle castle, Resources resources) {
		this.castle = castle;
		this.resources = resources;
	}

	@Override
	public void addMember(User newMember) throws AlreadyAddedException {
		if (UserManager.collectionContainsUser(members, newMember)) {
			throw new AlreadyAddedException("user is already producing");
		}
		castle.setInhabitantIdle(newMember);
		members.add(newMember);
		// TODO use Inhabitant instead of User
		// TODO set status in Inhabitant object

		start();
	}

	@Override
	public void removeMember(User user) {
		Iterables.removeIf(members, new UserByIdPredicate(user));
	}

	@Override
	public int getMemberCount() {
		return members.size();
	}

	@Override
	public String getMemberListByFirstname() {
		ArrayList<String> usersByFirstname = new ArrayList<>(members.size());
		for (User user : members) {
			usersByFirstname.add(user.getFirstName());
		}
		return StringUtils.join(usersByFirstname, ", ");
	}

	@Override
	public abstract ProcessResult process();

	/** start the facility's work. */
	protected abstract void start();
}
