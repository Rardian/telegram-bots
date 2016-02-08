package de.rardian.telegram.bot.castle.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import de.rardian.telegram.bot.castle.model.ResourcesManager.TYPE;

@Entity
@Table(name = "resourcedepot")
public class ResourceDepot {

	@Id
	@Enumerated(EnumType.STRING)
	private TYPE type;

	/** actual amount of resources */
	private int amount;
	/** dictates maximum amount of resources */
	private int capacity;
	/** dictates possible maximum increase, stored with times of 10 */
	private int resourceFieldCount;

	// public ResourceDepot(TYPE type, int initialResources, int
	// resourcesCapacity, int resourceFieldCount) {
	// this.type = type;
	// amount = initialResources;
	// capacity = resourcesCapacity;
	// this.resourceFieldCount = resourceFieldCount * 10;
	// }

	public ResourceDepot setType(TYPE type) {
		this.type = type;
		return this;
	}

	public ResourceDepot setAmount(int amount) {
		this.amount = amount;
		return this;
	}

	public ResourceDepot setCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public ResourceDepot setResourceFieldCount(int resourceFieldCount) {
		this.resourceFieldCount = resourceFieldCount * 10;
		return this;
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

	/**
	 * Increase {@link #amount} of resources if the {@link #capacity} allows it
	 * depending on {@link #resourceFieldCount}.
	 */
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
