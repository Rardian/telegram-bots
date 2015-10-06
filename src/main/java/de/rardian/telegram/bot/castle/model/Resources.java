package de.rardian.telegram.bot.castle.model;

import com.google.common.annotations.VisibleForTesting;

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

	@VisibleForTesting
	public int increaseIfPossible(int potentialResourceIncrease) {
		// act + inc <= max => inc
		// act + inc > max => max - act
		int actualResourceIncrease = 0;

		potentialResourceIncrease = Math.min(potentialResourceIncrease, getResourceFieldCount());

		if (getActual() + potentialResourceIncrease <= getCapacity()) {
			actualResourceIncrease = potentialResourceIncrease;
		} else {
			actualResourceIncrease = getCapacity() - getActual();
		}

		resources += actualResourceIncrease;

		return actualResourceIncrease;
	}

	//	public int increase(Collection<Inhabitant> members) {
	//		int actualIncrease = 0;
	//
	//		synchronized (members) {
	//
	//			for (Inhabitant inhabitant : members) {
	//				// System.out.println("increase from member: " + inhabitant.getName());
	//
	//				int potentialIncrease = inhabitant.getSkill(PRODUCING);
	//				// System.out.println("  potential increase : " + potentialIncrease);
	//
	//				actualIncrease += increaseIfPossible(potentialIncrease);
	//				// System.out.println("  actual increase : " + actualIncrease);
	//
	//				if (actualIncrease > 0) {
	//					inhabitant.increaseXp(PRODUCING);
	//					// System.out.println("  xp increased :)");
	//				} else {
	//					// System.out.println("  xp not increased :(");
	//				}
	//			}
	//		}
	//
	//		return actualIncrease;
	//	}

	public int getMaxCapacity() {
		return resourceFieldCount;
	}

	public int getResourceFieldCount() {
		return resourceFieldCount / 10;
	}

	public int increaseResourceFieldCount() {
		resourceFieldCount++;

		return getResourceFieldCount();
	}

}
