package de.rardian.telegram.bot.castle.model;

import de.rardian.telegram.bot.castle.model.ResourcesManager.TYPE;

public class ResourceDepot {

	private ResourcesManager.TYPE type;

	/** actual amount of resources */
	private int amount;
	/** dictates maximum amount of resources */
	private int capacity;
	/** dictates possible maximum increase, stored with times of 10 */
	private int resourceFieldCount;

	public ResourceDepot(TYPE type, int initialResources, int resourcesCapacity, int resourceFieldCount) {
		this.type = type;
		amount = initialResources;
		capacity = resourcesCapacity;
		this.resourceFieldCount = resourceFieldCount * 10;
	}

	public int getActual() {
		return amount;
	}

	public void reduce(int reducement) {
		// FIXME ensure non-negative values
		amount -= reducement;
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

		amount += actualResourceIncrease;

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

	@Override
	public String toString() {
		return type + " " + amount;
	}
}
