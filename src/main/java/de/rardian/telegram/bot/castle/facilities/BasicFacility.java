package de.rardian.telegram.bot.castle.facilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Iterables;

import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.castle.model.Resources;
import de.rardian.telegram.bot.manage.UserManager;

public abstract class BasicFacility implements CastleFacility {
	protected Castle castle;
	protected Resources resources;
	protected Collection<Inhabitant> members = Collections.synchronizedList(new ArrayList<>());
	protected CastleFacilityCategories category;

	public BasicFacility(Castle castle, Resources resources) {
		this.castle = castle;
		this.resources = resources;
		category = getCategory();
	}

	@Override
	public void addMember(Inhabitant newMember) throws AlreadyAddedException {
		if (UserManager.collectionContains(members, newMember, new Predicate<Inhabitant>() {

			@Override
			public boolean evaluate(Inhabitant object) {
				return object.compareTo(newMember) == 0;
			}
		})) {
			throw new AlreadyAddedException("user is already producing");
		}
		castle.setInhabitantIdle(newMember);
		members.add(newMember);
		// TODO set status in Inhabitant object

		start();
	}

	@Override
	public void removeMember(Inhabitant inhabitant) {
		Iterables.removeIf(members, new com.google.common.base.Predicate<Inhabitant>() {

			@Override
			public boolean apply(Inhabitant input) {
				return input.compareTo(inhabitant) == 0;
			}
		});
	}

	@Override
	public int getMemberCount() {
		return members.size();
	}

	@Override
	public String getMemberListByFirstname() {
		ArrayList<String> usersByFirstname = new ArrayList<>(members.size());
		for (Inhabitant inhabitant : members) {
			usersByFirstname.add(inhabitant.getName());
		}
		return StringUtils.join(usersByFirstname, ", ");
	}

	@Override
	public abstract ProcessResult process();

	/**
	 * Hook for calculating the potentialIncrease. Defaults to member count:
	 * Every member increases the output by one.
	 */
	protected int getPotentialIncrease() {
		return getMemberCount();
	}

	/** start the facility's work. */
	protected abstract void start();

	/** You need to define a category for your facility. */
	protected abstract CastleFacilityCategories getCategory();
}
