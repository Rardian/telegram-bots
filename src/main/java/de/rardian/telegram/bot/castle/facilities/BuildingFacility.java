package de.rardian.telegram.bot.castle.facilities;

import static de.rardian.telegram.bot.castle.facilities.CastleFacility.CATEGORY.BUILDING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;

import com.google.common.annotations.VisibleForTesting;

import de.rardian.telegram.bot.castle.commands.actions.SetInhabitantsIdle;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.castle.model.Project;
import de.rardian.telegram.bot.castle.model.ResourceAmount;
import de.rardian.telegram.bot.castle.model.ResourcesManager;
import de.rardian.telegram.bot.castle.model.ResourcesManager.TYPE;
import de.rardian.telegram.bot.command.action.BroadcastMessageAction;
import de.rardian.telegram.bot.model.Bot;
import de.rardian.telegram.bot.model.User;

public class BuildingFacility extends BasicFacility implements Runnable {
	public static final String RESULT_BUILDING_PROGRESS = "RESULT_BUILDING_PROGRESS";

	private int overallBuildingProgress = 0;
	private ScheduledExecutorService executorService;
	// TODO Start with simple project: ein Lager mit gleicher Kapazität für alle Ressourcen, dann ein Lager je Ressource oder ein kombiniertes Lager für beliebige Ressourcen.
	private Map<String, Project> projects = new HashMap<>();
	private Project currentProject = null;

	public BuildingFacility(Bot bot, Castle castle, ResourcesManager resources) {
		super(bot, castle, resources);
		projects.put("LAGER", new Project("Lager", Arrays.asList(new ResourceAmount(TYPE.WOOD, 10))));
	}

	@Override
	public CATEGORY getCategory() {
		return BUILDING;
	}

	/** Start the building queue, if needed. */
	protected void start() {
		Validate.notNull(castle, "a castle needs to be set");

		if (executorService == null) {
			executorService = Executors.newSingleThreadScheduledExecutor();
			executorService.scheduleAtFixedRate(this, 10, 35, TimeUnit.SECONDS);
			//			executorService.scheduleAtFixedRate(this, 1, 1, TimeUnit.SECONDS);
		}
	}

	@Override
	public ProcessResult2 process() {
		int actualBuildingProgress = 0;
		ProcessResult2 resultContainer = new ProcessResult2();

		synchronized (members) {
			// TODO work with different Resource Types
			// TODO if nothing can be built (e.g. max capacity reached), inform player and remove Inhabitant

			for (Inhabitant inhabitant : members) {
				int potentialBuildingProgress = inhabitant.getSkill(BUILDING);
				int overallBuildingStepsNeeded = (resources.getCapacity(ResourcesManager.TYPE.WOOD) + 1) * 2;

				// cost for extending capacity: new capacity * 2
				// every builder uses <skill> resources to increase buildprogress
				final int memberBuildingProgress = Math.min(potentialBuildingProgress, resources.getAmount(ResourcesManager.TYPE.WOOD));
				final boolean capacityCanBeIncreased = resources.getCapacity(ResourcesManager.TYPE.WOOD) < resources
						.getMaxCapacity(ResourcesManager.TYPE.WOOD);

				if (capacityCanBeIncreased && memberBuildingProgress > 0) {
					int remainingBuildingSteps = overallBuildingStepsNeeded - overallBuildingProgress;
					int actualBuildingSteps = Math.min(remainingBuildingSteps, memberBuildingProgress);

					resources.reduce(ResourcesManager.TYPE.WOOD, actualBuildingSteps);
					actualBuildingProgress += actualBuildingSteps;
					overallBuildingProgress += actualBuildingSteps;
					super.increaseInhabitantXp(inhabitant, BUILDING, resultContainer);
				}

				final boolean buildingFinished = overallBuildingProgress >= overallBuildingStepsNeeded;

				// FIXME remove capacityCanBeIncreased, it's not necessary
				if (buildingFinished && capacityCanBeIncreased) {
					resources.increaseCapacity(ResourcesManager.TYPE.WOOD);
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

	@VisibleForTesting
	void setProgress(int newOverallBuildingProgress) {
		overallBuildingProgress = newOverallBuildingProgress;
	}

	public int getProgress() {
		return overallBuildingProgress;
	}

	public boolean isProjectInProgress() {
		return currentProject == null;
	}

	public boolean isProjectValid(String projectId) {
		return projects.containsKey(projectId);
	}

	public Collection<String> getProjectIds() {
		return projects.keySet();
	}

	public void startProject(String projectId) {
		currentProject = projects.get(projectId);
	}

	public String getProjectName(String projectId) {
		return projects.get(projectId).getName();
	}
}
