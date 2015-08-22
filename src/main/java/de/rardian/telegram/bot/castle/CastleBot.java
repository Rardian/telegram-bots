package de.rardian.telegram.bot.castle;

import java.util.Collection;
import java.util.HashMap;

import de.rardian.telegram.bot.castle.commands.CastleStatusCommand;
import de.rardian.telegram.bot.castle.commands.InhabitantProduceCommand;
import de.rardian.telegram.bot.castle.commands.actions.CastleAware;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.command.Action;
import de.rardian.telegram.bot.command.Command;
import de.rardian.telegram.bot.command.CommandParser;
import de.rardian.telegram.bot.command.MessageReply;
import de.rardian.telegram.bot.command.SendsAnswer;
import de.rardian.telegram.bot.command.UserAware;
import de.rardian.telegram.bot.manage.Message;
import de.rardian.telegram.bot.manage.UserManager;
import de.rardian.telegram.bot.model.Bot;

public class CastleBot implements Bot {

	private static final String ID = "123030600:AAHn8CC4Q7PMvvdEGOiqmFYCZVcgHam_8uo";
	private UserManager userManager;
	private MessageReply reply;
	private CommandParser commandParser;
	private Castle castle = new Castle();

	public void setUserManager(UserManager manager) {
		this.userManager = manager;
	}

	public void setMessageReply(MessageReply mr) {
		reply = mr;
	}

	public void setCommandParser(CommandParser parser) {
		commandParser = parser;
	}

	@Override
	public String getId() {
		return ID;
	}

	public void processMessage(Message message) {

		// User user = message.getFrom();

		// ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
		// keyboard.addButtonRow("A", "B");

		Collection<Action> actions = getCommandParser().parse(message);
		for (Action action : actions) {
			injectDependencies(action, message);
			action.execute();
		}

		// if (getUserManager().isUserKnown(user)) {
		// System.out.println("User '" + user.getUserName() + "' is known");
		// getMessageReply(message).answer("Willkommen zurück, " +
		// user.getFirstName() + "!", null);
		// } else {
		// System.out.println("User '" + user.getUserName() + "' is new");
		// getUserManager().registerUser(user);
		// getMessageReply(message).answer("Herzlich Willkommen, " +
		// user.getFirstName() + ", schön dich kennenzulernen!", null);
		// }
	}

	private void injectDependencies(Action action, Message message) {
		if (action instanceof CastleAware) {
			((CastleAware) action).setCastle(castle);
		}
		if (action instanceof SendsAnswer) {
			((SendsAnswer) action).setMessageReply(getMessageReply(message));
		}
		if (action instanceof UserAware) {
			((UserAware) action).setUser(message.getFrom());
		}
	}

	private MessageReply getMessageReply(Message message) {
		return new MessageReply().asBot(this).toMessage(message);
	}

	private UserManager getUserManager() {
		if (userManager == null) {
			userManager = new UserManager();
		}
		return userManager;
	}

	private CommandParser getCommandParser() {
		if (commandParser == null) {
			commandParser = new CommandParser().withCommands(initCommands());
		}
		return commandParser;
	}

	private HashMap<String, Command> initCommands() {
		HashMap<String, Command> commands = new HashMap<>();

		commands.put("stat", new CastleStatusCommand());
		commands.put("prod", new InhabitantProduceCommand());

		return commands;
	}
}
