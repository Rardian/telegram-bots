package de.rardian.telegram.bot.castle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import de.rardian.telegram.bot.castle.commands.CommandInitializer;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.command.Command;
import de.rardian.telegram.bot.command.CommandParser;
import de.rardian.telegram.bot.command.action.Action;
import de.rardian.telegram.bot.command.action.ActionExecuter;
import de.rardian.telegram.bot.command.action.ActionInitializer;
import de.rardian.telegram.bot.command.action.ResultAction;
import de.rardian.telegram.bot.manage.UserManager;
import de.rardian.telegram.bot.model.Bot;
import de.rardian.telegram.bot.model.Message;
import de.rardian.telegram.bot.model.User;

@Component
public class CastleBot implements Bot {

	private static final String ID = "123030600:AAHn8CC4Q7PMvvdEGOiqmFYCZVcgHam_8uo";
	private UserManager userManager;
	private CommandParser commandParser;
	private CommandInitializer commandInitializer;
	private Castle castle;
	private ActionExecuter actionExecuter;

	private @Autowired AutowireCapableBeanFactory beanFactory;

	public void setUserManager(UserManager manager) {
		this.userManager = manager;
	}

	public void setCommandParser(CommandParser parser) {
		commandParser = parser;
	}

	public void setActionExecuter(ActionExecuter actionExecuter) {
		this.actionExecuter = actionExecuter;
	}

	public void setCastle(Castle castle) {
		this.castle = castle;
	}

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public void executeResultActions(Collection<ResultAction> actions) {
		//		System.out.println("ResultActions executed");
		for (ResultAction action : actions) {
			getActionExecuter().execute(action);
		}
	}

	@Override
	public String getCommandOverview() {
		StringBuffer helpText = new StringBuffer("Folgende Kommandos stehen dir zur Verfügung.\n");
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
			helpText.append(" - ");
			helpText.append(command.getDescription());
			helpText.append("\n");
		}
		helpText.append("Bitte hinterlasse Feedback auf: https://github.com/Rardian/telegram-bots/issues");

		return helpText.toString();
	}

	public void processMessage(Message message) {
		Collection<Action> actions = new ArrayList<>();
		User user = message.getFrom();

		Action resultAction = getUserManager().registerUser(user);
		actions.add(resultAction);
//		if (!getUserManager().isUserKnown(user)) {
//			getUserManager().registerUser(user);
//			actions.add(new UserMovesInAction());
//		}

		actions.addAll(getCommandParser().parse(message));

		for (Action action : actions) {
			getActionExecuter().execute(action, message);
		}
	}

	private ActionExecuter getActionExecuter() {
		if (actionExecuter == null) {
			ActionInitializer initializer = new ActionInitializer();
			beanFactory.autowireBean(initializer);
			initializer.setBot(this);
			initializer.setCastle(getCastle());
			actionExecuter = new ActionExecuter().withInitializer(initializer);
		}
		return actionExecuter;
	}

	private CommandInitializer getCommandInitializer() {
		if (commandInitializer == null) {
			commandInitializer = new CommandInitializer(getCastle());
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

	private Castle getCastle() {
		if (castle == null) {
			castle = new Castle(this, null);
			//			castle.setProductionListener();
		}
		return castle;
	}
}
