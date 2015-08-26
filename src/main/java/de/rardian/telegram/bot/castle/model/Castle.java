package de.rardian.telegram.bot.castle.model;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.google.common.annotations.VisibleForTesting;

import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.manage.UserManager;
import de.rardian.telegram.bot.model.User;

public class Castle {
	private Collection<User> inhabitants = new ArrayList<>();
	private Collection<User> producers = new ArrayList<>();

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
				+ ")\n";
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
	}

	@VisibleForTesting
	int getProducerCount() {
		return producers.size();
	}

	public void addInhabitant(User user) {
		inhabitants.add(user);
		// don't add users twice (actually ensured by UserManager)
		// use Inhabitant instead of User
	}

}
