package de.rardian.telegram.bot.castle.model;

public class Resources {

	public enum TYPE {
		WOOD, STONE, IRON
	};

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

		potentialResourceIncrease = Math.min(potentialResourceIncrease, getResourceFieldCount());

		if (getActual() + potentialResourceIncrease <= getCapacity()) {
			actualResourceIncrease = potentialResourceIncrease;
		} else {
			actualResourceIncrease = getCapacity() - getActual();
		}

		resources += actualResourceIncrease;

		return actualResourceIncrease;
	}

	public int getMaxCapacity() {
		return (getResourceFieldCount() + 1) * 5;
	}

	public int getResourceFieldCount() {
		return resourceFieldCount / 10;
	}

	public int increaseResourceFieldCount() {
		resourceFieldCount++;

		return getResourceFieldCount();
	}

}
