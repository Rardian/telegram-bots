package de.rardian.telegram.bot.castle.model;

import java.util.Collection;

public class Project {

	private Collection<ResourceAmount> neededResources;
	private String name;

	public Project(String name, Collection<ResourceAmount> neededResources) {
		this.name = name;
		this.neededResources = neededResources;
	}

	public String getName() {
		return name;
	}

	public Collection<ResourceAmount> getNeededResources() {
		return neededResources;
	}

}
