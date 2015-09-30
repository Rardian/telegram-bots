package de.rardian.telegram.bot.castle.facilities;

import static de.rardian.telegram.bot.castle.facilities.CastleFacilityCategories.SCOUTING;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.Validate;

import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.castle.model.Resources;

public class EnvironmentFacility extends BasicFacility implements Runnable {

	private ScheduledExecutorService executorService;

	public EnvironmentFacility(Castle castle, Resources resources) {
		super(castle, resources);
	}

	@Override
	protected void start() {
		Validate.notNull(castle, "a castle needs to be set");

		if (executorService == null) {
			executorService = Executors.newSingleThreadScheduledExecutor();
			executorService.scheduleAtFixedRate(this, 10, 5, TimeUnit.SECONDS);
		}
	}

	@Override
	public ProcessResult process() {
		// TODO based on SCOUTING skill and already existing resource locations the chance for finding new spots de- or increases
		for (Inhabitant inhabitant : members) {
			int scoutingSkill = inhabitant.getSkill(SCOUTING);
			int resourceFieldCount = resources.getResourceFieldCount();

			if (scoutingSkill * 10 > resourceFieldCount) {
				System.out.println("fieldCount erhöht");
				resources.increaseResourceFieldCount();
			}

			inhabitant.increaseXp(SCOUTING);
		}
		return new ProcessResult() {
		};
	}

	@Override
	public CastleFacilityCategories getCategory() {
		return SCOUTING;
	}

}
