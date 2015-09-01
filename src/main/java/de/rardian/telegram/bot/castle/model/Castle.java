package de.rardian.telegram.bot.castle.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang3.StringUtils;

import com.google.common.annotations.VisibleForTesting;

import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.manage.UserManager;
import de.rardian.telegram.bot.model.User;

public class Castle {
	private Collection<User> inhabitants = Collections.synchronizedList(new ArrayList<>());
	private Collection<User> producers = Collections.synchronizedList(new ArrayList<>());
	private ProductionController production;
	private int resources = 0;

	public void setProduction(ProductionController production) {
		this.production = production;
	}

	public String getStatusAsString() {
		String status = "Die Burg ist in gutem Zustand.\n"//
				+ "Bewohner: "//
				+ inhabitants.size()//
				+ " ("//
				+ StringUtils.join(inhabitants.toArray(), ", ")//
				+ ")\n"//
				+ "Produzenten: "//
				+ producers.size()//
				+ " ("//
				+ StringUtils.join(producers.toArray(), ", ")//
				+ ")\n"//
				+ "Ressourcen: "//
				+ resources;
		return status;
	}

	public void addProducer(User user) throws AlreadyAddedException {
		if (UserManager.collectionContainsUser(producers, user)) {
			throw new AlreadyAddedException("user is already producing");
		}
		producers.add(user);
		// use Inhabitant instead of User
		// set status in Inhabitant object
		// remove from other assemblies
		getProduction().start();
	}

	@VisibleForTesting
	int getProducerCount() {
		return producers.size();
	}

	public void addInhabitant(User user) {
		inhabitants.add(user);
		// TODO don't add users twice (actually ensured by UserManager)
		// use Inhabitant instead of User
	}

	private ProductionController getProduction() {
		if (production == null) {
			production = new ProductionController().forCastle(this);
		}
		return production;
	}

	public ProductionResult produce() {
		int resourceIncrease = getProducerCount();

		resources += resourceIncrease;

		return new ProductionResult(resourceIncrease);
	}

}
