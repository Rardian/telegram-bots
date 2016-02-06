package de.rardian.telegram.bot.command.action;

import de.rardian.telegram.bot.model.InhabitantRepository;

public interface InhabitantRepositoryAware {

	public void setInhabitantRepository(InhabitantRepository inhabitantRepository);

}
