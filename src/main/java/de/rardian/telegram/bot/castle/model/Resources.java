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

	public void increase(int actualResourceIncrease) {
		resources += actualResourceIncrease;
	}

}
