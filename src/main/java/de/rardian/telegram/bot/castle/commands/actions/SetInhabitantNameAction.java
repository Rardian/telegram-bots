package de.rardian.telegram.bot.castle.commands.actions;

import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.command.action.Action;
import de.rardian.telegram.bot.command.action.SendsAnswer;
import de.rardian.telegram.bot.communication.MessageReply;

public class SetInhabitantNameAction implements Action, InhabitantAware, SendsAnswer {

	private String name;
	private Inhabitant inhabitant;
	private MessageReply reply;

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
		// TODO save name, therefore inject InhabitantRepository to ActionInitializer
		reply.answer("Man kennt dich nun als " + name, null);
	}

}
