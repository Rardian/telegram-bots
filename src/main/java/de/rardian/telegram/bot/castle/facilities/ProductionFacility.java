package de.rardian.telegram.bot.castle.facilities;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.Validate;

import com.google.common.annotations.VisibleForTesting;

import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Resources;

public class ProductionFacility extends BasicFacility implements Runnable {

	private ScheduledExecutorService executorService;

	public ProductionFacility(Castle castle, Resources resources) {
		super(castle, resources);
	}

	@Override
	public void run() {
		ProcessResult result = process();
		System.out.println(result);

		// TODO Listener über result informieren
	}

	protected void start() {
		Validate.notNull(castle, "a castle needs to be set");

		if (executorService == null) {
			executorService = Executors.newSingleThreadScheduledExecutor();
			executorService.scheduleAtFixedRate(this, 10, 5, TimeUnit.SECONDS);
		}
	}

	@Override
	public ProcessResult process() {
		int potentialResourceIncrease = getMemberCount();

		int actualResourceIncrease = resources.increaseIfPossible(potentialResourceIncrease);

		return new ProductionResult(actualResourceIncrease, resources.getActual());
	}

	@VisibleForTesting
	ScheduledExecutorService getExecutorService() {
		return executorService;
	}
}
