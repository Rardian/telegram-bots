package de.rardian.telegram.bot.castle.commands.actions;

import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.command.action.Action;
import de.rardian.telegram.bot.command.action.InhabitantRepositoryAware;
import de.rardian.telegram.bot.command.action.SendsAnswer;
import de.rardian.telegram.bot.communication.MessageReply;
import de.rardian.telegram.bot.model.InhabitantRepository;

public class SetInhabitantNameAction implements Action, InhabitantAware, InhabitantRepositoryAware, SendsAnswer {

	private String name;
	private Inhabitant inhabitant;
	private MessageReply reply;

	private InhabitantRepository inhabitantRepository;

	public void setInhabitantRepository(InhabitantRepository inhabitantRepository) {
		this.inhabitantRepository = inhabitantRepository;
	}

	public Action withNewName(String newName) {
		name = newName;
		return this;
	}

	@Override
	public void setInhabitant(Inhabitant inhabitant) {
		this.inhabitant = inhabitant;

	}

	@Override
	public void setMessageReply(MessageReply reply) {
		this.reply = reply;
	}

	@Override
	public void execute() {
		inhabitant.setName(name);
		inhabitantRepository.save(inhabitant);
		reply.answer("Man kennt dich nun als " + name, null);
	}


}
