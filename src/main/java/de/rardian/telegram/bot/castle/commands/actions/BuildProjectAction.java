package de.rardian.telegram.bot.castle.commands.actions;

import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.command.action.Action;
import de.rardian.telegram.bot.command.action.SendsMessage;
import de.rardian.telegram.bot.communication.MessageSender;

public class BuildProjectAction implements Action, CastleAware, SendsMessage {

	private MessageSender sender;
	private Castle castle;
	private String projectAsString;

	public BuildProjectAction(String project) {
		projectAsString = project;
	}

	@Override
	public void setMessageSender(MessageSender sender) {
		this.sender = sender;
	}

	@Override
	public void setCastle(Castle castle) {
		this.castle = castle;
	}

	@Override
	public void execute() {
		// A) project empty && kein Bauprojekt: Starte ein neues Bauprojekt mit /bau <projekt1|projekt2>
		// B) project empty && Bauprojekt gestartet: Du wirst Teil des Bauprojekts
		// C) project valid && kein Bauprojekt: Du startest ein Bauprojekt.
		// D) project valid && Bauprojekt gestartet: Es läuft bereits ein Bauprojekt. Bitte mit /bau teilnehmen
		// E) project invalid: Projekt ungültig 

	}

}
