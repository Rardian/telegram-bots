package de.rardian.telegram.bot.castle.model;

import de.rardian.telegram.bot.castle.facilities.CastleFacilityCategories;

public class Skill {
	/**
	 * defines how fast one levels up by multiplying the needed xp for the next
	 * level.
	 */
	private static final int levelDistanceFactor = 15;
	private CastleFacilityCategories category;
	private int level;
	private int xp;

	public Skill(CastleFacilityCategories category) {
		this.category = category;
		level = 1;
		xp = 0;
	}

	public int level() {
		return level;
	}

	public boolean increase() {
		boolean levelup = false;

		xp++;

		if (xp >= level * levelDistanceFactor) {
			xp = 0;
			level++;
			levelup = true;
		}

		return levelup;
	}

	public String toString() {
		return category + " (Level=" + level + ", XP=" + xp + ")";
	}
}
