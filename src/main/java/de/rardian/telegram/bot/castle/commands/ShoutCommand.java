package de.rardian.telegram.bot.castle.commands;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.command.Command;
import de.rardian.telegram.bot.command.MessageAware;
import de.rardian.telegram.bot.command.action.Action;
import de.rardian.telegram.bot.command.action.BroadcastMessageAction;
import de.rardian.telegram.bot.command.action.SendMessageToUserAction;
import de.rardian.telegram.bot.model.Message;
import de.rardian.telegram.bot.model.User;

public class ShoutCommand implements Command, MessageAware {

	private Message message;
	private Castle castle;

	public ShoutCommand(Castle castle) {
		this.castle = castle;
	}

	@Override
	public void setMessage(Message message) {
		this.message = message;
	}

	@Override
	public Collection<Action> executeWithParams(String params) {
		User trigger = message.getFrom();
		Inhabitant triggersInhabitant = castle.getInhabitantFor(trigger);

		// FIXME Command darf castle nicht abfragen, bevor der Nutzer eingezogen ist. (ResultAction)
		/**
		 * Exception in thread "pool-1-thread-1" java.lang.NullPointerException at de.rardian.telegram.bot.castle.commands.ShoutCommand.
		 * executeWithParams(ShoutCommand.java:46) at de.rardian.telegram.bot.command.CommandParser.parse(CommandParser. java:44) at
		 * de.rardian.telegram.bot.castle.CastleBot.processMessage(CastleBot. java:97) at
		 * de.rardian.telegram.bot.manage.UpdatesRetriever.run(UpdatesRetriever. java:75) at
		 * java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor. java:1142) at
		 * java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor .java:617) at java.lang.Thread.run(Thread.java:745)
		 */
		if (StringUtils.isBlank(params)) {
			return Arrays.asList( //
					new SendMessageToUserAction(message.getFrom(), //
							"Bitte verwende /shout <nachricht> um eine Nachricht zu verschicken."));
		} else if (triggersInhabitant == null) {
			return Arrays.asList( //
					new SendMessageToUserAction(message.getFrom(), //
							"Das shout-Kommando kann erst verwendet werden, nachdem der Registrierungsprozess abgeschlossen ist."));
		} else {
			String triggerMessage = "Nachricht an alle versendet.";

			String messageToOthers = triggersInhabitant.getName() + " teilt dir mit: " + params;

			Collection<Inhabitant> otherMembers = CollectionUtils.disjunction(//
					castle.getInhabitants(), Arrays.asList());

			return Arrays.asList(new BroadcastMessageAction(trigger, triggerMessage, otherMembers, messageToOthers));
		}
	}

	@Override
	public Collection<String> getCommandStrings() {
		return Arrays.asList("/shout");
	}

	@Override
	public String getDescription() {
		return "Sende eine Nachricht an alle Spieler";
	}

}
