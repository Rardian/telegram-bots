package de.rardian.telegram.bot.castle.model;

import java.util.Collection;

import de.rardian.telegram.bot.castle.facilities.CastleFacilityCategories;

public class Resources {

	private int resources;
	private int capacity;

	public Resources(int initialResources, int resourcesCapacity) {
		resources = initialResources;
		capacity = resourcesCapacity;
	}

	public int getActual() {
		return resources;
	}

	public void reduce(int reducement) {
		resources -= reducement;
	}

	public int getCapacity() {
		return capacity;
	}

	public void increaseCapacity() {
		capacity++;
	}

	public int increaseIfPossible(int potentialResourceIncrease) {
		// act + inc <= max => inc
		// act + inc > max => max - act
		int actualResourceIncrease = 0;

		if (getActual() + potentialResourceIncrease <= getCapacity()) {
			actualResourceIncrease = potentialResourceIncrease;
		} else {
			actualResourceIncrease = getCapacity() - getActual();
		}
		increase(actualResourceIncrease);

		return actualResourceIncrease;
	}

	private void increase(int actualResourceIncrease) {
		resources += actualResourceIncrease;
	}

	public int increase(Collection<Inhabitant> members) {
		int actualIncrease = 0;

		for (Inhabitant inhabitant : members) {
			//			System.out.println("increase from member: " + inhabitant.getName());

			int potentialIncrease = inhabitant.getProductionSkill();
			//			System.out.println("  potential increase : " + potentialIncrease);

			actualIncrease += increaseIfPossible(potentialIncrease);
			//			System.out.println("  actual increase : " + actualIncrease);

			if (actualIncrease > 0) {
				inhabitant.increaseXp(CastleFacilityCategories.PRODUCING);
				//				System.out.println("  xp increased :)");
			} else {
				//				System.out.println("  xp not increased :(");
			}
		}

		return actualIncrease;
	}

	//	public int increaseCapacity(Collection<Inhabitant> members) {
	//		int actualBuildingProgress = 0;
	//
	//		for (Inhabitant inhabitant : members) {
	//			int potentialIncrease = inhabitant.getBuildingSkill();
	//
	//			// cost for extending capacity: new capacity * 2
	//			// every builder uses 1 resource and increases buildprogress
	//			actualBuildingProgress = Math.min(potentialIncrease, getActual());
	//
	//			resources.reduce(actualBuildingProgress);
	//			overallBuildingProgress += actualBuildingProgress;
	//		}
	//
	//
	//		if (overallBuildingProgress >= (resources.getCapacity() + 1) * 2) {
	//			resources.increaseCapacity();
	//			overallBuildingProgress = 0;
	//		} // TODO Auto-generated method stub
	//		return 0;
	//	}

}
