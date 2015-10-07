package de.rardian.telegram.bot.castle.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import de.rardian.telegram.bot.castle.facilities.CastleFacilityCategories;
import de.rardian.telegram.bot.model.User;

public class Inhabitant implements Comparable<Inhabitant> {
	private User representative;

	// Personality
	private String name;
	private String appearance;

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
		return ObjectUtils.compare(getUser(), o.getUser());
	}

	private User getUser() {
		return representative;
	}

	public String getStatusAsString() {
		String skillsAsString = StringUtils.join(getSkills().values(), ",\n  ");
		return getName() + " geht's gut.\n  "//
				+ skillsAsString;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		if (name == null) {
			return representative.getFirstName();
		}
		return name;
	}

	@Deprecated
	public int getProductionSkill() {
		return getSkills().get(CastleFacilityCategories.PRODUCING).level();
	}

	public int getSkill(CastleFacilityCategories skill) {
		return getSkills().get(skill).level();
	}

	public boolean increaseXp(CastleFacilityCategories category) {
		boolean levelup = getSkills().get(category).increase();
		//		System.out.println(getStatusAsString());
		if (levelup) {
			System.out.println("*** " + getName() + " steigt in " + category + " auf ***");
		}
		return levelup;
	}

	@Deprecated
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
