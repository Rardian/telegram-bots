package de.rardian.telegram.bot.castle;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import de.rardian.telegram.bot.castle.commands.CommandInitializer;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.command.Action;
import de.rardian.telegram.bot.command.Command;
import de.rardian.telegram.bot.command.CommandParser;
import de.rardian.telegram.bot.manage.Message;
import de.rardian.telegram.bot.manage.UserManager;
import de.rardian.telegram.bot.model.Bot;

public class CastleBot implements Bot {

	private static final String ID = "123030600:AAHn8CC4Q7PMvvdEGOiqmFYCZVcgHam_8uo";
	private UserManager userManager;
	private CommandParser commandParser;
	private CommandInitializer commandInitializer;
	private Castle castle = new Castle();

	public void setUserManager(UserManager manager) {
		this.userManager = manager;
	}

	public void setCommandParser(CommandParser parser) {
		commandParser = parser;
	}

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public String getCommandOverview() {
		StringBuffer helpText = new StringBuffer(
				"Willkommen bei CastleBot. Werde Teil einer wachsenden und florierenden Burggemeinschaft. Folgende Kommandos stehen dir zur Verfügung.\n");
		Collection<Command> commandList = getCommandInitializer().getCommandSet().values();

		Set<Command> commandSet = new TreeSet<>(new Comparator<Command>() {

			@Override
			public int compare(Command o1, Command o2) {
				return o1.getCommandStrings().toString().compareTo(o2.getCommandStrings().toString());
			}
		});
		commandSet.addAll(commandList);

		for (Command command : commandSet) {
			helpText.append(StringUtils.join(command.getCommandStrings(), ", "));
			helpText.append(": ");
			helpText.append(command.getDescription());
			helpText.append("\n");
		}

		return helpText.toString();
	}

	public void processMessage(Message message) {
		Collection<Action> actions = getCommandParser().parse(message);

		for (Action action : actions) {
			getCommandInitializer().injectActionDependencies(action, message);
			action.execute();
		}

		// User user = message.getFrom();

		// ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
		// keyboard.addButtonRow("A", "B");

		// if (getUserManager().isUserKnown(user)) {
		// System.out.println("User '" + user.getUserName() + "' is known");
		// getMessageReply(message).answer("Willkommen zur�ck, " +
		// user.getFirstName() + "!", null);
		// } else {
		// System.out.println("User '" + user.getUserName() + "' is new");
		// getUserManager().registerUser(user);
		// getMessageReply(message).answer("Herzlich Willkommen, " +
		// user.getFirstName() + ", sch�n dich kennenzulernen!", null);
		// }
	}

	private CommandInitializer getCommandInitializer() {
		if (commandInitializer == null) {
			commandInitializer = new CommandInitializer();
			commandInitializer.setBot(this);
			commandInitializer.setCastle(castle);
		}
		return commandInitializer;
	}

	private UserManager getUserManager() {
		if (userManager == null) {
			userManager = new UserManager();
		}
		return userManager;
	}

	private CommandParser getCommandParser() {
		if (commandParser == null) {
			commandParser = new CommandParser().withCommands(getCommandInitializer().getCommandSet());
		}
		return commandParser;
	}

}
