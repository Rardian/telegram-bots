package de.rardian.telegram.bot.castle.commands.actions;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import de.rardian.telegram.bot.castle.exception.AlreadyAddedException;
import de.rardian.telegram.bot.castle.facilities.CastleFacility;
import de.rardian.telegram.bot.castle.facilities.ProjectManager;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.command.action.Action;
import de.rardian.telegram.bot.command.action.ProjectManagerAware;
import de.rardian.telegram.bot.command.action.SendsAnswer;
import de.rardian.telegram.bot.communication.MessageReply;

public class BuildProjectAction implements Action, CastleAware, SendsAnswer, InhabitantAware, ProjectManagerAware {

	private Castle castle;
	private String projectId;
	private MessageReply reply;
	private Inhabitant inhabitant;
	private ProjectManager projectManager;

	public BuildProjectAction setProjectId(String project) {
		projectId = project;
		return this;
	}

	@Override
	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
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
		final boolean projectNameEmpty = StringUtils.isBlank(projectId);
		final boolean projectActive = projectManager.projectInProgress();

		if (projectNameEmpty) {

			if (projectActive) {
				try {
					castle.addWorkerFor(CastleFacility.CATEGORY.BUILDING, inhabitant);
					reply.answer("Du wirst Teil des Bauprojekts.");
				} catch (AlreadyAddedException e) {
					reply.answer("Du bist bereits Teil des Bauprojekts.");
				}
			} else {
				// XXX Auf ProjectManager umbiegen
				Collection<String> projectIds = castle.getProjectIds();

				reply.answer("Kein Bauprojekt aktiv. Starte ein Projekt mit /bau <" + StringUtils.join(projectIds, "|") + ">");
			}

		} else if (castle.isProjectValid(projectId)) {

			if (projectActive) {
				reply.answer("Es läuft bereits ein Bauprojekt. Nimm daran teil mit: /bau");
			} else {
				projectManager.startProject();
				reply.answer("Du startest das Bauprojekt " + projectManager.getProjectName());
			}

		} else {
			reply.answer("Bitte wähle ein gültiges Bauprojekt aus.");
		}

	}

}
