package de.rardian.telegram.bot.castle.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.castle.facilities.BuildingFacility;
import de.rardian.telegram.bot.castle.facilities.CastleFacility;
import de.rardian.telegram.bot.castle.facilities.ProductionFacility;
import de.rardian.telegram.bot.model.User;

public class Castle {
	private Map<User, Inhabitant> inhabitants = Maps.synchronizedNavigableMap(new TreeMap<>());

	private Resources resources = new Resources(0, 5);
	private CastleFacility buildingFacility;
	private CastleFacility produceFacility;
	private ArrayList<CastleFacility> facilities;

	public String getStatusAsString() {
		String listOfInhabitants = "";
		synchronized (inhabitants) {
			listOfInhabitants = getUserListByFirstname(inhabitants.keySet());
		}

		String status = "Die Burg ist in gutem Zustand.\n"//
				+ "Bewohner: "//
				+ inhabitants.size()//
				+ " ("//
				+ listOfInhabitants//
				+ ")\n"//
				+ "Produzenten: "//
				+ getProductionFacility().getMemberCount()//
				+ " ("//
				+ getProductionFacility().getMemberListByFirstname()//
				+ ")\n"//
				+ "Baumeister: "//
				+ getBuildingFacility().getMemberCount()//
				+ " ("//
				+ getBuildingFacility().getMemberListByFirstname()//
				+ ")\n"//
				+ "Ressourcen: "//
				+ resources.getActual() + " (von " + resources.getCapacity() + ")\n"//
				+ "Bauvorhaben: "//
				+ ((BuildingFacility) getBuildingFacility()).getProgress() + " (von " + (resources.getCapacity() + 1) * 2 + ")";
		return status;
	}

	public String getUserListByFirstname(Collection<User> users) {
		ArrayList<String> usersByFirstname = new ArrayList<>(users.size());

		for (User user : users) {
			usersByFirstname.add(user.getFirstName());
		}

		return StringUtils.join(usersByFirstname, ", ");
	}

	public void addProducer(Inhabitant inhabitant) throws AlreadyAddedException {
		getProductionFacility().addMember(inhabitant);
	}

	public void addBuilder(Inhabitant inhabitant) throws AlreadyAddedException {
		getBuildingFacility().addMember(inhabitant);
	}

	public void addInhabitant(User user) {
		Inhabitant newInhabitant = new Inhabitant();
		newInhabitant.setUser(user);
		inhabitants.put(user, newInhabitant);
		// TODO don't add users twice (actually ensured by UserManager)
	}

	public Inhabitant getInhabitant(User user) {
		return inhabitants.get(user);
	}

	public void setInhabitantIdle(Inhabitant user) {
		for (CastleFacility facility : getFacilities()) {
			facility.removeMember(user);
		}
	}

	private CastleFacility getBuildingFacility() {
		if (buildingFacility == null) {
			buildingFacility = new BuildingFacility(this, resources);
		}
		return buildingFacility;
	}

	private CastleFacility getProductionFacility() {
		if (produceFacility == null) {
			produceFacility = new ProductionFacility(this, resources);
		}
		return produceFacility;
	}

	private Collection<CastleFacility> getFacilities() {
		if (facilities == null) {
			facilities = new ArrayList<>(2);
			facilities.add(getBuildingFacility());
			facilities.add(getProductionFacility());
		}
		return facilities;
	}

}
