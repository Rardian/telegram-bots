package de.rardian.telegram.bot.rardian.commands.actions;

import de.rardian.telegram.bot.command.Action;
import de.rardian.telegram.bot.command.MessageReply;
import de.rardian.telegram.bot.command.SendsAnswer;
import de.rardian.telegram.bot.model.Castle;

public class CastleStatusAction implements Action, CastleAware, SendsAnswer {

	private Castle castle;
	private MessageReply reply;

	@Override
	public void setCastle(Castle castle) {
		this.castle = castle;

	}

	@Override
	public void setMessageReply(MessageReply reply) {
		this.reply = reply;
	}

	@Override
	public void execute() {
		reply.answer(castle.getStatusAsString(), null);
	}

}
