package de.rardian.telegram.bot.castle.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

import de.rardian.telegram.bot.castle.CastleBot;
import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.castle.facilities.BuildingFacility;
import de.rardian.telegram.bot.castle.facilities.CastleFacility;
import de.rardian.telegram.bot.castle.facilities.EnvironmentFacility;
import de.rardian.telegram.bot.castle.facilities.ProductionFacility;
import de.rardian.telegram.bot.castle.model.ResourcesManager.TYPE;
import de.rardian.telegram.bot.model.User;

public class Castle {
	private Map<User, Inhabitant> inhabitants = Maps.synchronizedNavigableMap(new TreeMap<>());

	private ResourcesManager resources = new ResourcesManager(0, 5, 1);
	private CastleFacility buildingFacility;
	private CastleFacility produceFacility;
	private CastleFacility environmentFacility;

	// private ArrayList<CastleFacility> facilities;
	private NavigableMap<CastleFacility.CATEGORY, CastleFacility> facilities;

	private CastleBot bot;

	public Castle(CastleBot castleBot) {
		this.bot = castleBot;
	}

	public String getStatusAsString() {
		String listOfInhabitants = "";
		synchronized (inhabitants) {
			listOfInhabitants = getInhabitantsByName(inhabitants.values());
		}

		String resourcesAsString = "";
		for (ResourcesManager.TYPE resourceType : ResourcesManager.TYPE.values()) {
			resourcesAsString += "->" + resourceType + "\n";
			resourcesAsString += "--> Aktuell: " + resources.getAmount(resourceType) + " (Fundstätten: "
					+ resources.getResourceFieldCount(resourceType) + ")\n";
			resourcesAsString += "--> Kapazität: " + resources.getCapacity(resourceType) + " (max. Kapazität: "
					+ resources.getMaxCapacity(resourceType) + ")\n";

		}
		String status = "Die Burg ist in gutem Zustand.\n"//
				+ "Bewohner: "//
				+ inhabitants.size()//
				+ " ("//
				+ listOfInhabitants//
				+ ")\n"//
				+ "-> Produzenten: " + printFacility(CastleFacility.CATEGORY.PRODUCING)//
				+ "-> Baumeister: " + printFacility(CastleFacility.CATEGORY.BUILDING)//
				+ "-> Scouts: " + printFacility(CastleFacility.CATEGORY.SCOUTING)//
				+ "Ressourcen:\n"//
				+ resourcesAsString//
		// TODO max Kapazität von Fieldcount trennen. Gibt dann zwei Typen findbarer Sachen in der Umgebung
				+ "Bauvorhaben: "//
				+ ((BuildingFacility) getBuildingFacility()).getProgress() + " (von " + (resources.getCapacity(TYPE.WOOD) + 1) * 2 + ")";
		return status;
	}

	public Collection<Inhabitant> getInhabitants() {
		return new ArrayList<Inhabitant>(inhabitants.values());
	}

	private String printFacility(CastleFacility.CATEGORY category) {
		CastleFacility facility = getFacilities().get(category);

		String result = facility.getMemberCount()//
				+ " ("//
				+ facility.getMemberListByName()//
				+ ")\n";

		return result;
	}

	public String getInhabitantsByName(Collection<Inhabitant> inhabitants) {
		ArrayList<String> inhabitantsByName = new ArrayList<>(inhabitants.size());

		for (Inhabitant inhabitant : inhabitants) {
			inhabitantsByName.add(inhabitant.getName());
		}

		return StringUtils.join(inhabitantsByName, ", ");
	}

	public void addWorkerFor(CastleFacility.CATEGORY category, Inhabitant inhabitant) throws AlreadyAddedException {
		getFacility(category).addMember(inhabitant);
	}

	@Deprecated
	public void addProducer(Inhabitant inhabitant) throws AlreadyAddedException {
		getFacility(CastleFacility.CATEGORY.PRODUCING).addMember(inhabitant);
	}

	@Deprecated
	public void addBuilder(Inhabitant inhabitant) throws AlreadyAddedException {
		getFacility(CastleFacility.CATEGORY.BUILDING).addMember(inhabitant);
	}

	private CastleFacility getFacility(CastleFacility.CATEGORY category) {
		return getFacilities().get(category);
	}

	public void addInhabitant(User user) {
		Inhabitant newInhabitant = new Inhabitant();
		newInhabitant.setUser(user);
		inhabitants.put(user, newInhabitant);
		// TODO don't add users twice (actually ensured by UserManager)
	}

	public Inhabitant getInhabitantFor(User user) {
		return inhabitants.get(user);
	}

	public User getUserBy(Inhabitant search) {
		synchronized (inhabitants) {
			for (Entry<User, Inhabitant> pairs : inhabitants.entrySet()) {
				if (pairs.getValue().compareTo(search) == 0) {
					return pairs.getKey();
				}
			}
		}
		throw new IllegalStateException("Keinen Nutzer für Einwohner '" + search.getName() + "' gefunden");
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
			buildingFacility = new BuildingFacility(bot, this, resources);
		}
		return buildingFacility;
	}

	private CastleFacility getEnvironmentFacility() {
		if (environmentFacility == null) {
			environmentFacility = new EnvironmentFacility(bot, this, resources);
		}
		return environmentFacility;
	}

	private CastleFacility getProductionFacility() {
		if (produceFacility == null) {
			produceFacility = new ProductionFacility(bot, this, resources);
		}
		return produceFacility;
	}

	private NavigableMap<CastleFacility.CATEGORY, CastleFacility> getFacilities() {
		if (facilities == null) {
			facilities = Maps.synchronizedNavigableMap(new TreeMap<>());
			facilities.put(getBuildingFacility().getCategory(), getBuildingFacility());
			facilities.put(getEnvironmentFacility().getCategory(), getEnvironmentFacility());
			facilities.put(getProductionFacility().getCategory(), getProductionFacility());
		}
		return facilities;
	}
}
