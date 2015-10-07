package de.rardian.telegram.bot.castle.commands.actions;

import java.util.Collection;

import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.command.action.ResultAction;
import de.rardian.telegram.bot.model.User;

public class SetInhabitantsIdle extends ResultAction implements CastleAware {

	private Collection<Inhabitant> idleInhabitants;
	private Castle castle;

	public SetInhabitantsIdle(User user, Collection<Inhabitant> idleInhabitants) {
		super(user);
		this.idleInhabitants = idleInhabitants;
	}

	@Override
	public void setCastle(Castle castle) {
		this.castle = castle;
	}

	@Override
	public void execute() {
		for (Inhabitant inhabitant : idleInhabitants) {
			castle.setInhabitantIdle(inhabitant);
		}
	}

}
