package de.rardian.telegram.bot.castle.facilities;

import static de.rardian.telegram.bot.castle.facilities.CastleFacility.CATEGORY.BUILDING;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import de.rardian.telegram.bot.castle.facilities.building.Blueprint;
import de.rardian.telegram.bot.castle.facilities.building.LagerProject;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.castle.model.Project;
import de.rardian.telegram.bot.castle.model.ResourceAmount;
import de.rardian.telegram.bot.castle.model.ResourcesManager;
import de.rardian.telegram.bot.castle.model.ResourcesManager.TYPE;
import de.rardian.telegram.bot.domain.ProjectRepository;

// XXX Nötig? Solange es nur ein Projekt gibt, sollte die BuildingFacility reichen.
@Component
public class ProjectManager {
	@Autowired
	private ApplicationContext appContext;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private ResourcesManager resourcesManager;

	public ProjectManager() {
		//		createProjects(); npe
	}

	// XXX Beliebige Projekte unterstützen
	public void startProject(/*String projectId*/) {
		Blueprint blueprint = new LagerProject();
		Project project = new Project();
		project.setBlueprint(blueprint.getId());
		project.setName(blueprint.getName());
		// XXX Woher kommt's Level?
		project.setResourcesToFinish(blueprint.getResourcesNeeded(1));

		projectRepository.save(project);
	}

	public boolean projectInProgress() {
		return projectRepository.count() > 0;
	}

	// XXX Beliebige Projekte unterstützen
	public String getProjectName(/*String projectId*/) {
		return getCurrentProject().getName();
	}

	@Deprecated
	/** should be private */
	public Project getCurrentProject() {
		List<Project> projects = projectRepository.findAll();
		int projectCount = projects.size();
		Validate.isTrue(projectCount <= 1, "More than one Project in database: %d", projectCount);

		if (projectCount == 1) {
			return projects.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Let {@link Inhabitant} work on current Project.
	 * 
	 * @return if inhabitant has actually worked
	 */
	public boolean workOnCurrentProject(ResourcesManager resources, Inhabitant inhabitant) {
		Project currentProject = getCurrentProject();

		if (currentProject == null) {
			return false;
		}

		Collection<ResourceAmount> resourcesToFinish = currentProject.getResourcesToFinish();
		boolean projectFinished = true;
		boolean inhabitantWorked = false;

		for (ResourceAmount finishResourceAmount : resourcesToFinish) {
			final int finishAmount = finishResourceAmount.getAmount();

			if (finishAmount > 0) {
				int potentialBuildingProgress = inhabitant.getSkill(BUILDING);
				TYPE resourceType = finishResourceAmount.getType();

				// Calculate actualBuildingProgress
				int actualBuildingProgress = Math.min(potentialBuildingProgress, resources.getAmount(resourceType)); // memberBuildingProgress
				actualBuildingProgress = Math.min(actualBuildingProgress, finishAmount);

				// Adjust resources
				resources.reduce(TYPE.WOOD, actualBuildingProgress);
				finishResourceAmount.setAmount(finishAmount - actualBuildingProgress);

				// Adjust Project status
				projectFinished = finishAmount == actualBuildingProgress;
				inhabitantWorked = actualBuildingProgress > 0;
				break;
			}
		}

		if (projectFinished) {
			String blueprintId = currentProject.getBlueprint();
			getBlueprintById(blueprintId).executeResult(resourcesManager);

			projectRepository.delete(currentProject);
		} else {
			projectRepository.save(currentProject);
		}

		return inhabitantWorked;
	}

	private Blueprint getBlueprintById(String blueprintId) {
		Map<String, Blueprint> projects = appContext.getBeansOfType(Blueprint.class);
		for (Blueprint blueprint : projects.values()) {
			if (blueprintId.equals(blueprint.getId())) {
				return blueprint;
			}
		}
		throw new IllegalStateException("No blueprint found for id '" + blueprintId + "'");
	}

}
