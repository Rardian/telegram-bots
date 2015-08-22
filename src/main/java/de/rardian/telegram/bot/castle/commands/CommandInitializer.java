package de.rardian.telegram.bot.castle.commands;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.rardian.telegram.bot.castle.commands.actions.CastleAware;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.command.Action;
import de.rardian.telegram.bot.command.BotAware;
import de.rardian.telegram.bot.command.Command;
import de.rardian.telegram.bot.command.MessageReply;
import de.rardian.telegram.bot.command.SendsAnswer;
import de.rardian.telegram.bot.command.UserAware;
import de.rardian.telegram.bot.manage.Message;
import de.rardian.telegram.bot.model.Bot;

public class CommandInitializer implements CastleAware, BotAware {

	private Bot bot;
	private Castle castle;
	private HashMap<String, Command> commands = new HashMap<>();

	public CommandInitializer() {
		initCommands();
	}

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

	public Map<String, Command> getCommandSet() {
		return commands;
	}

	private void initCommands() {
		Collection<Command> commandsToInitialize = Arrays.asList(//
				new CastleStatusCommand(), //
				new InhabitantProduceCommand(), //
				new HelpCommand());

		for (Command command : commandsToInitialize) {
			for (String commandString : command.getCommandStrings()) {
				commands.put(commandString, command);
			}
		}
	}

	private MessageReply getMessageReply(Message message) {
		return new MessageReply().asBot(bot).toMessage(message);
	}

}
