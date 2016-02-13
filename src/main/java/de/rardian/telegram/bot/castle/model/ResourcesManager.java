package de.rardian.telegram.bot.castle.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import de.rardian.telegram.bot.domain.ResourceDepotRepository;

@Component
public class ResourcesManager {

	@Autowired
	private ResourceDepotRepository resourceDepotRepository;
	@Autowired
	private AutowireCapableBeanFactory beanFactory;

	private int initialResources;
	private int initialCapacity;
	private int resourceFieldCount;

	public enum TYPE {
		WOOD, STONE, IRON
	};

	/**
	 * object needs to be initialized with {@link #initialize(int, int, int)}
	 */
	public ResourcesManager() {
		// Spring Component
	}

	public ResourcesManager initialize(int initialResources, int initialCapacity, int resourceFieldCount) {
		this.initialResources = initialResources;
		this.initialCapacity = initialCapacity;
		this.resourceFieldCount = resourceFieldCount;
		return this;
	}

	private ResourceDepot getResourceDepot(TYPE type) {
		ResourceDepot depot = new ResourceDepot()//
				.setType(type)//
				.setAmount(initialResources)//
				.setCapacity(initialCapacity)//
				.setResourceFieldCount(resourceFieldCount);
		ResourceDepotSupplier resourceDepotGenerator = new ResourceDepotSupplier();
		resourceDepotGenerator.setResourceDepot(depot);
		beanFactory.autowireBean(resourceDepotGenerator);

		return resourceDepotRepository.findOne(type).orElseGet(resourceDepotGenerator);
	}

	public int getAmount(TYPE type) {
		return getResourceDepot(type).getActual();
		// return resources.get(type).getActual();
	}

	public void reduce(TYPE type, int reducement) {
		ResourceDepot depot = getResourceDepot(type);

		depot.reduce(reducement);
		resourceDepotRepository.save(depot);
	}

	public int getCapacity(TYPE type) {
		return getResourceDepot(type).getCapacity();
	}

	public void increaseCapacity(TYPE type) {
		// resources.get(type).increaseCapacity();
		ResourceDepot depot = getResourceDepot(type);

		depot.increaseCapacity();
		resourceDepotRepository.save(depot);
	}

	public int increaseIfPossible(TYPE type, int potentialIncrease) {
		// return resources.get(type).increaseIfPossible(potentialIncrease);
		ResourceDepot depot = getResourceDepot(type);

		int actualIncrease = depot.increaseIfPossible(potentialIncrease);
		resourceDepotRepository.save(depot);

		return actualIncrease;
	}

	public int getMaxCapacity(TYPE type) {
		return getResourceDepot(type).getMaxCapacity();
	}

	public int getResourceFieldCount(TYPE type) {
		return getResourceDepot(type).getResourceFieldCount();
	}

	public int increaseResourceFieldCount(TYPE type) {
		ResourceDepot depot = getResourceDepot(type);

		int increase = depot.increaseResourceFieldCount();
		resourceDepotRepository.save(depot);

		return increase;
	}
}
