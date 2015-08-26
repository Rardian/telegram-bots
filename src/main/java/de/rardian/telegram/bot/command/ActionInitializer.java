package de.rardian.telegram.bot.command;

import de.rardian.telegram.bot.castle.commands.actions.CastleAware;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.manage.Message;
import de.rardian.telegram.bot.model.Bot;

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
		if (action instanceof UserAware) {
			((UserAware) action).setUser(message.getFrom());
		}
		if (action instanceof BotAware) {
			((BotAware) action).setBot(bot);
		}
	}

	private MessageReply getMessageReply(Message message) {
		return new MessageReply().asBot(bot).toMessage(message);
	}

}
