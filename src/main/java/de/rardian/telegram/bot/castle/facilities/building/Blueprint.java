package de.rardian.telegram.bot.castle.facilities.building;

import java.util.Collection;

import de.rardian.telegram.bot.castle.model.ResourceAmount;
import de.rardian.telegram.bot.castle.model.ResourcesManager;

public interface Blueprint {
	public String getId();

	public String getName();

	public Collection<ResourceAmount> getResourcesNeeded(int level);

	public void executeResult(ResourcesManager resourcesManager);
}
