package de.rardian.telegram.bot.castle.model;

import de.rardian.telegram.bot.castle.model.ResourcesManager.TYPE;

public class ResourceAmount {

	private TYPE type;
	private int amount;

	public ResourceAmount(TYPE type, int amount) {
		this.type = type;
		this.amount = amount;
	}
}
