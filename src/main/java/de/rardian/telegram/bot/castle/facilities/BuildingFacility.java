package de.rardian.telegram.bot.castle.facilities;

import static de.rardian.telegram.bot.castle.facilities.CastleFacilityCategories.BUILDING;

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
import de.rardian.telegram.bot.castle.model.Resources;
import de.rardian.telegram.bot.command.action.BroadcastMessageAction;
import de.rardian.telegram.bot.model.Bot;
import de.rardian.telegram.bot.model.User;

public class BuildingFacility extends BasicFacility implements Runnable {
	public static final String RESULT_BUILDING_PROGRESS = "RESULT_BUILDING_PROGRESS";

	private int overallBuildingProgress = 0;
	private ScheduledExecutorService executorService;

	public BuildingFacility(Castle castle, Resources resources, Bot bot) {
		super(castle, resources, bot);
	}

	@Override
	public CastleFacilityCategories getCategory() {
		return BUILDING;
	}

	/** Start the building queue, if needed. */
	protected void start() {
		Validate.notNull(castle, "a castle needs to be set");

		if (executorService == null) {
			executorService = Executors.newSingleThreadScheduledExecutor();
			executorService.scheduleAtFixedRate(this, 10, 35, TimeUnit.SECONDS);
		}
	}

	@Override
	public ProcessResult2 process() {
		int actualBuildingProgress = 0;
		ProcessResult2 resultContainer = new ProcessResult2();

		synchronized (members) {

			for (Inhabitant inhabitant : members) {
				int potentialBuildingProgress = inhabitant.getSkill(BUILDING);
				int overallBuildingStepsNeeded = (resources.getCapacity() + 1) * 2;

				// cost for extending capacity: new capacity * 2
				// every builder uses <skill> resources to increase buildprogress
				final int memberBuildingProgress = Math.min(potentialBuildingProgress, resources.getActual());
				final boolean capacityCanBeIncreased = resources.getCapacity() < resources.getMaxCapacity();

				if (capacityCanBeIncreased && memberBuildingProgress > 0) {
					int remainingBuildingSteps = overallBuildingStepsNeeded - overallBuildingProgress;
					int actualBuildingSteps = Math.min(remainingBuildingSteps, memberBuildingProgress);

					resources.reduce(actualBuildingSteps);
					actualBuildingProgress += actualBuildingSteps;
					overallBuildingProgress += actualBuildingSteps;
					super.increaseInhabitantXp(inhabitant, BUILDING, resultContainer);
				}

				final boolean buildingFinished = overallBuildingProgress >= overallBuildingStepsNeeded;

				// FIXME remove capacityCanBeIncreased, it's not necessary
				if (buildingFinished && capacityCanBeIncreased) {
					resources.increaseCapacity();
					overallBuildingProgress = 0;

					User user = castle.getUserBy(inhabitant);
					Collection<Inhabitant> otherInhabitants = CollectionUtils.disjunction(//
							castle.getInhabitants(), Arrays.asList(inhabitant));
					Collection<Inhabitant> otherMembers = CollectionUtils.disjunction(//
							members, Arrays.asList(inhabitant));

					// TODO assign a project leader (the starter), who is the one to finish the project
					resultContainer.addResultAction(//
							new BroadcastMessageAction(//
									user, "Du hast das Bauprojekt beendet.", //
									new ArrayList<Inhabitant>(otherInhabitants), inhabitant.getName() + " hat die Lagerkapazität erhöht."));
					resultContainer.addResultAction(//
							new BroadcastMessageAction(
							//
									user, "Nach Beendigung des Bauprojekts hast du die Baumannschaft verlassen.", //
									new ArrayList<Inhabitant>(otherMembers), "Nach Abschluss der Baumaßnahmen hast du die Baumannschaft verlassen."));
					resultContainer.addResultAction(//
							new SetInhabitantsIdle(user, new ArrayList<Inhabitant>(members)));
					break;
				}
			}
		}

		resultContainer.addResultInteger(RESULT_BUILDING_PROGRESS, actualBuildingProgress);
		return resultContainer;
	}

	public int getProgress() {
		return overallBuildingProgress;
	}
}
