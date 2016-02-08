package de.rardian.telegram.bot.castle.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.google.common.collect.Maps;

import de.rardian.telegram.bot.castle.CastleBot;
import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.castle.facilities.BuildingFacility;
import de.rardian.telegram.bot.castle.facilities.CastleFacility;
import de.rardian.telegram.bot.castle.facilities.CastleFacility.CATEGORY;
import de.rardian.telegram.bot.castle.facilities.EnvironmentFacility;
import de.rardian.telegram.bot.castle.facilities.ProductionFacility;
import de.rardian.telegram.bot.castle.model.ResourcesManager.TYPE;
import de.rardian.telegram.bot.manage.UserManager;
import de.rardian.telegram.bot.model.User;

public class Castle {
	// private Map<User, Inhabitant> inhabitants =
	// Maps.synchronizedNavigableMap(new TreeMap<>());


	private ResourcesManager resources;
	private CastleFacility buildingFacility;
	private CastleFacility produceFacility;
	private CastleFacility environmentFacility;

	// private ArrayList<CastleFacility> facilities;
	private NavigableMap<CastleFacility.CATEGORY, CastleFacility> facilities;

	private CastleBot bot;

	private UserManager userManager;
	private AutowireCapableBeanFactory beanFactory;

	@Autowired
	public Castle(CastleBot castleBot, AutowireCapableBeanFactory beanFactory) {
		this.bot = castleBot;
		this.beanFactory = beanFactory;
	}

	public String getStatusAsString() {
		String listOfInhabitants = "";

		//		synchronized (inhabitants) {
		listOfInhabitants = getInhabitantsByName(getInhabitants());
		//		}

		String resourcesAsString = "";
		for (ResourcesManager.TYPE resourceType : ResourcesManager.TYPE.values()) {
			resourcesAsString += "->" + resourceType + "\n";
			resourcesAsString += "--> Aktuell: " + getResourcesManager().getAmount(resourceType) + " (Fundstätten: "
					+ getResourcesManager().getResourceFieldCount(resourceType) + ")\n";
			resourcesAsString += "--> Kapazität: " + getResourcesManager().getCapacity(resourceType)
					+ " (max. Kapazität: " + getResourcesManager().getMaxCapacity(resourceType) + ")\n";

		}

		String producerAsString = "";
		for (CastleFacility.CATEGORY skillType : CastleFacility.CATEGORY.values()) {
			producerAsString += "->" + skillType + ": ";
			producerAsString += printFacility(skillType);
		}
		String status = "Die Burg ist in gutem Zustand.\n"//
				+ "Bewohner: "//
				+ userManager.countInhabitants()//
				+ " ("//
				+ listOfInhabitants //
				+ ")\n"//
				+ producerAsString //
		//				+ "-> Produzenten: " + printFacility(CastleFacility.CATEGORY.WOODCUTTING)//
		//				+ "-> Baumeister: " + printFacility(CastleFacility.CATEGORY.BUILDING)//
		//				+ "-> Scouts: " + printFacility(CastleFacility.CATEGORY.SCOUTING)//
				+ "Ressourcen:\n"//
				+ resourcesAsString//
		// TODO max Kapazität von Fieldcount trennen. Gibt dann zwei Typen
		// findbarer Sachen in der Umgebung
				+ "Bauvorhaben: "//
				+ ((BuildingFacility) getBuildingFacility()).getProgress() + " (von "
				+ (getResourcesManager().getCapacity(TYPE.WOOD) + 1) * 2 + ")";
		return status;
	}

	public Collection<Inhabitant> getInhabitants() {
		return userManager.getInhabitants();
	}

	private String printFacility(CastleFacility.CATEGORY category) {
		CastleFacility facility = getFacilities().get(category);

		String result = facility.getMemberCount()//
				+ " ("//
				+ facility.getMemberListByName()//
				+ ")\n";

		return result;
	}

	public String getInhabitantsByName(Iterable<Inhabitant> inhabitants) {
		ArrayList<String> inhabitantsByName = new ArrayList<>();

		for (Inhabitant inhabitant : inhabitants) {
			inhabitantsByName.add(inhabitant.getName());
		}

		return StringUtils.join(inhabitantsByName, ", ");
	}

	public void addWorkerFor(CastleFacility.CATEGORY category, Inhabitant inhabitant) throws AlreadyAddedException {
		CastleFacility facility = getFacility(category);
		facility.addMember(inhabitant);
	}

	//	@Deprecated
	//	public void addProducer(Inhabitant inhabitant) throws AlreadyAddedException {
	//		getFacility(CastleFacility.CATEGORY.PRODUCING).addMember(inhabitant);
	//	}

	@Deprecated
	public void addBuilder(Inhabitant inhabitant) throws AlreadyAddedException {
		getFacility(CastleFacility.CATEGORY.BUILDING).addMember(inhabitant);
	}

	private CastleFacility getFacility(CastleFacility.CATEGORY category) {
		return getFacilities().get(category);
	}

	public Inhabitant getInhabitantFor(User user) {
		return userManager.getInhabitantByUser(user);
	}

	public User getUserBy(Inhabitant search) {
		return userManager.getUserByInhabitant(search);
		//
		// synchronized (inhabitants) {
		// for (Entry<User, Inhabitant> pairs : inhabitants.entrySet()) {
		// if (pairs.getValue().compareTo(search) == 0) {
		// return pairs.getKey();
		// }
		// }
		// }
		// throw new IllegalStateException("Keinen Nutzer für Einwohner '" +
		// search.getName() + "' gefunden");
	}

	public void setInhabitantIdle(Inhabitant inhabitant) {
		Collection<CastleFacility> facilities = getFacilities().values();

		synchronized (getFacilities()) {
			for (CastleFacility facility : facilities) {
				facility.removeMember(inhabitant);
			}
		}
	}

	private CastleFacility getBuildingFacility() {
		if (buildingFacility == null) {
			buildingFacility = new BuildingFacility(bot, this, getResourcesManager());
		}
		return buildingFacility;
	}

	private CastleFacility getEnvironmentFacility() {
		if (environmentFacility == null) {
			environmentFacility = new EnvironmentFacility(bot, this, getResourcesManager());
		}
		return environmentFacility;
	}

	private CastleFacility createProductionFacility(ResourcesManager.TYPE resourceType, CastleFacility.CATEGORY skillType) {
		return new ProductionFacility(bot, this, getResourcesManager(), resourceType, skillType);
	}

	private NavigableMap<CastleFacility.CATEGORY, CastleFacility> getFacilities() {
		if (facilities == null) {
			facilities = Maps.synchronizedNavigableMap(new TreeMap<>());
			facilities.put(getBuildingFacility().getCategory(), getBuildingFacility());
			facilities.put(getEnvironmentFacility().getCategory(), getEnvironmentFacility());

			Map<ResourcesManager.TYPE, CastleFacility.CATEGORY> resourceMapping = new HashMap<>();
			resourceMapping.put(ResourcesManager.TYPE.WOOD, CastleFacility.CATEGORY.WOODCUTTING);
			resourceMapping.put(ResourcesManager.TYPE.IRON, CastleFacility.CATEGORY.MINING);
			resourceMapping.put(ResourcesManager.TYPE.STONE, CastleFacility.CATEGORY.QUARRYING);

			for (ResourcesManager.TYPE resourceType : resourceMapping.keySet()) {
				CastleFacility productionFacility = createProductionFacility(resourceType, resourceMapping.get(resourceType));
				facilities.put(productionFacility.getCategory(), productionFacility);
			}
		}
		return facilities;
	}

	private ResourcesManager getResourcesManager() {
		if (resources == null) {
			resources = new ResourcesManager();
			beanFactory.autowireBean(resources);
			resources.initialize(0, 5, 1);
		}
		return resources;
	}

	public boolean isProjectInProgress() {
		return ((BuildingFacility) getFacility(CATEGORY.BUILDING)).isProjectInProgress();
	}

	public boolean isProjectValid(String projectId) {
		return ((BuildingFacility) getFacility(CATEGORY.BUILDING)).isProjectValid(projectId);
	}

	public Collection<String> getProjectIds() {
		return ((BuildingFacility) getFacility(CATEGORY.BUILDING)).getProjectIds();
	}

	public void startProject(String projectId) {
		((BuildingFacility) getFacility(CATEGORY.BUILDING)).startProject(projectId);
	}

	public String getProjectName(String projectName) {
		return ((BuildingFacility) getFacility(CATEGORY.BUILDING)).getProjectName(projectName);
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
}
