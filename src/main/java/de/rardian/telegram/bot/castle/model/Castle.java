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
import de.rardian.telegram.bot.castle.facilities.CastleFacilityCategories;
import de.rardian.telegram.bot.castle.facilities.EnvironmentFacility;
import de.rardian.telegram.bot.castle.facilities.ProductionFacility;
import de.rardian.telegram.bot.model.User;

public class Castle {
	private Map<User, Inhabitant> inhabitants = Maps.synchronizedNavigableMap(new TreeMap<>());

	private Resources resources = new Resources(0, 5, 1);
	private CastleFacility buildingFacility;
	private CastleFacility produceFacility;
	private CastleFacility environmentFacility;

	// private ArrayList<CastleFacility> facilities;
	private NavigableMap<CastleFacilityCategories, CastleFacility> facilities;

	private CastleBot bot;

	public Castle(CastleBot castleBot) {
		this.bot = castleBot;
	}

	public String getStatusAsString() {
		String listOfInhabitants = "";
		synchronized (inhabitants) {
			listOfInhabitants = getInhabitantsByName(inhabitants.values());
		}

		String status = "Die Burg ist in gutem Zustand.\n"//
				+ "Bewohner: "//
				+ inhabitants.size()//
				+ " ("//
				+ listOfInhabitants//
				+ ")\n"//
				+ "-> Produzenten: " + printFacility(CastleFacilityCategories.PRODUCING)//
				+ "-> Baumeister: " + printFacility(CastleFacilityCategories.BUILDING)//
				+ "-> Scouts: " + printFacility(CastleFacilityCategories.SCOUTING)//
				+ "Ressourcen:\n"//
				+ "-> Aktuell: " + resources.getActual() + " (Fundstätten: " + resources.getResourceFieldCount() + ")\n"//
				+ "-> Kapazität: " + resources.getCapacity() + " (max. Kapazität: " + resources.getMaxCapacity() + ")\n"//
		// TODO max Kapazität von Fieldcount trennen. Gibt dann zwei Typen findbarer Sachen in der Umgebung
				+ "Bauvorhaben: "//
				+ ((BuildingFacility) getBuildingFacility()).getProgress() + " (von " + (resources.getCapacity() + 1) * 2 + ")";
		return status;
	}

	public Collection<Inhabitant> getInhabitants() {
		return new ArrayList<Inhabitant>(inhabitants.values());
	}

	private String printFacility(CastleFacilityCategories category) {
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

	public void addWorkerFor(CastleFacilityCategories category, Inhabitant inhabitant) throws AlreadyAddedException {
		getFacility(category).addMember(inhabitant);
	}

	@Deprecated
	public void addProducer(Inhabitant inhabitant) throws AlreadyAddedException {
		getFacility(CastleFacilityCategories.PRODUCING).addMember(inhabitant);
	}

	@Deprecated
	public void addBuilder(Inhabitant inhabitant) throws AlreadyAddedException {
		getFacility(CastleFacilityCategories.BUILDING).addMember(inhabitant);
	}

	private CastleFacility getFacility(CastleFacilityCategories category) {
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
			buildingFacility = new BuildingFacility(this, resources, bot);
		}
		return buildingFacility;
	}

	private CastleFacility getEnvironmentFacility() {
		if (environmentFacility == null) {
			environmentFacility = new EnvironmentFacility(this, resources, bot);
		}
		return environmentFacility;
	}

	private CastleFacility getProductionFacility() {
		if (produceFacility == null) {
			produceFacility = new ProductionFacility(this, resources, bot);
		}
		return produceFacility;
	}

	private NavigableMap<CastleFacilityCategories, CastleFacility> getFacilities() {
		if (facilities == null) {
			facilities = Maps.synchronizedNavigableMap(new TreeMap<>());
			facilities.put(getBuildingFacility().getCategory(), getBuildingFacility());
			facilities.put(getEnvironmentFacility().getCategory(), getEnvironmentFacility());
			facilities.put(getProductionFacility().getCategory(), getProductionFacility());
		}
		return facilities;
	}
}
