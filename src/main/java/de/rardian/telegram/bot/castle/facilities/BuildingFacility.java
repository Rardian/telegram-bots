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
import de.rardian.telegram.bot.castle.facilities.building.Blueprint;
import de.rardian.telegram.bot.castle.facilities.building.LagerProject;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.castle.model.ResourcesManager;
import de.rardian.telegram.bot.command.action.BroadcastMessageAction;
import de.rardian.telegram.bot.model.Bot;
import de.rardian.telegram.bot.model.User;

/** Manage Inhabitants working on Projects. */
public class BuildingFacility extends BasicFacility implements Runnable {
	public static final String RESULT_BUILDING_PROGRESS = "RESULT_BUILDING_PROGRESS";

	private int overallBuildingProgress = 0;
	private ScheduledExecutorService executorService;
	// TODO Start with simple project: ein Lager mit gleicher Kapazität für alle Ressourcen, dann ein Lager je Ressource oder ein kombiniertes Lager für beliebige Ressourcen.
	private Map<String, Blueprint> blueprints = new HashMap<>();
	private Blueprint currentProject = null;

	private ProjectManager projectManager;

	public BuildingFacility(Bot bot, Castle castle, ResourcesManager resources, ProjectManager projectManager) {
		super(bot, castle, resources);
		this.projectManager = projectManager;
		Blueprint lager = new LagerProject();
		blueprints.put(lager.getId(), lager);
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
				//				int potentialBuildingProgress = inhabitant.getSkill(BUILDING);

				boolean inhabitantWorked = projectManager.workOnCurrentProject(resources, inhabitant);
				// workOnCurrentProject: finish Project if possible (projectResult.executeResult())

				if (inhabitantWorked) {
					super.increaseInhabitantXp(inhabitant, BUILDING, resultContainer);
				}

				boolean projectFinished = !projectManager.projectInProgress();
				if (projectFinished) {
					User user = castle.getUserBy(inhabitant);

					Collection<Inhabitant> otherInhabitants = CollectionUtils.disjunction(//
							castle.getInhabitants(), Arrays.asList(inhabitant));
					Collection<Inhabitant> otherMembers = CollectionUtils.disjunction(//
							members, Arrays.asList(inhabitant));

					// TODO assign a project leader (the starter), who is the one to finish the project
					resultContainer.addResultAction(//
							new BroadcastMessageAction(//
									user, "Du hast das Bauprojekt beendet.", //
									new ArrayList<Inhabitant>(otherInhabitants), inhabitant.getName() + " hat das aktuelle Bauprojekt beendet."));
					resultContainer.addResultAction(//
							new BroadcastMessageAction(
									//
									user, "Nach Beendigung des Bauprojekts hast du die Baumannschaft verlassen.", //
									new ArrayList<Inhabitant>(otherMembers), "Nach Abschluss der Baumaßnahmen hast du die Baumannschaft verlassen."));
					resultContainer.addResultAction(//
							new SetInhabitantsIdle(user, new ArrayList<Inhabitant>(members)));
					break;
				}

				///// XXX entfernen
				//				int overallBuildingStepsNeeded = (resources.getCapacity(ResourcesManager.TYPE.WOOD) + 1) * 2;
				//
				//				final int memberBuildingProgress = Math.min(potentialBuildingProgress, resources.getAmount(ResourcesManager.TYPE.WOOD));
				//				final boolean capacityCanBeIncreased = resources.getCapacity(ResourcesManager.TYPE.WOOD) < resources
				//						.getMaxCapacity(ResourcesManager.TYPE.WOOD);
				//
				//				if (capacityCanBeIncreased && memberBuildingProgress > 0) {
				//					int remainingBuildingSteps = overallBuildingStepsNeeded - overallBuildingProgress;
				//					int actualBuildingSteps = Math.min(remainingBuildingSteps, memberBuildingProgress);
				//
				//					resources.reduce(ResourcesManager.TYPE.WOOD, actualBuildingSteps);
				//					actualBuildingProgress += actualBuildingSteps;
				//					overallBuildingProgress += actualBuildingSteps;
				//					super.increaseInhabitantXp(inhabitant, BUILDING, resultContainer);
				//				}
				//
				//				final boolean buildingFinished = overallBuildingProgress >= overallBuildingStepsNeeded;
			}
		}

		resultContainer.addResultInteger(RESULT_BUILDING_PROGRESS, actualBuildingProgress);
		return resultContainer;
	}

	/*
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
	*/

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
		return blueprints.containsKey(projectId);
	}

	public Collection<String> getProjectIds() {
		return blueprints.keySet();
	}

	public void startProject(String projectId) {
		currentProject = blueprints.get(projectId);
	}

	public String getProjectName(String projectId) {
		return blueprints.get(projectId).getId();
	}
}
