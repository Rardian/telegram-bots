package de.rardian.telegram.bot.castle.facilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.google.common.collect.Iterables;

import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Resources;
import de.rardian.telegram.bot.manage.UserByIdPredicate;
import de.rardian.telegram.bot.manage.UserManager;
import de.rardian.telegram.bot.model.User;

public class BuildingFacility implements Runnable, CastleFacility {
	private Collection<User> builders = Collections.synchronizedList(new ArrayList<>());
	private Castle castle;
	private Resources resources;

	private int overallBuildingProgress = 0;
	private ScheduledExecutorService executorService;

	/** Start the building queue, if needed. */
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
		if (UserManager.collectionContainsUser(builders, newMember)) {
			throw new AlreadyAddedException("user is already building");
		}
		castle.setInhabitantIdle(newMember);
		builders.add(newMember);
		// TODO use Inhabitant instead of User
		// TODO set status in Inhabitant object

		start();
	}

	@Override
	public void removeMember(User user) {
		Iterables.removeIf(builders, new UserByIdPredicate(user));
	}

	@Override
	public int getMemberCount() {
		return builders.size();
	}

	@Override
	public ProcessResult process() {
		int potentialBuildingProgress = getMemberCount();
		int actualBuildingProgress = 0;

		// cost for extending capacity: new capacity * 2
		// every builder uses 1 resource and increases buildprogress
		actualBuildingProgress = Math.min(potentialBuildingProgress, resources.getActual());
		resources.reduce(actualBuildingProgress);
		overallBuildingProgress += actualBuildingProgress;

		if (overallBuildingProgress >= (resources.getCapacity() + 1) * 2) {
			resources.increaseCapacity();
			overallBuildingProgress = 0;
		}

		return new BuildingResult(actualBuildingProgress);
	}

	public BuildingFacility forCastle(Castle castle) {
		this.castle = castle;
		return this;
	}

	public BuildingFacility withResources(Resources resources) {
		this.resources = resources;
		return this;
	}

	public String getMemberListByFirstname() {
		ArrayList<String> usersByFirstname = new ArrayList<>(builders.size());
		for (User user : builders) {
			usersByFirstname.add(user.getFirstName());
		}
		return StringUtils.join(usersByFirstname, ", ");
	}

	public int getProgress() {
		return overallBuildingProgress;
	}
}
