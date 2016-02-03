package de.rardian.telegram.bot.castle.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import de.rardian.telegram.bot.castle.facilities.CastleFacility;
import de.rardian.telegram.bot.model.User;

@Entity
@Table(name = "inhabitants")
public class Inhabitant implements Comparable<Inhabitant> {

	@Id
	@GeneratedValue
	private long id;
	@OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
	private User user;
	
	// Personality
	private String name;
	private transient String appearance;

	// Skills
	/**
	 * influences the count and increase rate of followers and relationships to other players
	 */
	private transient int leadership;
	/**
	 * influences the chance to discover secrets (either on quests or on building or producing)
	 */
	private transient int knowledge;

	private transient Map<CastleFacility.CATEGORY, Skill> skills;

	// Resources
	/**
	 * the amount of followers helping you with a task, increases with focus of specific tasks
	 */
	private transient int followers;
	/** the amount of damage a character can take before they get unconscious */
	private transient int health;

	private transient Map<CharacterClass, SkillSet> classes;

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int compareTo(Inhabitant o) {
		return ObjectUtils.compare(getUser(), o.getUser());
	}

	private User getUser() {
		return user;
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
			return user.getFirstName();
		}
		return name;
	}

	public int getSkill(CastleFacility.CATEGORY skill) {
		return getSkills().get(skill).level();
	}

	public boolean increaseXp(CastleFacility.CATEGORY category) {
		boolean levelup = getSkills().get(category).increase();
		//		System.out.println(getStatusAsString());
		if (levelup) {
			System.out.println("*** " + getName() + " steigt in " + category + " auf ***");
		}
		return levelup;
	}

	private Map<CastleFacility.CATEGORY, Skill> getSkills() {
		if (skills == null) {
			skills = new HashMap<>();
			for (CastleFacility.CATEGORY category : CastleFacility.CATEGORY.values()) {
				skills.put(category, new Skill(category));
			}
		}
		return skills;
	}
}
