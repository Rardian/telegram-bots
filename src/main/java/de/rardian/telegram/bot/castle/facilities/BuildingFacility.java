package de.rardian.telegram.bot.castle.facilities;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.Validate;

import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.castle.model.Resources;

public class BuildingFacility extends BasicFacility implements Runnable {
	private int overallBuildingProgress = 0;
	private ScheduledExecutorService executorService;

	public BuildingFacility(Castle castle, Resources resources) {
		super(castle, resources);
	}

	@Override
	protected CastleFacilityCategories getCategory() {
		return CastleFacilityCategories.BUILDING;
	}

	/** Start the building queue, if needed. */
	protected void start() {
		Validate.notNull(castle, "a castle needs to be set");

		if (executorService == null) {
			executorService = Executors.newSingleThreadScheduledExecutor();
			executorService.scheduleAtFixedRate(this, 10, 5, TimeUnit.SECONDS);
		}
	}

	@Override
	public void run() {
		ProcessResult result = process();
		//		System.out.println(result);

		// TODO Listener über result informieren
	}

	//	@Override
	//	public ProcessResult process() {
	//
	//		int actualBuildingProgress = resources.increaseCapacity(members);
	//
	//		return new BuildingResult(actualBuildingProgress);
	//	}
	@Override
	public ProcessResult process() {
		int actualBuildingProgress = 0;

		for (Inhabitant inhabitant : members) {
			int potentialBuildingProgress = inhabitant.getBuildingSkill();

			// cost for extending capacity: new capacity * 2
			// every builder uses 1 resource and increases buildprogress
			int memberBuildingProgress = Math.min(potentialBuildingProgress, resources.getActual());

			if (memberBuildingProgress > 0) {
				resources.reduce(memberBuildingProgress);
				actualBuildingProgress += memberBuildingProgress;
				overallBuildingProgress += memberBuildingProgress;
				inhabitant.increaseXp(category);
			}

			if (overallBuildingProgress >= (resources.getCapacity() + 1) * 2) {
				resources.increaseCapacity();
				overallBuildingProgress = 0;
				break;
			}
		}

		return new BuildingResult(actualBuildingProgress);
	}

	public int getProgress() {
		return overallBuildingProgress;
	}
}
