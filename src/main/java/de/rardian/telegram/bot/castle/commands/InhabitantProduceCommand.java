package de.rardian.telegram.bot.castle.commands;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import de.rardian.telegram.bot.castle.commands.actions.CastleStatusAction;
import de.rardian.telegram.bot.castle.commands.actions.SetInhabitantToWorkAction;
import de.rardian.telegram.bot.castle.facilities.CastleFacility;
import de.rardian.telegram.bot.command.Command;
import de.rardian.telegram.bot.command.MessageAware;
import de.rardian.telegram.bot.command.action.Action;
import de.rardian.telegram.bot.command.action.SendMessageToUserAction;
import de.rardian.telegram.bot.model.Message;

public class InhabitantProduceCommand implements Command, MessageAware {

	private Message message;
	private final Map<String, CastleFacility.CATEGORY> resourceMapping;

	public InhabitantProduceCommand() {
		resourceMapping = new HashMap<>();
		resourceMapping.put("holz", CastleFacility.CATEGORY.WOODCUTTING);
		resourceMapping.put("stein", CastleFacility.CATEGORY.QUARRYING);
		resourceMapping.put("eisen", CastleFacility.CATEGORY.MINING);
	}

	@Override
	public void setMessage(Message message) {
		this.message = message;
	}

	@Override
	public Collection<String> getCommandStrings() {
		return Arrays.asList("/prod");
	}

	@Override
	public String getDescription() {
		return "Produziere Güter für die Burg";
	}

	@Override
	public Collection<Action> executeWithParams(String params) {
		String strippedParam = StringUtils.strip(params);

		if (StringUtils.isBlank(params) || !resourceMapping.keySet().contains(strippedParam)) {
			String resourcesToChoseFrom = StringUtils.join(resourceMapping.keySet(), "|");

			return Arrays.asList( //
					new SendMessageToUserAction(message.getFrom(), //
							"Bitte verwende /prod <" + resourcesToChoseFrom + "> um Güter zu produzieren."));
		}

		return Arrays.asList(new SetInhabitantToWorkAction(resourceMapping.get(strippedParam)), new CastleStatusAction());
	}

}
