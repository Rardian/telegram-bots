package de.rardian.telegram.bot.command;

import de.rardian.telegram.bot.castle.commands.actions.CastleAware;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.model.User;

public class UserMovesInAction implements Action, SendsAnswer, CastleAware, UserAware {

	private MessageReply reply;
	private Castle castle;
	private User user;

	@Override
	public void setUser(User user) {
		this.user = user;
	}

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
		reply.answer("Willkommen, neuer Burgbewohner. Tippe /help", null);
		castle.addInhabitant(user);
	}

}