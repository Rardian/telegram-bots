package de.rardian.telegram.bot.castle.facilities;

import static de.rardian.telegram.bot.castle.facilities.CastleFacilityCategories.SCOUTING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;

import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.castle.model.Resources;
import de.rardian.telegram.bot.command.action.BroadcastMessageAction;
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
			executorService.scheduleAtFixedRate(this, 15, 45, TimeUnit.SECONDS);
		}
	}

	@Override
	public ProcessResult2 process() {
		// TODO based on SCOUTING skill and already existing resource locations the chance for finding new spots de- or
		// increases
		ProcessResult2 resultContainer = new ProcessResult2();

		synchronized (members) {

			for (Inhabitant inhabitant : members) {
				int scoutingSkill = inhabitant.getSkill(SCOUTING);
				int resourceFieldCount = resources.getResourceFieldCount();

				if (scoutingSkill * 10 > resourceFieldCount) {
					System.out.println(inhabitant.getName() + " erhöht fieldCount");
					int newResourceFieldCount = resources.increaseResourceFieldCount();

					if (newResourceFieldCount > resourceFieldCount) {
						User user = castle.getUserBy(inhabitant);
						Collection<Inhabitant> otherMembers = CollectionUtils.disjunction(members, Arrays.asList(inhabitant));

						resultContainer.addResultAction(//
								new BroadcastMessageAction(//
										user, "Du hast ein Ressourcenfeld entdeckt.", //
										new ArrayList<Inhabitant>(otherMembers), inhabitant.getName() + " hat ein Ressourcenfeld entdeckt."));
					}
				}

				// if (resourceFieldCount % 2 == 0) {

				super.increaseInhabitantXp(inhabitant, SCOUTING, resultContainer);
				// }
			}
		}

		return resultContainer;
	}

	@Override
	public CastleFacilityCategories getCategory() {
		return SCOUTING;
	}

}
