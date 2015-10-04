package de.rardian.telegram.bot.castle.facilities;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.Validate;

import com.google.common.annotations.VisibleForTesting;

import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Resources;
import de.rardian.telegram.bot.model.Bot;

public class ProductionFacility extends BasicFacility {

	public static final String RESULT_RESOURCES_ACTUAL = "RESULT_RESOURCES";
	public static final String RESULT_RESOURCES_INCREASE = "RESULT_RESOURCES_INCREASE";

	private ScheduledExecutorService executorService;

	public ProductionFacility(Castle castle, Resources resources, Bot bot) {
		super(castle, resources, bot);
	}

	@Override
	public CastleFacilityCategories getCategory() {
		return CastleFacilityCategories.PRODUCING;
	}

	protected void start() {
		Validate.notNull(castle, "a castle needs to be set");

		if (executorService == null) {
			executorService = Executors.newSingleThreadScheduledExecutor();
			executorService.scheduleAtFixedRate(this, 10, 5, TimeUnit.SECONDS);
		}
	}

	@Override
	public ProcessResult2 process() {
		//		int potentialResourceIncrease = getPotentialIncrease();

		//		int actualResourceIncrease = resources.increaseIfPossible(potentialResourceIncrease);

		int actualResourceIncrease = resources.increase(members);

		// TODO Nachricht an Interessierte senden, wenn Lager voll
		// TODO Listener aus Oberklasse protected machen oder den Nachricht-Code dorthin verschieben

		ProcessResult2 result = new ProcessResult2();
		result.addResultInteger(RESULT_RESOURCES_ACTUAL, Integer.valueOf(resources.getActual()));
		result.addResultInteger(RESULT_RESOURCES_INCREASE, Integer.valueOf(actualResourceIncrease));
		return result;
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
