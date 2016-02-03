package de.rardian.telegram.bot.castle.facilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import com.google.common.collect.Iterables;

import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.castle.model.ResourcesManager;
import de.rardian.telegram.bot.command.action.SendMessageToUserAction;
import de.rardian.telegram.bot.model.Bot;
import de.rardian.telegram.bot.model.User;

/** Provides a basic implementation for handling members. */
public abstract class BasicFacility implements CastleFacility, Runnable {
	protected Castle castle;
	protected ResourcesManager resources;
	protected Collection<Inhabitant> members = Collections.synchronizedList(new ArrayList<>());
	//	protected CATEGORY category;
	private Bot bot;

	public BasicFacility(Bot bot, Castle castle, ResourcesManager resources) {
		this.castle = castle;
		this.resources = resources;
		this.bot = bot;
		//		category = getCategory();
	}

	@Override
	public void run() {
		ProcessResult2 result = process();
		// System.out.println(result);

		bot.executeResultActions(result.getResultActions());

		// TODO Listener über result informieren
	}

	@Override
	public void addMember(Inhabitant newMember) throws AlreadyAddedException {
		if (CollectionUtils.exists(members, new Predicate<Inhabitant>() {

			@Override
			public boolean evaluate(Inhabitant object) {
				return object.compareTo(newMember) == 0;
			}
		})) {
			throw new AlreadyAddedException("user is already producing");
		}
		castle.setInhabitantIdle(newMember);
		members.add(newMember);
		// TODO consider setting status in Inhabitant object

		start();
	}

	protected void increaseInhabitantXp(Inhabitant inhabitant, CATEGORY skill, ProcessResult2 resultContainer) {
		boolean levelup = inhabitant.increaseXp(skill);
		System.out.println(skill + " von " + inhabitant.getName() + " erhöht");

		if (levelup) {
			User user = castle.getUserBy(inhabitant);
			resultContainer.addResultAction(new SendMessageToUserAction(user, "Du hast dich in " + skill + " verbessert."));
		}
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
	public String getMemberListByName() {
		return castle.getInhabitantsByName(new ArrayList<>(members));
	}

	/**
	 * Hook for calculating the potentialIncrease. Defaults to member count: Every member increases the output by one.
	 */
	protected int getPotentialIncrease() {
		return getMemberCount();
	}

	/** start the facility's work. */
	protected abstract void start();

}
