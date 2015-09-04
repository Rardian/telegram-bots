package de.rardian.telegram.bot.castle.facilities;

public class BuildingResult implements ProcessResult {

	private int buildingProgress;

	public BuildingResult(int buildingProgress) {
		this.buildingProgress = buildingProgress;
	}

	public int getBuildingProgress() {
		return buildingProgress;
	}

	@Override
	public String toString() {
		return buildingProgress + " Einheiten verbaut.";
	}

}
