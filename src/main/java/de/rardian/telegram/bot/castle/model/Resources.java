package de.rardian.telegram.bot.castle.model;

import java.util.Collection;

import de.rardian.telegram.bot.castle.facilities.CastleFacilityCategories;

public class Resources {

	/** actual amount of resources */
	private int resources;
	/** dictates maximum amount of resources */
	private int capacity;
	/** dictates possible maximum increase, stored with times of 10 */
	private int resourceFieldCount;

	public Resources(int initialResources, int resourcesCapacity, int resourceFieldCount) {
		resources = initialResources;
		capacity = resourcesCapacity;
		this.resourceFieldCount = resourceFieldCount * 10;
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

		potentialResourceIncrease = Math.min(potentialResourceIncrease, resourceFieldCount);

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

	public int getResourceFieldCount() {
		return resourceFieldCount / 10;
	}

	public void increaseResourceFieldCount() {
		resourceFieldCount++;
	}

}
