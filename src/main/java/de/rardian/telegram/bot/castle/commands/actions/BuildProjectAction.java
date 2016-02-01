package de.rardian.telegram.bot.castle.commands.actions;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.castle.facilities.CastleFacility;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.command.action.Action;
import de.rardian.telegram.bot.command.action.SendsAnswer;
import de.rardian.telegram.bot.communication.MessageReply;

public class BuildProjectAction implements Action, CastleAware, SendsAnswer, InhabitantAware {

	private Castle castle;
	private String projectName;
	private MessageReply reply;
	private Inhabitant inhabitant;

	public BuildProjectAction setProjectName(String project) {
		projectName = project;
		return this;
	}

	@Override
	public void setMessageReply(MessageReply reply) {
		this.reply = reply;
	}

	@Override
	public void setCastle(Castle castle) {
		this.castle = castle;
	}

	@Override
	public void setInhabitant(Inhabitant inhabitant) {
		this.inhabitant = inhabitant;
	}

	@Override
	public void execute() {
		final boolean projectNameEmpty = StringUtils.isBlank(projectName);
		final boolean projectActive = castle.isProjectInProgress();

		if (projectNameEmpty) {

			if (projectActive) {
				try {
					castle.addWorkerFor(CastleFacility.CATEGORY.BUILDING, inhabitant);
					reply.answer("Du wirst Teil des Bauprojekts.");
				} catch (AlreadyAddedException e) {
					reply.answer("Du bist bereits Teil des Bauprojekts.");
				}
			} else {
				Collection<String> projectIds = castle.getProjectIds();

				reply.answer("Kein Bauprojekt aktiv. Starte ein Projekt mit /bau <" + StringUtils.join(projectIds, "|") + ">");
			}

		} else if (castle.isProjectValid(projectName)) {

			if (projectActive) {
				reply.answer("Es läuft bereits ein Bauprojekt. Nimm daran teil mit: /bau");
			} else {
				castle.startProject(projectName);
				reply.answer("Du startest das Bauprojekt " + castle.getProjectName(projectName));
			}

		} else {
			reply.answer("Bitte wähle ein gültiges Bauprojekt aus.");
		}

	}

}
