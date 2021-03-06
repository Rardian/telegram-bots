package de.rardian.telegram.bot.castle.facilities;

import static de.rardian.telegram.bot.castle.facilities.CastleFacility.CATEGORY.SCOUTING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;

import de.rardian.telegram.bot.castle.commands.actions.SetInhabitantsIdle;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.castle.model.ResourcesManager;
import de.rardian.telegram.bot.command.action.BroadcastMessageAction;
import de.rardian.telegram.bot.model.Bot;
import de.rardian.telegram.bot.model.User;

public class EnvironmentFacility extends BasicFacility implements Runnable {

	private ScheduledExecutorService executorService;

	public EnvironmentFacility(Bot bot, Castle castle, ResourcesManager resources) {
		super(bot, castle, resources);
	}

	@Override
	protected void start() {
		Validate.notNull(castle, "a castle needs to be set");

		if (executorService == null) {
			executorService = Executors.newSingleThreadScheduledExecutor();
			executorService.scheduleAtFixedRate(this, 15, 55, TimeUnit.SECONDS);
			//			executorService.scheduleAtFixedRate(this, 1, 1, TimeUnit.SECONDS);
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
				int resourceFieldCount = resources.getResourceFieldCount(ResourcesManager.TYPE.WOOD);

				if (scoutingSkill * 10 > resourceFieldCount) {
					System.out.println(inhabitant.getName() + " erhöht fieldCount");
					int newResourceFieldCount = resources.increaseResourceFieldCount(ResourcesManager.TYPE.WOOD);

					if (newResourceFieldCount > resourceFieldCount) {
						User user = castle.getUserBy(inhabitant);
						Collection<Inhabitant> otherMembers = CollectionUtils.disjunction(//
								castle.getInhabitants(), Arrays.asList(inhabitant));

						resultContainer.addResultAction(//
								new BroadcastMessageAction(
										//
										user,
										"Du hast ein Ressourcenfeld entdeckt. Das erhöht die Effizienz der Produzenten und die maximale Lagerkapazität! Gut gelaunt kehrst du zur Burg zurück.", //
										new ArrayList<Inhabitant>(otherMembers), inhabitant.getName()
												+ " hat ein Ressourcenfeld entdeckt. Dadurch erhöht sich die Effizienz der Produzenten und die maximale Lagerkapazität!"));
						resultContainer.addResultAction(//
								new SetInhabitantsIdle(user, Arrays.asList(inhabitant)));

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
	public CATEGORY getCategory() {
		return SCOUTING;
	}

}
