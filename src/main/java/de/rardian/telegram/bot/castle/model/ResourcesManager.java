package de.rardian.telegram.bot.castle.model;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class ResourcesManager {

	public enum TYPE {
		WOOD, STONE, IRON
	};

	private Map<TYPE, ResourceDepot> resources = Collections.synchronizedMap(new EnumMap<TYPE, ResourceDepot>(TYPE.class));

	public ResourcesManager(int initialResources, int initialCapacity, int resourceFieldCount) {
		for (TYPE type : TYPE.values()) {
			resources.put(type, new ResourceDepot(type, initialResources, initialCapacity, resourceFieldCount));
		}
	}

	public int getAmount(TYPE type) {
		return resources.get(type).getActual();
	}

	public void reduce(TYPE type, int reducement) {
		resources.get(type).reduce(reducement);
	}

	public int getCapacity(TYPE type) {
		return resources.get(type).getCapacity();
	}

	public void increaseCapacity(TYPE type) {
		resources.get(type).increaseCapacity();
	}

	public int increaseIfPossible(TYPE type, int potentialIncrease) {
		return resources.get(type).increaseIfPossible(potentialIncrease);
	}

	public int getMaxCapacity(TYPE type) {
		return resources.get(type).getMaxCapacity();
	}

	public int getResourceFieldCount(TYPE type) {
		return resources.get(type).getResourceFieldCount();
	}

	public int increaseResourceFieldCount(TYPE type) {
		return resources.get(type).increaseResourceFieldCount();
	}

}
