package de.rardian.telegram.bot.castle.facilities;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.Validate;

import com.google.common.annotations.VisibleForTesting;

import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.castle.model.ResourcesManager;
import de.rardian.telegram.bot.model.Bot;

public class ProductionFacility extends BasicFacility {

	public static final String RESULT_RESOURCES_ACTUAL = "RESULT_RESOURCES";
	public static final String RESULT_RESOURCES_INCREASE = "RESULT_RESOURCES_INCREASE";

	private ScheduledExecutorService executorService;
	private ResourcesManager.TYPE resourceType;
	private CATEGORY productionSubCategory;

	public ProductionFacility(Bot bot, Castle castle, ResourcesManager resources, ResourcesManager.TYPE resourceToProduce,
			CATEGORY productionSubCategory) {
		super(bot, castle, resources);
		resourceType = resourceToProduce;
		this.productionSubCategory = productionSubCategory;
	}

	@Override
	public CATEGORY getCategory() {
		return productionSubCategory;
	}

	protected void start() {
		Validate.notNull(castle, "a castle needs to be set");

		if (executorService == null) {
			executorService = Executors.newSingleThreadScheduledExecutor();
			executorService.scheduleAtFixedRate(this, 5, 15, TimeUnit.SECONDS);
			//			executorService.scheduleAtFixedRate(this, 1, 1, TimeUnit.SECONDS);
		}
	}

	@Override
	public ProcessResult2 process() {
		ProcessResult2 resultContainer = new ProcessResult2();
		int actualResourceIncrease = 0;

		synchronized (members) {

			for (Inhabitant inhabitant : members) {
				// System.out.println("increase from member: " + inhabitant.getName());

				int potentialIncrease = inhabitant.getSkill(productionSubCategory);
				// System.out.println("  potential increase : " + potentialIncrease);

				actualResourceIncrease += resources.increaseIfPossible(resourceType, potentialIncrease);
				// System.out.println("  actual increase : " + actualIncrease);

				if (actualResourceIncrease > 0) {
					super.increaseInhabitantXp(inhabitant, productionSubCategory, resultContainer);
					// System.out.println("  xp increased :)");
				} else {
					// System.out.println("  xp not increased :(");
				}
			}
		}

		// TODO Nachricht an Interessierte senden, wenn Lager voll
		// TODO Listener aus Oberklasse protected machen oder den Nachricht-Code dorthin verschieben

		resultContainer.addResultInteger(RESULT_RESOURCES_ACTUAL, Integer.valueOf(resources.getAmount(resourceType)));
		resultContainer.addResultInteger(RESULT_RESOURCES_INCREASE, Integer.valueOf(actualResourceIncrease));
		return resultContainer;
	}

	@Override
	protected int getPotentialIncrease() {
		int potentialIncrease = 0;
		// for (Inhabitant inhabitant : members) {
		// potentialIncrease += inhabitant.getProductionSkill();
		// inhabitant.increaseXp(category);
		// }
		return potentialIncrease;
	}

	@VisibleForTesting
	ScheduledExecutorService getExecutorService() {
		return executorService;
	}
}
