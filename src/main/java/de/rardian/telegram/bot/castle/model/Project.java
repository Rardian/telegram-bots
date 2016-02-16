package de.rardian.telegram.bot.castle.model;

import static javax.persistence.FetchType.EAGER;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "projects")
public class Project {

	@Id
	@GeneratedValue
	private long id;
	private String blueprint;
	private String name;

	@OneToMany(fetch = EAGER)
	@JoinColumn(name = "project_id")
	private Collection<ResourceAmount> resourcesToFinish;

	public String getBlueprint() {
		return blueprint;
	}

	public void setBlueprint(String blueprint) {
		this.blueprint = blueprint;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<ResourceAmount> getResourcesToFinish() {
		return Collections.unmodifiableCollection(resourcesToFinish);
	}

	public void setResourcesToFinish(Collection<ResourceAmount> resourcesToFinish) {
		this.resourcesToFinish = resourcesToFinish;
	}

}
