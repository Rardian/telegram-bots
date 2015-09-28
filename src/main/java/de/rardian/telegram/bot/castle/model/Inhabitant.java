package de.rardian.telegram.bot.castle.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import de.rardian.telegram.bot.castle.facilities.CastleFacilityCategories;
import de.rardian.telegram.bot.model.User;

public class Inhabitant implements Comparable<Inhabitant> {
	private User representative;

	// Level
	/**
	 * influences the amount of xp needed for advancement, not shown to players
	 */
	//	private int level = 1;
	/**
	 * gather level * 100 XP to gain a level and thus advance in a skill of your
	 * choice
	 */
	//	private int xp = 0;

	// Skills
	/**
	 * influences the count and increase rate of followers and relationships to
	 * other players
	 */
	private int leadership;
	/**
	 * influences the chance to discover secrets (either on quests or on
	 * building or producing)
	 */
	private int knowledge;

	private Map<CastleFacilityCategories, Skill> skills;

	// Resources
	/**
	 * the amount of followers helping you with a task, increases with focus of
	 * specific tasks
	 */
	private int followers;
	/** the amount of damage a character can take before they get unconscious */
	private int health;

	private Map<CharacterClass, SkillSet> classes;

	public void setUser(User user) {
		representative = user;
	}

	@Override
	public int compareTo(Inhabitant o) {
		return ObjectUtils.compare(representative, o.getUser());
	}

	private User getUser() {
		return representative;
	}

	public String getStatusAsString() {
		String skillsAsString = StringUtils.join(getSkills().values(), ",\n  ");
		return "Deinem Bewohner geht's gut.\n  "//
				+ skillsAsString;
	}

	public String getName() {
		return representative.getFirstName();
	}

	public int getProductionSkill() {
		return getSkills().get(CastleFacilityCategories.PRODUCING).level();
	}

	public int getSkill(CastleFacilityCategories skill) {
		return getSkills().get(skill).level();
	}

	public void increaseXp(CastleFacilityCategories category) {
		boolean levelup = getSkills().get(category).increase();
		System.out.println(getStatusAsString());
		if (levelup) {
			System.out.println("*** Levelaufstieg " + getName() + " ***");
		}
	}

	public int getBuildingSkill() {
		return getSkills().get(CastleFacilityCategories.BUILDING).level();
	}

	private Map<CastleFacilityCategories, Skill> getSkills() {
		if (skills == null) {
			skills = new HashMap<>();
			for (CastleFacilityCategories category : CastleFacilityCategories.values()) {
				skills.put(category, new Skill(category));
			}
		}
		return skills;
	}
}
