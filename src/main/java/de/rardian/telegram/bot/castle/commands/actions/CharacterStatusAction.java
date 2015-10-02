package de.rardian.telegram.bot.castle.commands.actions;

import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.command.action.Action;
import de.rardian.telegram.bot.command.action.SendsAnswer;
import de.rardian.telegram.bot.communication.MessageReply;

public class CharacterStatusAction implements Action, InhabitantAware, SendsAnswer {

	private MessageReply reply;
	private Inhabitant inhabitant;

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
		reply.answer(inhabitant.getStatusAsString(), null);
	}

}
