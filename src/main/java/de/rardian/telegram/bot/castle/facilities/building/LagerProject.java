package de.rardian.telegram.bot.castle.facilities.building;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import de.rardian.telegram.bot.castle.model.ResourceAmount;
import de.rardian.telegram.bot.castle.model.ResourcesManager;
import de.rardian.telegram.bot.castle.model.ResourcesManager.TYPE;

/* Als Component erstellen und als Collection von Typ Blueprint in ProjectManager injizieren, der die Brücke zwischen Blueprint und Project darstellt? */
@Component
@Configuration
public class LagerProject implements Blueprint {

	public static final String ID = "LAGER";
	public static final String NAME = "Materiallager";

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public Collection<ResourceAmount> getResourcesNeeded(int level) {
		return Arrays.asList(//
				ResourceAmount.create(TYPE.WOOD, level * 2 + 10));
	}

	@Override
	public void executeResult(ResourcesManager resourcesManager) {
		for (ResourcesManager.TYPE resourceType : ResourcesManager.TYPE.values()) {
			resourcesManager.increaseCapacity(resourceType);
		}
	}

}
