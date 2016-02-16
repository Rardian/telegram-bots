package de.rardian.telegram.bot.domain;

import org.springframework.stereotype.Repository;

import de.rardian.telegram.bot.castle.model.Project;

@Repository
public interface ProjectRepository extends BaseRepository<Project, Long> {
	long count();
}
