package de.rardian.telegram.bot.castle.commands.actions;

import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.castle.facilities.CastleFacility;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.command.action.Action;
import de.rardian.telegram.bot.command.action.SendsAnswer;
import de.rardian.telegram.bot.communication.MessageReply;

public class SetInhabitantToWorkAction implements Action, CastleAware, InhabitantAware, SendsAnswer {
	private Castle castle;
	private MessageReply reply;
	private Inhabitant inhabitant;
	private CastleFacility.CATEGORY category;

	public SetInhabitantToWorkAction(CastleFacility.CATEGORY category) {
		this.category = category;
	}

	@Override
	public void setMessageReply(MessageReply reply) {
		this.reply = reply;
	}

	@Override
	public void setCastle(Castle castle) {
		this.castle = castle;
	}

	@Override
	public void setInhabitant(Inhabitant inhabitant) {
		this.inhabitant = inhabitant;
	}

	@Override
	public void execute() {
		try {
			castle.addWorkerFor(category, inhabitant);
			reply.answer("Du bist jetzt Teil von " + category, null);
		} catch (AlreadyAddedException e) {
			reply.answer("Du bist bereits Teil von " + category, null);
		}
	}

}
