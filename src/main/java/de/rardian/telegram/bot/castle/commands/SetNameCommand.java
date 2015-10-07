package de.rardian.telegram.bot.castle.commands;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import de.rardian.telegram.bot.castle.commands.actions.CastleStatusAction;
import de.rardian.telegram.bot.castle.commands.actions.SetInhabitantNameAction;
import de.rardian.telegram.bot.command.Command;
import de.rardian.telegram.bot.command.MessageAware;
import de.rardian.telegram.bot.command.action.Action;
import de.rardian.telegram.bot.command.action.SendMessageToUserAction;
import de.rardian.telegram.bot.model.Message;

public class SetNameCommand implements Command, MessageAware {

	private Message message;

	@Override
	public Collection<String> getCommandStrings() {
		return Arrays.asList("/name");
	}

	@Override
	public String getDescription() {
		return "Gib deinem Bewohner einen Namen";
	}

	@Override
	public void setMessage(Message message) {
		this.message = message;
	}

	@Override
	public Collection<Action> executeWithParams(String params) {
		if (StringUtils.isBlank(params)) {
			return Arrays.asList(new SendMessageToUserAction(message.getFrom(), "Bitte verwende /name <name> um deinen Bewohner zu benennen."));
		} else {
			return Arrays.asList(new SetInhabitantNameAction(params), new CastleStatusAction());
		}
	}

}
