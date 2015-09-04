package de.rardian.telegram.bot.castle.facilities;

public class ProductionResult implements ProcessResult {

	private int productionIncrease;
	private int resources;

	public ProductionResult(int productionIncrease, int resources) {
		this.productionIncrease = productionIncrease;
		this.resources = resources;
	}

	public int getProductionIncrease() {
		return productionIncrease;
	}

	public int getResources() {
		return resources;
	}

	@Override
	public String toString() {
		return productionIncrease + " Einheiten produziert";
	}
}
