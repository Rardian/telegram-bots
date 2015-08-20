package de.rardian.telegram.bot.rardian;

import java.util.Collection;
import java.util.HashMap;

import de.rardian.telegram.bot.command.Action;
import de.rardian.telegram.bot.command.Command;
import de.rardian.telegram.bot.command.CommandParser;
import de.rardian.telegram.bot.command.MessageReply;
import de.rardian.telegram.bot.manage.Message;
import de.rardian.telegram.bot.manage.UserManager;
import de.rardian.telegram.bot.model.Bot;
import de.rardian.telegram.bot.model.User;
import de.rardian.telegram.bot.rardian.commands.Test1Command;
import de.rardian.telegram.bot.rardian.commands.Test2Command;

public class RardianBot implements Bot {

	private static final String ID = "123030600:AAHn8CC4Q7PMvvdEGOiqmFYCZVcgHam_8uo";
	private UserManager userManager;
	private MessageReply reply;
	private CommandParser commandParser;

	public void setUserManager(UserManager manager) {
		this.userManager = manager;
	}

	public void setMessageReply(MessageReply mr) {
		reply = mr;
	}

	@Override
	public String getId() {
		return ID;
	}

	public void processMessage(Message message) {

		User user = message.getFrom();

		//		ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
		//		keyboard.addButtonRow("A", "B");

		Collection<Action> actions = getCommandParser().parse(message);
		for (Action action : actions) {
			action.execute();
		}

		if (getUserManager().isUserKnown(user)) {
			System.out.println("User '" + user.getUserName() + "' is known");
			getMessageReply(message).answer("Willkommen zurück, " + user.getFirstName() + "!", null);
		} else {
			System.out.println("User '" + user.getUserName() + "' is new");
			getUserManager().registerUser(user);
			getMessageReply(message).answer("Herzlich Willkommen, " + user.getFirstName() + ", schön dich kennenzulernen!", null);
		}
	}

	private MessageReply getMessageReply(Message message) {
		if (reply == null) {
			reply = new MessageReply().asBot(this).toMessage(message);
		}
		return reply;
	}

	private UserManager getUserManager() {
		if (userManager == null) {
			userManager = new UserManager();
		}
		return userManager;
	}

	private CommandParser getCommandParser() {
		if (commandParser == null) {
			HashMap<String, Command> commands = new HashMap<String, Command>();

			commands.put("test1", new Test1Command());
			commands.put("test2", new Test2Command());

			commandParser = new CommandParser().withCommands(commands);
		}
		return commandParser;
	}
}
