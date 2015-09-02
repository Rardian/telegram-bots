package de.rardian.telegram.bot.castle.commands.actions;

import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.command.Action;
import de.rardian.telegram.bot.command.MessageReply;
import de.rardian.telegram.bot.command.SendsAnswer;
import de.rardian.telegram.bot.command.UserAware;
import de.rardian.telegram.bot.model.User;

public class MoveInhabitantToProductionAction implements Action, CastleAware, UserAware, SendsAnswer {
	private Castle castle;
	private User user;
	private MessageReply reply;

	@Override
	public void setMessageReply(MessageReply reply) {
		this.reply = reply;
	}

	@Override
	public void setCastle(Castle castle) {
		this.castle = castle;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void execute() {
		try {
			castle.addProducer(user);
			reply.answer("Du bist jetzt Teil der Produktionsmannschaft.", null);
		} catch (AlreadyAddedException e) {
			reply.answer("Du bist bereits Teil der Produktionsmannschaft.", null);
		}
	}
}
