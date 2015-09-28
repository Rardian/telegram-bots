package de.rardian.telegram.bot.castle.model;

import com.google.common.annotations.VisibleForTesting;

public class InhabitantTestFactory {
	public static Inhabitant newInhabitant() {
		Inhabitant testInhabitant = new Inhabitant();

		testInhabitant.setUser(new TestUser(8039535, "Vorname", "Nachname", "Username"));

		return testInhabitant;
	}

	/**
	 * Returns a new User object that differs (i.e. not equal) from previously
	 * delivered User objects.
	 */
	@VisibleForTesting
	public static Inhabitant newUniqueInhabitant(long uniqueId) {
		Inhabitant testInhabitant = new Inhabitant();

		testInhabitant.setUser(new TestUser(uniqueId, "Vorname" + uniqueId, "Nachname" + uniqueId, "Username" + uniqueId));

		return testInhabitant;
	}

}
