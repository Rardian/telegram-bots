package de.rardian.telegram.bot.castle.model;

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

}
