package de.rardian.telegram.bot.castle.commands.actions;

import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.command.Action;
import de.rardian.telegram.bot.command.UserAware;
import de.rardian.telegram.bot.model.User;

public class MoveInhabitantToProductionAction implements Action, CastleAware, UserAware {
	private Castle castle;
	private User user;

	@Override
	public void setCastle(Castle castle) {
		this.castle = castle;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void execute() {
		castle.addProducer(user);
	}
}
