package de.rardian.telegram.bot.castle.commands.actions;

import org.springframework.beans.factory.annotation.Autowired;

import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.command.action.Action;
import de.rardian.telegram.bot.command.action.SendsAnswer;
import de.rardian.telegram.bot.communication.MessageReply;
import de.rardian.telegram.bot.manage.UserManager;

public class SetInhabitantNameAction implements Action, InhabitantAware, SendsAnswer, CastleAware {

	private String name;
	private Inhabitant inhabitant;
	private MessageReply reply;
	private Castle castle;

	//	@Autowired
	//	private InhabitantRepository inhabitantRepository;
	@Autowired
	private UserManager userManager;

	public SetInhabitantNameAction(String name) {
		this.name = name;
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
		userManager.updateInhabitant(inhabitant);
		//		inhabitantRepository.save(inhabitant);
		reply.answer("Man kennt dich nun als " + name, null);
	}

	@Override
	public void setCastle(Castle castle) {
		this.castle = castle;
	}

}
