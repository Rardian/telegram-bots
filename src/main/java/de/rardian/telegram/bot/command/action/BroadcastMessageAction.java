package de.rardian.telegram.bot.command.action;

import java.util.Collection;

import de.rardian.telegram.bot.castle.commands.actions.CastleAware;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.communication.MessageSender;
import de.rardian.telegram.bot.model.User;

public class BroadcastMessageAction extends SendMessageToUserAction implements SendsMessage, CastleAware {

	private MessageSender sender;
	private Collection<Inhabitant> otherMembers;
	private String othersMessage;
	private Castle castle;

	@Override
	public void setCastle(Castle castle) {
		this.castle = castle;
	}

	public BroadcastMessageAction(User trigger, String triggerMessage, Collection<Inhabitant> otherMembers, String othersMessage) {
		super(trigger, triggerMessage);
		this.otherMembers = otherMembers;
		this.othersMessage = othersMessage;
	}

	@Override
	public void execute() {
		super.execute();

		for (Inhabitant inhabitant : otherMembers) {
			User otherMembersUser = castle.getUserBy(inhabitant);
			sender.sendMessage(otherMembersUser, othersMessage);
		}
	}

}
