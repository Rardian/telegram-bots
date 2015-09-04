package de.rardian.telegram.bot.castle.facilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Iterables;

import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Resources;
import de.rardian.telegram.bot.manage.UserByIdPredicate;
import de.rardian.telegram.bot.manage.UserManager;
import de.rardian.telegram.bot.model.User;

public class ProductionFacility implements Runnable, CastleFacility {
	private Collection<User> producers = Collections.synchronizedList(new ArrayList<>());

	private Castle castle;
	private Resources resources;
	private ScheduledExecutorService executorService;

	/** Start the production queue, if needed. */
	public void start() {
		Validate.notNull(castle, "a castle needs to be set");

		if (executorService == null) {
			executorService = Executors.newSingleThreadScheduledExecutor();
			executorService.scheduleAtFixedRate(this, 10, 5, TimeUnit.SECONDS);
		}
	}

	@Override
	public void run() {
		ProcessResult result = process();
		System.out.println(result);

		// TODO Listener über result informieren
	}

	@Override
	public void addMember(User newMember) throws AlreadyAddedException {
		if (UserManager.collectionContainsUser(producers, newMember)) {
			throw new AlreadyAddedException("user is already producing");
		}
		castle.setInhabitantIdle(newMember);
		producers.add(newMember);
		// TODO use Inhabitant instead of User
		// TODO set status in Inhabitant object

		start();
	}

	@Override
	public void removeMember(User user) {
		Iterables.removeIf(producers, new UserByIdPredicate(user));
	}

	@Override
	public int getMemberCount() {
		return producers.size();
	}

	@Override
	public ProcessResult process() {
		// act + inc <= max => inc
		// act + inc > max => max - act
		int potentialResourceIncrease = getMemberCount();
		int actualResourceIncrease = 0;

		if (resources.getActual() + potentialResourceIncrease <= resources.getCapacity()) {
			actualResourceIncrease = potentialResourceIncrease;
		} else {
			actualResourceIncrease = resources.getCapacity() - resources.getActual();
		}

		resources.increase(actualResourceIncrease);

		return new ProductionResult(actualResourceIncrease, resources.getActual());
	}

	public ProductionFacility forCastle(Castle castle) {
		this.castle = castle;
		return this;
	}

	public ProductionFacility withResources(Resources _resources) {
		this.resources = _resources;
		return this;
	}

	public String getMemberListByFirstname() {
		ArrayList<String> usersByFirstname = new ArrayList<>(producers.size());
		for (User user : producers) {
			usersByFirstname.add(user.getFirstName());
		}
		return StringUtils.join(usersByFirstname, ", ");
	}

	@VisibleForTesting
	ScheduledExecutorService getExecutorService() {
		return executorService;
	}
}
