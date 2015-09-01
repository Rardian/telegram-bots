package de.rardian.telegram.bot.castle.model;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.Validate;

/**
 * In order to work {@link ProductionController} needs to be started via
 * <code>controller.start()</code>. This won't work unless a castle is set via
 * <code>controller.forCastle()</code>. The API is fluent, so you can write
 * something like
 * <code>new ProductionController().forCastle(castle).start()</code>.
 */
public class ProductionController implements Runnable {

	private Castle castle;
	private ScheduledExecutorService executorService;

	/** Start the production queue, if needed. */
	public void start() {
		Validate.notNull(castle, "a castle needs to be set");

		if (executorService == null) {
			executorService = Executors.newSingleThreadScheduledExecutor();
			executorService.scheduleAtFixedRate(this, 10, 5, TimeUnit.SECONDS);
		}
	}

	@Override
	public void run() {
		ProductionResult result = castle.produce();
		System.out.println(result);

		// TODO Listener über result informieren

	}

	public ProductionController forCastle(Castle castle) {
		this.castle = castle;
		return this;
	}

}
