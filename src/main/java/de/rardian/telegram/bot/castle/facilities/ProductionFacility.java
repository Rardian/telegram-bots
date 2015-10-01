package de.rardian.telegram.bot.castle.facilities;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.Validate;

import com.google.common.annotations.VisibleForTesting;

import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Resources;

public class ProductionFacility extends BasicFacility {

	private ScheduledExecutorService executorService;

	public ProductionFacility(Castle castle, Resources resources) {
		super(castle, resources);
	}

	@Override
	public CastleFacilityCategories getCategory() {
		return CastleFacilityCategories.PRODUCING;
	}

	protected void start() {
		Validate.notNull(castle, "a castle needs to be set");

		if (executorService == null) {
			executorService = Executors.newSingleThreadScheduledExecutor();
			executorService.scheduleAtFixedRate(this, 10, 1, TimeUnit.SECONDS);
		}
	}

	@Override
	public ProcessResult process() {
		//		int potentialResourceIncrease = getPotentialIncrease();

		//		int actualResourceIncrease = resources.increaseIfPossible(potentialResourceIncrease);

		int actualResourceIncrease = resources.increase(members);

		// TODO Nachricht an Interessierte senden, wenn Lager voll
		// TODO Listener aus Oberklasse protected machen oder den Nachricht-Code dorthin verschieben

		return new ProductionResult(actualResourceIncrease, resources.getActual());
	}

	@Override
	protected int getPotentialIncrease() {
		int potentialIncrease = 0;
		//		for (Inhabitant inhabitant : members) {
		//			potentialIncrease += inhabitant.getProductionSkill();
		//			inhabitant.increaseXp(category);
		//		}
		return potentialIncrease;
	}

	@VisibleForTesting
	ScheduledExecutorService getExecutorService() {
		return executorService;
	}
}
