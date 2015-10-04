package de.rardian.telegram.bot.command.action;

import de.rardian.telegram.bot.castle.commands.actions.CastleAware;
import de.rardian.telegram.bot.castle.commands.actions.InhabitantAware;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.communication.MessageReply;
import de.rardian.telegram.bot.communication.MessageSender;
import de.rardian.telegram.bot.model.Bot;
import de.rardian.telegram.bot.model.Message;
import de.rardian.telegram.bot.model.User;

public class ActionInitializer implements CastleAware, BotAware {

	private Bot bot;
	private Castle castle;

	@Override
	public void setBot(Bot bot) {
		this.bot = bot;
	}

	@Override
	public void setCastle(Castle castle) {
		this.castle = castle;
	}

	public void injectActionDependencies(Action action, Message message) {

		if (action instanceof CastleAware) {
			((CastleAware) action).setCastle(castle);
		}
		if (action instanceof SendsAnswer) {
			((SendsAnswer) action).setMessageReply(getMessageReply(message));
		}
		if (action instanceof SendsMessage) {
			((SendsMessage) action).setMessageSender(new MessageSender().asBot(bot));
		}
		if (action instanceof UserAware) {
			final User user = message.getFrom();
			((UserAware) action).setUser(user);
		}
		if (action instanceof InhabitantAware) {
			final User user = message.getFrom();
			((InhabitantAware) action).setInhabitant(castle.getInhabitantFor(user));
		}
		if (action instanceof BotAware) {
			((BotAware) action).setBot(bot);
		}
	}

	private MessageReply getMessageReply(Message message) {
		return new MessageReply().asBot(bot).toMessage(message);
	}

}
