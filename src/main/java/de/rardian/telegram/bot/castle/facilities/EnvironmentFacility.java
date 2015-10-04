package de.rardian.telegram.bot.castle.facilities;

import static de.rardian.telegram.bot.castle.facilities.CastleFacilityCategories.SCOUTING;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.Validate;

import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.castle.model.Resources;
import de.rardian.telegram.bot.command.action.SendMessageToUserAction;
import de.rardian.telegram.bot.model.Bot;
import de.rardian.telegram.bot.model.User;

public class EnvironmentFacility extends BasicFacility implements Runnable {

	private ScheduledExecutorService executorService;

	public EnvironmentFacility(Castle castle, Resources resources, Bot bot) {
		super(castle, resources, bot);
	}

	@Override
	protected void start() {
		Validate.notNull(castle, "a castle needs to be set");

		if (executorService == null) {
			executorService = Executors.newSingleThreadScheduledExecutor();
			executorService.scheduleAtFixedRate(this, 10, 1, TimeUnit.SECONDS);
		}
	}

	@Override
	public ProcessResult2 process() {
		// TODO based on SCOUTING skill and already existing resource locations the chance for finding new spots de- or increases
		ProcessResult2 resultContainer = new ProcessResult2();

		synchronized (members) {

			for (Inhabitant inhabitant : members) {
				int scoutingSkill = inhabitant.getSkill(SCOUTING);
				int resourceFieldCount = resources.getResourceFieldCount();

				if (scoutingSkill * 10 > resourceFieldCount) {
					System.out.println(inhabitant.getName() + " erhöht fieldCount");
					resources.increaseResourceFieldCount();
				}

				//				if (resourceFieldCount % 2 == 0) {
				System.out.println("scoutingSkill von " + inhabitant.getName() + " erhöht");
				boolean levelup = inhabitant.increaseXp(SCOUTING);
				if (levelup) {
					User user = castle.getUserBy(inhabitant);
					resultContainer.addResultAction(new SendMessageToUserAction(user, "Du hast dich im Kundschaften verbessert."));
				}
				//				}
			}
		}

		return resultContainer;
	}

	@Override
	public CastleFacilityCategories getCategory() {
		return SCOUTING;
	}

}
