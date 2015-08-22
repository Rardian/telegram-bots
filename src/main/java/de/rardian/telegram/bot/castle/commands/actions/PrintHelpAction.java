package de.rardian.telegram.bot.castle.commands.actions;

import de.rardian.telegram.bot.command.Action;
import de.rardian.telegram.bot.command.BotAware;
import de.rardian.telegram.bot.command.MessageReply;
import de.rardian.telegram.bot.command.SendsAnswer;
import de.rardian.telegram.bot.model.Bot;

public class PrintHelpAction implements Action, SendsAnswer, BotAware {

	private MessageReply reply;
	private Bot bot;

	@Override
	public void setMessageReply(MessageReply reply) {
		this.reply = reply;
	}

	@Override
	public void setBot(Bot bot) {
		this.bot = bot;
	}

	@Override
	public void execute() {
		reply.answer(bot.getCommandOverview(), null);
	}

}
