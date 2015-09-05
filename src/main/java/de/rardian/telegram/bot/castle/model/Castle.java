package de.rardian.telegram.bot.castle.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang3.StringUtils;

import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.castle.facilities.BuildingFacility;
import de.rardian.telegram.bot.castle.facilities.CastleFacility;
import de.rardian.telegram.bot.castle.facilities.ProductionFacility;
import de.rardian.telegram.bot.model.User;

public class Castle {
	private Collection<User> inhabitants = Collections.synchronizedList(new ArrayList<>());

	private Resources resources = new Resources(0, 5);
	private CastleFacility buildingFacility;
	private CastleFacility produceFacility;
	private ArrayList<CastleFacility> facilities;

	public String getStatusAsString() {
		String status = "Die Burg ist in gutem Zustand.\n"//
				+ "Bewohner: "//
				+ inhabitants.size()//
				+ " ("//
				+ getUserListByFirstname(inhabitants)//
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

	public void addProducer(User user) throws AlreadyAddedException {
		getProductionFacility().addMember(user);
	}

	public void addBuilder(User user) throws AlreadyAddedException {
		getBuildingFacility().addMember(user);
	}

	public void addInhabitant(User user) {
		inhabitants.add(user);
		// TODO don't add users twice (actually ensured by UserManager)
		// TODO use Inhabitant instead of User
	}

	public void setInhabitantIdle(User user) {
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
