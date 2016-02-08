package de.rardian.telegram.bot.castle.model;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.rardian.telegram.bot.domain.ResourceDepotRepository;

@Component
public class ResourceDepotSupplier implements Supplier<ResourceDepot> {

	@Autowired
	private ResourceDepotRepository repository;

	private ResourceDepot depot;

	ResourceDepotSupplier() {
		// Default Constructor for use as Supplier
	}

	public void setResourceDepot(ResourceDepot depot) {
		this.depot = depot;
	}

	@Override
	public ResourceDepot get() {
		depot = repository.save(depot);
		return depot;
	}

}