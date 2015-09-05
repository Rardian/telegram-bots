package de.rardian.telegram.bot.castle.facilities;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.Validate;

import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Resources;

public class BuildingFacility extends BasicFacility implements Runnable {
	private int overallBuildingProgress = 0;
	private ScheduledExecutorService executorService;

	public BuildingFacility(Castle castle, Resources resources) {
		super(castle, resources);
	}

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

	public int getProgress() {
		return overallBuildingProgress;
	}
}
