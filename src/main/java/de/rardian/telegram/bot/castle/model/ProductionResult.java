package de.rardian.telegram.bot.castle.model;

public class ProductionResult {

	private int productionIncrease;

	public ProductionResult(int productionIncrease) {
		this.productionIncrease = productionIncrease;
	}

	public int getProductionUnits() {
		return productionIncrease;
	}

	@Override
	public String toString() {
		return productionIncrease + " Einheiten produziert";
	}
}
