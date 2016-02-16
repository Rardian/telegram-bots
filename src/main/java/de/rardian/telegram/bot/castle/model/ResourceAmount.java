package de.rardian.telegram.bot.castle.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import de.rardian.telegram.bot.castle.model.ResourcesManager.TYPE;

@Entity
@Table(name = "resourceamounts")
public class ResourceAmount {

	@Id
	@GeneratedValue
	private long id;
	private TYPE type;
	private int amount;

	public static ResourceAmount create(TYPE type, int amount) {
		ResourceAmount resourceAmount = new ResourceAmount();

		resourceAmount.setType(type);
		resourceAmount.setAmount(amount);

		return resourceAmount;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
